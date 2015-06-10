package com.kongzhonghd.api.LogQuery;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.*;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-8-1
 * Time: PM3:00
 * To change this template use File | Settings | File Templates.
 */
public class SearchTablesRequest extends AbstractRequest
{
    private Date startTime;
    private Date endTime;

    public SearchTablesRequest(Date startTime, Date endTime)
    {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    @Override
    protected String getProtocol()
    {
        return "LogQuery";
    }

    @Override
    protected String getCommand()
    {
        return "SearchTables";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return SearchTablesResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("StartTime")).setText(startTime.toString());
        (request.addElement("EndTime")).setText(endTime.toString());
        return xml;
    }
}
