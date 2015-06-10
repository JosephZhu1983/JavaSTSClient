package com.kongzhonghd.api.Game;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-8-1
 * Time: PM2:45
 * To change this template use File | Settings | File Templates.
 */
public class GetAccountFeaturesRequest extends AbstractRequest
{
    private String gameAccount;

    public GetAccountFeaturesRequest(String gameAccount)
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
        return "Game";
    }

    @Override
    protected String getCommand()
    {
        return "GetAccountFeatures";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return GetAccountFeaturesResponse.class;
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
