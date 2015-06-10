package com.kongzhonghd.api.Kongzhong;

import com.kongzhonghd.sts.business.AbstractRequestHandler;
import com.kongzhonghd.sts.exception.STSRequestException;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-29
 * Time: PM1:54
 * To change this template use File | Settings | File Templates.
 */
public class TestRequestHandler extends AbstractRequestHandler<TestRequest, TestResponse>
{
    @Override
    public TestRequest getRequest()
    {
        return new TestRequest();
    }

    @Override
    public TestResponse handleRequest(TestRequest request) throws STSRequestException
    {
        TestResponse response = new TestResponse();
        response.setParm1("yes" + request.getParm1());
        response.setParm2("yes" + request.getParm2());
        return response;
    }
}
