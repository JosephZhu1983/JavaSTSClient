package com.kongzhonghd.api.Sts;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.sts.config.PortalGateEnum;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 2:41 PM
 */
public class ConnectRequest extends AbstractRequest
{
    private PortalGateEnum connType;
    private String processId;
    private int programId;
    private int buildId;
    private int productTypeId;
    private int appIndex;
    private String address;

    public ConnectRequest(PortalGateEnum connType, int programId, int buildId, int productTypeId, int appIndex)
    {
        this.connType = connType;
        this.programId = programId;
        this.buildId = buildId;
        this.productTypeId = productTypeId;
        this.appIndex = appIndex;
        this.processId = STSConst.PROCESS_ID;
        this.address = STSConst.ADDRESS;
    }

    @Override
    protected String getProtocol()
    {
        return "Sts";
    }

    @Override
    protected String getCommand()
    {
        return "Connect";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return null;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement("Connect");
        (request.addElement("ConnType")).setText(String.valueOf(connType.getGateType()));
        (request.addElement("Program")).setText(String.valueOf(programId));
        (request.addElement("Build")).setText(String.valueOf(buildId));
        (request.addElement("Process")).setText(processId);
        (request.addElement("ProductType")).setText(String.valueOf(productTypeId));
        (request.addElement("AppIndex")).setText(String.valueOf(appIndex));
        (request.addElement("Address")).setText(address);
        return xml;
    }
}
