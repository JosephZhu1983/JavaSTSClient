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
public class PostLeaderNameRequest extends AbstractRouteRequest {
	private String leaderboard;
	private String leader;
	private String leaderName;

	public PostLeaderNameRequest(ToUser toUser, String leaderboard, String leader, String leaderName) {
		super(toUser);
		this.leaderboard = leaderboard;
		this.leader = leader;
		this.leaderName = leaderName;
	}

	public String getLeader() {
		return leader;
	}

	/**
	 * @return the leaderboard
	 */
	public String getLeaderboard() {
		return leaderboard;
	}

	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}

	@Override
	protected String getProtocol() {
		return "Leaderboard";
	}

	@Override
	protected String getCommand() {
		return "PostLeaderName";
	}

	@Override
	public Class<? extends AbstractResponse> getResponseType() {
		return PostLeaderNameResponse.class;
	}

	@Override
	public Document buildToXmlDocument() throws Exception {
		Document xml = DocumentHelper.createDocument();
		Element request = xml.addElement(XML_ROOT);
		(request.addElement("Leaderboard")).setText(leaderboard);
		(request.addElement("Leader")).setText(leader);
		(request.addElement("LeaderName")).setText(leaderName);
		return xml;
	}
}
