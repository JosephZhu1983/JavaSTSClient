package com.kongzhonghd.api.Leaderboard;

import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.sts.business.AbstractRouteRequest;
import com.kongzhonghd.sts.business.ToUser;

/**
 * @author stefanie zhao
 * @date 2014-8-14 上午10:31:02
 */
public class ListWorldsRequest extends AbstractRouteRequest {

	public ListWorldsRequest(ToUser toUser) {
		super(toUser);
	}

	@Override
	protected String getProtocol() {
		return "Leaderboard";
	}

	@Override
	protected String getCommand() {
		return "ListWorlds";
	}

	@Override
	public Class<? extends AbstractResponse> getResponseType() {
		return ListWorldsResponse.class;
	}

}
