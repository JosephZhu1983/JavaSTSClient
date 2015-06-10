package com.kongzhonghd.sts.business;

import com.kongzhonghd.sts.common.STSRequestHeader;
import com.kongzhonghd.sts.common.STSRequestMessage;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/23/13
 * Time: 5:52 PM
 * 带有TO头的请求
 */
public abstract class AbstractDigestRouteRequest extends AbstractRequest
{
    private ToDigest toDigest;

    protected AbstractDigestRouteRequest(ToDigest toDigest)
    {
        this.toDigest = toDigest;
    }

    @Override
    protected void extraStep(STSRequestMessage message)
    {
        STSRequestHeader header = message.getHeader();
        header.setTo(toDigest.build());
    }
}

