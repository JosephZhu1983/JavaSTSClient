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
public class ForceSnapshotRequest extends AbstractRouteRequest {
	private String leaderboard;

	public ForceSnapshotRequest(ToUser toUser, String leaderboard) {
		super(toUser);
		this.leaderboard = leaderboard;
	}

	public String getLeaderboard() {
		return leaderboard;
	}

	@Override
	protected String getProtocol() {
		return "Leaderboard";
	}

	@Override
	protected String getCommand() {
		return "ForceSnapshot";
	}

	@Override
	public Class<? extends AbstractResponse> getResponseType() {
		return ForceSnapshotResponse.class;
	}

	@Override
	public Document buildToXmlDocument() throws Exception {
		Document xml = DocumentHelper.createDocument();
		Element request = xml.addElement(XML_ROOT);
		(request.addElement("Leaderboard")).setText(leaderboard);
		return xml;
	}
}
