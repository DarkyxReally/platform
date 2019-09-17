package com.platform.auth.bs.rpc.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.auth.common.annotation.AuthUserStatus;
import com.platform.auth.common.annotation.IgnoreClientToken;
import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.common.config.AuthUserConfig;
import com.platform.auth.common.util.jwt.LoginDTO;
import com.platform.auth.model.model.response.Token;
import com.platform.auth.service.impl.ClientAuthServiceImpl;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.web.response.entity.BaseResponse;
import com.platform.system.common.web.response.entity.CollectionResponse;
import com.platform.system.common.web.response.entity.ErrorResponse;

/**
 * 客户端认证服务
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("client")
@IgnoreClientToken
@AuthUserStatus(requireUser = false)
public class ClientProvider extends BaseAuthProvider{

    @Autowired
    private ClientAuthServiceImpl clientAuthService;

    @Autowired
    private AuthClientConfig authClientConfig;
    @Autowired
    private AuthUserConfig authUserConfig;

    /**
     * 根据服务id获取授权的客户端列表
     * @param serviceId
     * @param secret
     * @return
     */
    @RequestMapping(value = "/myClient", method = RequestMethod.GET)
    public CollectionResponse<String> getAllowedClient(@RequestParam("serviceId") String serviceId,
            @RequestParam("secret") String secret){
        return new CollectionResponse<String>(clientAuthService.getAllowedClient(serviceId, secret));
    }

    /**
     * 获取服务认证公钥
     * @param clientId
     * @param secret
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/servicePubKey", method = RequestMethod.GET)
    public BaseResponse<byte[]> getServicePublicKey(@RequestParam("clientId") String clientId,
            @RequestParam("secret") String secret) throws Exception{
    	log.info("==========> 获取服务认证公钥");
    	log.info("==========> 公钥：{}",authClientConfig.getPublicKey());
        boolean validate = clientAuthService.validate(clientId, secret);
        if (validate){
            return new BaseResponse<byte[]>(authClientConfig.getPublicKey());
        }else{
            return new ErrorResponse<>(StatusCode.DATA_NOT_EXISTS);
        }
    }

    /**
     * 获取用户认证公钥
     * @param clientId
     * @param secret
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userPubKey", method = RequestMethod.GET)
    public BaseResponse<byte[]> getUserPublicKey(@RequestParam("clientId") String clientId,
            @RequestParam("secret") String secret) throws Exception{
    	log.info("===========> 获取用户认证公钥");
    	log.info("==========> 公钥：{}",authClientConfig.getPublicKey());
        boolean validate = clientAuthService.validate(clientId, secret);
        if (validate){
            return new BaseResponse<byte[]>(authUserConfig.getPublicKey());
        }else{
            return new ErrorResponse<>(StatusCode.DATA_NOT_EXISTS);
        }
    }

    @Override
    protected Token refreshToken(String token){
        try{
            return clientAuthService.refresh(token);
        }catch(Exception e){
            log.error("刷新token异常" + e.getMessage(), e);
            return null;
        }
    }
//
//    @Override
//    protected boolean invalidToken(String token){
//        try{
//            return clientAuthService.invalid(token);
//        }catch(Exception e){
//            log.error("注销token:" + token + ",异常:", e);
//        }
//        return false;
//    }

    @Override
    protected Object[] validateToken(String token){
        try{
            clientAuthService.validate(token);
            return new Object[]{true, null};
        }catch(Exception e){
            log.error("验证token合法性:" + token + "异常", e);
        }
        return new Object[]{false, null};
    }

    @Override
    protected Token login(LoginDTO loginDTO){
        try{
            return clientAuthService.apply(loginDTO.getUsername(), loginDTO.getPassword());
        }catch(Exception e){
            log.error("客户端验证异常:" + e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected Token createTokenByIdUser(String idUser){
        log.error("调用有误, 不支持该接口的调用");
        return null;
    }

}
