package com.kongzhonghd.sts.common;

import com.kongzhonghd.sts.exception.STSException;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-31
 * Time: PM2:45
 * To change this template use File | Settings | File Templates.
 * 可解析STS消息
 */
public interface Parsable
{
    void parseFromString(String value) throws STSException;
}
