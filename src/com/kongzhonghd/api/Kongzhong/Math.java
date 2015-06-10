package com.kongzhonghd.api.Kongzhong;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-30
 * Time: PM12:23
 * To change this template use File | Settings | File Templates.
 */
public class Math implements Serializable
{
    private String method;
    private int number1;
    private int number2;
    private int result;

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public int getNumber1()
    {
        return number1;
    }

    public void setNumber1(int number1)
    {
        this.number1 = number1;
    }

    public int getNumber2()
    {
        return number2;
    }

    public void setNumber2(int number2)
    {
        this.number2 = number2;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }
}
