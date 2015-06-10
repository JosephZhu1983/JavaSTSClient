package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.*;


/**
 * @author stefanie zhao
 * @date 2013-11-22 下午01:15:09
 */
public class PredictUserNameRequest extends AbstractRequest
{
    private String userName;

    private String loginName;

    public PredictUserNameRequest(String userName, String loginName)
    {
        this.userName = userName;
        this.loginName = loginName;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getLoginName()
    {

        return loginName;

    }

    @Override
    protected String getProtocol()
    {
        return "Auth";
    }

    @Override
    protected String getCommand()
    {
        return "PredictUserName";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return PredictUserNameResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("UserName")).setText(userName);
        (request.addElement("LoginName")).setText(loginName);
        return xml;
    }
}
