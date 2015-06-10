package com.kongzhonghd.api.Auth;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.*;


/**
 * @author stefanie zhao
 * @date 2013-11-14 下午04:48:32
 */
public class CreateAccountTrustedRequest extends AbstractRequest
{
    private String userId;
    private String provider;
    private String providerLogin;
    private String userName;
    private String userCenter;
    // private String password;
    private String language;
    private String address;
    private String forcePassword;



    /**
     * @param userId
     * @param providerLogin
     * @param userName
     * @param userCenter
     * @param forcePassword
     */
    public CreateAccountTrustedRequest(String userId, String providerLogin, String userName, String userCenter, String forcePassword)
    {
        this.userId = userId;
        this.provider = "KZ";
        this.providerLogin = providerLogin;
        this.userName = userName;
        this.userCenter = userCenter;
        //this.password = password;
        this.language = "zh-Hans";
        this.address = STSConst.ADDRESS;
        this.forcePassword = forcePassword;
    }


    /**
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }


    /**
     * @return the provider
     */
    public String getProvider()
    {
        return provider;
    }


    /**
     * @return the providerLogin
     */
    public String getProviderLogin()
    {
        return providerLogin;
    }


    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }


    /**
     * @return the userCenter
     */
    public String getUserCenter()
    {
        return userCenter;
    }


    /**
     * @return the language
     */
    public String getLanguage()
    {
        return language;
    }


    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }


    /**
     * @return the forcePassword
     */
    public String getForcePassword()
    {
        return forcePassword;
    }


    @Override
    protected String getProtocol()
    {
        return "Auth";
    }

    @Override
    protected String getCommand()
    {
        //return "CreateAccountTrusted";
        return "CreateAccount";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return CreateAccountTrustedResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("UserId")).setText(userId);
        (request.addElement("Provider")).setText(provider);
        (request.addElement("ProviderLogin")).setText(providerLogin);
        (request.addElement("UserName")).setText(userName);
        (request.addElement("UserCenter")).setText(userCenter);
       /* (request.addElement("Password")).setText(password);*/
        (request.addElement("Language")).setText(language);
        (request.addElement("Address")).setText(address);
        (request.addElement("ForcePassword")).setText(forcePassword);
        return xml;
    }
}
