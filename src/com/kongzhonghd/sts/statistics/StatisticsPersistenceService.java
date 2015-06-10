package com.kongzhonghd.sts.statistics;


import com.kongzhonghd.sts.statistics.model.*;


/**
 * 保存性能统计日志到数据库
 *
 * @author zhangjinwen
 * @date 2013-8-8
 * @since 1.0
 */
public interface StatisticsPersistenceService
{

    /**
     * 保存网络性能日志
     *
     * @param log 网络日志
     */
    public void saveNetlog(NetworkLog log);


    /**
     * 保存api详细日志
     *
     * @param log api详细日志
     */
    public void saveApiDetailLog(ApiDetailLog log);


    /**
     * 保存api日志
     *
     * @param log api日志
     */
    public void saveApiLog(ApiLog log);

    /**
     * 保存Threadpool日志
     * add by zhaolg
     *
     * @param log Threadpool日志
     */
    public void saveThreadPoolLog(TstsThreadpoolPerformanceLog log);

    public void destory();

}
