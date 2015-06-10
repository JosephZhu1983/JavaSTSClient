package com.kongzhonghd.api.Sts;

import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/22/13
 * Time: 6:03 PM
 */
public class PingRequest extends AbstractRequest
{
    @Override
    protected String getProtocol()
    {
        return "Sts";
    }

    @Override
    protected String getCommand()
    {
        return "Ping";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseType()
    {
        return null;
    }
}
