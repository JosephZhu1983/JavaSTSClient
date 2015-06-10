package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-8-1
 * Time: PM3:10
 * To change this template use File | Settings | File Templates.
 */
public class SetRoleRequest extends AbstractRequest
{
    private String roleName;

    public SetRoleRequest(String roleName)
    {
        this.roleName = roleName;
    }

    public String getRoleName()
    {
        return roleName;
    }

    @Override
    protected String getProtocol()
    {
        return "Auth";
    }

    @Override
    protected String getCommand()
    {
        return "SetRole";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return SetRoleResponse.class;
    }

    @Override
    public Document buildToXmlDocument() throws Exception
    {
        Document xml = DocumentHelper.createDocument();
        Element request = xml.addElement(XML_ROOT);
        (request.addElement("Role")).setText(roleName);
        return xml;
    }
}
