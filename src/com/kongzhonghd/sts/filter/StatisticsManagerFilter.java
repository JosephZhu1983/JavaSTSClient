package com.kongzhonghd.sts.filter;

import com.kongzhonghd.sts.statistics.IoSessionStateChangeCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * User: apple
 * Date: 13-8-11
 * Time: PM12:32
 * 处理统计信息的过滤器
 */
public class StatisticsManagerFilter extends IoFilterAdapter
{
    private static final Log log = LogFactory.getLog(StatisticsManagerFilter.class);
    private IoSessionStateChangeCallback sessionStateChangeCallback;

    public StatisticsManagerFilter(IoSessionStateChangeCallback sessionStateChangeCallback) throws Exception
    {
        if (sessionStateChangeCallback == null)
            throw new Exception("sessionStateChangeCallback参数为空");

        this.sessionStateChangeCallback = sessionStateChangeCallback;
    }

    @Override
    public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception
    {
        if (log.isDebugEnabled())
            log.debug("已经创建会话：" + session);

        sessionStateChangeCallback.sessionCreated();

        nextFilter.sessionCreated(session);
    }

    @Override
    public void sessionOpened(NextFilter nextFilter, IoSession session) throws Exception
    {
        if (log.isInfoEnabled())
            log.info("已经打开会话：" + session);

        sessionStateChangeCallback.sessionOpened();

        nextFilter.sessionOpened(session);
    }

    @Override
    public void sessionClosed(NextFilter nextFilter, IoSession session) throws Exception
    {
        if (log.isInfoEnabled())
            log.info("已经关闭会话：" + session);

        sessionStateChangeCallback.sessionClosed();

        nextFilter.sessionClosed(session);
    }

    @Override
    public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception
    {
        sessionStateChangeCallback.sessionIdled();

        nextFilter.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(NextFilter nextFilter, IoSession session, Throwable cause) throws Exception
    {
        sessionStateChangeCallback.sessionGotException();

        nextFilter.exceptionCaught(session, cause);
    }
}
