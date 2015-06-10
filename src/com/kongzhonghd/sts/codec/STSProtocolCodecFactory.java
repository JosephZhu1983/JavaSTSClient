package com.kongzhonghd.sts.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 12:28 PM
 * 消息编解码工厂
 */
public class STSProtocolCodecFactory implements ProtocolCodecFactory
{

    private final ProtocolEncoder encoder;
    private final ProtocolDecoder decoder;

    public STSProtocolCodecFactory()
    {
        encoder = new STSMessageEncoder();
        decoder = new STSMessageDecoder();
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception
    {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception
    {
        return decoder;
    }
}
