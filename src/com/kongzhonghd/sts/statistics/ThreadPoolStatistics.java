package com.kongzhonghd.sts.statistics;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: apple
 * Date: 13-8-12
 * Time: PM2:17
 * 线程池的统计信息
 */
public class ThreadPoolStatistics
{
    private String name;
    private int corePoolSize;
    private int maximumPoolSize;
    private int largestPoolSize;
    private int poolSize;
    private int activeCount;
    private long completedTaskCount;
    private long taskCount;

    public String getName()
    {
        return name;
    }

    public int getCorePoolSize()
    {
        return corePoolSize;
    }

    public int getMaximumPoolSize()
    {
        return maximumPoolSize;
    }

    public int getLargestPoolSize()
    {
        return largestPoolSize;
    }

    public int getPoolSize()
    {
        return poolSize;
    }

    public int getActiveCount()
    {
        return activeCount;
    }

    public long getCompletedTaskCount()
    {
        return completedTaskCount;
    }

    public long getTaskCount()
    {
        return taskCount;
    }

    public ThreadPoolStatistics(String name, ThreadPoolExecutor pool)
    {
        this.name = name;
        this.poolSize = pool.getPoolSize();
        this.maximumPoolSize = pool.getMaximumPoolSize();
        this.largestPoolSize = pool.getLargestPoolSize();
        this.corePoolSize = pool.getCorePoolSize();
        this.taskCount = pool.getTaskCount();
        this.completedTaskCount = pool.getCompletedTaskCount();
        this.activeCount = pool.getActiveCount();
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
