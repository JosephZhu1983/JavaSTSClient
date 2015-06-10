package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.*;

/**
 * User: apple
 * Date: 14-2-8
 * Time: 下午3:46
 */
public class LogoutMyClientRequest extends AbstractRouteRequest
{
    public LogoutMyClientRequest(ToUser toUser)
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
        return "LogoutMyClient";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return LogoutMyClientResponse.class;
    }
}