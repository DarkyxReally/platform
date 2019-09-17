package com.platform.auth.bs.controller;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.platform.auth.common.config.AuthUserConfig;
import com.platform.auth.model.innermodel.TerminalChannelConstant.TerminalChannel;
import com.platform.auth.model.innermodel.token.AccessToken;
import com.platform.auth.model.model.TokenUserInfo;
import com.platform.auth.model.model.api.response.WechatRegisterResponse;
import com.platform.auth.model.model.constant.UserTypeConstant.UserType;
import com.platform.auth.model.model.request.WechatRegisterRequest;
import com.platform.auth.service.IAccessTokenService;
import com.platform.system.common.config.WechatConfig;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.rest.BaseController;
import com.platform.system.common.util.AesCbcUtil;
import com.platform.system.common.util.HttpWxUtils;
import com.platform.system.common.util.RsaKeyHelper;
import com.platform.system.common.web.response.entity.BaseResponse;
import com.platform.user.client.WechatUserClient;
import com.platform.user.innermodel.request.UserRegisterRequest;
import com.platform.user.innermodel.response.UserInfoResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth/wechat")
public class WechatController extends BaseController{
	
	/*
	 * @Autowired
	 * 
	 * @Qualifier(AppUserLoginLogoutHandleServiceImpl.BEAN_NAME) private
	 * ILoginHandleService<WechatminiUserLoginModel> loginHandleService;
	 */
	
	@Autowired
    private WechatConfig wechatConfig;
	
	@Autowired
    private WechatUserClient wechatUserClient;
	
	@Autowired
    private IAccessTokenService accessTokenService;
	
