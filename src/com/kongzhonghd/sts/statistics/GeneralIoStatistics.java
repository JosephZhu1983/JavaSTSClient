package com.kongzhonghd.sts.statistics;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoServiceStatistics;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * User: JosephZhu
 * Date: 13-8-5
 * Time: AM11:01
 * IO总体情况的统计信息
 */
public class GeneralIoStatistics implements IoSessionStateChangeCallback
{
    private static final Log log = LogFactory.getLog(GeneralIoStatistics.class);
    private final Object throughputCalculationLock = new Object();
    private double readBytesThroughput;
    private double writtenBytesThroughput;
    private double readMessagesThroughput;
    private double writtenMessagesThroughput;
    private double largestReadBytesThroughput;
    private double largestWrittenBytesThroughput;
    private double largestReadMessagesThroughput;
    private double largestWrittenMessagesThroughput;
    private AtomicLong readBytes = new AtomicLong();
    private AtomicLong writtenBytes = new AtomicLong();
    private AtomicLong readMessages = new AtomicLong();
    private AtomicLong writtenMessages = new AtomicLong();
    private long lastReadTime;
    private long lastWriteTime;
    private long lastReadBytes;
    private long lastWrittenBytes;
    private long lastReadMessages;
    private long lastWrittenMessages;
    private AtomicLong createdSession = new AtomicLong();
    private AtomicLong closedSession = new AtomicLong();
    private AtomicLong openedSession = new AtomicLong();
    private AtomicLong idledSession = new AtomicLong();
    private AtomicLong exceptionedSession = new AtomicLong();

    @Override
    public void sessionCreated()
    {
        createdSession.incrementAndGet();
    }

    @Override
    public void sessionClosed()
    {
        closedSession.incrementAndGet();
    }

    @Override
    public void sessionOpened()
    {
        openedSession.incrementAndGet();
    }

    @Override
    public void sessionIdled()
    {
        idledSession.incrementAndGet();
    }

    @Override
    public void sessionGotException()
    {
        exceptionedSession.incrementAndGet();
    }

    public double getReadBytesThroughput()
    {
        return readBytesThroughput;
    }

    public double getWrittenBytesThroughput()
    {
        return writtenBytesThroughput;
    }

    public double getReadMessagesThroughput()
    {
        return readMessagesThroughput;
    }

    public double getWrittenMessagesThroughput()
    {
        return writtenMessagesThroughput;
    }

    public double getLargestReadBytesThroughput()
    {
        return largestReadBytesThroughput;
    }

    public double getLargestWrittenBytesThroughput()
    {
        return largestWrittenBytesThroughput;
    }

    public double getLargestReadMessagesThroughput()
    {
        return largestReadMessagesThroughput;
    }

    public double getLargestWrittenMessagesThroughput()
    {
        return largestWrittenMessagesThroughput;
    }

    public long getReadBytes()
    {
        return readBytes.get();
    }

    public long getWrittenBytes()
    {
        return writtenBytes.get();
    }

    public long getReadMessages()
    {
        return readMessages.get();
    }

    public long getWrittenMessages()
    {
        return writtenMessages.get();
    }

    public Date getLastReadTime()
    {
        return new Date(lastReadTime);
    }

    public Date getLastWriteTime()
    {
        return new Date(lastWriteTime);
    }

    public void updateData(List<IoServiceStatistics> ioServiceStatisticsList)
    {
        for (IoServiceStatistics item : ioServiceStatisticsList)
        {
            readBytes.addAndGet(item.getReadBytes());
            writtenBytes.addAndGet(item.getWrittenBytes());
            readMessages.addAndGet(item.getReadMessages());
            writtenMessages.addAndGet(item.getWrittenMessages());

            if (item.getLastReadTime() > lastReadTime)
                lastReadTime = item.getLastReadTime();
            if (item.getLastWriteTime() > lastWriteTime)
                lastWriteTime = item.getLastWriteTime();
        }
    }

    public void updateThroughput(long intervalSeconds)
    {
        synchronized (throughputCalculationLock)
        {
            long readBytes = this.readBytes.get();
            long writtenBytes = this.writtenBytes.get();
            long readMessages = this.readMessages.get();
            long writtenMessages = this.writtenMessages.get();

            readBytesThroughput = (readBytes - lastReadBytes) / intervalSeconds;
            writtenBytesThroughput = (writtenBytes - lastWrittenBytes) / intervalSeconds;
            readMessagesThroughput = (readMessages - lastReadMessages) / intervalSeconds;
            writtenMessagesThroughput = (writtenMessages - lastWrittenMessages) / intervalSeconds;

            if (readBytesThroughput > largestReadBytesThroughput)
            {
                largestReadBytesThroughput = readBytesThroughput;
            }
            if (writtenBytesThroughput > largestWrittenBytesThroughput)
            {
                largestWrittenBytesThroughput = writtenBytesThroughput;
            }
            if (readMessagesThroughput > largestReadMessagesThroughput)
            {
                largestReadMessagesThroughput = readMessagesThroughput;
            }
            if (writtenMessagesThroughput > largestWrittenMessagesThroughput)
            {
                largestWrittenMessagesThroughput = writtenMessagesThroughput;
            }

            lastReadBytes = readBytes;
            lastWrittenBytes = writtenBytes;
            lastReadMessages = readMessages;
            lastWrittenMessages = writtenMessages;
        }
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
