package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.*;
import org.dom4j.*;

/**
 * User: apple
 * Date: 13-8-8
 * Time: PM1:42
 */
public class CheckUserNameRequest extends AbstractRouteRequest
{
    private String userName;

    public CheckUserNameRequest(ToUser toUser, String userName)
    {
        super(toUser);
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }

    @Override
    protected String getProtocol()
    {
        return "Auth";
    }

    @Override
    protected String getCommand()
    {
        return "CheckUserName";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return CheckUserNameResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("UserName")).setText(userName);
        return xml;
    }
}
