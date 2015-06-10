package com.kongzhonghd.sts.statistics;

import com.kongzhonghd.sts.config.ClientSetting;
import com.kongzhonghd.sts.statistics.model.*;
import com.kongzhonghd.util.CommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * User: JosephZhu
 * Date: 13-8-5
 * Time: AM11:39
 * 统计信息管理器
 */
public class StatisticsManager
{
    private static final Log log = LogFactory.getLog(StatisticsManager.class);
    private Thread t;
    private boolean work = true;
    private boolean enable = false;
    private GeneralApiStatistics generalApiStatistics;
    private GeneralIoStatistics generalIoStatistics;
    private Map<String, ThreadPoolStatistics> threadPoolStatisticsMap = new HashMap<String, ThreadPoolStatistics>();
    private StatisticsPersistenceService psService;
    private int writeCount = 1;

    public StatisticsManager()
    {
        generalApiStatistics = new GeneralApiStatistics();
        generalIoStatistics = new GeneralIoStatistics();
    }

    public IoSessionStateChangeCallback getIoSessionStateChangeCallback()
    {
        return generalIoStatistics;
    }

    public ReportApiStatisticsCallback getReportApiStatisticsCallback()
    {
        return generalApiStatistics;
    }

    public void init(final ClientSetting clientSetting,
                     final GetIoServiceStatisticsListCallback getIoServiceStatisticsListCallback,
                     final GetThreadPoolStatisticsCallback... getThreadPoolStatisticsCallbacks)
    {
        //psService = StatisticsPersistenceServiceImpl.getInstance();
        enable = clientSetting.isEnableStatisticsReport();
        generalApiStatistics.setEnable(enable);

        if (enable)
        {
            t = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while (work)
                    {
                        try
                        {
                            Thread.sleep(clientSetting.getReportStatisticsSeconds() * 1000);
                        }
                        catch (Exception ex)
                        {

                        }

                        for (GetThreadPoolStatisticsCallback getThreadPoolStatisticsCallback : getThreadPoolStatisticsCallbacks)
                        {
                            for (ThreadPoolStatistics item : getThreadPoolStatisticsCallback.getThreadPoolStatistics())
                                threadPoolStatisticsMap.put(item.getName(), item);
                        }
                        generalIoStatistics.updateData(getIoServiceStatisticsListCallback.getIoServiceStatisticsList());
                        generalIoStatistics.updateThroughput(clientSetting.getReportStatisticsSeconds());
                        generalApiStatistics.updateThroughput(clientSetting.getReportStatisticsSeconds());

                        //writeStatisticsToDb();
                    }
                }
            });
            t.start();
            log.debug("启动提交统计数据的线程");
        }

        log.debug("初始化统计数据管理器完成");
    }

    public void destory()
    {
        work = false;
        psService.destory();
    }

    private void writeStatisticsToDb()
    {
        log.warn("====================================================IO计数器====================================================\r\n" + generalIoStatistics + "\r\n");
        log.warn("====================================================API计数器====================================================\r\n" + generalApiStatistics + "\r\n");
        for (ThreadPoolStatistics threadPoolStatistics : threadPoolStatisticsMap.values())
        {
            log.warn("====================================================线程池计数器：" + threadPoolStatistics.getName() + "====================================================\r\n" + threadPoolStatistics + "\r\n");
        }

        System.out.println("writeStatisticsToDb:" + writeCount++);
        //  psService=StatisticsPersistenceServiceImpl.getInstance();
        //线程池日志数据
        for (ThreadPoolStatistics threadPoolStatistics : threadPoolStatisticsMap.values())
        {
            TstsThreadpoolPerformanceLog threadPoolLog = getThreadPoolLog(new TstsThreadpoolPerformanceLog(), threadPoolStatistics);
            psService.saveThreadPoolLog(threadPoolLog);
        }
        //api detail 日志数据
        for (ApiStatistics detailApiStatistics : generalApiStatistics.getApiStatisticsList().values())
        {
            //未知api id
            ApiDetailLog apiDetaillog = getApiDetaillog(new ApiDetailLog(), detailApiStatistics, 1l);
            psService.saveApiDetailLog(apiDetaillog);
        }
        //api日志数据
        ApiLog apiLog = getApilog(new ApiLog());
        psService.saveApiLog(apiLog);
        //网络io数据
        NetworkLog netLog = getNetworkLog(new NetworkLog());
        psService.saveNetlog(netLog);

    }

    /**
     * @param @param  tstsThreadpoolPerformanceLog
     * @param @return
     * @return TstsThreadpoolPerformanceLog
     * @throws
     * @Description: TODO
     */
    private TstsThreadpoolPerformanceLog getThreadPoolLog(
            TstsThreadpoolPerformanceLog tstsThreadpoolPerformanceLog, ThreadPoolStatistics threadPoolStatistics)
    {
        tstsThreadpoolPerformanceLog.setActivecount(threadPoolStatistics.getActiveCount());
        tstsThreadpoolPerformanceLog.setCompletedtaskcount(threadPoolStatistics.getCompletedTaskCount());
        tstsThreadpoolPerformanceLog.setCorepoolsize(threadPoolStatistics.getCorePoolSize());
        tstsThreadpoolPerformanceLog.setIpaddr(CommonUtil.getIp());
        tstsThreadpoolPerformanceLog.setLargestpoolsize(threadPoolStatistics.getLargestPoolSize());
        tstsThreadpoolPerformanceLog.setLogtime(new Date());
        tstsThreadpoolPerformanceLog.setMaximumpoolsize(threadPoolStatistics.getMaximumPoolSize());
        tstsThreadpoolPerformanceLog.setName(threadPoolStatistics.getName());
        tstsThreadpoolPerformanceLog.setPoolsize(threadPoolStatistics.getPoolSize());
        tstsThreadpoolPerformanceLog.setTaskcount(threadPoolStatistics.getTaskCount());
        return tstsThreadpoolPerformanceLog;
    }

    private NetworkLog getNetworkLog(NetworkLog networkLog)
    {
        networkLog.setIPAddr(CommonUtil.getIp());
        networkLog.setLargestReadBytesThroughput(generalIoStatistics.getLargestReadBytesThroughput());
        networkLog.setLargestReadMessagesThroughput(generalIoStatistics.getLargestReadMessagesThroughput());
        networkLog.setLargestWrittenBytesThroughput(generalIoStatistics.getLargestWrittenBytesThroughput());

        networkLog.setLargestWrittenMessagesThroughput(generalIoStatistics.getLargestWrittenMessagesThroughput());
        networkLog.setLastReadTime(generalIoStatistics.getLastReadTime());
        networkLog.setLastWriteTime(generalIoStatistics.getLastWriteTime());
        networkLog.setLogtime(new Date());

        networkLog.setReadBytes(generalIoStatistics.getReadBytes());
        networkLog.setReadBytesThroughput(generalIoStatistics.getReadBytesThroughput());
        networkLog.setReadMessages(generalIoStatistics.getReadMessages());
        networkLog.setReadMessagesThroughput(generalIoStatistics.getReadMessagesThroughput());

        networkLog.setWrittenBytes(generalIoStatistics.getWrittenBytes());
        networkLog.setWrittenBytesThroughput(generalIoStatistics.getWrittenBytesThroughput());
        networkLog.setWrittenMessages(generalIoStatistics.getWrittenMessages());
        networkLog.setWrittenMessagesThroughput(generalIoStatistics.getWrittenMessagesThroughput());

        return networkLog;
    }

    private ApiLog getApilog(ApiLog apiLog)
    {
        // /\*{1,2}[\s\S]*?\*/


        apiLog.setSendRequestTimes(generalApiStatistics.getSendRequestTimes().longValue());
        apiLog.setReceiveSuccessResponseTimes(generalApiStatistics.getReceiveSuccessResponseTimes().longValue());
        apiLog.setReceiveErrorResponseTimes(generalApiStatistics.getReceiveErrorResponseTimes().longValue());
        apiLog.setSendRequestThroughput(generalApiStatistics.getSendRequestThroughput());
        apiLog.setReceiveSuccessResponseThroughput(generalApiStatistics.getReceiveSuccessResponseThroughput());

        apiLog.setReceiveErrorResponseThroughput(generalApiStatistics.getReceiveErrorResponseThroughput());
        apiLog.setLargestSendRequestThroughput(generalApiStatistics.getLargestSendRequestThroughput());
        apiLog.setLargestReceiveSuccessResponseThroughput(generalApiStatistics.getLargestReceiveSuccessResponseThroughput());
        apiLog.setLargestReceiveErrorResponseThroughput(generalApiStatistics.getLargestReceiveErrorResponseThroughput());
        apiLog.setLastSendRequestTime(generalApiStatistics.getLastSendRequestTime());

        apiLog.setLastReceiveSuccessResponseTime(generalApiStatistics.getLastReceiveSuccessResponseTime());
        apiLog.setLastReceiveErrorResponseTime(generalApiStatistics.getLastReceiveErrorResponseTime());
        apiLog.setAverageReceiveSuccessResponseMilliseconds(generalApiStatistics.getAverageReceiveSuccessResponseMilliseconds());
        apiLog.setReceiveSuccessRequestTimes(generalApiStatistics.getReceiveSuccessRequestTimes().longValue());
        apiLog.setReceiveErrorRequestTimes(generalApiStatistics.getReceiveErrorRequestTimes().longValue());

        apiLog.setSendSuccessResponseTimes(generalApiStatistics.getSendSuccessResponseTimes().longValue());
        apiLog.setSendErrorResponseTimes(generalApiStatistics.getSendErrorResponseTimes().longValue());
        apiLog.setReceiveSuccessRequestThroughput(generalApiStatistics.getReceiveSuccessRequestThroughput());
        apiLog.setReceiveErrorRequestThroughput(generalApiStatistics.getReceiveErrorRequestThroughput());
        apiLog.setSendSuccessResponseThroughput(generalApiStatistics.getSendSuccessResponseThroughput());

        apiLog.setSendErrorResponseThroughput(generalApiStatistics.getSendErrorResponseThroughput());
        apiLog.setLargestReceiveSuccessRequestThroughput(generalApiStatistics.getLargestReceiveSuccessRequestThroughput());
        apiLog.setLargestReceiveErrorRequestThroughput(generalApiStatistics.getLargestReceiveErrorRequestThroughput());
        apiLog.setLargestSendSuccessResponseThroughput(generalApiStatistics.getLargestSendSuccessResponseThroughput());
        apiLog.setLargestSendErrorResponseThroughput(generalApiStatistics.getLargestSendErrorResponseThroughput());

        apiLog.setLastReceiveSuccessRequestTime(generalApiStatistics.getLastReceiveSuccessRequestTime());
        apiLog.setLastReceiveErrorRequestTime(generalApiStatistics.getLastReceiveErrorRequestTime());
        apiLog.setLastSendSuccessResponseTime(generalApiStatistics.getLastSendSuccessResponseTime());
        apiLog.setLastSendErrorResponseTime(generalApiStatistics.getLastSendErrorResponseTime());
        apiLog.setAverageSendSuccessResponseMilliseconds(generalApiStatistics.getAverageSendSuccessResponseMilliseconds());

        apiLog.setIPAddr(CommonUtil.getIp());
        apiLog.setLogtime(new Date());
        return apiLog;
    }

    private ApiDetailLog getApiDetaillog(ApiDetailLog apiDetaillog, ApiStatistics apiSta, Long APIID)
    {

        apiDetaillog.setAPIID(APIID);
        apiDetaillog.setAPIKey("api-key");
        apiDetaillog.setSendRequestTimes(apiSta.getSendRequestTimes().longValue());
        apiDetaillog.setReceiveSuccessResponseTimes(apiSta.getReceiveSuccessResponseTimes().longValue());
        apiDetaillog.setReceiveErrorResponseTimes(apiSta.getReceiveErrorResponseTimes().longValue());
        apiDetaillog.setSendRequestThroughput(apiSta.getSendRequestThroughput());

        apiDetaillog.setReceiveSuccessResponseThroughput(apiSta.getReceiveSuccessResponseThroughput());
        apiDetaillog.setReceiveErrorResponseThroughput(apiSta.getReceiveErrorResponseThroughput());
        apiDetaillog.setLargestSendRequestThroughput(apiSta.getLargestSendRequestThroughput());
        apiDetaillog.setLargestReceiveSuccessResponseThroughput(apiSta.getLargestReceiveSuccessResponseThroughput());
        apiDetaillog.setLargestReceiveErrorResponseThroughput(apiSta.getLargestReceiveErrorResponseThroughput());
        apiDetaillog.setLastSendRequestTime(apiSta.getLastSendRequestTime());

        apiDetaillog.setLastReceiveSuccessResponseTime(apiSta.getLastReceiveSuccessResponseTime());
        apiDetaillog.setLastReceiveErrorResponseTime(apiSta.getLastReceiveErrorResponseTime());
        apiDetaillog.setAverageReceiveSuccessResponseMilliseconds(apiSta.getAverageReceiveSuccessResponseMilliseconds());
        apiDetaillog.setReceiveSuccessRequestTimes(apiSta.getReceiveSuccessRequestTimes().longValue());
        apiDetaillog.setReceiveErrorRequestTimes(apiSta.getReceiveErrorRequestTimes().longValue());
        apiDetaillog.setSendSuccessResponseTimes(apiSta.getSendSuccessResponseTimes().longValue());

        apiDetaillog.setSendErrorResponseTimes(apiSta.getSendErrorResponseTimes().longValue());
        apiDetaillog.setReceiveSuccessRequestThroughput(apiSta.getReceiveSuccessRequestThroughput());
        apiDetaillog.setReceiveErrorRequestThroughput(apiSta.getReceiveErrorRequestThroughput());
        apiDetaillog.setSendSuccessResponseThroughput(apiSta.getSendSuccessResponseThroughput());
        apiDetaillog.setSendErrorResponseThroughput(apiSta.getSendErrorResponseThroughput());
        apiDetaillog.setLargestReceiveSuccessRequestThroughput(apiSta.getLargestReceiveSuccessRequestThroughput());

        apiDetaillog.setLargestReceiveErrorRequestThroughput(apiSta.getLargestReceiveErrorRequestThroughput());
        apiDetaillog.setLargestSendSuccessResponseThroughput(apiSta.getLargestSendSuccessResponseThroughput());
        apiDetaillog.setLargestSendErrorResponseThroughput(apiSta.getLargestSendErrorResponseThroughput());
        apiDetaillog.setLastReceiveSuccessRequestTime(apiSta.getLastReceiveSuccessRequestTime());
        apiDetaillog.setLastReceiveErrorRequestTime(apiSta.getLastReceiveErrorRequestTime());
        apiDetaillog.setLastSendSuccessResponseTime(apiSta.getLastSendSuccessResponseTime());

        apiDetaillog.setLastSendErrorResponseTime(apiSta.getLastSendErrorResponseTime());
        apiDetaillog.setAverageSendSuccessResponseMilliseconds(apiSta.getAverageSendSuccessResponseMilliseconds());
        apiDetaillog.setLogtime(new Date());

        return apiDetaillog;
    }
}
