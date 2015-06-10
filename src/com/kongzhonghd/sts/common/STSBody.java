package com.kongzhonghd.sts.common;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/22/13
 * Time: 8:46 AM
 * STS主体
 */
public class STSBody implements Serializable, Parsable, Buildable
{
    private boolean isLastBody = true;

    public String getContent()
    {
        return content;
    }

    private String content;

    public STSBody()
    {

    }

    public STSBody(String content)
    {
        this.content = content;
    }

    public STSBody(String content, boolean isLastBody)
    {
        this(content);
        this.isLastBody = isLastBody;
    }

    @Override
    public String buildToString()
    {
        if (isLastBody)
            return content + "\n";
        else
            return content;
    }

    @Override
    public void parseFromString(String value)
    {
        if (value.endsWith("\n"))
            value = value.substring(0, value.length() - 1);
        else if (value.endsWith("\r\n"))
            value = value.substring(0, value.length() - 2);
        this.content = value;
    }
}
