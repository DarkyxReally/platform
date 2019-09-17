package com.platform.auth.client.runner;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.platform.auth.client.jwt.ServiceAuthUtil;
import com.platform.auth.client.rpc.client.AuthClient;
import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.common.config.AuthUserConfig;
import com.platform.auth.common.util.jwt.LoginDTO;
import com.platform.auth.model.model.response.Token;
import com.platform.system.common.exception.ExceptionUtils;
import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.web.response.entity.ObjectResponse;

/**
 * 监听服务启动完成时触发运行认证客户端配置数据获取
 * @version: 1.0
 */
@Configuration
@Slf4j
public class AuthClientRunner implements CommandLineRunner, Ordered {

    @Autowired
    private AuthClientConfig clientConfig;
    @Autowired
    private AuthUserConfig userConfig;
    @Autowired
    private AuthClient authClient;
    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    /**
     * 尝试30次
     */
    private int tryTimes = 30;

    /**
     * 已失败次数
     */
    private int failTimes = 0;

    @Override
    public void run(String... args) throws Exception{
        boolean isUserAuthInit = false;
        boolean isClientAuthInit = false;
        boolean isTokenAuthInit = false;

        while(failTimes < tryTimes){
            try{
                if(!isUserAuthInit){
                    log.info("初始化加载用户pubKey");
                    log.info("clientId: "+ clientConfig.getClientId());
                    log.info("Secret: "+ clientConfig.getSecret());
                    ObjectResponse<byte[]> resp = authClient.getUserPublicKey(clientConfig.getClientId(), clientConfig.getSecret());
                    if(resp.isSuccessCode()){
                        this.userConfig.setPublicKey(resp.getData());
                        isUserAuthInit = true;
                    }else{
                        log.warn("获取认证服务用户加密公钥失败:{}", JsonUtil.toJSONString(resp));
                    }
                }

                if(!isClientAuthInit){
                    log.info("初始化加载客户端pubKey");
                    ObjectResponse<byte[]> resp = authClient.getServicePublicKey(clientConfig.getClientId(), clientConfig.getSecret());
                    if(resp.isSuccessCode()){
                        this.clientConfig.setPublicKey(resp.getData());
                        isClientAuthInit = true;
                    }else{
                        log.warn("获取认证服务服务间认证加密公钥失败:{}", JsonUtil.toJSONString(resp));
                    }
                }

                if(!isTokenAuthInit){
                    log.info("初始化加载客户端token");
                    ObjectResponse<Token> accessToken = authClient.token(new LoginDTO(clientConfig.getClientId(), clientConfig.getSecret()));
                    if(accessToken.isSuccessCode()){
                        serviceAuthUtil.setClientToken(accessToken.getData().getAccessToken());
                        isTokenAuthInit = true;
                    }else{
                        log.warn("获取认证客户端token失败,{}", JsonUtil.toJSONString(accessToken));
                    }
                }
            }catch(HystrixRuntimeException e){
                if(clientConfig.isFastFail()){
                    // 快速失败
                    throw e;
                }
                log.error("认证异常:{}", ExceptionUtils.getMessage(e));
            }catch(Exception e){
                if(clientConfig.isFastFail()){
                    // 快速失败
                    throw e;
                }
                log.error("认证异常:"+e.getMessage(), e);
            }

            if(isUserAuthInit && isClientAuthInit && isTokenAuthInit){
                // 初始化结束
                break;
            }else{
                failTimes++;
                if(clientConfig.isFastFail()){
                    // 快速失败, 跳出结束
                    break;
                }
                log.info("5秒钟后重试");
                Thread.sleep(5000);
            }
        }

        if(!isUserAuthInit){
            // 初始化认证服务用户加密公钥失败
            throw new RuntimeException("初始化认证服务用户加密公钥失败");
        }

        if(!isClientAuthInit){
            // 初始化服务间认证加密公钥失败
            throw new RuntimeException("初始化服务间认证加密公钥失败");
        }

        if(!isTokenAuthInit){
            // 初始化认证客户端token失败
            throw new RuntimeException("初始化认证客户端token失败");
        }

        failTimes = 0;
        // 标记为已经初始化
        clientConfig.setInit(true);
        log.info("初始化[认证相关]信息结束");
    }

    @Override
    public int getOrder(){
        return 1;
    }
}