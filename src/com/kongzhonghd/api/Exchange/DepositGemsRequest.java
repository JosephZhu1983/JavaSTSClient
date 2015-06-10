package com.kongzhonghd.api.Exchange;

import com.kongzhonghd.sts.business.*;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-30
 * Time: PM3:33
 * To change this template use File | Settings | File Templates.
 */
public class DepositGemsRequest extends AbstractRouteRequest
{
    private int systemId;
    private int sequenceId;
    private int gems;

    public DepositGemsRequest(ToUser toUser, int systemId, int sequenceId, int gems)
    {
        super(toUser);
        this.systemId = systemId;
        this.sequenceId = sequenceId;
        this.gems = gems;
    }

    public int getSystemId()
    {
        return systemId;
    }

    public int getSequenceId()
    {
        return sequenceId;
    }

    public int getGems()
    {
        return gems;
    }

    @Override
    protected String getProtocol()
    {
        return "Game.gw2.Exchange";
    }

    @Override
    protected String getCommand()
    {
        return "DepositGems";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return DepositGemsResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("SystemId")).setText(String.valueOf(systemId));
        (request.addElement("SequenceId")).setText(String.valueOf(sequenceId));
        (request.addElement("Gems")).setText(String.valueOf(gems));
        return xml;
    }
}
