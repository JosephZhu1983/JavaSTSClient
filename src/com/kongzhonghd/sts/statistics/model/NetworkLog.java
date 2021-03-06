package com.kongzhonghd.sts.statistics.model;

import java.util.Date;

public class NetworkLog
{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.id
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.ReadBytesThroughput
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Double readBytesThroughput;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.WrittenBytesThroughput
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Double writtenBytesThroughput;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.ReadMessagesThroughput
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Double readMessagesThroughput;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.WrittenMessagesThroughput
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Double writtenMessagesThroughput;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.LargestReadBytesThroughput
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Double largestReadBytesThroughput;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.LargestWrittenBytesThroughput
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Double largestWrittenBytesThroughput;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.LargestReadMessagesThroughput
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Double largestReadMessagesThroughput;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.LargestWrittenMessagesThroughput
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Double largestWrittenMessagesThroughput;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.ReadBytes
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Long readBytes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.WrittenBytes
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Long writtenBytes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.ReadMessages
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Long readMessages;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.WrittenMessages
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Long writtenMessages;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.LastReadTime
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Date lastReadTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.LastWriteTime
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Date lastWriteTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.IPAddr
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private String IPAddr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tsts_network_performance_log.logtime
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    private Date logtime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.id
     *
     * @return the value of tsts_network_performance_log.id
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Long getId()
    {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.id
     *
     * @param id the value for tsts_network_performance_log.id
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.ReadBytesThroughput
     *
     * @return the value of tsts_network_performance_log.ReadBytesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Double getReadBytesThroughput()
    {
        return readBytesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.ReadBytesThroughput
     *
     * @param readBytesThroughput the value for tsts_network_performance_log.ReadBytesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setReadBytesThroughput(Double readBytesThroughput)
    {
        this.readBytesThroughput = readBytesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.WrittenBytesThroughput
     *
     * @return the value of tsts_network_performance_log.WrittenBytesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Double getWrittenBytesThroughput()
    {
        return writtenBytesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.WrittenBytesThroughput
     *
     * @param writtenBytesThroughput the value for tsts_network_performance_log.WrittenBytesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setWrittenBytesThroughput(Double writtenBytesThroughput)
    {
        this.writtenBytesThroughput = writtenBytesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.ReadMessagesThroughput
     *
     * @return the value of tsts_network_performance_log.ReadMessagesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Double getReadMessagesThroughput()
    {
        return readMessagesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.ReadMessagesThroughput
     *
     * @param readMessagesThroughput the value for tsts_network_performance_log.ReadMessagesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setReadMessagesThroughput(Double readMessagesThroughput)
    {
        this.readMessagesThroughput = readMessagesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.WrittenMessagesThroughput
     *
     * @return the value of tsts_network_performance_log.WrittenMessagesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Double getWrittenMessagesThroughput()
    {
        return writtenMessagesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.WrittenMessagesThroughput
     *
     * @param writtenMessagesThroughput the value for tsts_network_performance_log.WrittenMessagesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setWrittenMessagesThroughput(Double writtenMessagesThroughput)
    {
        this.writtenMessagesThroughput = writtenMessagesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.LargestReadBytesThroughput
     *
     * @return the value of tsts_network_performance_log.LargestReadBytesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Double getLargestReadBytesThroughput()
    {
        return largestReadBytesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.LargestReadBytesThroughput
     *
     * @param largestReadBytesThroughput the value for tsts_network_performance_log.LargestReadBytesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setLargestReadBytesThroughput(Double largestReadBytesThroughput)
    {
        this.largestReadBytesThroughput = largestReadBytesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.LargestWrittenBytesThroughput
     *
     * @return the value of tsts_network_performance_log.LargestWrittenBytesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Double getLargestWrittenBytesThroughput()
    {
        return largestWrittenBytesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.LargestWrittenBytesThroughput
     *
     * @param largestWrittenBytesThroughput the value for tsts_network_performance_log.LargestWrittenBytesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setLargestWrittenBytesThroughput(Double largestWrittenBytesThroughput)
    {
        this.largestWrittenBytesThroughput = largestWrittenBytesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.LargestReadMessagesThroughput
     *
     * @return the value of tsts_network_performance_log.LargestReadMessagesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Double getLargestReadMessagesThroughput()
    {
        return largestReadMessagesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.LargestReadMessagesThroughput
     *
     * @param largestReadMessagesThroughput the value for tsts_network_performance_log.LargestReadMessagesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setLargestReadMessagesThroughput(Double largestReadMessagesThroughput)
    {
        this.largestReadMessagesThroughput = largestReadMessagesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.LargestWrittenMessagesThroughput
     *
     * @return the value of tsts_network_performance_log.LargestWrittenMessagesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Double getLargestWrittenMessagesThroughput()
    {
        return largestWrittenMessagesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.LargestWrittenMessagesThroughput
     *
     * @param largestWrittenMessagesThroughput
     *         the value for tsts_network_performance_log.LargestWrittenMessagesThroughput
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setLargestWrittenMessagesThroughput(Double largestWrittenMessagesThroughput)
    {
        this.largestWrittenMessagesThroughput = largestWrittenMessagesThroughput;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.ReadBytes
     *
     * @return the value of tsts_network_performance_log.ReadBytes
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Long getReadBytes()
    {
        return readBytes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.ReadBytes
     *
     * @param readBytes the value for tsts_network_performance_log.ReadBytes
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setReadBytes(Long readBytes)
    {
        this.readBytes = readBytes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.WrittenBytes
     *
     * @return the value of tsts_network_performance_log.WrittenBytes
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Long getWrittenBytes()
    {
        return writtenBytes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.WrittenBytes
     *
     * @param writtenBytes the value for tsts_network_performance_log.WrittenBytes
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setWrittenBytes(Long writtenBytes)
    {
        this.writtenBytes = writtenBytes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.ReadMessages
     *
     * @return the value of tsts_network_performance_log.ReadMessages
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Long getReadMessages()
    {
        return readMessages;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.ReadMessages
     *
     * @param readMessages the value for tsts_network_performance_log.ReadMessages
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setReadMessages(Long readMessages)
    {
        this.readMessages = readMessages;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.WrittenMessages
     *
     * @return the value of tsts_network_performance_log.WrittenMessages
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Long getWrittenMessages()
    {
        return writtenMessages;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.WrittenMessages
     *
     * @param writtenMessages the value for tsts_network_performance_log.WrittenMessages
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setWrittenMessages(Long writtenMessages)
    {
        this.writtenMessages = writtenMessages;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.LastReadTime
     *
     * @return the value of tsts_network_performance_log.LastReadTime
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Date getLastReadTime()
    {
        return lastReadTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.LastReadTime
     *
     * @param lastReadTime the value for tsts_network_performance_log.LastReadTime
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setLastReadTime(Date lastReadTime)
    {
        this.lastReadTime = lastReadTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.LastWriteTime
     *
     * @return the value of tsts_network_performance_log.LastWriteTime
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Date getLastWriteTime()
    {
        return lastWriteTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.LastWriteTime
     *
     * @param lastWriteTime the value for tsts_network_performance_log.LastWriteTime
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setLastWriteTime(Date lastWriteTime)
    {
        this.lastWriteTime = lastWriteTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.IPAddr
     *
     * @return the value of tsts_network_performance_log.IPAddr
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public String getIPAddr()
    {
        return IPAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.IPAddr
     *
     * @param IPAddr the value for tsts_network_performance_log.IPAddr
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setIPAddr(String IPAddr)
    {
        this.IPAddr = IPAddr == null ? null : IPAddr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tsts_network_performance_log.logtime
     *
     * @return the value of tsts_network_performance_log.logtime
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public Date getLogtime()
    {
        return logtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tsts_network_performance_log.logtime
     *
     * @param logtime the value for tsts_network_performance_log.logtime
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    public void setLogtime(Date logtime)
    {
        this.logtime = logtime;
    }
}