	@Autowired
    private AuthUserConfig userConfig;
	/**
	 * @description：检查用户是否登录
	 * @author：李文斌
	 * @since： 2019年9月2日下午7:05:12
	 */
	@ResponseBody
	@RequestMapping("/checkLogin")
	public BaseResponse<WechatRegisterResponse> checkLogin(String token,String js_code,String iv,String emcryptedData) throws Exception {
		log.info("======> 进入登录检查");
		//用户没有登录 重新登录
		try {
			if(StringUtils.isNotBlank(token)){//一段时间内用户登录过 免重复登录
				log.info("======> token存在");
				PublicKey key = RsaKeyHelper.getPublicKey(userConfig.getPublicKey()); 
	            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
				Claims claims = claimsJws.getBody();
				// tokenId
		        String idToken = claims.getId();
		        // 用户id
		        String idUser = claims.getSubject();
		        log.info("======> token信息中解析出的用户id：{}",idUser);
		        if(StringUtils.isNotBlank(idToken) && StringUtils.isNotBlank(idUser)){//token未失效，登录成功
		        	WechatRegisterResponse res = new WechatRegisterResponse();
		        	res.setUserId(idUser);
		        	res.setToken(token);
		        	return success(res);
		        }
			}
			String unionId = getUnionId(js_code,emcryptedData,iv);
			if(StringUtils.isNotBlank(unionId)){
				BaseResponse<UserInfoResponse> response = wechatUserClient.getUserByUnionId(unionId);
				UserInfoResponse user = response.getData();
				if(user != null && StringUtils.isNotBlank(user.getUserId())){//该用户已注册,并保存用户信息
					TokenUserInfo userInfo = new TokenUserInfo();
					userInfo.setIdUser(user.getUserId());
					userInfo.setUserType(UserType.WECHATMINI_USER);
					userInfo.setTerminalChannel(TerminalChannel.WECHAT_MINI);
					AccessToken accessToken = accessTokenService.createAccessToken(userInfo);
					WechatRegisterResponse res = new WechatRegisterResponse();
					res.setUserId(user.getUserId());
					res.setToken(accessToken.getToken());
					log.info("============>离开checkLogin方法，返回用户id：{}",user.getUserId());
					return success(res);
				}else{
					log.info("======> unionid为"+unionId+"的用户不存在");
					return success();
				}
			} else {
				log.info("======> unionid不存在，未注册");
				return success();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return fail(StatusCode.LOGIN_FAIL);
		}
	}
	
	/**
	 * @description：获取unionId
	 * @author：李文斌
	 * @since： 2019年8月30日上午10:33:40
	 */
	private String getUnionId(String js_code, String emcryptedData, String iv) {
		//获取js_code对应的unionId
		String grant_type = "authorization_code";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", wechatConfig.getZhAppId());
		map.put("secret", wechatConfig.getZhSecret());
		map.put("js_code", js_code);
		map.put("grant_type", grant_type);
		String url = wechatConfig.getWechatUrl();
		log.info("======> appid：{},secret：{},url：{}",wechatConfig.getZhAppId(),wechatConfig.getZhSecret(),wechatConfig.getWechatUrl());
		String result;
		String unionId = null;
		try {
			result = HttpWxUtils.get(url,map);
			log.info("======> jscode参数：{},获取的结果：{}",js_code,result);
			Map<String,Object> resultMap = JsonUtil.StringToMap(result);
			unionId= (String)resultMap.get("unionid").toString();
			String key = resultMap.get("session_key").toString();
			if(StringUtils.isBlank(unionId) && StringUtils.isNotBlank(key) && StringUtils.isNotBlank(emcryptedData.toString()) && StringUtils.isNotBlank(iv)){
				log.info("==========>开始解密获取用户unionid");
				String re = AesCbcUtil.decrypt(emcryptedData, key, iv, "UTF-8");
				log.info("==========>解密获取用户信息" + re);
				if (re != null) {
					Map<String, Object> m = JsonUtil.StringToMap(re);
					unionId = (String)m.get("unionId");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("请求获取微信unionId异常");
		}
		return unionId;
    }
	/**
	 * @description：注册小程序用户
	 * @author：李文斌
	 * @since： 2019年8月30日下午7:28:22
	 */
	@ResponseBody
	@RequestMapping("/register")
	public BaseResponse<WechatRegisterResponse> register(WechatRegisterRequest registerRequest){
		try {
			String unionId = getUnionId(registerRequest.getJsCode(), registerRequest.getEncryptedData(), registerRequest.getIv());
			if (StringUtils.isNotBlank(unionId)) {
				// 查询该unionId是否注册，如果没有就注册小程序用户
				BaseResponse<UserInfoResponse> response = wechatUserClient.getUserByUnionId(unionId);
				UserInfoResponse user = response.getData();
				String userId = null;
				if (user == null || StringUtils.isBlank(user.getUserId())) {
					//用户不存在
					UserRegisterRequest ur = new UserRegisterRequest();
					if(StringUtils.isNotBlank(registerRequest.getPhone())) {
						ur.setPhone(registerRequest.getPhone());
					}
					if(StringUtils.isNotBlank(registerRequest.getImgUrl())) {
						ur.setThirdUrl(registerRequest.getImgUrl());
					}
					ur.setName(registerRequest.getNickname());
					ur.setThirdUuid(unionId);
					BaseResponse<String> re = wechatUserClient.registerUser(ur);
					userId = re.getData();
				}else {
					userId = user.getUserId();
				}
				if(StringUtils.isBlank(userId)) {
					return fail(StatusCode.OPERATION_FAIL);
				}
				TokenUserInfo userInfo = new TokenUserInfo();
				userInfo.setIdUser(userId);
				userInfo.setUserType(UserType.WECHATMINI_USER);
				userInfo.setTerminalChannel(TerminalChannel.WECHAT_MINI);
				AccessToken accessToken = accessTokenService.createAccessToken(userInfo);
				WechatRegisterResponse res = new WechatRegisterResponse();
				res.setUserId(userId);
				res.setToken(accessToken.getToken());
				log.info("============>离开register方法，返回用户id：{}",userId);
				return success(res);
			} else {
				log.info("用户-unionid未获取到，或者js_code错误");
				return fail(StatusCode.RET_PARAMERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("小程序用户注册异常", e);
			return fail(StatusCode.OPERATION_FAIL);
		}
	}
	
	 /**
     * @description：解密获取手机号
     * @author：李文斌
     * @since： 2019年9月10日下午3:58:52
     * @param：
     */
    @RequestMapping(value = {"/getPhone"})
    public BaseResponse<?> getPhone(@RequestParam("encryptedData") String encryptedData,@RequestParam("iv") String iv,@RequestParam("jsCode") String jsCode){
    	log.info("=======> 开始调用解密获取手机号接口加密参数encryptedData：{},加密参数iv：{}",encryptedData,iv);
		if(StringUtils.isBlank(encryptedData) || StringUtils.isBlank(iv) || StringUtils.isBlank(jsCode)) {
			return fail(StatusCode.RET_PARAMERROR);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", wechatConfig.getZhAppId());
		map.put("secret", wechatConfig.getZhSecret());
		map.put("js_code", jsCode);
		map.put("grant_type", "authorization_code");
		String url = wechatConfig.getWechatUrl();
		log.info("======> appid：{},secret：{},url：{}",wechatConfig.getZhAppId(),wechatConfig.getZhSecret(),wechatConfig.getWechatUrl());
		Map<String,Object> state = new HashMap<String,Object>();
		try {
			String result = HttpWxUtils.get(url,map);
			log.info("======> jscode参数：{},获取的结果：{}",jsCode,result);
			Map<String,Object> resultMap = JsonUtil.StringToMap(result);
			String key = resultMap.get("session_key").toString();
			String re = AesCbcUtil.decrypt(encryptedData, key, iv, "UTF-8");
			if(re != null){
				Map<String, Object> m = JsonUtil.StringToMap(re);
				Object phone  = m.get("purePhoneNumber");
				state.put("phone", phone);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("请求获取微信小程序绑定手机号异常");
			return fail(StatusCode.OPERATION_FAIL);
		}
		return success(state);
	}
}
