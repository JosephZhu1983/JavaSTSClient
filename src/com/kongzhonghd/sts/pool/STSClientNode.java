package com.kongzhonghd.sts.pool;

import com.kongzhonghd.api.Sts.ConnectRequest;
import com.kongzhonghd.sts.business.AbstractRequest;
import com.kongzhonghd.sts.business.AbstractResponse;
import com.kongzhonghd.sts.codec.STSProtocolCodecFactory;
import com.kongzhonghd.sts.common.STSRequestMessage;
import com.kongzhonghd.sts.common.STSResponseMessage;
import com.kongzhonghd.sts.config.ClientSetting;
import com.kongzhonghd.sts.config.PortalSetting;
import com.kongzhonghd.sts.context.NetworkContext;
import com.kongzhonghd.sts.context.RequestAndResponseContext;
import com.kongzhonghd.sts.exception.*;
import com.kongzhonghd.sts.filter.*;
import com.kongzhonghd.sts.statistics.*;
import com.kongzhonghd.util.RequestAndResponseTrigger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoServiceStatistics;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-8-1
 * Time: PM3:44
 * To change this template use File | Settings | File Templates.
 * 一个客户端节点，对应一个Portal地址，管理多个会话
 */
public class STSClientNode implements GetIoServiceStatisticsListCallback
{
    private static final Log log = LogFactory.getLog(STSClientNode.class);
    private final Map<Long, IoServiceStatistics> ioServiceStatisticsList = new HashMap<Long, IoServiceStatistics>();
    private final RoundRobbinScheduler<IoSession> sessions = new RoundRobbinScheduler<IoSession>();
    private final Map<Long, IoConnector> connectors = new HashMap<Long, IoConnector>();
    private final STSClientCluster clientCluster;
    private final ClientSetting clientSetting;
    private final PortalSetting portalSetting;
    private final ReportApiStatisticsCallback reportApiStatisticsCallback;
    private final IoSessionStateChangeCallback ioSessionStateChangeCallback;

    STSClientNode(STSClientCluster clientCluster, ClientSetting clientSetting, PortalSetting portalSetting,
                  ReportApiStatisticsCallback reportApiStatisticsCallback, IoSessionStateChangeCallback ioSessionStateChangeCallback)
    {
        this.clientCluster = clientCluster;
        this.clientSetting = clientSetting;
        this.portalSetting = portalSetting;
        this.reportApiStatisticsCallback = reportApiStatisticsCallback;
        this.ioSessionStateChangeCallback = ioSessionStateChangeCallback;
    }

    public List<IoServiceStatistics> getIoServiceStatisticsList()
    {
        List<IoServiceStatistics> list = new ArrayList<IoServiceStatistics>(ioServiceStatisticsList.values());
        return list;
    }

