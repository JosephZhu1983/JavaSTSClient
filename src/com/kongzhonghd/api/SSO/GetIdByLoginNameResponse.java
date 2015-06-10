package com.kongzhonghd.api.SSO;

import org.dom4j.Document;
import org.dom4j.Element;

import com.kongzhonghd.sts.business.AbstractResponse;

/**
 * @author stefanie zhao
 * @date 2014-2-19 下午06:45:31
 */
public class GetIdByLoginNameResponse extends AbstractResponse {
	private String id;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	@Override
	public void parseFromXmlDocument(Document xml) throws Exception {
		Element reply = xml.getRootElement();
		if (reply.element("Id") != null)
			this.id = reply.element("Id").getTextTrim();
	}
}
