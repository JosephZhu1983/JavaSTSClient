package com.kongzhonghd.sts.common;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.exception.STSException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/23/13
 * Time: 9:21 AM
 * STS响应起始行
 */
public class STSResponseStartLine extends AbstractSTSStartLine
{
    private static final Log log = LogFactory.getLog(STSResponseStartLine.class);
    private int statusCode;
    private String reasonPhrase;

    public STSResponseStartLine()
    {
        this.statusCode = 200;
        this.reasonPhrase = "OK";
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase()
    {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase)
    {
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public String buildToString()
    {
        return String.format("%s %d %s%s", PROTOCOL_VERSION, statusCode, reasonPhrase, STSConst.CRLF);
    }

    @Override
    public void parseFromString(String startLine) throws STSException
    {
        String[] parts = startLine.trim().split(" ");
        if (parts.length == 3 && parts[0].equals(PROTOCOL_VERSION))
        {
            this.statusCode = Integer.parseInt(parts[1]);
            this.reasonPhrase = parts[2];
        }
        else
        {
            String error = "错误的响应起始行：" + startLine;
            log.warn(error);
            throw new STSException(error);
        }
    }
}
