package com.kongzhonghd.sts.business;

import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.util.XmlUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/22/13
 * Time: 6:01 PM
 * 响应中的Error主体
 */
public class Error implements XmlParsable, XmlBuildable
{
    private static final String XML_ERROR_ROOT = "Error";
    private static final Log log = LogFactory.getLog(Error.class);
    private int code = 0;
    private int server = 0;
    private int module = 0;
    private int line = 0;
    private String text = "";

    public Error(int code, int server, int module, int line, String text)
    {
        this.code = code;
        this.server = server;
        this.module = module;
        this.line = line;
        this.text = text;
    }

    public Error()
    {

    }

    public int getCode()
    {
        return code;
    }

    public String getText()
    {
        return text;
    }

    public int getServer()
    {
        return server;
    }

    public int getModule()
    {
        return module;
    }

    public int getLine()
    {
        return line;
    }

    public void parseFromXmlDocument(Document xml) throws STSException
    {
        Element root = xml.getRootElement();
        if (!root.getName().equals(XML_ERROR_ROOT))
            throw new STSException("收到错误消息的节点名为：" + root.getName() + "，期望为：" + XML_ERROR_ROOT);

        if (root.attribute("code") != null)
            this.code = Integer.parseInt(root.attribute("code").getValue());
        if (root.attribute("server") != null)
            this.server = Integer.parseInt(root.attribute("server").getValue());
        if (root.attribute("line") != null)
            this.line = Integer.parseInt(root.attribute("line").getValue());
        if (root.attribute("module") != null)
            this.module = Integer.parseInt(root.attribute("module").getValue());
        if (root.attribute("text") != null)
            this.text = root.attribute("text").getValue();
    }

    public Document buildToXmlDocument()
    {
        Document xml = DocumentHelper.createDocument();
        Element error = xml.addElement(XML_ERROR_ROOT);
        error.addAttribute("code", String.valueOf(code));
        error.addAttribute("server", String.valueOf(server));
        error.addAttribute("line", String.valueOf(line));
        error.addAttribute("module", String.valueOf(module));
        error.addAttribute("text", text);
        return xml;
    }

    @Override
    public String toString()
    {
        return XmlUtils.xmlToString(buildToXmlDocument());
    }
}
