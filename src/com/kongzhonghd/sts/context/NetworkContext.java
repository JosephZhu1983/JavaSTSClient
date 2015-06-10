package com.kongzhonghd.sts.context;

import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.sts.exception.STSIoSessionException;
import com.kongzhonghd.sts.pool.STSClientCluster;
import com.kongzhonghd.sts.pool.STSClientNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-31
 * Time: PM8:41
 * To change this template use File | Settings | File Templates.
 * 网络上下文
 */
public class NetworkContext
{
    private static final Log log = LogFactory.getLog(NetworkContext.class);
    private static final String IOSESSION_NETWORK_CONTEXT_KEY = "IOSESSION_NETWORK_CONTEXT";
    private STSClientNode clientNode;
    private STSClientCluster clientCluster;

    public NetworkContext(STSClientCluster clientCluster, STSClientNode clientNode)
    {
        this.clientCluster = clientCluster;
        this.clientNode = clientNode;
    }

    public synchronized static NetworkContext getContext(IoSession session) throws STSException
    {
        NetworkContext context = (NetworkContext) session.getAttribute(IOSESSION_NETWORK_CONTEXT_KEY);
        if (context == null)
            throw new STSIoSessionException(session, "无法从会话上获得NetworkContext");
        return context;
    }

    public synchronized static void setContext(IoSession session, NetworkContext context)
    {
        session.setAttribute(IOSESSION_NETWORK_CONTEXT_KEY, context);
    }

    public STSClientNode getClientNode()
    {
        return clientNode;
    }

    public STSClientCluster getClientCluster()
    {
        return clientCluster;
    }
}
