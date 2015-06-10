package com.kongzhonghd.api.Partner;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-30
 * Time: PM3:46
 * To change this template use File | Settings | File Templates.
 */
public class GetAccountSkusRequest extends AbstractRequest
{
    private String gameAccount;

    public GetAccountSkusRequest(String gameAccount)
    {
        this.gameAccount = gameAccount;
    }

    public String getGameAccount()
    {
        return gameAccount;
    }

    @Override
    protected String getProtocol()
    {
        return "Game.gw2.Partner";
    }

    @Override
    protected String getCommand()
    {
        return "GetAccountSkus";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return GetAccountSkusResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("GameAccount")).setText(gameAccount);
        return xml;
    }
}
