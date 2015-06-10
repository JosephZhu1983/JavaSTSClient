package com.kongzhonghd.sts.business;

/**
 * User: JosephZhu
 * Date: 13-8-2
 * Time: PM5:32
 * 错误的响应
 */
public class ErrorResponse extends AbstractResponse
{
    public ErrorResponse(int statusCode, String reasonPhase, Error error)
    {
        this.statusCode = statusCode;
        this.reasonPhase = reasonPhase;
        this.error = error;
    }
}