package com.kongzhonghd.sts.common;

import com.kongzhonghd.STSConst;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/25/13
 * Time: 11:10 AM
 * 抽象的STS消息
 */
public abstract class AbstractSTSMessage<StartLine extends AbstractSTSStartLine, Header extends AbstractSTSHeader> implements Serializable, Buildable
{
    protected StartLine startLine;
    protected Header header;
    protected STSBody body;

    public int getMessageId()
    {
        if (header == null)
            return 0;

        if (header.getSubject() == null)
            return 0;

        return header.getSubject().getTransactionId();
    }

    public Header getHeader()
    {
        return header;
    }

    public StartLine getStartLine()
    {
        return startLine;
    }

    public STSBody getBody()
    {
        return body;
    }

    public void setBody(STSBody body) throws UnsupportedEncodingException
    {
        this.body = body;
        this.header.setContentLength(body.buildToString().getBytes(STSConst.DEFAULT_ENCODING_CHARSET).length);
    }

    public String buildToString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(startLine.buildToString());
        builder.append(header.buildToString());
        builder.append(body.buildToString());
        return builder.toString();
    }
}
