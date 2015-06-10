package com.kongzhonghd.sts.common;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/22/13
 * Time: 4:36 PM
 * STS响应头
 */
public class STSResponseHeader extends AbstractSTSHeader
{
    protected static final String FROM = "f";
    protected static final String FROM_RIGHTS = "h";
    private String from;
    private String fromRights;

    public STSResponseHeader()
    {
        this.setSubject(new STSResponseHeaderSubject());
    }

    public String getFrom()
    {
        return from;
    }

    void setFrom(String from)
    {
        rawHeaders.put(FROM, from);
        this.from = from;
    }

    public String getFromRights()
    {
        return fromRights;
    }

    void setFromRights(String fromRights)
    {
        rawHeaders.put(FROM_RIGHTS, fromRights);
        this.fromRights = fromRights;
    }

    @Override
    protected void setOtherHeader(String name, String value)
    {
        if (name.equalsIgnoreCase(FROM))
        {
            setFrom(value);
        }
        else if (name.equalsIgnoreCase(FROM_RIGHTS))
        {
            setFromRights(value);
        }
    }
}
