package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.Document;
import org.dom4j.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/23/13
 * Time: 6:06 PM
 */
public class GetUserInfoResponse extends AbstractResponse
{
    private String userId;
    private int userCenter;
    private String userName;
    private String loingName;
    private String transferGameCode;
    private Date lastPasswordChange;

    public String getUserId()
    {
        return userId;
    }

    public int getUserCenter()
    {
        return userCenter;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getLoingName()
    {
        return loingName;
    }

    public String getTransferGameCode()
    {
        return transferGameCode;
    }

    public Date getLastPasswordChange()
    {
        return lastPasswordChange;
    }

    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {
        Element reply = xml.getRootElement();
        if (reply.element("UserId") != null)
            this.userId = reply.element("UserId").getTextTrim();
        if (reply.element("UserCenter") != null)
            this.userCenter = Integer.parseInt(reply.element("UserCenter").getTextTrim());
        if (reply.element("UserName") != null)
            this.userName = reply.element("UserName").getTextTrim();
        if (reply.element("LoginName") != null)
            this.loingName = reply.element("LoginName").getTextTrim();
        if (reply.element("TransferGameCode") != null)
            this.transferGameCode = reply.element("TransferGameCode").getTextTrim();
        if (reply.element("LastPasswordChange") != null)
        {
            DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                this.lastPasswordChange = m_ISO8601Local.parse(reply.element("LastPasswordChange").getTextTrim().replace('T', ' ').replace('Z', ' '));
            }
            catch (Exception ex)
            {
                throw new Exception("无法解析LastPasswordChange为日期：" + reply.element("LastPasswordChange").getTextTrim(), ex);
            }
        }
    }
}
