package com.platform.system.gate.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;

import com.alibaba.fastjson.JSONObject;
import com.platform.system.common.constant.CommonConstants;
import com.platform.system.common.util.WebUtil;
import com.platform.system.gate.service.IAppIdService;

@Slf4j
public class DefaultAppIdServiceImpl implements IAppIdService {

    /**
     * 获取appId
     * 
     * @param request
     * @return
     */

    public String getAppId(HttpServletRequest request){
        Map<String, String> params = getRequestParams(request);
        if(null != params){
            return params.get("apiKey");
        }
        return null;
    }

    /**
     * 获取请求参数
     * 
     * @param request
     * @return
     */
    private Map<String, String> getRequestParams(HttpServletRequest request){
        String method = request.getMethod();
        Map<String, String> params = null;
        if(!HttpMethod.GET.name().equals(method)){
            // 非GET请求的,直接获取body
            try{
                String bodyString = WebUtil.getRequestBodyString(request);
                if(StringUtils.isBlank(bodyString)){
                    return params;
                }
                params = new HashMap<String, String>();
                JSONObject json = JSONObject.parseObject(bodyString);
                for(Entry<String, Object> entry : json.entrySet()){
                    if(null != entry.getValue()){
                        params.put(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                }
            }catch(Exception e){
                log.error("校验签名,获取请求body数据出现异常:" + e.getMessage(), e);
            }
        }else{
            params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for(Entry<String, String[]> entry : requestParams.entrySet()){
                String name = entry.getKey();
                String[] values = entry.getValue();
                StringBuffer valueStr = new StringBuffer("");
                for(String vl : values){
                    valueStr.append(vl).append(",");
                }

                if(valueStr.length() > 0){
                    String vl = valueStr.substring(0, valueStr.length() - 1);
                    if(!"sign".equals(name)){ // 签名字段 不做解码
                        try{
                            vl = URLDecoder.decode(valueStr.substring(0, valueStr.length() - 1), CommonConstants.UTF8);
                        }catch(UnsupportedEncodingException e){
                            // 忽略解码异常
                        }
                    }

                    params.put(name, vl);
                }
            }
        }

        return params;
    }
}
