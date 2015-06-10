package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-28
 * Time: PM2:33
 * To change this template use File | Settings | File Templates.
 */
public class ListGameAccountsRequest extends AbstractRouteRequest
{
    public ListGameAccountsRequest(ToUser toUser)
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
        return "ListGameAccounts";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return ListGameAccountsResponse.class;
    }
}
