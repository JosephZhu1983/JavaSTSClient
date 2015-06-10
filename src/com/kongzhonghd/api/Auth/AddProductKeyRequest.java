package com.kongzhonghd.api.Auth;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.business.*;
import org.dom4j.*;

/**
 * User: apple
 * Date: 13-8-8
 * Time: PM1:26
 */
public class AddProductKeyRequest extends AbstractRouteRequest
{
    private String gameCode;
    private String alias;
    private String productKey;
    private String address;

    public String getGameCode()
    {
        return gameCode;
    }

    public String getAlias()
    {
        return alias;
    }

    public String getProductKey()
    {
        return productKey;
    }

    public String getAddress()
    {
        return address;
    }

    public AddProductKeyRequest(ToUser toUser, String productKey)
    {
        super(toUser);
        this.productKey = productKey.replace("-", "").trim();
        this.gameCode = "gw2cn";
        this.alias = "Guild Wars 2";
        this.address = STSConst.ADDRESS;
    }

    @Override
    protected String getProtocol()
    {
        return "Auth";
    }

    @Override
    protected String getCommand()
    {
        return "AddProductKey";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return AddProductKeyResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("GameCode")).setText(gameCode);
        (request.addElement("Alias")).setText(alias);
        (request.addElement("ProductKey")).setText(productKey);
        (request.addElement("Address")).setText(address);
        return xml;
    }
}
