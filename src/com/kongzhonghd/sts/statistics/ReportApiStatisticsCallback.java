package com.kongzhonghd.sts.statistics;

/**
 * User: JosephZhu
 * Date: 13-8-5
 * Time: PM3:11
 * 汇报API调用状态变化的接口
 */
public interface ReportApiStatisticsCallback
{
    void sendRequest(String name);

    void receiveSuccessResponse(String name, int time);

    void receiveErrorResponse(String name);

    void receiveSuccessRequest(String name);

    void receiveErrorRequest(String name);

    void sendSuccessResponse(String name, int time);

    void sendErrorResponse(String name);
}
