package com.kongzhonghd.sts.common;

import com.kongzhonghd.sts.exception.STSException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: JosephZhu
 * Date: 13-8-6
 * Time: PM8:56
 * STS请求的Subject头
 */
public class STSRequestHeaderSubject extends AbstractSTSHeaderSubject
{
    private static final Log log = LogFactory.getLog(STSRequestHeaderSubject.class);

    public STSRequestHeaderSubject(int transactionId)
    {
        super(transactionId);
    }

    public STSRequestHeaderSubject()
    {
    }

    public void parseFromString(String value) throws STSException
    {
        try
        {
            this.transactionId = Integer.valueOf(value);
        }
        catch (Exception ex)
        {
            String error = "不正确的请求Subject头：" + value;
            log.warn(error);
            throw new STSException(error);
        }
    }

    public String buildToString()
    {
        return String.valueOf(transactionId);
    }
}
