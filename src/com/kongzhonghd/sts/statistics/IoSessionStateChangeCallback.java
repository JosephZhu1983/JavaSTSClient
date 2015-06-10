package com.kongzhonghd.sts.statistics;

/**
 * User: apple
 * Date: 13-8-8
 * Time: PM5:25
 * 会话状态变化的接口
 */
public interface IoSessionStateChangeCallback
{
    void sessionCreated();

    void sessionClosed();

    void sessionGotException();

    void sessionOpened();

    void sessionIdled();
}
