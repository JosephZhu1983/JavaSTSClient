package com.kongzhonghd.sts.common;

import com.kongzhonghd.sts.exception.STSException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: JosephZhu
 * Date: 13-8-6
 * Time: PM8:56
 * STS响应Subject头
 */
public class STSResponseHeaderSubject extends AbstractSTSHeaderSubject
{
    private static final Log log = LogFactory.getLog(STSResponseHeaderSubject.class);
    private boolean isFinalReply;
    private int sequenceNumber;

    public STSResponseHeaderSubject()
    {
        //this.isFinalReply = true;
    }

    public STSResponseHeaderSubject(int transactionId)
    {
        super(transactionId);
        this.isFinalReply = true;
    }

    public boolean isFinalReply()
    {
        return isFinalReply;
    }

    public int getSequenceNumber()
    {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber)
    {
        this.sequenceNumber = sequenceNumber;
    }

    public void parseFromString(String value) throws STSException
    {
        if (value.indexOf("R") > 0)
        {
            this.isFinalReply = true;
            String parts[] = value.split("R");
            this.transactionId = Integer.valueOf(parts[0]);
            if (parts.length == 2)
                this.sequenceNumber = Integer.valueOf(parts[1]);
            return;
        }
        else if (value.indexOf("r") > 0)
        {
            this.isFinalReply = false;
            String parts[] = value.split("r");
            this.transactionId = Integer.valueOf(parts[0]);
            if (parts.length == 2)
                this.sequenceNumber = Integer.valueOf(parts[1]);
            return;
        }

        String error = "不正确的响应Subject头：" + value;
        log.warn(error);
        throw new STSException(error);
    }

    public String buildToString()
    {
        if (isFinalReply)
            return String.format("%d%s", transactionId, "R");
        else
            return String.format("%d%s%d", transactionId, "r", sequenceNumber);
    }
}
