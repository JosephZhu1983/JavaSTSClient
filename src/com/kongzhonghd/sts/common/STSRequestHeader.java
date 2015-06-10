package com.kongzhonghd.sts.common;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/22/13
 * Time: 4:35 PM
 * STS请求头
 */
public class STSRequestHeader extends AbstractSTSHeader
{
    protected static final String TO = "t";
    private String to;

    public STSRequestHeader()
    {
        this.setSubject(new STSRequestHeaderSubject());
    }

    public STSRequestHeader(int transactionId)
    {
        this.setSubject(new STSRequestHeaderSubject(transactionId));
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        rawHeaders.put(TO, to);
        this.to = to;
    }

    @Override
    protected void setOtherHeader(String name, String value)
    {
        if (name.equalsIgnoreCase(TO))
        {
            setTo(value);
        }
    }
}
