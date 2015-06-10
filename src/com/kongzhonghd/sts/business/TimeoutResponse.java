package com.kongzhonghd.sts.business;

/**
 * User: JosephZhu
 * Date: 13-8-2
 * Time: PM4:18
 * 超时的响应
 */
public class TimeoutResponse extends AbstractResponse
{
    public TimeoutResponse(String text)
    {
        this.statusCode = 400;
        this.reasonPhase = "ErrTimeout";
        this.error = new Error(0, 0, 0, 0, text);
    }
}
