package com.kongzhonghd.sts.business;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-28
 * Time: PM1:22
 * To change this template use File | Settings | File Templates.
 * TO的实体
 */
public class ToUser
{
    private ToUserType type;
    private String user;

    public ToUser(ToUserType type, String user)
    {
        this.type = type;
        this.user = user;
    }

    public ToUserType getType()
    {
        return type;
    }

    public String getUser()
    {
        return user;
    }

    public String build()
    {
        return type.getPrefix() + user;
    }
}
