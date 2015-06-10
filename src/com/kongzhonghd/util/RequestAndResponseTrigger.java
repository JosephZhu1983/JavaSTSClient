package com.kongzhonghd.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * User: JosephZhu
 * Date: 13-8-6
 * Time: PM9:58
 */
public class RequestAndResponseTrigger<Request, Response>
{
    private Request request;
    private Response response;
    private CountDownLatch trigger;

    public RequestAndResponseTrigger()
    {
        trigger = new CountDownLatch(1);
    }

    public Request getRequest()
    {
        return request;
    }

    public void setRequest(Request request)
    {
        this.request = request;
    }

    public Response getResponse()
    {
        return response;
    }

    public void setResponse(Response response)
    {
        this.response = response;
    }

    public void release()
    {
        trigger.countDown();
    }

    public boolean await(long timeout, TimeUnit unit) throws InterruptedException
    {
        return this.trigger.await(timeout, unit);
    }

    public void await() throws InterruptedException
    {
        this.trigger.await();
    }
}

