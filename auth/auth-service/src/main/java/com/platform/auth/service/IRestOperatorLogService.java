package com.platform.auth.service;

import com.platform.auth.model.innermodel.RestOperatorLogModel;

/**
 * 请求日志接口
 * @version: 1.0
 */
public interface IRestOperatorLogService {

    /**
     * 保存日志
     * @param model
     * @return
     */
    int save(RestOperatorLogModel model);
}
