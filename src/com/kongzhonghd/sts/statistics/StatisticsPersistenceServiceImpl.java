package com.kongzhonghd.sts.statistics;

import com.kongzhonghd.sts.statistics.dao.*;
import com.kongzhonghd.sts.statistics.model.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhangjinwen
 * @date 2013-8-8
 * @see StatisticsPersistenceService
 * @since 1.0
 */
public class StatisticsPersistenceServiceImpl implements StatisticsPersistenceService
{

    private ApiDetailLogMapper apiDetailLog;
    private ApiLogMapper apiLog;
    private NetworkLogMapper networkLog;
    private TstsThreadpoolPerformanceLogMapper threadpoolLog;
    private ClassPathXmlApplicationContext applicationContext;

    private static volatile StatisticsPersistenceServiceImpl instance = null;

    public static StatisticsPersistenceService getInstance()
    {
        if (instance == null)
        {
            synchronized (StatisticsPersistenceServiceImpl.class)
            {
                if (instance == null)
                {
                    instance = new StatisticsPersistenceServiceImpl("/application-context.xml");
                }
            }
        }
        return instance;
    }

    private StatisticsPersistenceServiceImpl(String appContext)
    {
        init(appContext);
    }

    private void init(String appContext)
    {
        this.applicationContext = new ClassPathXmlApplicationContext(appContext);
        networkLog = applicationContext.getBean(NetworkLogMapper.class);
        apiLog = applicationContext.getBean(ApiLogMapper.class);
        apiDetailLog = applicationContext.getBean(ApiDetailLogMapper.class);
        threadpoolLog = applicationContext.getBean(TstsThreadpoolPerformanceLogMapper.class);
    }

    @Override
    public void destory()
    {
        if (this.applicationContext != null)
        {
            this.applicationContext.close();
        }
    }


    @Override
    public void saveNetlog(NetworkLog log)
    {
        networkLog.insert(log);
    }

    @Override
    public void saveApiDetailLog(ApiDetailLog log)
    {
        apiDetailLog.insert(log);
    }

    @Override
    public void saveApiLog(ApiLog log)
    {
        apiLog.insert(log);
    }

    @Override
    public void saveThreadPoolLog(TstsThreadpoolPerformanceLog log)
    {
        threadpoolLog.insert(log);
    }

}
