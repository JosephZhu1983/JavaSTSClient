package com.kongzhonghd.sts.context;

import com.kongzhonghd.sts.common.AbstractSTSMessage;
import com.kongzhonghd.sts.exception.STSMessageException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 5:06 PM
 * 分段消息的上下文
 */
public class PartialMessageContext
{
    public static final String IOSESSION_PARTIAL_MESSAGE_CONTEXT_KEY = "PARTIAL_MESSAGE_CONTEXT";
    private static final Log log = LogFactory.getLog(PartialMessageContext.class);
    private Thread clearTimeoutMessageThread;
    private ConcurrentHashMap<Integer, PartialMessage> partialMessageList = new ConcurrentHashMap<Integer, PartialMessage>();
    private boolean enable = true;

    public PartialMessageContext()
    {
        clearTimeoutMessageThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (enable)
                {
                    Map<Integer, PartialMessage> partialMessages = new HashMap<Integer, PartialMessage>();
                    for (Map.Entry<Integer, PartialMessage> item : partialMessageList.entrySet())
                        partialMessages.put(item.getKey(), item.getValue());

                    for (Map.Entry<Integer, PartialMessage> item : partialMessages.entrySet())
                    {
                        if (item.getValue().isTimeout())
                        {
                            if (log.isInfoEnabled())
                                log.info("清理了一个经过" + PartialMessage.INCOMPLETE_PARTIAL_MESSAGE_TIMEOUT_SECONDS + "秒还没有收到完整消息的PartialMessage：" + item.getValue());
                            partialMessageList.remove(item.getKey());
                        }
                    }
                }
            }
        });
        clearTimeoutMessageThread.start();
        log.debug("启动清理过期片段消息的线程");
    }

    public synchronized static PartialMessageContext getContext(IoSession session)
    {
        PartialMessageContext context = (PartialMessageContext) session.getAttribute(IOSESSION_PARTIAL_MESSAGE_CONTEXT_KEY);
        if (context == null)
        {
            context = new PartialMessageContext();
            session.setAttribute(IOSESSION_PARTIAL_MESSAGE_CONTEXT_KEY, context);
        }
        return context;
    }

    public synchronized static void removeContext(IoSession session)
    {
        getContext(session).enable = false;
        session.removeAttribute(IOSESSION_PARTIAL_MESSAGE_CONTEXT_KEY);
    }

    public AbstractSTSMessage addFragmentedMessage(AbstractSTSMessage message) throws UnsupportedEncodingException
    {
        int id = message.getMessageId();
        partialMessageList.putIfAbsent(id, new PartialMessage());
        PartialMessage partialMessage = partialMessageList.get(id);

        synchronized (partialMessage)
        {
            try
            {
                partialMessage.addMessage(message);
            }
            catch (STSMessageException ex)
            {
                if (log.isWarnEnabled())
                    log.warn("增加片段消息出现异常：" + ex);
            }

            AbstractSTSMessage completeMessage = partialMessage.getCompleteMessage();
            if (completeMessage != null)
            {
                partialMessageList.remove(id);
                return completeMessage;
            }
        }
        return null;
    }
}