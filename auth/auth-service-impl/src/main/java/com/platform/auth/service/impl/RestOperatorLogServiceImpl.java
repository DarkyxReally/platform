package com.platform.auth.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.platform.auth.common.util.IRequestDataLogService;
import com.platform.auth.common.util.RequestData;
import com.platform.auth.dao.RestOperatorLogEntityDao;
import com.platform.auth.entity.RestOperatorLogEntity;
import com.platform.auth.model.innermodel.ClientRestOperatorLogModel;
import com.platform.auth.model.innermodel.RestOperatorLogModel;
import com.platform.auth.service.IRestOperatorLogService;


/**
 * 请求日志实体实现
 * @version: 1.0
 */
@Service
public class RestOperatorLogServiceImpl implements IRestOperatorLogService, IRequestDataLogService {

    @Autowired
    private RestOperatorLogEntityDao restOperatorLogEntityDao;
    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String applicationName;
    
    @Override
    public int save(RestOperatorLogModel model){
        RestOperatorLogEntity entity = new RestOperatorLogEntity();
        entity.setIdRequest(model.getIdRequest());
        entity.setReqUrl(model.getUrl());
        entity.setReqMethod(model.getMethod());
        entity.setReqParam(model.getParam());
        entity.setReqHeader(model.getHeader());
        entity.setReqBody(model.getBody());
        entity.setReqIp(model.getIp());
        entity.setReqDate(model.getDatetime());
        entity.setIdUser(model.getIdUser());
        if (StringUtils.isNotBlank(model.getUserIp())){
            entity.setUserIp(model.getUserIp());
        }
        entity.setServiceName(model.getServiceName());
        entity.setSystemCode(model.getSystemCode());
        entity.setModule(model.getModule());
        entity.setDescription(model.getDescription());
        if (model instanceof ClientRestOperatorLogModel){
            entity.setIdClient(((ClientRestOperatorLogModel)model).getClientId());
        }
        entity.setDateCreated(new Date());
        return restOperatorLogEntityDao.insertSelective(entity);
    }

    @Override
    public void save(RequestData requestData){
        RestOperatorLogEntity entity = new RestOperatorLogEntity();
        entity.setIdRequest(requestData.getIdRequest());
        entity.setReqUrl(requestData.getUrl());
        entity.setReqMethod(requestData.getMethod());
        entity.setReqParam(requestData.getParam());
        entity.setReqHeader(requestData.getHeader());
        entity.setReqBody(requestData.getBody());
        entity.setReqIp(requestData.getIp());
        entity.setReqDate(requestData.getDatetime());
        entity.setIdUser(requestData.getIdUser());
        if (StringUtils.isNotBlank(requestData.getUserIp())){
            entity.setUserIp(requestData.getUserIp());
        }
        entity.setServiceName(applicationName);
        entity.setSystemCode(requestData.getSystemCode());
        entity.setModule(requestData.getModule());
        entity.setDescription(requestData.getDescription());
        entity.setDateCreated(new Date());
        restOperatorLogEntityDao.insertSelective(entity);
    }

}
