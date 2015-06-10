package com.kongzhonghd.sts.context;

import com.kongzhonghd.sts.common.STSRequestMessage;
import com.kongzhonghd.sts.common.STSResponseMessage;
import com.kongzhonghd.util.RequestAndResponseTrigger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-31
 * Time: PM3:35
 * To change this template use File | Settings | File Templates.
 * 请求和响应的上下文
 */
public class RequestAndResponseContext
{
    public static final String IOSESSION_REQUEST_RESPONSE_CONTEXT_KEY = "REQUEST_RESPONSE_CONTEXT";
    private static final Log log = LogFactory.getLog(RequestAndResponseContext.class);
    private ConcurrentHashMap<Integer, RequestAndResponseTrigger<STSRequestMessage, STSResponseMessage>> triggers = new ConcurrentHashMap<Integer, RequestAndResponseTrigger<STSRequestMessage, STSResponseMessage>>();

    public synchronized static RequestAndResponseContext getContext(IoSession session)
    {
        RequestAndResponseContext context = (RequestAndResponseContext) session.getAttribute(IOSESSION_REQUEST_RESPONSE_CONTEXT_KEY);
        if (context == null)
        {
            context = new RequestAndResponseContext();
            session.setAttribute(IOSESSION_REQUEST_RESPONSE_CONTEXT_KEY, context);
        }
        return context;
    }

    public synchronized static void removeContext(IoSession session)
    {
        RequestAndResponseContext context = getContext(session);
        context.clearAndRelease();

        session.removeAttribute(IOSESSION_REQUEST_RESPONSE_CONTEXT_KEY);
    }

    public void set(int id, RequestAndResponseTrigger<STSRequestMessage, STSResponseMessage> trigger)
    {
        triggers.put(id, trigger);
    }

    public RequestAndResponseTrigger<STSRequestMessage, STSResponseMessage> get(int id)
    {
        RequestAndResponseTrigger<STSRequestMessage, STSResponseMessage> trigger = triggers.get(id);
        return trigger;
    }

    public void remove(int id)
    {
        triggers.remove(id);
    }

    private void clearAndRelease()
    {
        for (RequestAndResponseTrigger<STSRequestMessage, STSResponseMessage> trigger : triggers.values())
        {
            trigger.release();
        }
        triggers.clear();
    }
}
