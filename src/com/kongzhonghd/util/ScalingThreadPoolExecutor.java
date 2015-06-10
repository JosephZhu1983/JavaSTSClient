package com.kongzhonghd.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: apple
 * Date: 13-8-9
 * Time: PM7:26
 */

public class ScalingThreadPoolExecutor extends ThreadPoolExecutor
{

    /**
     * number of threads that are actively executing tasks
     */

    private final AtomicInteger activeCount = new AtomicInteger();


    public ScalingThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
                                     long keepAliveTime, TimeUnit unit, BlockingQueue workQueue)
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public int getActiveCount()
    {
        return activeCount.get();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r)
    {
        activeCount.incrementAndGet();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t)
    {
        activeCount.decrementAndGet();
    }
}
