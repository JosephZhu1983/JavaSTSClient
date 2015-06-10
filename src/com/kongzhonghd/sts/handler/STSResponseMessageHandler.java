package com.kongzhonghd.sts.handler;

import com.kongzhonghd.sts.common.STSRequestMessage;
import com.kongzhonghd.sts.common.STSResponseMessage;
import com.kongzhonghd.sts.context.RequestAndResponseContext;
import com.kongzhonghd.sts.exception.*;
import com.kongzhonghd.util.RequestAndResponseTrigger;
import org.apache.mina.core.session.IoSession;

/**
 * User: apple
 * Date: 13-8-16
 * Time: PM10:26
 */
public class STSResponseMessageHandler implements STSMessageHandler<STSResponseMessage>
{
    @Override
    public void handle(final IoSession session, final STSResponseMessage message) throws STSException
    {
        RequestAndResponseContext requestAndResponseContext = RequestAndResponseContext.getContext(session);
        if (requestAndResponseContext == null)
            throw new STSIoSessionException(session, "无法从当前会话中获取RequestAndResponseContext");

        int messageId = message.getMessageId();

        RequestAndResponseTrigger<STSRequestMessage, STSResponseMessage> trigger = requestAndResponseContext.get(messageId);
        if (trigger == null)
            throw new STSMessageException(message, "无法找到回复消息对应的触发器");

        trigger.setResponse(message);
        trigger.release();
        requestAndResponseContext.remove(messageId);
    }
}
