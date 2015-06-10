package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.Document;
import org.dom4j.Element;


/**
 * @author stefanie zhao
 * @date 2014-1-10 上午09:17:24
 */
public class GetWorldIdResponse extends AbstractResponse
{
    private String worldId;
    private String datacenter;

    /**
     * @return the worldId
     */
    public String getWorldId()
    {
        return worldId;
    }


    /**
     * @return the datacenter
     */
    public String getDatacenter()
    {
        return datacenter;
    }

    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {
        Element reply = xml.getRootElement();
        if (reply.element("WorldId") != null)
            this.worldId = reply.element("WorldId").getTextTrim();
        if (reply.element("Datacenter") != null)
            this.datacenter = reply.element("WorldId").getTextTrim();
    }
}
