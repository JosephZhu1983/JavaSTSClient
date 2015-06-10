package com.kongzhonghd.sts.codec;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.common.AbstractSTSMessage;
import com.kongzhonghd.sts.common.AbstractSTSStartLine;
import com.kongzhonghd.sts.context.MessageDecoderContext;
import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.sts.exception.STSMessageException;
import com.kongzhonghd.util.LangUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 12:29 PM
 * STS消息的解码器
 */
class STSMessageDecoder extends CumulativeProtocolDecoder
{
    private static final Log log = LogFactory.getLog(STSMessageDecoder.class);
    private Charset charset = Charset.forName(STSConst.DEFAULT_ENCODING_CHARSET);

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws STSException
    {
        try
        {
            MessageDecoderContext context = MessageDecoderContext.getContext(session);

            if (context.getState() != MessageDecoderContext.STATE_CONTENT_READ)
            {
                if (context.getStartLineReadTimes() + context.getHeaderReadTimes() + context.getBodyReadTimes() > 100)
                    throw new STSMessageException(context.getTempMessage(), "太多次接受同一条消息都没有解码成功！");
            }
            if (context.getState() == MessageDecoderContext.STATE_START)
            {
                context.setTempMessage(processStartLine(in));
                if (context.getTempMessage() == null)
                {
                    context.increaseStartLineReadTimers();
                    return false;
                }

                context.setState(MessageDecoderContext.STATE_STARTLINE_READ);
            }

            if (context.getState() == MessageDecoderContext.STATE_STARTLINE_READ)
            {
                if (!processHeaders(context.getTempMessage(), in))
                {
                    context.increaseHeaderReadTimers();
                    return false;
                }

                context.setState(MessageDecoderContext.STATE_HEADERS_READ);
            }

            if (context.getState() == MessageDecoderContext.STATE_HEADERS_READ)
            {
                if (!processContent(context.getTempMessage(), in))
                {
                    context.increaseBodyReadTimers();
                    return false;
                }

                context.setState(MessageDecoderContext.STATE_CONTENT_READ);
            }

            if (context.getState() == MessageDecoderContext.STATE_CONTENT_READ)
            {
                context.resetTimes();
                context.setState(MessageDecoderContext.STATE_START);
            }

            out.write(context.getTempMessage());

            return in.remaining() >= 0;
        }
        catch (STSMessageException ex)
        {
            if (log.isWarnEnabled())
                log.warn("解码数据出现错误，准备关闭连接，异常信息：" + ex.toString());

            session.close(true);
            return true;
        }
    }

    private AbstractSTSMessage processStartLine(IoBuffer in) throws STSException
    {
        String line = decodeLine(in);
        if (line == null)
            return null;

        AbstractSTSMessage message = AbstractSTSStartLine.getMessageFromStartLine(line);
        return message;
    }

    private boolean processHeaders(AbstractSTSMessage message, IoBuffer in) throws STSException
    {
        while (true)
        {
            String line = decodeLine(in);
            if (line == null)
                return false;

            if (line.length() == 0)
                break;

            message.getHeader().parseFromString(line);
        }
        return true;
    }

    private boolean processContent(AbstractSTSMessage message, IoBuffer in)
    {
        int size = message.getHeader().getContentLength();
        if (size == 0)
            return true;

        int r = in.remaining();
        if (size > r)
            return false;

        byte content[] = new byte[size];
        in.get(content);
        String body = new String(content, charset);
        message.getBody().parseFromString(body);
        return true;
    }

    private String decodeLine(IoBuffer in) throws STSException
    {
        int beginPos = in.position();
        int limit = in.limit();
        boolean lastIsCR = false;
        int terminatorPos = -1;

        for (int i = beginPos; i < limit; i++)
        {
            byte b = in.get(i);
            if (b == STSConst.CR)
            {
                lastIsCR = true;
            }
            else
            {
                if (b == STSConst.LF && lastIsCR)
                {
                    terminatorPos = i;
                    break;
                }
                lastIsCR = false;
            }
        }

        if (terminatorPos == -1)
        {
            return null;
        }

        String result = "";
        if (terminatorPos > 1)
        {
            IoBuffer line = in.slice();
            line.limit(terminatorPos - beginPos - 1);
            try
            {
                result = line.getString(charset.newDecoder());
            }
            catch (CharacterCodingException ex)
            {
                throw new STSException("解析数据的时候出现解码错误：" + LangUtils.getExceptionContent(ex));
            }
        }

        in.position(terminatorPos + 1);

        return result;
    }
}
