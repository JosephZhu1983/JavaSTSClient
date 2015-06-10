package com.kongzhonghd.api.Auth;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;


/**
 * @author stefanie zhao
 * @date 2013-11-14 下午04:38:49
 */
public class GetProspectiveUserIdRequest extends AbstractRequest
{


    @Override
    public String getProtocol()
    {
        return "Auth";
    }

    @Override
    public String getCommand()
    {
        return "GetProspectiveUserId";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return GetProspectiveUserIdResponse.class;
    }
}
