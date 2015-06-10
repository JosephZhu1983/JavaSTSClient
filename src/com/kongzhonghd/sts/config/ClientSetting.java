package com.kongzhonghd.sts.config;

import com.kongzhonghd.sts.exception.STSException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.dom4j.Element;

/**
 * User: apple
 * Date: 13-8-17
 * Time: PM1:31
 */
public class ClientSetting
{
    private String customProtocolNamespace;
    private PortalGateEnum portalGate;
    private int programId;
    private int buildId;
    private int productTypeId;
    private int appIndex;
    private int messageHandlerThreadPoolSize = 50;
    private int taskExecutorThreadPoolSize = 50;
    private int asyncTaskThreadPoolSize = 50;
    private int replyTimeoutSeconds = 30;
    private int taskExecutionTimeoutSeconds = 25;
    private int autoPingSeconds = 30;
    private boolean enableStatisticsReport;
    private int reportStatisticsSeconds = 10;

    public ClientSetting(Element root) throws STSException
    {
        if (root.element("EnableStatisticsReport") == null)
            throw new STSException("在配置文件中找不到EnableStatisticsReport节点！");
        this.enableStatisticsReport = Boolean.parseBoolean(root.element("EnableStatisticsReport").getTextTrim());

        if (root.element("ReportStatisticsSeconds") == null)
            throw new STSException("ReportStatisticsSeconds节点！");
        this.reportStatisticsSeconds = Integer.valueOf(root.element("ReportStatisticsSeconds").getTextTrim());

        if (root.element("BuildId") == null)
            throw new STSException("在配置文件中找不到BuildId节点！");
        this.buildId = Integer.parseInt(root.element("BuildId").getTextTrim());

        if (root.element("MessageHandlerThreadPoolSize") == null)
            throw new STSException("在配置文件中找不到MessageHandlerThreadPoolSize节点！");
        this.messageHandlerThreadPoolSize = Integer.parseInt(root.element("MessageHandlerThreadPoolSize").getTextTrim());

        if (root.element("TaskExecutorThreadPoolSize") == null)
            throw new STSException("在配置文件中找不到TaskExecutorThreadPoolSize节点！");
        this.taskExecutorThreadPoolSize = Integer.parseInt(root.element("TaskExecutorThreadPoolSize").getTextTrim());

        if (root.element("AsyncTaskThreadPoolSize") == null)
            throw new STSException("在配置文件中找不到AsyncTaskThreadPoolSize节点！");
        this.asyncTaskThreadPoolSize = Integer.parseInt(root.element("AsyncTaskThreadPoolSize").getTextTrim());

        if (root.element("ProgramId") == null)
            throw new STSException("在配置文件中找不到ProgramId节点！");
        this.programId = Integer.parseInt(root.element("ProgramId").getTextTrim());

        if (root.element("ProductTypeId") == null)
            throw new STSException("在配置文件中找不到ProductTypeId节点！");
        this.productTypeId = Integer.parseInt(root.element("ProductTypeId").getTextTrim());

        if (root.element("AppIndex") == null)
            throw new STSException("在配置文件中找不到AppIndex节点！");
        this.appIndex = Integer.parseInt(root.element("AppIndex").getTextTrim());

        if (root.element("CustomProtocolNamespace") == null)
            throw new STSException("在配置文件中找不到CustomProtocolNamespace节点！");
        this.customProtocolNamespace = root.element("CustomProtocolNamespace").getTextTrim();

        if (root.element("PortalGate") == null)
            throw new STSException("在配置文件中找不到PortalGate节点！");
        this.portalGate = PortalGateEnum.valueOf(root.element("PortalGate").getTextTrim());

        if (root.element("ReplyTimeoutSeconds") == null)
            throw new STSException("在配置文件中找不到ReplyTimeoutSeconds节点！");
        this.replyTimeoutSeconds = Integer.valueOf(root.element("ReplyTimeoutSeconds").getTextTrim());

        if (root.element("TaskExecutionTimeoutSeconds") == null)
            throw new STSException("在配置文件中找不到TaskExecutionTimeoutSeconds节点！");
        this.taskExecutionTimeoutSeconds = Integer.valueOf(root.element("TaskExecutionTimeoutSeconds").getTextTrim());

        if (root.element("AutoPingSeconds") == null)
            throw new STSException("在配置文件中找不到AutoPingSeconds节点！");
        this.autoPingSeconds = Integer.valueOf(root.element("AutoPingSeconds").getTextTrim());
    }

    public String getCustomProtocolNamespace()
    {
        return customProtocolNamespace;
    }

    public PortalGateEnum getPortalGate()
    {
        return portalGate;
    }

    public int getProgramId()
    {
        return programId;
    }

    public int getBuildId()
    {
        return buildId;
    }

    public int getProductTypeId()
    {
        return productTypeId;
    }

    public int getAppIndex()
    {
        return appIndex;
    }

    public int getMessageHandlerThreadPoolSize()
    {
        return messageHandlerThreadPoolSize;
    }

    public int getTaskExecutorThreadPoolSize()
    {
        return taskExecutorThreadPoolSize;
    }

    public int getAsyncTaskThreadPoolSize()
    {
        return asyncTaskThreadPoolSize;
    }

    public int getReplyTimeoutSeconds()
    {
        return replyTimeoutSeconds;
    }

    public int getTaskExecutionTimeoutSeconds()
    {
        return taskExecutionTimeoutSeconds;
    }

    public int getAutoPingSeconds()
    {
        return autoPingSeconds;
    }

    public boolean isEnableStatisticsReport()
    {
        return enableStatisticsReport;
    }

    public int getReportStatisticsSeconds()
    {
        return reportStatisticsSeconds;
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
