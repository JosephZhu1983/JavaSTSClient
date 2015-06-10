package com.kongzhonghd.api.Kongzhong;

import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-29
 * Time: PM1:44
 * To change this template use File | Settings | File Templates.
 */
public class TestResponse extends AbstractResponse
{
    private String parm1;
    private String parm2;

    public String getParm1()
    {
        return parm1;
    }

    public void setParm1(String parm1)
    {
        this.parm1 = parm1;
    }

    public String getParm2()
    {
        return parm2;
    }

    public void setParm2(String parm2)
    {
        this.parm2 = parm2;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("Parm1")).setText(parm1);
        (request.addElement("Parm2")).setText(parm2);
        return xml;
    }

    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {
        try
        {
            Element reply = xml.getRootElement();
            this.parm1 = reply.element("Parm1").getTextTrim();
            this.parm2 = reply.element("Parm2").getTextTrim();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
