package com.kongzhonghd.util;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/23/13
 * Time: 1:21 PM
 */
public class Sequence
{
    private static final Object locker = new Object();
    private static int sequence = 1000;

    public static int next()
    {
        synchronized (locker)
        {
            sequence++;
            if (sequence < 0)
                sequence = 1;
            return sequence;
        }
    }
}
