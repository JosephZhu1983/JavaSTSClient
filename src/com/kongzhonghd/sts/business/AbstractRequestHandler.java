package com.kongzhonghd.sts.business;

import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.sts.exception.STSRequestException;
import com.kongzhonghd.util.RequestAndResponseTrigger;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-29
 * Time: PM1:54
 * To change this template use File | Settings | File Templates.
 * 抽象的请求处理程序，所有请求处理程序都要继承它
 */
public abstract class AbstractRequestHandler<Request extends AbstractRequest, Response extends AbstractResponse>
{
    public abstract Request getRequest();

    protected abstract Response handleRequest(Request request) throws STSRequestException;

    public void handle(RequestAndResponseTrigger<Request, Response> trigger) throws STSException
    {
        Request request = trigger.getRequest();
        if (request == null)
            throw new STSException("无法获得请求");

        Response response = handleRequest(request);
        if (response == null)
            throw new STSRequestException(request, "处理程序没有返回响应");

        trigger.setResponse(response);
        trigger.release();
    }
}
