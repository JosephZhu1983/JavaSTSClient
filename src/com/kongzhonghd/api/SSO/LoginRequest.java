package com.kongzhonghd.api.SSO;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.*;

/**
 * User: apple
 * Date: 14-2-19
 * Time: 下午3:10
 */
public class LoginRequest extends AbstractRequest
{
    private String loginName;
    private String password;
    private String prospectiveUserId;
    private String address;


    public LoginRequest(String loginName, String password, String prospectiveUserId)
    {
        this.address = STSConst.ADDRESS;
        this.prospectiveUserId = prospectiveUserId;
        this.loginName = loginName;
        this.password = new String(Base64.encodeBase64(password.getBytes()));
    }

    @Override
    protected String getProtocol()
    {
        return "Game.kz.Auth";
    }

    @Override
    protected String getCommand()
    {
        return "Login";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return LoginResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("LoginName")).setText(loginName);
        (request.addElement("Password")).setText(password);
        (request.addElement("ProspectiveUserId")).setText(prospectiveUserId);
        (request.addElement("Address")).setText(address);
        return xml;
    }
}
