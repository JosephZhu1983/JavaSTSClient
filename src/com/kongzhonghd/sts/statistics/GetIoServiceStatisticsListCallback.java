package com.kongzhonghd.sts.statistics;

import org.apache.mina.core.service.IoServiceStatistics;

import java.util.List;

/**
 * User: JosephZhu
 * Date: 13-8-5
 * Time: PM2:14
 * 获取IO统计信息的接口
 */
public interface GetIoServiceStatisticsListCallback
{
    List<IoServiceStatistics> getIoServiceStatisticsList();
}
