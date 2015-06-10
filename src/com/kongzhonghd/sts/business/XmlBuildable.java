package com.kongzhonghd.sts.business;

import org.dom4j.Document;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-31
 * Time: PM2:45
 * 可构建XML消息
 */
public interface XmlBuildable
{
    Document buildToXmlDocument() throws Exception;
}
