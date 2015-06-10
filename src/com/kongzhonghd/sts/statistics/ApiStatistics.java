package com.kongzhonghd.sts.statistics;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * User: JosephZhu
 * Date: 13-8-5
 * Time: AM11:10
 * 具体API的统计信息
 */
public class ApiStatistics
{
    public AtomicLong getSendRequestTimes()
    {
        return sendRequestTimes;
    }

    public AtomicLong getReceiveSuccessResponseTimes()
    {
        return receiveSuccessResponseTimes;
    }

    public AtomicLong getReceiveErrorResponseTimes()
    {
        return receiveErrorResponseTimes;
    }

    public long getLastSendRequestTimes()
    {
        return lastSendRequestTimes;
    }

    public long getLastReceiveSuccessResponseTimes()
    {
        return lastReceiveSuccessResponseTimes;
    }

    public long getLastReceiveErrorResponseTimes()
    {
        return lastReceiveErrorResponseTimes;
    }

    public double getSendRequestThroughput()
    {
        return sendRequestThroughput;
    }

    public double getReceiveSuccessResponseThroughput()
    {
        return receiveSuccessResponseThroughput;
    }

    public double getReceiveErrorResponseThroughput()
    {
        return receiveErrorResponseThroughput;
    }

    public double getLargestSendRequestThroughput()
    {
        return largestSendRequestThroughput;
    }

    public double getLargestReceiveSuccessResponseThroughput()
    {
        return largestReceiveSuccessResponseThroughput;
    }

    public double getLargestReceiveErrorResponseThroughput()
    {
        return largestReceiveErrorResponseThroughput;
    }

    public Date getLastSendRequestTime()
    {
        return lastSendRequestTime;
    }

    public Date getLastReceiveSuccessResponseTime()
    {
        return lastReceiveSuccessResponseTime;
    }

    public Date getLastReceiveErrorResponseTime()
    {
        return lastReceiveErrorResponseTime;
    }

    public int getAverageReceiveSuccessResponseMilliseconds()
    {
        return averageReceiveSuccessResponseMilliseconds;
    }

    public AtomicLong getTotalReceiveSuccessResponseMilliseconds()
    {
        return totalReceiveSuccessResponseMilliseconds;
    }

    public AtomicLong getReceiveSuccessRequestTimes()
    {
        return receiveSuccessRequestTimes;
    }

    public AtomicLong getReceiveErrorRequestTimes()
    {
        return receiveErrorRequestTimes;
    }

    public AtomicLong getSendSuccessResponseTimes()
    {
        return sendSuccessResponseTimes;
    }

    public AtomicLong getSendErrorResponseTimes()
    {
        return sendErrorResponseTimes;
    }

    public long getLastReceiveSuccessRequestTimes()
    {
        return lastReceiveSuccessRequestTimes;
    }

    public long getLastReceiveErrorRequestTimes()
    {
        return lastReceiveErrorRequestTimes;
    }

    public long getLastSendSuccessResponseTimes()
    {
        return lastSendSuccessResponseTimes;
    }

    public long getLastSendErrorResponseTimes()
    {
        return lastSendErrorResponseTimes;
    }

    public double getReceiveSuccessRequestThroughput()
    {
        return receiveSuccessRequestThroughput;
    }

    public double getReceiveErrorRequestThroughput()
    {
        return receiveErrorRequestThroughput;
    }

    public double getSendSuccessResponseThroughput()
    {
        return sendSuccessResponseThroughput;
    }

    public double getSendErrorResponseThroughput()
    {
        return sendErrorResponseThroughput;
    }

    public double getLargestReceiveSuccessRequestThroughput()
    {
        return largestReceiveSuccessRequestThroughput;
    }

    public double getLargestReceiveErrorRequestThroughput()
    {
        return largestReceiveErrorRequestThroughput;
    }

    public double getLargestSendSuccessResponseThroughput()
    {
        return largestSendSuccessResponseThroughput;
    }

