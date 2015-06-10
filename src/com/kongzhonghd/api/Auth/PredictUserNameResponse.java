package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.Document;
import org.dom4j.Element;


/**
 * @author stefanie zhao
 * @date 2013-11-22 下午01:19:14
 */
public class PredictUserNameResponse extends AbstractResponse
{
    private String userName;

    public String getUserName()
    {
        return userName;
    }

    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {
        Element reply = xml.getRootElement();
        if (reply.element("UserName") != null)
            this.userName = reply.element("UserName").getTextTrim();
    }
}
