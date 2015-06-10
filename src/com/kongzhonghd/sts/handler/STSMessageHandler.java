package com.kongzhonghd.sts.handler;

import com.kongzhonghd.sts.common.AbstractSTSMessage;
import com.kongzhonghd.sts.exception.STSException;
import org.apache.mina.core.session.IoSession;

/**
 * User: apple
 * Date: 13-8-16
 * Time: PM10:31
 */
public interface STSMessageHandler<Message extends AbstractSTSMessage>
{
    void handle(final IoSession session, final Message message) throws STSException;
}
