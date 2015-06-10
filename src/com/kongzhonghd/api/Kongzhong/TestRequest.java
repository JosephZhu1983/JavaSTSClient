package com.kongzhonghd.api.Kongzhong;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.sts.exception.STSException;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/25/13
 * Time: 10:13 AM
 */
public class TestRequest extends AbstractRequest
{
    private String parm1;
    private String parm2;

    public TestRequest(String parm1, String parm2)
    {
        this.parm1 = parm1;
        this.parm2 = parm2;
    }

    public TestRequest()
    {

    }

    @Override
    protected String getProtocol()
    {
        return "Game.gw2.Kongzhong";
    }

    @Override
    protected String getCommand()
    {
        return "Test";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return TestResponse.class;
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
    public void parseFromXmlDocument(Document xml) throws STSException
    {
        Element request = xml.getRootElement();
        this.parm1 = request.element("Parm1").getTextTrim();
        this.parm2 = request.element("Parm2").getTextTrim();
    }

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
}
