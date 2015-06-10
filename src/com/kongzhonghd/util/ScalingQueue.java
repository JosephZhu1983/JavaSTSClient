package com.kongzhonghd.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: apple
 * Date: 13-8-9
 * Time: PM7:28
 */
public class ScalingQueue<E> extends LinkedBlockingQueue<E>
{

    /**
     * The executor this Queue belongs to
     */

    private ThreadPoolExecutor executor;


    /**
     * Creates a TaskQueue with a capacity of
     * <p/>
     * <p/>
     * {@link Integer#MAX_VALUE}.
     */

    public ScalingQueue()
    {

        super();

    }


    /**
     * Creates a TaskQueue with the given (fixed) capacity.
     *
     * @param capacity the capacity of this queue.
     */

    public ScalingQueue(int capacity)
    {

        super(capacity);

    }

    /**
     * Sets the executor this queue belongs to.
     */

    public void setThreadPoolExecutor(ThreadPoolExecutor executor)
    {

        this.executor = executor;

    }

    /**
     * Inserts the specified element at the tail of this queue if there is at
     * <p/>
     * <p/>
     * least one available thread to run the current task. If all pool threads
     * <p/>
     * <p/>
     * are actively busy, it rejects the offer.
     *
     * @param o the element to add.
     * @return true if it was possible to add the element to this
     *         <p/>
     *         <p/>
     *         queue, else false
     * @see ThreadPoolExecutor#execute(Runnable)
     */

    @Override
    public boolean offer(E o)
    {

        int allWorkingThreads = executor.getActiveCount() + super.size();

        return allWorkingThreads < executor.getPoolSize() && super.offer(o);

    }

}