package com.kongzhonghd.sts.codec;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.common.AbstractSTSMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 12:29 PM
 * STS消息的编码器
 */
class STSMessageEncoder extends ProtocolEncoderAdapter
{
    private static final Log log = LogFactory.getLog(STSMessageEncoder.class);
    private Charset charset = Charset.forName(STSConst.DEFAULT_ENCODING_CHARSET);

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception
    {
        AbstractSTSMessage msg = (AbstractSTSMessage) message;
        String s = msg.buildToString();
        IoBuffer buf = IoBuffer.allocate(256).setAutoExpand(true);
        CharsetEncoder charsetEncoder = charset.newEncoder();
        buf.putString(s, charsetEncoder);
        buf.flip();
        out.write(buf);
    }
}
