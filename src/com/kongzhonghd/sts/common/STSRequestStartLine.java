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
 * STS请求起始行
 */
public class STSRequestStartLine extends AbstractSTSStartLine
{
    private static final Log log = LogFactory.getLog(STSRequestStartLine.class);
    private STSMethod requestMethod = STSMethod.P;
    private String requestUri;

    public STSRequestStartLine()
    {
    }

    public STSRequestStartLine(String requestUri)
    {
        this.requestUri = requestUri;
    }

    public STSMethod getRequestMethod()
    {
        return requestMethod;
    }

    public String getRequestUri()
    {
        return requestUri;
    }

    @Override
    public String buildToString()
    {
        return String.format("%s %s %s%s", requestMethod, requestUri, PROTOCOL_VERSION, STSConst.CRLF);
    }

    @Override
    public void parseFromString(String startLine) throws STSException
    {
        String[] parts = startLine.trim().split(" ");
        if (parts.length == 3 && parts[2].equals(PROTOCOL_VERSION))
        {
            this.requestUri = parts[1];
            this.requestMethod = STSMethod.valueOf(parts[0]);
        }
        else
        {
            String error = "错误的请求起始行：" + startLine;
            log.warn(error);
            throw new STSException(error);
        }
    }
}
