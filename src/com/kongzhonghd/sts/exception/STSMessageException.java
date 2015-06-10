package com.kongzhonghd.sts.exception;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.common.AbstractSTSMessage;

/**
 * User: apple
 * Date: 13-8-9
 * Time: PM3:40
 * STS协议异常
 */
public class STSMessageException extends STSException
{
    private AbstractSTSMessage stsMessage;

    public STSMessageException(AbstractSTSMessage stsMessage, String errorMessage)
    {
        super(errorMessage);
        this.stsMessage = stsMessage;
    }

    public STSMessageException(AbstractSTSMessage stsMessage, String errorMessage, Throwable cause)
    {
        super(errorMessage, cause);
        this.stsMessage = stsMessage;
    }

    public AbstractSTSMessage getStsMessage()
    {
        return stsMessage;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("消息：");
        stringBuilder.append(STSConst.CRLF);
        stringBuilder.append(stsMessage.buildToString());
        stringBuilder.append(STSConst.CRLF);
        stringBuilder.append("异常：");
        stringBuilder.append(STSConst.CRLF);
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}