package com.kongzhonghd.sts.common;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 12:30 PM
 * STS响应消息
 */
public class STSResponseMessage extends AbstractSTSMessage<STSResponseStartLine, STSResponseHeader>
{
    public STSResponseMessage()
    {
        this.startLine = new STSResponseStartLine();
        this.header = new STSResponseHeader();
        this.body = new STSBody();
    }

    public STSResponseMessage(STSResponseStartLine startLine, STSResponseHeader header)
    {
        this.startLine = startLine;
        this.header = header;
    }
}
