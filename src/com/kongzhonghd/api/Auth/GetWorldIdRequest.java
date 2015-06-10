package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.*;
import org.dom4j.*;


/**
 * @author stefanie zhao
 * @date 2014-1-10 上午09:15:23
 */
public class GetWorldIdRequest extends AbstractRouteRequest
{
    private String gameCode;
    private String alias;

    public GetWorldIdRequest(ToUser toUser, String alias)
    {
        super(toUser);
        this.gameCode = "gw2cn";
        this.alias = alias;
    }

    /**
     * @return the gameCode
     */
    public String getGameCode()
    {
        return gameCode;
    }

    /**
     * @return the alias
     */
    public String getAlias()
    {
        return alias;
    }

    @Override
    protected String getProtocol()
    {
        return "Auth";
    }

    @Override
    protected String getCommand()
    {
        return "GetWorldId";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return GetWorldIdResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("GameCode")).setText(gameCode);
        (request.addElement("Alias")).setText(alias);
        return xml;
    }
}
