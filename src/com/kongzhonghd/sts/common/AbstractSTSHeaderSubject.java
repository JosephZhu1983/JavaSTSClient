package com.kongzhonghd.sts.common;

import com.kongzhonghd.sts.exception.STSException;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-31
 * Time: PM2:34
 * To change this template use File | Settings | File Templates.
 * 抽象的STS Subject头
 */
public abstract class AbstractSTSHeaderSubject implements Parsable, Buildable, Serializable
{
    protected int transactionId;

    public AbstractSTSHeaderSubject()
    {
    }

    public AbstractSTSHeaderSubject(int transactionId)
    {
        this.transactionId = transactionId;
    }

    public int getTransactionId()
    {
        return transactionId;
    }

    public abstract void parseFromString(String value) throws STSException;

    public abstract String buildToString();
}
