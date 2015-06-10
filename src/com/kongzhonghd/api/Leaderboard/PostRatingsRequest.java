package com.kongzhonghd.api.Leaderboard;

import java.util.Map;

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
public class PostRatingsRequest extends AbstractRouteRequest {
	private String leaderboard;
	private String leader;
	private String world;
	private Map<String, String> ratings;

	public PostRatingsRequest(ToUser toUser, String leaderboard, String leader, String world, Map<String, String> ratings) {
		super(toUser);
		this.leaderboard = leaderboard;
		this.leader = leader;
		this.world = world;
		this.ratings = ratings;
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
	 * @return the world
	 */
	public String getWorld() {
		return world;
	}

	/**
	 * @return the ratings
	 */
	public Map<String, String> getRatings() {
		return ratings;
	}

	@Override
	protected String getProtocol() {
		return "Leaderboard";
	}

	@Override
	protected String getCommand() {
		return "PostRatings";
	}

	@Override
	public Class<? extends AbstractResponse> getResponseType() {
		return PostRatingsResponse.class;
	}

	@Override
	public Document buildToXmlDocument() throws Exception {
		Document xml = DocumentHelper.createDocument();
		Element request = xml.addElement(XML_ROOT);
		(request.addElement("Leaderboard")).setText(leaderboard);
		(request.addElement("Leader")).setText(leader);
		(request.addElement("World")).setText(world);
		Element ratingsEle = request.addElement("Ratings");
		if (ratings != null) {
			ratingsEle.addAttribute("_type", "array");
			for (Map.Entry<String, String> input : ratings.entrySet()) {
				Element rating = ratingsEle.addElement("Rating");
				String component = input.getKey();
				String value = input.getValue();
				(rating.addElement("Component")).setText(component);
				(rating.addElement("Value")).setText(value);
			}
		}

		return xml;
	}
}
