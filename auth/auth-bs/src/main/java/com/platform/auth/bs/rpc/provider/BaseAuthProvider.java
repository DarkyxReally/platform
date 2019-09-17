package com.platform.auth.bs.rpc.provider;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.platform.auth.common.annotation.AuthUserStatus;
import com.platform.auth.common.util.jwt.LoginDTO;
import com.platform.auth.model.model.response.Token;
import com.platform.system.common.enums.rest.AccountCode;
import com.platform.system.common.enums.rest.AuthCode;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.rest.BaseProvider;
import com.platform.system.common.web.response.entity.BaseResponse;
import com.platform.system.common.web.response.entity.ErrorResponse;


/**
 * 认证基础类
 * @version: 1.0
 */
public abstract class BaseAuthProvider extends BaseProvider {

    @Autowired
    protected HttpServletRequest request;

    /**
     * 刷新token
     * @param token
     * @return
     */
    protected abstract Token refreshToken(String token);

    /**
     * 验证token合法性
     * @param token
     * @return Object[0] = 验证结果, Object[1] = 扩展信息
     */
    protected abstract Object[] validateToken(String token);

    /**
     * 登陆
     * @param userName
     * @param password
     * @return
     */
    protected abstract Token login(LoginDTO loginDTO);
    
    /**
     * 根据用户id生成token
     * @param idUser
     * @return
     */
    protected abstract Token createTokenByIdUser(String idUser);

    /**
     * 登陆、获取token凭证
     * @param loginDTO
     * @return
     * @throws Exception
     */
    @AuthUserStatus(requireUser = false)
    @RequestMapping(value = "token", method = RequestMethod.POST)
    public BaseResponse<Token> token(@RequestBody LoginDTO loginDTO){
        Token token = login(loginDTO);
        if(null != token){
            return new BaseResponse<Token>(token);
        }else{
            return new ErrorResponse<Token>(AccountCode.INVALID_CREDENTIAL);
        }
    }
    
    /**
     * 根据用户id获取token
     * @param loginDTO
     * @return
     * @throws Exception
     */
    @AuthUserStatus(requireUser = false)
    @RequestMapping(value = "tokenByUserId", method = RequestMethod.POST)
    public BaseResponse<Token> tokenByUserId(@RequestParam("userId") String userId){
        if (StringUtils.isBlank(userId)){
            return new ErrorResponse<Token>(StatusCode.DATA_NOT_EXISTS);
        }
        Token token = createTokenByIdUser(userId);
        if(null != token){
            return new BaseResponse<Token>(token);
        }else{
            return new ErrorResponse<Token>(StatusCode.DATA_NOT_EXISTS);
        }
    }

    /**
     * 刷新token服务
     * @return
     */
    @AuthUserStatus(requireUser = false)
    @RequestMapping(value = "refresh", method = RequestMethod.POST)
    public BaseResponse<Token> refresh(@RequestParam("token")String token){
        Token refreshedToken = refreshToken(token);

        if(refreshedToken == null){
            return new ErrorResponse<Token>(AuthCode.TOKEN_REFRESH_FAIL);
        }else{
            return new BaseResponse<Token>(refreshedToken);
        }
    }

//    /**
//     * 验证token合法性
//     * @param token
//     * @return
//     * @throws Exception
//     */
//    @AuthUserStatus(require = false)
//    @RequestMapping(value = "verify", method = RequestMethod.POST)
//    protected abstract BaseResponse<Boolean> verify(@RequestParam("token") String token);
//
//    /**
//     * 注销token
//     * @param token
//     * @return
//     */
//    @AuthUserStatus(require = false)
//    @RequestMapping(value = "invalid", method = RequestMethod.POST)
//    public BaseResponse<Boolean> invalid(@RequestParam("token") String token){
//        boolean invalid = invalidToken(token);
//        if(invalid){
//            return new BaseResponse<Boolean>(true);
//        }else{
//            return new ErrorResponse<Boolean>(AuthCode.TOKEN_REFRESH_FAIL);
//        }
//    }
}
