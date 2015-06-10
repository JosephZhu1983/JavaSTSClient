package com.kongzhonghd.sts.business;

import com.kongzhonghd.sts.common.*;
import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.sts.exception.STSMessageException;
import com.kongzhonghd.util.LangUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 3:17 PM
 * 所有响应的基类
 */
public abstract class AbstractResponse implements XmlBuildable, XmlParsable
{
    protected static final String XML_ROOT = "Reply";
    private static final Log log = LogFactory.getLog(AbstractResponse.class);
    protected String rawXml;
    protected String reasonPhase;
    protected int statusCode;
    protected Error error;
    protected String uri;
    protected STSResponseHeader header;
    protected boolean success = true;
    protected String remoteAddress;

    public String getUri()
    {
        return uri;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public String getRawXml()
    {
        return rawXml;
    }


    /**
     * @return the header
     */
    public STSResponseHeader getHeader()
    {
        return header;
    }

    public String getReasonPhase()
    {
        return reasonPhase;
    }

    public Error getError()
    {
        return error;
    }

    
    
    /**
	 * @return the remoteAddress
	 */
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * @param remoteAddress the remoteAddress to set
	 */
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	@Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        if (this.error == null)
        {
            xml.addElement(XML_ROOT);
            return xml;
        }
        else
        {
            return error.buildToXmlDocument();
        }
    }

    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {

    }

    public void parseFromResponseMessage(String uri, STSResponseMessage message)
            throws STSMessageException
    {
        this.uri = uri;

        STSResponseStartLine responseStartLine = message.getStartLine();
        reasonPhase = responseStartLine.getReasonPhrase();
        statusCode = responseStartLine.getStatusCode();
        rawXml = message.getBody().getContent();
        header = message.getHeader();
        Document xml = null;

        try
        {
            xml = DocumentHelper.parseText(rawXml);
        }
        catch (Exception ex)
        {
            success = false;
            throw new STSMessageException(message, "收到的响应消息无法解析成XML文档！错误信息为：" + LangUtils.getExceptionContent(ex));
        }

        if (statusCode >= 400)
        {
            error = new Error();
            try
            {
                error.parseFromXmlDocument(xml);
                if (error.getCode() > 0)
                    success = false;
            }
            catch (Exception ex)
            {
                success = false;
                throw new STSMessageException(message, "收到的响应消息在解析Error部分的时候发生错误！错误信息为：" + LangUtils.getExceptionContent(ex));
            }
        }
        else
        {
            try
            {
                parseFromXmlDocument(xml);
            }
            catch (Exception ex)
            {
                success = false;
                throw new STSMessageException(message, "收到的响应消息由" + this.getClass().getName() + "在解析的时候发生错误！错误信息为：" + LangUtils.getExceptionContent(ex));
            }
        }

    }

    public STSResponseMessage buildToResponseMessage() throws Exception
    {
        STSResponseStartLine startLine = new STSResponseStartLine();
        if (statusCode > 0)
            startLine.setStatusCode(statusCode);
        if (reasonPhase != null && !reasonPhase.equals(""))
            startLine.setReasonPhrase(reasonPhase);

        Document xml = null;
        try
        {
            xml = buildToXmlDocument();
        }
        catch (Exception ex)
        {
            throw new STSException("无法把响应解析为响应消息！", ex);
        }
        //STSResponseHeader header = new STSResponseHeader();
        //STSBody body = new STSBody(XmlUtils.xmlToString(xml));
        STSBody body = null;
        if (rawXml != null && !rawXml.equals("")) body = new STSBody(rawXml);
        else body = new STSBody();

        STSResponseMessage message = new STSResponseMessage(startLine, header);
        message.setBody(body);
        return message;
    }

    @Override
    public String toString()
    {
        try
        {
            return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
        catch (Exception ex)
        {
            return "toString出错：" + ex.getMessage();
        }
    }
}
