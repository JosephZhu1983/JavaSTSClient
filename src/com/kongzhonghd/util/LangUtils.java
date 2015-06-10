package com.kongzhonghd.util;

import com.kongzhonghd.STSConst;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/22/13
 * Time: 8:23 AM
 */
public class LangUtils
{

    public static final int HASH_SEED = 17;
    private static final int HASH_OFFSET = 37;

    private LangUtils()
    {
        super();
    }

    private static int hashCode(final int seed, final int hashcode)
    {
        return seed * HASH_OFFSET + hashcode;
    }

    public static int hashCode(final int seed, final Object obj)
    {
        return hashCode(seed, obj != null ? obj.hashCode() : 0);
    }

    public static int hashCode(final int seed, final boolean b)
    {
        return hashCode(seed, b ? 1 : 0);
    }

    public static boolean equals(final Object obj1, final Object obj2)
    {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }

    public static ThreadPoolExecutor newScalingThreadPool(int min, int max,

                                                          long keepAliveTime)
    {

        ScalingQueue queue = new ScalingQueue();

        ThreadPoolExecutor executor = new ScalingThreadPoolExecutor(min, max, keepAliveTime, TimeUnit.MILLISECONDS, queue);

        executor.setRejectedExecutionHandler(new ForceQueuePolicy());

        queue.setThreadPoolExecutor(executor);

        return executor;

    }

    public static String getExceptionContent(Throwable ex)
    {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return STSConst.CRLF + sw.toString() + STSConst.CRLF;
    }

}
