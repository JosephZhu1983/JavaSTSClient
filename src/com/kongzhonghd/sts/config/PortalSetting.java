package com.kongzhonghd.sts.config;

import com.kongzhonghd.sts.exception.STSException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.dom4j.Element;

/**
 * User: apple
 * Date: 13-8-17
 * Time: PM1:31
 */
public class PortalSetting
{
    private String ip;
    private int port;
    private int poolSize;

    public PortalSetting(Element portal) throws STSException
    {
        if (portal.attribute("IP") == null)
            throw new STSException("在配置文件的AddressList的Address中找不到IP属性！");
        this.ip = portal.attribute("IP").getText();

        if (portal.attribute("Port") == null)
            throw new STSException("在配置文件的AddressList的Address中找不到Port属性！");
        this.port = Integer.parseInt(portal.attribute("Port").getText());

        if (portal.attribute("PoolSize") == null)
            throw new STSException("在配置文件的AddressList的Address中找不到PoolSize属性！");
        this.poolSize = Integer.parseInt(portal.attribute("PoolSize").getText());
    }

    public String getIp()
    {
        return ip;
    }

    public int getPort()
    {
        return port;
    }

    public int getPoolSize()
    {
        return poolSize;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
