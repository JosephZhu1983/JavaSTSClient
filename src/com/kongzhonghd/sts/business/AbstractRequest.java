package com.kongzhonghd.sts.business;

import com.kongzhonghd.sts.common.*;
import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.sts.exception.STSMessageException;
import com.kongzhonghd.util.*;
import org.apache.commons.lang.StringUtils;
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
 * Time: 2:53 PM
 * 所有请求的基类
 */
public abstract class AbstractRequest implements XmlBuildable, XmlParsable
{
    protected static final String XML_ROOT = "Request";
    private static final Log log = LogFactory.getLog(AbstractRequest.class);
    protected boolean success = true;

    public boolean isSuccess()
    {
        return success;
    }

    protected abstract String getProtocol();

    protected abstract String getCommand();

    public abstract Class<? extends AbstractResponse> getResponseType();

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        xml.addElement("Request");
        return xml;
    }

    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {

    }

    protected void extraStep(STSRequestMessage message)
    {

    }

    public String getUri()
    {
        String protocol = "/".concat(StringUtils.strip(getProtocol(), "/"));
        String command = "/".concat(StringUtils.strip(getCommand(), "/"));
        return protocol.concat(command);
    }

    public void parseFromRequestMessage(STSRequestMessage message) throws STSMessageException
    {
        String rawXml = message.getBody().getContent();
        Document xml = null;
        String errorMessage = "";

        try
        {
            xml = DocumentHelper.parseText(rawXml);
        }
        catch (Exception ex)
        {
            success = false;
            errorMessage = "收到的请求消息无法解析成XML文档！错误信息为：" + LangUtils.getExceptionContent(ex);
        }

        if (xml != null)
        {
            try
            {
                parseFromXmlDocument(xml);
            }
            catch (Exception ex)
            {
                success = false;
                errorMessage = "收到的请求消息由" + this.getClass().getName() + "在解析的时候发生错误！错误信息为：" + LangUtils.getExceptionContent(ex);
            }
        }

        if (!success)
            throw new STSMessageException(message, errorMessage);
    }

    public STSRequestMessage buildToRequestMessage(boolean needReply) throws STSException
    {
        STSRequestStartLine startLine = new STSRequestStartLine(getUri());
        STSRequestHeader header = null;
        if (needReply)
            header = new STSRequestHeader(Sequence.next());
        else
            header = new STSRequestHeader();
        try
        {
            Document xml = buildToXmlDocument();
            STSBody body = new STSBody(XmlUtils.xmlToString(xml));
            STSRequestMessage requestMessage = new STSRequestMessage(startLine, header);
            requestMessage.setBody(body);
            extraStep(requestMessage);
            return requestMessage;
        }
        catch (Exception ex)
        {
            throw new STSException("无法把请求转化为请求消息！", ex);
        }
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
