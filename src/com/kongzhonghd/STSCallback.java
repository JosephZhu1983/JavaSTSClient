package com.kongzhonghd;

import com.kongzhonghd.sts.business.AbstractResponse;

/**
 * User: apple
 * Date: 13-8-9
 * Time: PM4:26
 * 异步请求回调处理接口
 */
public interface STSCallback<T extends AbstractResponse>
{
    void whenSuccess(T response);

    void whenFail(Exception ex);
}
