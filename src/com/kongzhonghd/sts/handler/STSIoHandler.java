package com.kongzhonghd.sts.handler;

import com.kongzhonghd.sts.business.AbstractRequestHandler;
import com.kongzhonghd.sts.common.STSRequestMessage;
import com.kongzhonghd.sts.common.STSResponseMessage;
import com.kongzhonghd.sts.config.ClientSetting;
import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.sts.statistics.ReportApiStatisticsCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 12:39 PM
 * STS消息到达后的处理程序
 */
public class STSIoHandler extends IoHandlerAdapter
{
    private static final Log log = LogFactory.getLog(STSIoHandler.class);

    public STSRequestMessageHandler getRequestMessageHandler()
    {
        return requestMessageHandler;
    }

    public STSResponseMessageHandler getResponseMessageHandler()
    {
        return responseMessageHandler;
    }

    private STSRequestMessageHandler requestMessageHandler;
    private STSResponseMessageHandler responseMessageHandler;

    public void registerRequestHandler(AbstractRequestHandler handler)
    {
        requestMessageHandler.registerRequestHandler(handler);
    }

    public void clearHandlers()
    {
        requestMessageHandler.clearHandlers();
    }

    public STSIoHandler(ClientSetting clientSetting, ReportApiStatisticsCallback reportApiStatisticsCallback)
    {
        if (clientSetting == null)
            throw new IllegalArgumentException("clientSetting为空！");

        if (reportApiStatisticsCallback == null)
            throw new IllegalArgumentException("reportApiStatisticsCallback为空！");

        this.requestMessageHandler = new STSRequestMessageHandler(
                clientSetting.getTaskExecutionTimeoutSeconds(),
                clientSetting.getMessageHandlerThreadPoolSize(),
                clientSetting.getTaskExecutorThreadPoolSize(),
                reportApiStatisticsCallback);

        this.responseMessageHandler = new STSResponseMessageHandler();
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws STSException
    {
        if (message instanceof STSRequestMessage)
        {
            try
            {
                requestMessageHandler.handle(session, (STSRequestMessage) message);
            }
            catch (STSException ex)
            {
                if (log.isWarnEnabled())
                    log.warn(requestMessageHandler + "在处理收到的消息的时候出现异常，异常内容为：" + ex.toString());
            }
        }
        else if (message instanceof STSResponseMessage)
        {
            try
            {
                responseMessageHandler.handle(session, (STSResponseMessage) message);
            }
            catch (STSException ex)
            {
                if (log.isWarnEnabled())
                    log.warn(responseMessageHandler + "在处理收到的消息的时候出现异常，异常内容为：" + ex.toString());
            }
        }
        else
        {
            if (log.isWarnEnabled())
                log.warn("收到的消息不是STSRequestMessage也不是STSResponseMessage");
        }
    }
}