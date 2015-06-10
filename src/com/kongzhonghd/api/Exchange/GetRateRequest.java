package com.kongzhonghd.api.Exchange;

import com.kongzhonghd.sts.business.*;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-8-1
 * Time: PM2:08
 * To change this template use File | Settings | File Templates.
 */
public class GetRateRequest extends AbstractRouteRequest
{
    private int type;
    private int quantity;

    public GetRateRequest(ToUser toUser, int type, int quantity)
    {
        super(toUser);
        this.type = type;
        this.quantity = quantity;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public int getType()
    {
        return type;
    }

    @Override
    protected String getProtocol()
    {
        return "Game.gw2.Exchange";
    }

    @Override
    protected String getCommand()
    {
        return "GetRate";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return GetRateResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("Type")).setText(String.valueOf(type));
        (request.addElement("Quantity")).setText(String.valueOf(quantity));
        return xml;
    }
}
