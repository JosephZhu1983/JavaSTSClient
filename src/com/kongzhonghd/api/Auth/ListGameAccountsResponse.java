package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.Document;
import org.dom4j.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-28
 * Time: PM2:33
 * To change this template use File | Settings | File Templates.
 */
public class ListGameAccountsResponse extends AbstractResponse
{
    private List<GameAccountInfo> gameAccountInfoList = new ArrayList<GameAccountInfo>();

    public List<GameAccountInfo> getGameAccountInfoList()
    {
        return gameAccountInfoList;
    }

    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {
        Element reply = xml.getRootElement();
        for (Object item : reply.elements())
        {
            Element row = (Element) item;
            GameAccountInfo accountInfo = new GameAccountInfo();
            if (row.element("Alias") != null)
                accountInfo.alias = row.element("Alias").getTextTrim();
            if (row.element("GameCode") != null)
                accountInfo.gameCode = row.element("GameCode").getTextTrim();
            if (row.element("GameAccount") != null)
                accountInfo.gameAccount = row.element("GameAccount").getTextTrim();
            if (row.element("Created") != null)
            {
                DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try
                {
                    accountInfo.created = m_ISO8601Local.parse(row.element("Created").getTextTrim().replace('T', ' ').replace('Z', ' '));
                }
                catch (Exception ex)
                {
                    throw new Exception("无法解析Created为日期：" + row.element("Created").getTextTrim(), ex);
                }
            }
            gameAccountInfoList.add(accountInfo);
        }
    }

    public class GameAccountInfo
    {
        private String gameAccount;
        private String gameCode;
        private String alias;
        private Date created;

        public String getGameAccount()
        {
            return gameAccount;
        }

        public void setGameAccount(String gameAccount)
        {
            this.gameAccount = gameAccount;
        }

        public String getGameCode()
        {
            return gameCode;
        }

        public void setGameCode(String gameCode)
        {
            this.gameCode = gameCode;
        }

        public String getAlias()
        {
            return alias;
        }

        public void setAlias(String alias)
        {
            this.alias = alias;
        }

        public Date getCreated()
        {
            return created;
        }

        public void setCreated(Date created)
        {
            this.created = created;
        }
    }
}

