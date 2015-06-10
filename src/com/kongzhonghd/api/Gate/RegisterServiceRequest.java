package com.kongzhonghd.api.Gate;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/24/13
 * Time: 8:52 PM
 */
public class RegisterServiceRequest extends AbstractRequest
{
    private String protocolName;

    public RegisterServiceRequest(String protocolName)
    {
        this.protocolName = protocolName;
    }

    public String getProtocolName()
    {
        return protocolName;
    }

    @Override
    protected String getProtocol()
    {
        return "Gate";
    }

    @Override
    protected String getCommand()
    {
        return "RegisterService";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return RegisterServiceResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("Protocol")).setText(this.protocolName);
        return xml;
    }
}
