package com.platform.auth.bs.rpc.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.auth.common.annotation.AuthUserStatus;
import com.platform.auth.model.model.TokenUserInfo;
import com.platform.auth.model.model.provider.response.TokenInfoResponse;
import com.platform.auth.service.impl.WechatminiUserTokenServiceImpl;
import com.platform.system.common.context.user.AppUserDetail;
import com.platform.system.common.context.user.UserDetail;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.rest.BaseProvider;
import com.platform.system.common.web.response.entity.BaseResponse;
import com.platform.user.client.WechatUserClient;
import com.platform.user.innermodel.response.UserInfoResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;


/**
 * API用户token
 * 
 * @version: 1.0
 */
@Slf4j
@RestController
@AuthUserStatus(requireUser = false)
@RequestMapping("/api/jwt/user")
public class ApiUserTokenProvider extends BaseProvider {

    @Autowired
    @Qualifier(WechatminiUserTokenServiceImpl.BEAN_NAME)
    private WechatminiUserTokenServiceImpl userTokenService;
    
    @Autowired
    private WechatUserClient wechatUserClient;

    /**
     * 查询token信息
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public BaseResponse<TokenInfoResponse> info(@RequestParam("token") String token){
    	log.info("=========> 查询token信息");
        // 解析token是否异常, 默认为非异常
        boolean isTokenException = false;
        TokenUserInfo tokenInfo = null;
        try{
            tokenInfo = userTokenService.getAccessTokenInfo(token);
        }catch(ExpiredJwtException e){
            log.info("token已过期");
        }catch(UnsupportedJwtException e){
            isTokenException = true;
            log.error("不支持的JWTtoken", token);
        }catch(MalformedJwtException e){
            isTokenException = true;
            log.error("签名算法异常,token:{}, {}", token, e);
        }catch(SignatureException e){
            isTokenException = true;
            log.error("签名验证异常,token:{}", token);
        }catch(IllegalArgumentException e){
            isTokenException = true;
            log.error("签名参数异常,token:{}, {}", token, e);
        }catch(Exception e){
            isTokenException = true;
            log.error("验证token出现异常:" + e.getMessage(), e);
        }
        if(isTokenException){
            // 验证token出现异常
            return fail(StatusCode.SERVER_UNKNOWN_ERROR, "验证出现异常");
        }
        if(null == tokenInfo){
            // token验证通过,用户信息无效或者已过期,返回空,需要重新登陆
            return success();
        }else{
            UserDetail userDetail = getUserDetail(tokenInfo.getIdUser());
            if(null == userDetail){
                // 没有用户信息, 返回失败
                return fail(StatusCode.SERVER_UNKNOWN_ERROR, "查询不到用户信息");
            }
            TokenInfoResponse resp = new TokenInfoResponse();
            resp.setExpireDate(tokenInfo.getExpired());
            resp.setIdToken(tokenInfo.getIdToken());
            resp.setUserJsonInfo(JsonUtil.toJSONString(userDetail));
            return success(resp);
        }
    }

    /**
     * 获取用户详情
     * 
     * @param userType
     * @param idUser
     * @return
     */
    private UserDetail getUserDetail(String idUser){
    	UserDetail userDetail = null;
    	BaseResponse<UserInfoResponse> res = wechatUserClient.getUserByUserId(idUser);
    	UserInfoResponse re = res.getData();
    	if(re != null) {
    		userDetail = new AppUserDetail();
    		userDetail.setIdUser(idUser);
    		userDetail.setNickName(re.getName());
    	}
        return userDetail;
    }
}