    public double getLargestSendErrorResponseThroughput()
    {
        return largestSendErrorResponseThroughput;
    }

    public Date getLastReceiveSuccessRequestTime()
    {
        return lastReceiveSuccessRequestTime;
    }

    public Date getLastReceiveErrorRequestTime()
    {
        return lastReceiveErrorRequestTime;
    }

    public Date getLastSendSuccessResponseTime()
    {
        return lastSendSuccessResponseTime;
    }

    public Date getLastSendErrorResponseTime()
    {
        return lastSendErrorResponseTime;
    }

    public AtomicLong getTotalSendSuccessResponseMilliseconds()
    {
        return totalSendSuccessResponseMilliseconds;
    }

    public int getAverageSendSuccessResponseMilliseconds()
    {
        return averageSendSuccessResponseMilliseconds;
    }

    private AtomicLong sendRequestTimes = new AtomicLong();
    private AtomicLong receiveSuccessResponseTimes = new AtomicLong();
    private AtomicLong receiveErrorResponseTimes = new AtomicLong();
    private long lastSendRequestTimes;
    private long lastReceiveSuccessResponseTimes;
    private long lastReceiveErrorResponseTimes;
    private double sendRequestThroughput;
    private double receiveSuccessResponseThroughput;
    private double receiveErrorResponseThroughput;
    private double largestSendRequestThroughput;
    private double largestReceiveSuccessResponseThroughput;
    private double largestReceiveErrorResponseThroughput;
    private Date lastSendRequestTime;
    private Date lastReceiveSuccessResponseTime;
    private Date lastReceiveErrorResponseTime;
    private int averageReceiveSuccessResponseMilliseconds;
    private AtomicLong totalReceiveSuccessResponseMilliseconds = new AtomicLong();
    private AtomicLong receiveSuccessRequestTimes = new AtomicLong();
    private AtomicLong receiveErrorRequestTimes = new AtomicLong();
    private AtomicLong sendSuccessResponseTimes = new AtomicLong();
    private AtomicLong sendErrorResponseTimes = new AtomicLong();
    private long lastReceiveSuccessRequestTimes;
    private long lastReceiveErrorRequestTimes;
    private long lastSendSuccessResponseTimes;
    private long lastSendErrorResponseTimes;
    private double receiveSuccessRequestThroughput;
    private double receiveErrorRequestThroughput;
    private double sendSuccessResponseThroughput;
    private double sendErrorResponseThroughput;
    private double largestReceiveSuccessRequestThroughput;
    private double largestReceiveErrorRequestThroughput;
    private double largestSendSuccessResponseThroughput;
    private double largestSendErrorResponseThroughput;
    private Date lastReceiveSuccessRequestTime;
    private Date lastReceiveErrorRequestTime;
    private Date lastSendSuccessResponseTime;
    private Date lastSendErrorResponseTime;
    private AtomicLong totalSendSuccessResponseMilliseconds = new AtomicLong();
    private int averageSendSuccessResponseMilliseconds;

    public void sendRequest()
    {
        sendRequestTimes.incrementAndGet();
        lastSendRequestTime = new Date();
    }

    public void receiveSuccessResponse(int time)
    {
        long times = receiveSuccessResponseTimes.incrementAndGet();
        lastReceiveSuccessResponseTime = new Date();
        long all = totalReceiveSuccessResponseMilliseconds.addAndGet(time);
        averageReceiveSuccessResponseMilliseconds = (int) (all / times);
    }

    public void receiveErrorResponse()
    {
        receiveErrorResponseTimes.incrementAndGet();
        lastReceiveErrorResponseTime = new Date();
    }

    public void receiveSuccessRequest()
    {
        receiveSuccessRequestTimes.incrementAndGet();
        lastReceiveSuccessRequestTime = new Date();
    }

    public void receiveErrorRequest()
    {
        receiveErrorRequestTimes.incrementAndGet();
        lastReceiveErrorRequestTime = new Date();
    }

