package com.kongzhonghd.api.SSO;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.sts.business.AbstractRouteRequest;
import com.kongzhonghd.sts.business.ToUser;

/**
 * @author stefanie zhao
 * @date 2014-2-19 下午06:45:46
 */
public class GetIdByLoginNameRequest extends AbstractRouteRequest {

	private String loginName;
	private String address;
	private String gameCode;

	public GetIdByLoginNameRequest(ToUser toUser, String loginName) {
		super(toUser);
		this.loginName = loginName;
		this.address = STSConst.ADDRESS;
		this.gameCode = "gw2cn";
	}

	@Override
	protected String getProtocol() {
		return "Game.kz.Auth";
	}

	@Override
	protected String getCommand() {
		return "GetIdByLoginName";
	}

	@Override
	public Class<? extends AbstractResponse> getResponseType() {
		return GetIdByLoginNameResponse.class;
	}

	@Override
	public Document buildToXmlDocument() throws Exception {
		Document xml = DocumentHelper.createDocument();
		Element request = xml.addElement(XML_ROOT);
		(request.addElement("LoginName")).setText(loginName);
		(request.addElement("Address")).setText(address);
		(request.addElement("GameCode")).setText(gameCode);
		return xml;
	}
}
