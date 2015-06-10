package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.Document;
import org.dom4j.Element;


/**
 * @author stefanie zhao
 * @date 2013-11-14 下午04:57:02
 */
public class CreateAccountTrustedResponse extends AbstractResponse
{
    private String userId;
    private int userCenter;
    private String userName;

    public String getUserName()
    {
        return userName;
    }

    public String getUserId()
    {
        return userId;
    }

    public int getUserCenter()
    {
        return userCenter;
    }

    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {
        Element reply = xml.getRootElement();
        if (reply.element("UserId") != null)
            this.userId = reply.element("UserId").getTextTrim();
        if (reply.element("UserCenter") != null)
            this.userCenter = Integer.parseInt(reply.element("UserCenter").getTextTrim());
        if (reply.element("UserName") != null)
            this.userName = reply.element("UserName").getTextTrim();
    }
}
