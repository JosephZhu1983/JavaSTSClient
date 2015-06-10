package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.*;
import org.dom4j.*;


/**
 * @author stefanie zhao
 * @date 2013-11-22 下午05:33:23
 */
public class ChangeUserNameRequest extends AbstractRouteRequest
{

    private String userName;


    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }

    public ChangeUserNameRequest(ToUser toUser, String userName)
    {
        super(toUser);
        this.userName = userName;
    }

    @Override
    protected String getProtocol()
    {
        return "Auth";
    }

    @Override
    protected String getCommand()
    {
        return "ChangeUserName";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return ChangeUserNameResponse.class;
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
