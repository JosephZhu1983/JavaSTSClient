package com.kongzhonghd.sts.exception;

/**
 * User: apple
 * Date: 13-8-11
 * Time: PM3:25
 * STS异常基类
 */
public class STSException extends Exception
{
    public STSException(String message)
    {
        super(message);
    }

    public STSException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

