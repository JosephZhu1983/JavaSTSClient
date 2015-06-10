package com.kongzhonghd.sts.common;

import com.kongzhonghd.sts.exception.STSException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-31
 * Time: PM2:03
 * To change this template use File | Settings | File Templates.
 * STS Range头，用于片段消息
 */
public class STSHeaderRange implements Parsable, Buildable, Serializable
{
    private static final Log log = LogFactory.getLog(STSHeaderRange.class);
    private String prefix = "bytes";
    private int totalBytes;
    private int beginOffset;
    private int endOffset;

    public STSHeaderRange()
    {

    }

    public STSHeaderRange(int beginOffset, int endOffset, int totalBytes)
    {
        this.beginOffset = beginOffset;
        this.endOffset = endOffset;
        this.totalBytes = totalBytes;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public int getTotalBytes()
    {
        return totalBytes;
    }

    public int getBeginOffset()
    {
        return beginOffset;
    }

    public int getEndOffset()
    {
        return endOffset;
    }

    public void parseFromString(String value) throws STSException
    {
        if (value != null && value.length() > 0)
        {
            if (value.indexOf(" ") > 0)
            {
                String prefixPart = value.split(" ")[0];
                String rangePart = value.split(" ")[1];
                if (rangePart.indexOf("/") > 0)
                {
                    String offsetPart = rangePart.split("/")[0];
                    String totalPart = rangePart.split("/")[1];
                    if (offsetPart.indexOf("-") > 0)
                    {
                        String beginOffsetPart = offsetPart.split("-")[0];
                        String endOffsetPart = offsetPart.split("-")[1];
                        this.prefix = prefixPart;
                        this.totalBytes = Integer.parseInt(totalPart);
                        this.beginOffset = Integer.parseInt(beginOffsetPart);
                        this.endOffset = Integer.parseInt(endOffsetPart);
                        return;
                    }
                }
            }
        }
        String error = "错误的Range头：" + value;
        log.warn(error);
        throw new STSException(error);
    }

    public String buildToString()
    {
        return String.format("%s %d-%d/%d", this.prefix, this.beginOffset, this.endOffset, this.totalBytes);
    }
}
