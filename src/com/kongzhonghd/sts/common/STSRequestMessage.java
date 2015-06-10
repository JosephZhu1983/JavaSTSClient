package com.kongzhonghd.sts.common;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 12:30 PM
 * STS请求消息
 */
public class STSRequestMessage extends AbstractSTSMessage<STSRequestStartLine, STSRequestHeader>
{
    public STSRequestMessage()
    {
        this.startLine = new STSRequestStartLine();
        this.header = new STSRequestHeader(0);
        this.body = new STSBody();
    }

    public STSRequestMessage(STSRequestStartLine startLine, STSRequestHeader header)
    {
        this.startLine = startLine;
        this.header = header;
    }
}