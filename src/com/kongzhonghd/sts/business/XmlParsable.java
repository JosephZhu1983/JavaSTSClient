package com.kongzhonghd.sts.business;

import org.dom4j.Document;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-31
 * Time: PM2:45
 * To change this template use File | Settings | File Templates.
 * 可从解析XML消息
 */
public interface XmlParsable
{
    void parseFromXmlDocument(Document xml) throws Exception;
}
