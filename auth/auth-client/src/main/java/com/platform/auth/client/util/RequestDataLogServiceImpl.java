package com.platform.auth.client.util;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.platform.auth.client.rpc.client.IRestOperatorLogClient;
import com.platform.auth.common.util.IRequestDataLogService;
import com.platform.auth.common.util.RequestData;
import com.platform.auth.model.innermodel.request.RestOperatorLogRequest;
import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.web.response.entity.BaseResponse;

/**
 * 请求日志记录实现类
 * 
 * @version: 1.0
 */
@Slf4j
public class RequestDataLogServiceImpl implements IRequestDataLogService {

	@Autowired
	private IRestOperatorLogClient restOperatorLogClient;
	/**
	 * 应用名称
	 */
	@Value("${spring.application.name}")
	private String applicationName;

	@Override
	public void save(RequestData requestData) {

		RestOperatorLogRequest logRequest = new RestOperatorLogRequest();
		logRequest.setDatetime(requestData.getDatetime());
		logRequest.setUrl(requestData.getUrl());
		logRequest.setHeader(requestData.getHeader());
		logRequest.setParam(requestData.getParam());
		logRequest.setMethod(requestData.getMethod());
		logRequest.setBody(requestData.getBody());
		logRequest.setIdRequest(requestData.getIdRequest());
		logRequest.setDescription(requestData.getDescription());
		logRequest.setSystemCode(requestData.getSystemCode());
		logRequest.setModule(requestData.getModule());
		logRequest.setIp(requestData.getIp());
		logRequest.setUserIp(requestData.getUserIp());
		logRequest.setServiceName(applicationName);
		logRequest.setIdUser(requestData.getIdUser());
		BaseResponse<Object> response = restOperatorLogClient.save(logRequest);
		if (!response.isSuccessCode()) {
			log.info("保存日志失败:{}, 日志信息:{}", response, JsonUtil.toJSONString(requestData));
		}
	}
}
