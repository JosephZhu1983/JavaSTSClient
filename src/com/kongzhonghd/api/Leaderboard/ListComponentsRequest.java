package com.kongzhonghd.api.Leaderboard;

import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.sts.business.AbstractRouteRequest;
import com.kongzhonghd.sts.business.ToUser;

/**
 * @author stefanie zhao
 * @date 2014-8-14 上午10:31:02
 */
public class ListComponentsRequest extends AbstractRouteRequest {

	public ListComponentsRequest(ToUser toUser) {
		super(toUser);
	}

	@Override
	protected String getProtocol() {
		return "Leaderboard";
	}

	@Override
	protected String getCommand() {
		return "ListComponents";
	}

	@Override
	public Class<? extends AbstractResponse> getResponseType() {
		return ListComponentsResponse.class;
	}

}
