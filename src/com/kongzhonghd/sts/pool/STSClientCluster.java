package com.kongzhonghd.sts.pool;

import com.kongzhonghd.sts.business.*;
import com.kongzhonghd.sts.config.PortalSetting;
import com.kongzhonghd.sts.config.STSClientConfig;
import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.sts.handler.STSIoHandler;
import com.kongzhonghd.sts.statistics.GetIoServiceStatisticsListCallback;
import com.kongzhonghd.sts.statistics.StatisticsManager;
import com.kongzhonghd.util.LangUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoServiceStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-8-1
 * Time: PM3:42
 * To change this template use File | Settings | File Templates.
 * 客户端集群，管理多个Portal地址的连接
 */
public class STSClientCluster implements GetIoServiceStatisticsListCallback
{
    private static final Log log = LogFactory.getLog(STSClientCluster.class);
    private final RoundRobbinScheduler<STSClientNode> nodes = new RoundRobbinScheduler<STSClientNode>();
    private final List<STSClientNode> failedNodes = new ArrayList<STSClientNode>();
    private final STSClientConfig config;
    private Thread nodeRecoveryThread;
    private boolean nodeRecoveryThreadWork = true;
    private STSIoHandler ioHandler;
    private StatisticsManager statisticsManager;

    public STSClientCluster(STSClientConfig config, StatisticsManager statisticsManager)
    {
        this.config = config;
        this.statisticsManager = statisticsManager;
        ioHandler = new STSIoHandler(config.getClientSetting(), statisticsManager.getReportApiStatisticsCallback());
    }

    public STSIoHandler getIoHandler()
    {
        return ioHandler;
    }

    public void registerRequestHandler(AbstractRequestHandler handler)
    {
        ioHandler.registerRequestHandler(handler);
    }

    public void clearHandlers()
    {
        ioHandler.clearHandlers();
    }

    public AbstractResponse requestAndWaitForResponse(AbstractRequest request)
            throws STSException
    {
        STSClientNode node = nodes.get();
        return requestAndWaitForResponse(node, request);
    }

    public void request(AbstractRequest request)
            throws STSException
    {
        STSClientNode node = nodes.get();
        request(node, request);
    }

    public List<IoServiceStatistics> getIoServiceStatisticsList()
    {
        List<IoServiceStatistics> ioServiceStatisticsList = new ArrayList<IoServiceStatistics>();

        for (STSClientNode node : nodes)
            ioServiceStatisticsList.addAll(node.getIoServiceStatisticsList());

        return ioServiceStatisticsList;
    }

    private void initRecoveryThread()
    {
        nodeRecoveryThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (nodeRecoveryThreadWork)
                {
                    try
                    {

                        Thread.sleep(1000);
                    }
                    catch (Exception ex)
                    {

                    }

                    List<STSClientNode> clientNodeList = new ArrayList<STSClientNode>();
                    for (STSClientNode ndoe : failedNodes)
                        clientNodeList.add(ndoe);

                    for (STSClientNode failedNode : clientNodeList)
                    {
                        try
                        {
                            failedNode.connect(true);
                            failedNodes.remove(failedNode);
                            nodes.add(failedNode);
                            if (log.isInfoEnabled())
                                log.info("节点已经恢复：" + failedNode);
                        }
                        catch (STSException ex)
                        {
                            if (log.isDebugEnabled())
                                log.debug("节点尚未恢复：" + failedNode + "，原因：" + ex.toString());
                        }
                    }

                }
            }
        });
        nodeRecoveryThread.start();
        log.debug("启动恢复节点的线程");
    }

    public void init() throws STSException
    {
        initRecoveryThread();


        for (PortalSetting portalSetting : config.getPortalSettings())
        {
            try
            {
                STSClientNode node = new STSClientNode(this, config.getClientSetting(), portalSetting, statisticsManager.getReportApiStatisticsCallback(), statisticsManager.getIoSessionStateChangeCallback());
                node.init();
                nodes.add(node);
            }
            catch (STSException ex)
            {
                if (log.isWarnEnabled())
                    log.warn("初始化节点的时候发生错误：" + LangUtils.getExceptionContent(ex));
            }
        }

        if (nodes.size() == 0)
        {
            nodeRecoveryThreadWork = false;
            throw new STSException("所有地址都无法连接上，无法初始化集群：" + this);
        }

        if (log.isDebugEnabled())
            log.debug("初始化集群完成" + this);
    }

    public void close() throws STSException
    {
        nodeRecoveryThreadWork = false;

        for (STSClientNode node : nodes)
            node.close();

        if (log.isDebugEnabled())
            log.debug("已经关闭集群：" + this);
    }

    public List<AbstractResponse> requestAllNodesAndWaitForResponse(AbstractRequest request)
            throws STSException
    {
        List<AbstractResponse> responses = new ArrayList<AbstractResponse>();

        for (STSClientNode node : nodes)
        {
            responses.addAll(node.requestAllNodesAndWaitForResponse(request));
        }
        return responses;
    }

    private AbstractResponse requestAndWaitForResponse(STSClientNode node, AbstractRequest request)
            throws STSException
    {
        if (node == null)
            throw new STSException("无法获取到有效的连接！");

        return node.requestAndWaitForResponse(request);
    }

    private void request(STSClientNode node, AbstractRequest request)
            throws STSException
    {
        if (node == null)
            throw new STSException("无法获取到有效的连接！");

        node.request(request);
    }

    public synchronized void nodeFail(STSClientNode node)
    {
        nodes.remove(node);
        failedNodes.add(node);

        if (log.isWarnEnabled())
            log.warn("节点已经失效：" + node);
    }

    @Override
    public String toString()
    {
        return String.format("（有效节点数量：%d\t失效节点数量：%d\t配置信息：%s）", nodes.size(), failedNodes.size(), config.toString());
    }
}
