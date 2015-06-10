package com.kongzhonghd.sts.config;

import com.kongzhonghd.sts.exception.STSException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 1:31 PM
 * STS配置实体
 */
public class STSClientConfig
{
    private ClientSetting clientSetting;
    private List<PortalSetting> portalSettings;

    @SuppressWarnings("unchecked")
    public STSClientConfig(Document xml) throws STSException
    {
        if (xml == null)
            throw new STSException("xml参数为空！");

        Element root = xml.getRootElement();
        if (root == null)
            throw new STSException("配置文件没有根节点！");

        clientSetting = new ClientSetting(root);
        portalSettings = new ArrayList<PortalSetting>();

        if (root.element("Portals") == null)
            throw new STSException("在配置文件中找不到Portals节点！");
        Element portals = root.element("Portals");

        List<Element> portalList = (List<Element>) portals.elements("Portal");
        if (portalList == null || portalList.size() == 0)
            throw new STSException("在配置文件中的Portals节点下找不到Portal子元素！");

        for (Element portal : portalList)
        {
            portalSettings.add(new PortalSetting(portal));
        }
    }

    public ClientSetting getClientSetting()
    {
        return clientSetting;
    }

    public List<PortalSetting> getPortalSettings()
    {
        return portalSettings;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
