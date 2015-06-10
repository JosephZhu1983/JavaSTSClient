package com.kongzhonghd.api.Kongzhong;

import com.kongzhonghd.sts.business.AbstractRequest;
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
public class MathRequest extends AbstractRequest
{
    private Math math = new Math();

    public long getId()
    {
        return id;
    }

    private long id;

    public MathRequest()
    {

    }

    public MathRequest(long id, String method, int number1, int number2)
    {
        this.id = id;
        math.setMethod(method);
        math.setNumber1(number1);
        math.setNumber2(number2);
    }

    @Override
    protected String getProtocol()
    {
        return "Game.gw2.Kongzhong";
    }

    @Override
    protected String getCommand()
    {
        return "Math";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return MathResponse.class;
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
        try
        {
            Element request = xml.getRootElement();
            String s = request.element("Math").getTextTrim();
            this.math = (Math) ObjectUtils.fromString(s);
            this.id = Long.parseLong(request.element("Id").getTextTrim());
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Math getMath()
    {
        return math;
    }
}
