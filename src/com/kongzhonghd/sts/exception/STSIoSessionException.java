package com.kongzhonghd.sts.exception;

import com.kongzhonghd.STSConst;
import org.apache.mina.core.session.IoSession;

/**
 * User: apple
 * Date: 13-8-9
 * Time: PM3:37
 * STS网络异常
 */
public class STSIoSessionException extends STSException
{
    private IoSession ioSession;

    public STSIoSessionException(IoSession ioSession, String message)
    {
        super(message);
        this.ioSession = ioSession;
    }

    public STSIoSessionException(IoSession ioSession, String message, Throwable cause)
    {
        super(message, cause);
        this.ioSession = ioSession;
    }

    public IoSession getIoSession()
    {
        return ioSession;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("会话：");
        stringBuilder.append(STSConst.CRLF);
        stringBuilder.append(ioSession.toString());
        stringBuilder.append(STSConst.CRLF);
        stringBuilder.append("异常：");
        stringBuilder.append(STSConst.CRLF);
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}