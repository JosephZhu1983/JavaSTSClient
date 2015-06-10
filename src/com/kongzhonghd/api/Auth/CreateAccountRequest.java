package com.kongzhonghd.api.Auth;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.util.Base64Coder;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 2:41 PM
 */
public class CreateAccountRequest extends AbstractRequest
{
    private String loginName;
    private String userName;
    private String password;
    private String address;

    public CreateAccountRequest(String loginName, String userName, String password)
    {
        this.loginName = loginName;
        this.userName = userName;
        this.password = Base64Coder.encodeString(password);
        this.address = STSConst.ADDRESS;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public String getAddress()
    {
        return address;
    }

    @Override
    protected String getProtocol()
    {
        return "Auth";
    }

    @Override
    protected String getCommand()
    {
        return "CreateAccount";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return CreateAccountResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("Address")).setText(address);
        (request.addElement("LoginName")).setText(loginName);
        (request.addElement("UserName")).setText(userName);
        (request.addElement("Password")).setText(password);
        return xml;
    }
}
