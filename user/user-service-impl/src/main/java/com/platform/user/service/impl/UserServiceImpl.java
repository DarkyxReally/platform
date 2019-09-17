package com.platform.user.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.system.common.util.GuidGenerator;
import com.platform.system.common.util.RandomNumUtil;
import com.platform.system.common.util.UserPasswordEncode;
import com.platform.user.dao.UserEntityDao;
import com.platform.user.dao.UserThirdEntityDao;
import com.platform.user.entity.UserEntity;
import com.platform.user.entity.UserThirdEntity;
import com.platform.user.innermodel.dto.UserInfoDTO;
import com.platform.user.innermodel.request.UserRegisterRequest;
import com.platform.user.innermodel.response.UserInfoResponse;
import com.platform.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl extends AbstractUserService<UserEntityDao, UserEntity> implements UserService{
	
	@Autowired
	private UserEntityDao userEntityDao;
	@Autowired
	private UserThirdEntityDao userThirdEntityDao;
	@Value("${user.password}")
    private String password;
	@Override
	@Transactional
	public String addUser(UserRegisterRequest registerReq) {
		log.info("======> addUser()");
		UserEntity userEntity = new UserEntity();
		String userId = GuidGenerator.generate32();
		String pwd = UserPasswordEncode.encode(password);
		userEntity.setUserId(userId);
		userEntity.setPassword(pwd);
		userEntity.setAccount(System.currentTimeMillis()+RandomNumUtil.getFourRandomNum());
		userEntity.setType("WECHAT");
		userEntity.setName(registerReq.getName());
		userEntity.setCreatedDate(new Date());
		userEntity.setUpdatedDate(new Date());
		userEntity.setCreatedUser(userId);
		userEntity.setUpdatedUser(userId);
		UserThirdEntity en = new UserThirdEntity();
		en.setCreatedDate(new Date());
		en.setUpdatedDate(new Date());
		en.setThirdType(1);
		en.setThirdUuid(registerReq.getThirdUuid());
		en.setUserId(userId);
		en.setCreatedUser(userId);
		en.setUpdatedUser(userId);
		if(StringUtils.isNotBlank(registerReq.getThirdUrl())) {
			en.setThirdUrl(registerReq.getThirdUrl());
		}
		userEntityDao.insertSelective(userEntity);//插入用户表
		userThirdEntityDao.insertSelective(en);//插入用户三方表
		log.info("======> 新增用户成功");
		return userId;
	}

	@Override
	public UserInfoResponse selectByUnionId(String unionId) {
		UserInfoDTO dto = userEntityDao.selectByUnionId(unionId);
		UserInfoResponse re = new UserInfoResponse();
		if(dto != null) {
			re.setUserId(dto.getUserId());
			re.setUrl(dto.getUrl());
			re.setName(dto.getName());
		}
		return re;
	}

	@Override
	public UserInfoResponse selectByUserId(String userId) {
		UserEntity user = new UserEntity();
		user.setUserId(userId);
		UserEntity en = userEntityDao.selectOne(user);
		UserInfoResponse re = null;
		if(en != null) {
			re = new UserInfoResponse();
			re.setName(en.getName());
		}
		return re;
	}
}
