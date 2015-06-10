package com.kongzhonghd.sts.filter;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.api.Sts.PingRequest;
import com.kongzhonghd.sts.context.*;
import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.sts.pool.STSClientNode;
import com.kongzhonghd.util.LangUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * User: apple
 * Date: 13-8-11
 * Time: PM12:39
 * 管理IoSession的过滤器
 */
public class SessionManagerFilter extends IoFilterAdapter
{
    private static final Log log = LogFactory.getLog(SessionManagerFilter.class);

    @Override
    public void sessionClosed(NextFilter nextFilter, IoSession session)
    {
        if (log.isWarnEnabled())
            log.warn("会话已经关闭：" + session);

        RequestAndResponseContext.removeContext(session);
        PartialMessageContext.removeContext(session);

        try
        {
            STSClientNode node = NetworkContext.getContext(session).getClientNode();
            if (node != null)
            {
                node.disConnect(session);
                if (log.isDebugEnabled())
                    log.debug("已经结束会话：" + session);
                node.connect(false);
                log.debug("已经重新添加一个新的会话");
            }
        }
        catch (Exception ex)
        {
            if (log.isWarnEnabled())
                log.warn("sessionClosed出现错误：" + LangUtils.getExceptionContent(ex));
        }

        nextFilter.sessionClosed(session);
    }

    @Override
    public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) throws STSException
    {
        try
        {
            STSClientNode node = NetworkContext.getContext(session).getClientNode();
            if (node != null)
            {
                node.request(session, new PingRequest());
            }

        }
        catch (Exception ex)
        {
            if (log.isWarnEnabled())
                log.warn("sessionIdle出现错误：" + LangUtils.getExceptionContent(ex));
        }

        nextFilter.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(NextFilter nextFilter, IoSession session, Throwable cause) throws STSException
    {

        try
        {
            if (log.isWarnEnabled())
            {
                String closing = "";
                if (session.isClosing())
                    closing = "（即将关闭）";
                log.warn(STSConst.CRLF + "会话" + session + "出错" + closing + "，错误信息：" + STSConst.CRLF + LangUtils.getExceptionContent(cause));
            }
        }
        catch (Exception ex)
        {

        }
    }
}