    public void sendSuccessResponse(int time)
    {
        long times = sendSuccessResponseTimes.incrementAndGet();
        lastSendSuccessResponseTime = new Date();
        long all = totalSendSuccessResponseMilliseconds.addAndGet(time);
        averageSendSuccessResponseMilliseconds = (int) (all / times);
    }

    public void sendErrorResponse()
    {
        sendErrorResponseTimes.incrementAndGet();
        lastSendErrorResponseTime = new Date();
    }

    public void updateThroughput(long intervalSeconds)
    {
        long sendRequestTimes = this.sendRequestTimes.get();
        long receiveSuccessResponseTimes = this.receiveSuccessResponseTimes.get();
        long receiveErrorResponseTimes = this.receiveErrorResponseTimes.get();
        long receiveSuccessRequestTimes = this.receiveSuccessRequestTimes.get();
        long receiveErrorRequestTimes = this.receiveErrorRequestTimes.get();
        long sendSuccessResponseTimes = this.sendSuccessResponseTimes.get();
        long sendErrorResponseTimes = this.sendErrorResponseTimes.get();

        sendRequestThroughput = (sendRequestTimes - lastSendRequestTimes) / intervalSeconds;
        receiveSuccessResponseThroughput = (receiveSuccessResponseTimes - lastReceiveSuccessResponseTimes) / intervalSeconds;
        receiveErrorResponseThroughput = (receiveErrorResponseTimes - lastReceiveErrorResponseTimes) / intervalSeconds;
        receiveSuccessRequestThroughput = (receiveSuccessRequestTimes - lastReceiveSuccessRequestTimes) / intervalSeconds;
        receiveErrorRequestThroughput = (receiveErrorRequestTimes - lastReceiveErrorRequestTimes) / intervalSeconds;
        sendSuccessResponseThroughput = (sendSuccessResponseTimes - lastSendSuccessResponseTimes) / intervalSeconds;
        sendErrorResponseThroughput = (sendErrorResponseTimes - lastSendErrorResponseTimes) / intervalSeconds;

        if (sendRequestThroughput > largestSendRequestThroughput)
        {
            largestSendRequestThroughput = sendRequestThroughput;
        }
        if (receiveSuccessResponseThroughput > largestReceiveSuccessResponseThroughput)
        {
            largestReceiveSuccessResponseThroughput = receiveSuccessResponseThroughput;
        }
        if (receiveErrorResponseThroughput > largestReceiveErrorResponseThroughput)
        {
            largestReceiveErrorResponseThroughput = receiveErrorResponseThroughput;
        }
        if (receiveSuccessRequestThroughput > largestReceiveSuccessRequestThroughput)
        {
            largestReceiveSuccessRequestThroughput = receiveSuccessRequestThroughput;
        }
        if (receiveErrorRequestThroughput > largestReceiveErrorRequestThroughput)
        {
            largestReceiveErrorRequestThroughput = receiveErrorRequestThroughput;
        }
        if (sendSuccessResponseThroughput > largestSendSuccessResponseThroughput)
        {
            largestSendSuccessResponseThroughput = sendSuccessResponseThroughput;
        }
        if (sendErrorResponseThroughput > largestSendErrorResponseThroughput)
        {
            largestSendErrorResponseThroughput = sendErrorResponseThroughput;
        }

        lastSendRequestTimes = sendRequestTimes;
        lastReceiveSuccessResponseTimes = receiveSuccessResponseTimes;
        lastReceiveErrorResponseTimes = receiveErrorResponseTimes;
        lastReceiveSuccessRequestTimes = receiveSuccessRequestTimes;
        lastReceiveErrorRequestTimes = receiveErrorRequestTimes;
        lastSendSuccessResponseTimes = sendSuccessResponseTimes;
        lastSendErrorResponseTimes = sendErrorResponseTimes;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
