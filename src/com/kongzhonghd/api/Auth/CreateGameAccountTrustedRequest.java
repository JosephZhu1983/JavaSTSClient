package com.kongzhonghd.api.Auth;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.business.*;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-28
 * Time: PM12:27
 * To change this template use File | Settings | File Templates.
 */
public class CreateGameAccountTrustedRequest extends AbstractRouteRequest
{
    private String alias;
    private String datacenter;
    private String territory;
    private String address;
    private String billingInfo;
    private String gameCode;

    public CreateGameAccountTrustedRequest(ToUser toUser, String datacenter, String territory, String billingInfo)
    {
        super(toUser);
        this.datacenter = datacenter;
        this.gameCode = "gw2cn";
        this.territory = territory;
        this.billingInfo = billingInfo;
        this.address = STSConst.ADDRESS;
        this.alias = "Guild Wars 2";
    }

    public String getAlias()
    {
        return alias;
    }


    /**
     * @return the gameCode
     */
    public String getGameCode()
    {
        return gameCode;
    }

    public String getDatacenter()
    {
        return datacenter;
    }

    public String getTerritory()
    {
        return territory;
    }

    public String getAddress()
    {
        return address;
    }

    public String getBillingInfo()
    {
        return billingInfo;
    }

    @Override
    protected String getProtocol()
    {
        return "Auth";
    }

    @Override
    protected String getCommand()
    {
        return "CreateGameAccountTrusted";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return CreateGameAccountTrustedResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("Alias")).setText(alias);
        (request.addElement("Address")).setText(address);
        (request.addElement("GameCode")).setText(gameCode);
        Element accountInfo = request.addElement("AccountInfo");
        (accountInfo.addElement("Territory")).setText(territory);
        (accountInfo.addElement("Datacenter")).setText(datacenter);
        (accountInfo.addElement("BillingInfo")).setText(billingInfo);
        return xml;
    }

}
