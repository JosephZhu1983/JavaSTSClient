package com.kongzhonghd.util;

import java.util.concurrent.*;

/**
 * User: apple
 * Date: 13-8-9
 * Time: PM7:26
 */
public class ForceQueuePolicy implements RejectedExecutionHandler
{

    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
    {

        try
        {

            executor.getQueue().put(r);

        }
        catch (InterruptedException e)
        {
            throw new RejectedExecutionException(e);

        }

    }

}
