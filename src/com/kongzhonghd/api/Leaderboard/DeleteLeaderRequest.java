package com.kongzhonghd.api.Leaderboard;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.sts.business.AbstractRouteRequest;
import com.kongzhonghd.sts.business.ToUser;

/**
 * @author stefanie zhao
 * @date 2014-8-14 上午10:31:02
 */
public class DeleteLeaderRequest extends AbstractRouteRequest {
	private String leader;

	public DeleteLeaderRequest(ToUser toUser, String leader) {
		super(toUser);
		this.leader = leader;
	}

	public String getLeader() {
		return leader;
	}

	@Override
	protected String getProtocol() {
		return "Leaderboard";
	}

	@Override
	protected String getCommand() {
		return "DeleteLeader";
	}

	@Override
	public Class<? extends AbstractResponse> getResponseType() {
		return DeleteLeaderResponse.class;
	}

	@Override
	public Document buildToXmlDocument() throws Exception {
		Document xml = DocumentHelper.createDocument();
		Element request = xml.addElement(XML_ROOT);
		(request.addElement("Leader")).setText(leader);
		return xml;
	}
}
