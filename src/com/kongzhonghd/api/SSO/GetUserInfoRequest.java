package com.kongzhonghd.api.SSO;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;

/**
 * @author stefanie zhao
 * @date 2014-2-19 下午06:45:46
 */
public class GetUserInfoRequest extends AbstractRequest {

	private String userId;
	private String antiAddiction;

	public GetUserInfoRequest(String userId, String antiAddiction) {
		this.userId = userId;
		this.antiAddiction = antiAddiction;
	}

	@Override
	protected String getProtocol() {
		return "Game.kz.Auth";
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
		(request.addElement("UserId")).setText(userId);
		Element parts = request.addElement("Parts");
		(parts.addElement("AntiAddiction")).setText(antiAddiction);
		return xml;
	}
}