    private IoConnector initIoConnector() throws STSException
    {
        try
        {
            IoConnector ioConnector = new NioSocketConnector();
            ioConnector.getFilterChain().addFirst("StatisticsManagerFilter", new StatisticsManagerFilter(ioSessionStateChangeCallback));
            ioConnector.getFilterChain().addAfter("StatisticsManagerFilter", "SessionManagerFilter", new SessionManagerFilter());
            ioConnector.getFilterChain().addAfter("SessionManagerFilter", "ProtocolCodecFilter", new ProtocolCodecFilter(new STSProtocolCodecFactory()));
            ioConnector.getFilterChain().addAfter("ProtocolCodecFilter", "LogMessageFilter", new LogMessageFilter());
            ioConnector.getFilterChain().addAfter("LogMessageFilter", "PartialMessageFilter", new PartialMessageFilter());
            ioConnector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, clientSetting.getAutoPingSeconds());
            ioConnector.setHandler(clientCluster.getIoHandler());
            return ioConnector;
        }
        catch (Exception ex)
        {
            throw new STSException("无法初始化一个IoConnector", ex);
        }
    }

    public void connect(boolean quick)
            throws STSException
    {
        if (!quick && log.isDebugEnabled())
            log.debug("开始连接节点：" + this);

        SocketAddress address = new InetSocketAddress(portalSetting.getIp(), portalSetting.getPort());
        IoConnector connector = initIoConnector();
        connector.setConnectTimeoutMillis(5000);
        ConnectFuture future = connector.connect(address);
        future.awaitUninterruptibly();
        if (!future.isConnected())
        {
            connector.dispose();
            connector = null;
            if (!quick)
                clientCluster.nodeFail(this);
            throw new STSException("无法连接到远端地址：" + address.toString());
        }

        IoSession session = future.getSession();
        NetworkContext.setContext(session, new NetworkContext(clientCluster, this));
        sessions.add(session);
        connectors.put(session.getId(), connector);
        ioServiceStatisticsList.put(session.getId(), connector.getStatistics());
        ConnectRequest request = new ConnectRequest(clientSetting.getPortalGate(), clientSetting.getProgramId(), clientSetting.getBuildId(), clientSetting.getProductTypeId(), clientSetting.getAppIndex());
        request(session, request);
    }

    void init() throws STSException
    {
        for (int i = 0; i < portalSetting.getPoolSize(); i++)
        {
            connect(false);
            if (log.isDebugEnabled())
                log.debug("初始化节点完成：" + this);
        }
    }

    public void disConnect(IoSession session)
    {
        long id = session.getId();

        IoConnector connector = connectors.get(id);
        if (connector != null && !connector.isDisposed() && !connector.isDisposing())
        {
            connector.dispose();
            connector = null;
        }
        connectors.remove(id);

        if (session != null && !session.isClosing())
        {
            session.close(false);
            session = null;
        }
        sessions.remove(session);

        ioServiceStatisticsList.remove(id);

        if (log.isDebugEnabled())
            log.debug("已经关闭连接：" + session);

    }

    void close()
    {
        List<IoSession> sessionList = new ArrayList<IoSession>();
        for (IoSession session : sessions)
            sessionList.add(session);

        for (IoSession session : sessionList)
            disConnect(session);

        if (log.isDebugEnabled())
            log.debug("已经关闭节点：" + this);
    }

    AbstractResponse requestAndWaitForResponse(AbstractRequest request)
            throws STSException
    {
        IoSession session = sessions.get();
        return requestAndWaitForResponse(session, request);
    }

    List<AbstractResponse> requestAllNodesAndWaitForResponse(AbstractRequest request)
            throws STSException
    {
        List<AbstractResponse> responses = new ArrayList<AbstractResponse>();
        for (IoSession session : sessions)
        {
            responses.add(requestAndWaitForResponse(session, request));
        }
        return responses;
    }

    private AbstractResponse requestAndWaitForResponse(IoSession session, AbstractRequest request)
            throws STSException
    {
        if (session == null)
            throw new STSIoSessionException(session, "无法获取到有效的连接！");
        STSRequestMessage requestMessage = request.buildToRequestMessage(true);
        RequestAndResponseTrigger<STSRequestMessage, STSResponseMessage> trigger = new RequestAndResponseTrigger<STSRequestMessage, STSResponseMessage>();
        trigger.setRequest(requestMessage);

        int id = requestMessage.getMessageId();
        RequestAndResponseContext requestAndResponseContext = RequestAndResponseContext.getContext(session);
        requestAndResponseContext.set(id, trigger);

        long begin = System.currentTimeMillis();
        session.write(requestMessage);
        reportApiStatisticsCallback.sendRequest(request.getUri());

        String uri = requestMessage.getStartLine().getRequestUri();
        try
        {
            if (!trigger.await(clientSetting.getReplyTimeoutSeconds(), TimeUnit.SECONDS))
            {
                reportApiStatisticsCallback.receiveErrorResponse(uri);
                throw new STSMessageException(requestMessage, "针对此请求没有及时收到回复（不应该出现的情况）");
            }
        }
        catch (InterruptedException ex)
        {
            if (log.isWarnEnabled())
            {
                log.warn("trigger.await出错");
            }
        }

        STSResponseMessage responseMessage = trigger.getResponse();
        if (responseMessage == null)
        {
            reportApiStatisticsCallback.receiveErrorResponse(uri);
            throw new STSMessageException(requestMessage, "请求没有收到回复（可能因为会话出错已经关闭）");
        }

        AbstractResponse response = null;
        try
        {
            response = request.getResponseType().newInstance();
        }
        catch (Exception ex)
        {
            throw new STSException("无法获得一个Response的实例！", ex);
        }

        String remoteAddress = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();
        response.setRemoteAddress(remoteAddress);
        
        int time = (int) (System.currentTimeMillis() - begin);
        try
        {
            response.parseFromResponseMessage(uri, responseMessage);
            reportApiStatisticsCallback.receiveSuccessResponse(uri, time);
        }
        catch (STSException ex)
        {
            reportApiStatisticsCallback.receiveErrorResponse(uri);
            throw ex;
        }
        return response;
    }

    public void request(AbstractRequest request)
            throws STSException
    {
        IoSession session = sessions.get();
        request(session, request);
    }

    public void request(IoSession session, AbstractRequest request)
            throws STSException
    {
        if (session == null)
            throw new STSIoSessionException(session, "无法获取到有效的连接！");

        session.write(request.buildToRequestMessage(false));
        reportApiStatisticsCallback.sendRequest(request.getUri());
    }

    @Override
    public String toString()
    {
        return String.format("（远端地址：%s\t会话数量：%d）", this.portalSetting.getIp() + ":" + this.portalSetting.getPort(), sessions.size());
    }
}
