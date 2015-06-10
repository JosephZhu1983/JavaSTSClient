package com.kongzhonghd.api.Kongzhong;

import com.kongzhonghd.sts.business.AbstractRequestHandler;
import com.kongzhonghd.sts.exception.STSRequestException;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-30
 * Time: PM12:23
 * To change this template use File | Settings | File Templates.
 */
public class MathRequestHandler extends AbstractRequestHandler<MathRequest, MathResponse>
{
    @Override
    public MathRequest getRequest()
    {
        return new MathRequest();
    }

    @Override
    public MathResponse handleRequest(MathRequest request) throws STSRequestException
    {
        MathResponse response = new MathResponse();
        response.setId(request.getId());
        response.getMath().setNumber1(request.getMath().getNumber1());
        response.getMath().setNumber2(request.getMath().getNumber2());
        response.getMath().setMethod(request.getMath().getMethod());

        if (request.getMath().getMethod().equals("加"))
        {
            response.getMath().setResult(request.getMath().getNumber1() + request.getMath().getNumber2());
        }
        else if (request.getMath().getMethod().equals("减"))
        {
            response.getMath().setResult(request.getMath().getNumber1() - request.getMath().getNumber2());
        }

        try
        {
            Thread.sleep(request.getMath().getNumber1() * 1000);
        }
        catch (Exception ex)
        {

        }
        return response;
    }
}
