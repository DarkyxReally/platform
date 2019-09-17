package com.platform.auth.common.userdetail;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.auth.common.exception.AuthException;
import com.platform.auth.common.exception.NotSupportAuthException;
import com.platform.auth.common.exception.NullUserException;
import com.platform.system.common.constant.CommonConstants;
import com.platform.system.common.constant.RequestAttributeConst;
import com.platform.system.common.context.user.AppUserDetail;
import com.platform.system.common.context.user.AuthUserTypeConstant.AuthUserType;
import com.platform.system.common.context.user.IUserDetail;
import com.platform.system.common.context.user.UserDetail;
import com.platform.system.common.context.user.V0UserDetail;

import lombok.extern.slf4j.Slf4j;

/** 用户信息详情获取实现类
 * 
 * @version: 1.0
 * */
@Slf4j
public class UserDetailServiceImpl implements IUserDetailService {

    /** 分割符 */
    private static final String SPLIT_CHARACTER = "#";

    /** 编码 */
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Autowired
    private Environment environment;

    @Override
    public UserDetail getUser(HttpServletRequest request) {
        // 编码后的用户信息
        String encodeUserInfo = request.getHeader(RequestAttributeConst.AUTHORIZATION_HEADER);
        String clientVersion = request.getHeader("client-version");
        if(StringUtils.isBlank(clientVersion)) {
            return getUserInfoV0(encodeUserInfo);
        }
        return getUser(encodeUserInfo);
    }

    /**
     * 最初版的获取用户信息
     * @version: 
     * @param encodeUserInfo
     * @return
     */
    public static UserDetail getUserInfoV0(String encodeUserInfo) {
        if(StringUtils.isBlank(encodeUserInfo)) {
            //
            return null;
        }
        // 从请求头中获取
        byte[] userInfoByte = Base64Utils.decodeFromString(encodeUserInfo);
        if(null == userInfoByte) {
            //
            return null;
        }

        String userInfo = new String(userInfoByte, CommonConstants.UTF_8);
        String[] split = userInfo.split(SPLIT_CHARACTER);
        String idUser = null;
        String account = null;
        String nickName = null;
     
        if(split.length == 1) {
            idUser = split[0];
        } else if(split.length == 2) {
            idUser = split[0];
            account = split[1];
        } else if(split.length == 3) {
            idUser = split[0];
            account = split[1];
            nickName = split[2];
        } else {
            idUser = split[0];
            account = split[1];
            nickName = split[2];
        }
        if(StringUtils.isEmpty(idUser)) {
            idUser = null;
        }
        if(StringUtils.isEmpty(account)) {
            account = null;
        }
        if(StringUtils.isEmpty(nickName)) {
            nickName = null;
        }
        V0UserDetail userDetail = new V0UserDetail();
        userDetail.setIdUser(idUser);
        userDetail.setNickName(nickName);
        return userDetail;
    }

    @Override
    public UserDetail getUser(String encodeUserInfo) {
        if(StringUtils.isBlank(encodeUserInfo)) { return null; }

        // 解码后的用户信息
        String decodeUserInfo = new String(Base64Utils.decode(encodeUserInfo.getBytes(UTF8)), UTF8);
        try {
            String[] split = decodeUserInfo.split(SPLIT_CHARACTER);
            AuthUserType userType = null;
            try {
                String typeCode = split[0];
                userType = AuthUserType.valueOfCode(typeCode);
            } catch(IllegalArgumentException e) {
                throw new NotSupportAuthException("不支持的用户类型");
            }

            String userInfoStr = split[1];
            UserDetail userDetail = null;
            if(StringUtils.isNotBlank(userInfoStr)) {
                switch(userType){
//                    case AMDIN_USER:
//                        userDetail = JSON.parseObject(userInfoStr, AdminUserDetail.class);
//                        break;
                    case APP_USER:
                        userDetail = JSON.parseObject(userInfoStr, AppUserDetail.class);
                        break;
                	case AMDIN_USER:
                		userDetail = JSON.parseObject(userInfoStr, AppUserDetail.class);
                    default:
                        throw new NotSupportAuthException("不支持的用户类型");
                }
            }
            return userDetail;
        } catch(AuthException e) {
            log.error("用户认证异常:{}", e.getMessage());
            return null;
        }
    }

    @Override
    public String encodeUser(IUserDetail user){
        if(null == user){ throw new NullUserException("用户不存在"); }
        StringBuffer buffer = new StringBuffer();
        buffer.append(user.userType().strCode()).append(SPLIT_CHARACTER).append(JSONObject.toJSONString(user));
        // 用户类型#用户信息
        return Base64Utils.encodeToString(buffer.toString().getBytes(UTF8));
    }
}
