package com.kongzhonghd.api.presence;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.kongzhonghd.sts.business.*;

/**
 * @author stefanie zhao
 * @date 2015-1-6 下午01:28:24
 */
public class GetUserInfoRequest extends AbstractRouteRequest {
	public GetUserInfoRequest(ToUser toUser) {
		super(toUser);
	}

	@Override
	protected String getProtocol() {
		return "Presence";
	}

	@Override
	protected String getCommand() {
		return "GetUserInfo";
	}

	@Override
	public Class<? extends AbstractResponse> getResponseType() {
		return GetUserInfoResponse.class;
	}

	@Override
	public Document buildToXmlDocument() throws Exception {
		Document xml = DocumentHelper.createDocument();
		Element request = xml.addElement(XML_ROOT);
		Element parts = request.addElement("Parts");
		parts.addElement("Alias");
		parts.addElement("AppData");
		parts.addElement("AppDataPrivate");
		parts.addElement("Channels");
		parts.addElement("Contacts");
		parts.addElement("Groups");
		parts.addElement("Logins");
		parts.addElement("Logins2");
		parts.addElement("Status");
		parts.addElement("Talk");
		parts.addElement("Whisper");
		return xml;
	}
}
