package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.Document;
import org.dom4j.Element;


/**
 * @author stefanie zhao
 * @date 2013-11-14 下午04:41:37
 */
public class GetProspectiveUserIdResponse extends AbstractResponse
{
    private String prospectiveUserId;

    public String getProspectiveUserId()
    {

        return prospectiveUserId;

    }


    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {
        Element reply = xml.getRootElement();
        if (reply.element("ProspectiveUserId") != null)
            this.prospectiveUserId = reply.element("ProspectiveUserId").getTextTrim();
    }
}
