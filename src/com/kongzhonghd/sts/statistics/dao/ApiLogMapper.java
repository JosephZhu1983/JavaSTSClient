package com.kongzhonghd.sts.statistics.dao;

import com.kongzhonghd.sts.statistics.model.ApiLog;
import com.kongzhonghd.sts.statistics.model.ApiLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApiLogMapper
{
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    int countByExample(ApiLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    int deleteByExample(ApiLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    int insert(ApiLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    int insertSelective(ApiLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    List<ApiLog> selectByExample(ApiLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    ApiLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    int updateByExampleSelective(@Param("record") ApiLog record, @Param("example") ApiLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    int updateByExample(@Param("record") ApiLog record, @Param("example") ApiLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    int updateByPrimaryKeySelective(ApiLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tsts_api_performance_log
     *
     * @mbggenerated Thu Aug 08 17:52:21 CST 2013
     */
    int updateByPrimaryKey(ApiLog record);
}