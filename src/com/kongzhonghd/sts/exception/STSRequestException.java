package com.kongzhonghd.sts.exception;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.business.AbstractRequest;

/**
 * User: apple
 * Date: 13-8-17
 * Time: AM11:09
 */
public class STSRequestException extends STSException
{
    public AbstractRequest getRequest()
    {
        return request;
    }

    private AbstractRequest request;

    public STSRequestException(AbstractRequest request, String message)
    {
        super(message);
        this.request = request;
    }

    public STSRequestException(AbstractRequest request, String message, Throwable cause)
    {
        super(message, cause);
        this.request = request;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("请求：");
        stringBuilder.append(STSConst.CRLF);
        stringBuilder.append(request.toString());
        stringBuilder.append(STSConst.CRLF);
        stringBuilder.append("异常：");
        stringBuilder.append(STSConst.CRLF);
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}
