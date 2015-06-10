package com.kongzhonghd;

import com.kongzhonghd.api.Gate.*;
import com.kongzhonghd.sts.business.*;
import com.kongzhonghd.sts.config.STSClientConfig;
import com.kongzhonghd.sts.exception.STSException;
import com.kongzhonghd.sts.pool.STSClientCluster;
import com.kongzhonghd.sts.statistics.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/21/13
 * Time: 12:40 PM
 * STS客户端的入口
 */
public class STSClient implements GetThreadPoolStatisticsCallback
{
    private static final Log log = LogFactory.getLog(STSClient.class);
    private STSClientConfig config;
    private ThreadPoolExecutor asyncTaskThreadPool;
    private StatisticsManager statisticsManager;
    private STSClientCluster cluster;

    @Override
    public List<ThreadPoolStatistics> getThreadPoolStatistics()
    {
        return new ArrayList<ThreadPoolStatistics>(Arrays.asList(new ThreadPoolStatistics("asyncTaskThreadPool", asyncTaskThreadPool)));
    }

    private void parseConfig(String configName)
            throws STSException
    {

        // URL url = STSClient.class.getResource(configName);
        InputStream in = STSClient.class.getResourceAsStream(configName);
        //File file = new File(configName);
      /*  if (!file.exists())
        {
            try
            {
                file = new File(URLDecoder.decode(url.getFile(), "utf8"));
            }
            catch (Exception e)
            {
                throw new STSException("无法找到配置文件！", e);
            }
        }*/

        SAXReader reader = new SAXReader();

        Document xml = null;
        try
        {
            //xml = reader.read(file);
            xml = reader.read(in);
        }
        catch (DocumentException e)
        {
            throw new STSException("无法读取配置文件！", e);
        }

        config = new STSClientConfig(xml);

        log.debug("解析配置文件完成");
    }

    public synchronized void connect(String configName)
            throws STSException
    {
        parseConfig(configName);
        asyncTaskThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(config.getClientSetting().getAsyncTaskThreadPoolSize());

        statisticsManager = new StatisticsManager();
        cluster = new STSClientCluster(config, statisticsManager);

        //statisticsManager.init(this.config.getClientSetting(), cluster, this, this.cluster.getIoHandler().getRequestMessageHandler());
        cluster.init();
        log.debug("连接客户端完成");
    }

    public synchronized void close() throws STSException
    {
        cluster.close();
        statisticsManager.destory();
        log.debug("关闭客户端完成");
    }

    @SuppressWarnings("unchecked")
    private <T extends AbstractResponse> List<T> requestAllNodesAndWaitForResponse(AbstractRequest request)
            throws STSException
    {
        return (List<T>) cluster.requestAllNodesAndWaitForResponse(request);
    }

    public synchronized void registerService(AbstractRequestHandler... handlers)
            throws STSException
    {
        if (cluster == null)
            throw new STSException("请先调用connect()连接！");

        for (AbstractRequestHandler handler : handlers)
        {
            cluster.registerRequestHandler(handler);
        }

        List<RegisterServiceResponse> registerServiceResponses = this.requestAllNodesAndWaitForResponse(new RegisterServiceRequest(config.getClientSetting().getCustomProtocolNamespace() + STSConst.CUSTOM_PROTOCOL_NAME));
        int success = 0;
        int total = registerServiceResponses.size();
        String reason = "";
        for (RegisterServiceResponse response : registerServiceResponses)
        {
            if (response.isSuccess())
            {
                success++;
            }
            else
            {
                reason = response.getError().getText();
            }
        }
        if (success < total)
            throw new STSException(String.format("注册服务失败！总共需要注册%d个连接，失败%d个！原因是：%s", total, total - success, reason));

        log.debug("注册服务完成");
    }

    public synchronized void unRegisterService()
            throws STSException
    {
        if (cluster == null)
            throw new STSException("请先调用connect()连接！");

        cluster.clearHandlers();

        List<UnregisterServiceResponse> unregisterServiceResponses = this.requestAllNodesAndWaitForResponse(new UnregisterServiceRequest(config.getClientSetting().getCustomProtocolNamespace() + STSConst.CUSTOM_PROTOCOL_NAME));
        int success = 0;
        int total = unregisterServiceResponses.size();
        for (UnregisterServiceResponse response : unregisterServiceResponses)
        {
            if (response.isSuccess())
                success++;
        }
        if (success < total)
            throw new STSException(String.format("反注册服务失败！总共需要注册%d个连接，失败%d个！", total, total - success));

        log.debug("反注册服务完成");
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractResponse> T requestAndWaitForResponse(final AbstractRequest request)
            throws STSException
    {

        return (T) cluster.requestAndWaitForResponse(request);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractResponse> void requestAndExecuteCallbackWhenGetResponse(final AbstractRequest request, final STSCallback<T> callback)
    {
        asyncTaskThreadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    T response = requestAndWaitForResponse(request);
                    callback.whenSuccess(response);
                }
                catch (Exception ex)
                {
                    callback.whenFail(ex);
                }
            }
        });
    }
}
