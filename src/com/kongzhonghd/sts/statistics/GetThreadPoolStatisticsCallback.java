package com.kongzhonghd.sts.statistics;

import java.util.List;

/**
 * User: apple
 * Date: 13-8-12
 * Time: PM2:12
 * 获取线程池统计信息的接口
 */
public interface GetThreadPoolStatisticsCallback
{
    List<ThreadPoolStatistics> getThreadPoolStatistics();
}
