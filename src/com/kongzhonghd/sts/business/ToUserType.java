package com.kongzhonghd.sts.business;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-28
 * Time: PM1:22
 * To change this template use File | Settings | File Templates.
 * TO的类型
 */
public enum ToUserType
{
    Id("$"),
    LoginName("@login:");
    private String prefix;

    ToUserType(String prefix)
    {
        this.prefix = prefix;
    }

    public String getPrefix()
    {
        return this.prefix;
    }
}
