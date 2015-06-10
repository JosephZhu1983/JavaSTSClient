package com.kongzhonghd.sts.context;

import com.kongzhonghd.sts.common.AbstractSTSMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-31
 * Time: PM8:36
 * To change this template use File | Settings | File Templates.
 * 消息解码器上下文，用于解析到一半的消息
 */
public class MessageDecoderContext
{
    public static final int STATE_START = 0;
    public static final int STATE_STARTLINE_READ = 1;
    public static final int STATE_HEADERS_READ = 2;
    public static final int STATE_CONTENT_READ = 3;
    private static final String IOSESSION_MESSAGE_DECODER_CONTEXT_KEY = "IOSESSION_MESSAGE_DECODER_CONTEXT";
    private static final Log log = LogFactory.getLog(MessageDecoderContext.class);
    private int state = STATE_START;

    private AbstractSTSMessage tempMessage;
    private AtomicInteger headerReadTimes = new AtomicInteger();
    private AtomicInteger bodyReadTimes = new AtomicInteger();
    private AtomicInteger startLineReadTimes = new AtomicInteger();

    public static MessageDecoderContext getContext(IoSession session)
    {
        MessageDecoderContext context = (MessageDecoderContext) session.getAttribute(IOSESSION_MESSAGE_DECODER_CONTEXT_KEY);
        if (context == null)
        {
            context = new MessageDecoderContext();
            session.setAttribute(IOSESSION_MESSAGE_DECODER_CONTEXT_KEY, context);
        }
        return context;
    }

    public void resetTimes()
    {
        headerReadTimes.set(0);
        startLineReadTimes.set(0);
        bodyReadTimes.set(0);
    }

    public void increaseHeaderReadTimers()
    {
        headerReadTimes.incrementAndGet();
    }

    public void increaseStartLineReadTimers()
    {
        startLineReadTimes.incrementAndGet();
    }

    public void increaseBodyReadTimers()
    {
        bodyReadTimes.incrementAndGet();
    }

    public int getStartLineReadTimes()
    {
        return startLineReadTimes.get();
    }

    public int getBodyReadTimes()
    {
        return bodyReadTimes.get();
    }

    public int getHeaderReadTimes()
    {
        return headerReadTimes.get();
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public AbstractSTSMessage getTempMessage()
    {
        return tempMessage;
    }

    public void setTempMessage(AbstractSTSMessage tempMessage)
    {
        this.tempMessage = tempMessage;
    }
}
