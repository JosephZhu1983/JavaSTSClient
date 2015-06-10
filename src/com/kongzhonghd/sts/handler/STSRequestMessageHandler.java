package com.kongzhonghd.sts.handler;

import com.kongzhonghd.sts.business.*;
import com.kongzhonghd.sts.business.Error;
import com.kongzhonghd.sts.common.*;
import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.sts.exception.STSMessageException;
import com.kongzhonghd.sts.statistics.*;
import com.kongzhonghd.util.RequestAndResponseTrigger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

import java.util.*;
import java.util.concurrent.*;

/**
 * User: apple
 * Date: 13-8-16
 * Time: PM10:26
 */
public class STSRequestMessageHandler implements STSMessageHandler<STSRequestMessage>, GetThreadPoolStatisticsCallback
{
    private static final Log log = LogFactory.getLog(STSRequestMessageHandler.class);
    private final Map<String, AbstractRequestHandler> handlers = new ConcurrentHashMap<String, AbstractRequestHandler>();
    private ThreadPoolExecutor messageHandlerThreadPool;
    private ThreadPoolExecutor taskExecutorThreadPool;
    private ReportApiStatisticsCallback reportApiStatisticsCallback;
    private int taskExecutionTimeoutSeconds;

    public STSRequestMessageHandler(int taskExecutionTimeoutSeconds, int messageHandlerThreadPoolSize, int taskExecutorThreadPoolSize, ReportApiStatisticsCallback reportApiStatisticsCallback)
    {
        if (taskExecutionTimeoutSeconds <= 0)
            throw new IllegalArgumentException("不正确的taskExecutionTimeoutSeconds");

        if (messageHandlerThreadPoolSize <= 0)
            throw new IllegalArgumentException("不正确的messageHandlerThreadPoolSize");

        if (taskExecutorThreadPoolSize <= 0)
            throw new IllegalArgumentException("不正确的taskExecutorThreadPoolSize");

        if (reportApiStatisticsCallback == null)
            throw new IllegalArgumentException("reportApiStatisticsCallback为空");

        this.taskExecutionTimeoutSeconds = taskExecutionTimeoutSeconds;
        this.taskExecutorThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(taskExecutorThreadPoolSize);
        this.messageHandlerThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(messageHandlerThreadPoolSize);
        this.reportApiStatisticsCallback = reportApiStatisticsCallback;
    }

    @Override
    public List<ThreadPoolStatistics> getThreadPoolStatistics()
    {
        return new ArrayList<ThreadPoolStatistics>(Arrays.asList(
                new ThreadPoolStatistics("messageHandlerThreadPool", messageHandlerThreadPool),
                new ThreadPoolStatistics("taskExecutorThreadPool", taskExecutorThreadPool)));
    }

    public void registerRequestHandler(AbstractRequestHandler handler)
    {
        handlers.put(handler.getRequest().getUri(), handler);
    }

    public void clearHandlers()
    {
        handlers.clear();
    }

    @SuppressWarnings("unchecked")
    private void internalHandle(final AbstractRequestHandler handler, final RequestAndResponseTrigger trigger)
    {
        taskExecutorThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    handler.handle(trigger);
                }
                catch (STSException ex)
                {
                    if (log.isWarnEnabled())
                        log.warn("处理请求消息发生错误，错误信息为：" + ex.toString());
                }
            }
        });
    }

    @Override
    public void handle(final IoSession session, final STSRequestMessage message) throws STSException
    {
        final int messageId = message.getMessageId();

        final String uri = message.getStartLine().getRequestUri();
        if (uri == null || uri.equals(""))
            throw new STSMessageException(message, "无法从请求消息中获取请求地址");

        final AbstractRequestHandler handler = handlers.get(uri);
        if (handler == null)
            throw new STSMessageException(message, "无法根据收到的消息找到处理程序，请检查是否注册了合适的处理程序");

        final AbstractRequest request = handler.getRequest();
        if (request == null)
            throw new STSException("消息处理程序" + handler + "没有返回正确的请求类型");

        messageHandlerThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    request.parseFromRequestMessage(message);
                    reportApiStatisticsCallback.receiveSuccessRequest(uri);
                }
                catch (STSMessageException ex)
                {
                    reportApiStatisticsCallback.receiveErrorRequest(uri);
                    if (log.isWarnEnabled())
                        log.warn("解析请求消息发生错误，错误信息为：" + ex.toString());
                }

                final RequestAndResponseTrigger<AbstractRequest, AbstractResponse> trigger = new RequestAndResponseTrigger<AbstractRequest, AbstractResponse>();
                trigger.setRequest(request);
                AbstractResponse response = null;

                long time = System.currentTimeMillis();
                internalHandle(handler, trigger);

                try
                {
                    if (trigger.await(taskExecutionTimeoutSeconds, TimeUnit.SECONDS))
                        response = trigger.getResponse();
                    else
                        response = new TimeoutResponse("Reply message can not be generated in " + taskExecutionTimeoutSeconds + " seconds!");
                }
                catch (InterruptedException ex)
                {
                    if (log.isWarnEnabled())
                        log.warn("trigger.await发生了错误" + ex.toString());
                }

                if (response instanceof TimeoutResponse || response instanceof ErrorResponse)
                {
                    reportApiStatisticsCallback.sendErrorResponse(uri);
                }
                else
                {
                    reportApiStatisticsCallback.sendSuccessResponse(uri, (int) (System.currentTimeMillis() - time));
                }

                if (messageId > 0)
                {
                    STSResponseMessage responseMessage = null;

                    try
                    {
                        if (response != null)
                        {
                            responseMessage = response.buildToResponseMessage();
                            responseMessage.getHeader().setSubject(new STSResponseHeaderSubject(messageId));
                        }
                    }
                    catch (Exception ex)
                    {
                        if (log.isWarnEnabled())
                            log.warn("把响应解析为响应消息出错", ex);
                    }

                    if (responseMessage == null)
                    {
                        try
                        {
                            ErrorResponse errorResponse = new ErrorResponse(500, "ErrReply", new Error());
                            responseMessage = errorResponse.buildToResponseMessage();
                        }
                        catch (Exception ex)
                        {

                        }

                    }
                    session.write(responseMessage);
                }
                else
                {
                    if (log.isWarnEnabled())
                        log.warn("由于请求消息无需获得响应，所以丢弃了响应结果" + response);
                }
            }
        });
    }
}
