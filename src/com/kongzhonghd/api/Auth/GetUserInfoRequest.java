package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/23/13
 * Time: 5:50 PM
 */
public class GetUserInfoRequest extends AbstractRouteRequest
{
    public GetUserInfoRequest(ToUser toUser)
    {
        super(toUser);
    }

    @Override
    protected String getProtocol()
    {
        return "Auth";
    }

    @Override
    protected String getCommand()
    {
        return "GetUserInfo";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return GetUserInfoResponse.class;
    }
}
