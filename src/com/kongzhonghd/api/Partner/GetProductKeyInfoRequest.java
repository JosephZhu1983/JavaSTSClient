package com.kongzhonghd.api.Partner;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.*;

/**
 * User: apple
 * Date: 13-8-8
 * Time: PM1:25
 */
public class GetProductKeyInfoRequest extends AbstractRequest
{
    private String productKey;
    private String address;

    public GetProductKeyInfoRequest(String productKey)
    {
        this.productKey = productKey;
        this.address = STSConst.ADDRESS;
    }

    public String getAddress()
    {
        return address;
    }

    public String getProductKey()
    {
        return productKey;
    }

    @Override
    protected String getProtocol()
    {
        return "Game.gw2.Partner";
    }

    @Override
    protected String getCommand()
    {
        return "GetProductKeyInfo";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return GetProductKeyInfoResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("Address")).setText(address);
        (request.addElement("ProductKey")).setText(productKey);
        return xml;
    }
}
