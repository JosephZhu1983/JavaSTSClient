package com.kongzhonghd.sts.common;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.util.LangUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/22/13
 * Time: 8:46 AM
 * 抽象STS头
 */
public abstract class AbstractSTSHeader implements Serializable, Parsable, Buildable
{
    protected static final String CONTENT_ENCODING = "e";
    protected static final String CONTENT_TYPE = "c";
    protected static final String CONTENT_LENGTH = "l";
    protected static final String CONTENT_RANGE = "n";
    protected static final String SUBJECT = "s";
    private static final Log log = LogFactory.getLog(AbstractSTSHeader.class);
    protected Map<String, Object> rawHeaders = new HashMap<String, Object>();
    private int contentLength;
    private String contentType;
    private String contentEncoding;
    private STSHeaderRange contentRange = new STSHeaderRange();
    private AbstractSTSHeaderSubject subject;

    public int getContentLength()
    {
        return contentLength;
    }

    public void setContentLength(int contentLength)
    {
        rawHeaders.put(CONTENT_LENGTH, contentLength);
        this.contentLength = contentLength;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        rawHeaders.put(CONTENT_TYPE, contentType);
        this.contentType = contentType;
    }

    public String getContentEncoding()
    {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding)
    {
        rawHeaders.put(CONTENT_ENCODING, contentEncoding);
        this.contentEncoding = contentEncoding;
    }

    public STSHeaderRange getContentRange()
    {
        return contentRange;
    }

    public void setContentRange(STSHeaderRange contentRange)
    {
        rawHeaders.put(CONTENT_RANGE, contentRange.buildToString());
        this.contentRange = contentRange;
    }

    public void removeContentRange()
    {
        rawHeaders.remove(CONTENT_RANGE);
        this.contentRange = null;
    }

    public void parseContentRange(String contentRange) throws STSException
    {
        rawHeaders.put(CONTENT_RANGE, contentRange);
        this.contentRange.parseFromString(contentRange);
    }

    @Override
    public String buildToString()
    {
        StringBuilder builder = new StringBuilder();
        for (String key : rawHeaders.keySet())
        {
            builder.append(String.format("%s:%s", key, String.valueOf(rawHeaders.get(key))));
            builder.append(STSConst.CRLF);
        }
        builder.append(STSConst.CRLF);

        return builder.toString();
    }

    @Override
    public void parseFromString(String line) throws STSException
    {
        try
        {
            //[2013-12-26]bug:不正确的请求Subject头：1;timeout=24990
            line = line.split(";")[0];
            String parts[] = line.split(":");
            String name = parts[0].trim();
            String value = parts[1].trim();
            setHeader(name, value);
        }
        catch (Exception ex)
        {
            String error = "解析一行头出错，内容为：" + line + "，错误信息为：" + LangUtils.getExceptionContent(ex);
            log.warn(error);
            throw new STSException(error);
        }
    }

    void setOtherHeader(String name, String value)
    {

    }

    void setHeader(String name, String value) throws STSException
    {
        if (name.equalsIgnoreCase(CONTENT_TYPE))
        {
            setContentType(value);
        }
        else if (name.equalsIgnoreCase(CONTENT_ENCODING))
        {
            setContentEncoding(value);
        }
        else if (name.equalsIgnoreCase(CONTENT_LENGTH))
        {
            setContentLength(Integer.valueOf(value));
        }
        else if (name.equalsIgnoreCase(CONTENT_RANGE))
        {
            parseContentRange(value);
        }
        else if (name.equalsIgnoreCase(SUBJECT))
        {
            parseSubject(value);
        }
        else
        {
            setOtherHeader(name, value);
        }
    }

    public AbstractSTSHeaderSubject getSubject()
    {
        return subject;
    }

    public void setSubject(AbstractSTSHeaderSubject subject)
    {
        rawHeaders.put(SUBJECT, subject.buildToString());
        this.subject = subject;
    }

    private void parseSubject(String subject) throws STSException
    {
        rawHeaders.put(SUBJECT, subject);
        this.subject.parseFromString(subject);
    }
}
