package com.kongzhonghd.api.Gate;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.*;

/**
 * User: apple
 * Date: 13-8-8
 * Time: PM1:27
 */
public class UnregisterServiceRequest extends AbstractRequest
{
    private String protocolName;

    public UnregisterServiceRequest(String protocolName)
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
        return "UnregisterService";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return UnregisterServiceResponse.class;
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
