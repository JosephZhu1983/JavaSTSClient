package com.kongzhonghd.sts.statistics.dao;

import com.kongzhonghd.sts.statistics.model.TstsThreadpoolPerformanceLog;
import com.kongzhonghd.sts.statistics.model.TstsThreadpoolPerformanceLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TstsThreadpoolPerformanceLogMapper
{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    int countByExample(TstsThreadpoolPerformanceLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    int deleteByExample(TstsThreadpoolPerformanceLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    int insert(TstsThreadpoolPerformanceLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    int insertSelective(TstsThreadpoolPerformanceLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    List<TstsThreadpoolPerformanceLog> selectByExample(TstsThreadpoolPerformanceLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    TstsThreadpoolPerformanceLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    int updateByExampleSelective(@Param("record") TstsThreadpoolPerformanceLog record, @Param("example") TstsThreadpoolPerformanceLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    int updateByExample(@Param("record") TstsThreadpoolPerformanceLog record, @Param("example") TstsThreadpoolPerformanceLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    int updateByPrimaryKeySelective(TstsThreadpoolPerformanceLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_threadPool_performance_log
     *
     * @mbggenerated Wed Aug 28 16:19:38 CST 2013
     */
    int updateByPrimaryKey(TstsThreadpoolPerformanceLog record);
}