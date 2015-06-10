package com.kongzhonghd.api.Partner;

import com.kongzhonghd.sts.business.AbstractResponse;
import org.dom4j.Document;
import org.dom4j.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-30
 * Time: PM3:46
 * To change this template use File | Settings | File Templates.
 */
public class GetAccountSkusResponse extends AbstractResponse
{
    private List<AccountSku> accountSkuList = new ArrayList<AccountSku>();

    public List<AccountSku> getAccountSkuList()
    {
        return accountSkuList;
    }

    @Override
    public void parseFromXmlDocument(Document xml) throws Exception
    {
        Element reply = xml.getRootElement();
        if (reply.element("Skus") != null)
        {
            Element skus = reply.element("Skus");
            for (Object item : skus.elements())
            {
                Element sku = (Element) item;
                AccountSku accountSku = new AccountSku();
                if (sku.element("Id") != null)
                    accountSku.id = sku.element("Id").getTextTrim();
                if (sku.element("DateActivated") != null)
                {
                    DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try
                    {
                        accountSku.dateActivated = m_ISO8601Local.parse(sku.element("DateActivated").getTextTrim().replace('T', ' ').replace('Z', ' '));
                    }
                    catch (Exception ex)
                    {
                        throw new Exception("无法解析DateActivated为日期：" + sku.element("DateActivated").getTextTrim(), ex);
                    }
                }
                accountSkuList.add(accountSku);
            }
        }
    }

    public class AccountSku
    {
        private Date dateActivated;
        private String id;

        public Date getDateActivated()
        {
            return dateActivated;
        }

        public void setDateActivated(Date dateActivated)
        {
            this.dateActivated = dateActivated;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

    }
}
