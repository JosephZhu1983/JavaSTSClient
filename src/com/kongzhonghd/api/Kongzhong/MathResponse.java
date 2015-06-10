package com.kongzhonghd.api.Kongzhong;

import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.util.ObjectUtils;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-30
 * Time: PM12:23
 * To change this template use File | Settings | File Templates.
 */
public class MathResponse extends AbstractResponse
{
    private Math math = new Math();
    private long id;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Math getMath()
    {
        return math;
    }

    public void setMath(Math math)
    {
        this.math = math;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        String s = "";
        try
        {
            s = ObjectUtils.toString(this.math);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        (request.addElement("Id")).setText(String.valueOf(id));
        (request.addElement("Math")).setText(s);
        return xml;
    }

    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {
        Element response = xml.getRootElement();
        String s = response.element("Math").getTextTrim();
        this.math = (Math) ObjectUtils.fromString(s);
        this.id = Long.parseLong(response.element("Id").getTextTrim());

    }
}
