package com.platform.auth.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.platform.auth.dao.AccessTokenEntityDao;
import com.platform.auth.entity.AccessTokenEntity;
import com.platform.auth.entity.AccessTokenEntityExample;
import com.platform.auth.entity.AccessTokenEntityExample.Criteria;
import com.platform.auth.model.innermodel.TerminalChannelConstant.TerminalChannel;
import com.platform.auth.model.model.constant.UserTypeConstant.UserType;
import com.platform.auth.service.IAccessTokenEntityService;
import com.platform.system.common.util.GuidGenerator;


/**
 * AccessToken实体实现
 * @version: 1.0
 */
@Service
public class AccessTokenEntityServiceImpl extends BaseCrudServiceImpl<AccessTokenEntityDao, AccessTokenEntity> implements IAccessTokenEntityService {

	@Override
	public String insertToken(String idUser, Date expire, String platform, TerminalChannel channel, UserType userType) {
		String tokenId = GuidGenerator.generate32();
        AccessTokenEntity entity = new AccessTokenEntity();
        entity.setTokenId(tokenId);
        entity.setUserId(idUser);
        entity.setCreatedDate(new Date());
        entity.setExpiredDate(expire);
        entity.setIsInvalid(Boolean.FALSE);
        entity.setPlatform(platform);
        entity.setTokenType(channel.strCode());
        entity.setUserType(userType.strCode());
        entity.setCreatedUser(idUser);
        entity.setUpdatedUser(idUser);
        entity.setUpdatedDate(new Date());
        baseEntityDao.insertSelective(entity);
		return tokenId;
	}

	@Override
	public boolean isValid(String idToken) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AccessTokenEntity getTokenEntity(String idToken) {
		AccessTokenEntity entity = new AccessTokenEntity();
		entity.setTokenId(idToken);
		entity.setIsInvalid(false);
		AccessTokenEntity en = baseEntityDao.selectOne(entity);
        return en;
	}

	@Override
	public int updateToInvalid(String idToken) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateToInvalid(String idToken, String idUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateToInvalid(List<String> idTokens) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateTokenToInvalidByIdUser(String idUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateTokenToInvalidByIdUser(String idUser, TerminalChannel channel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateToInvalid(List<String> idTokens, String idUser) {
		AccessTokenEntity entity = new AccessTokenEntity();
        entity.setIsInvalid(Boolean.TRUE);

        AccessTokenEntityExample example = new AccessTokenEntityExample();
        Criteria criteria = example.createCriteria();
        // tokenID匹配
        criteria.andTokenIdIn(idTokens);
        // 未过期
        criteria.andIsInvalidEqualTo(Boolean.FALSE);
        criteria.andExpiredDateGreaterThan(new Date());
        return baseEntityDao.updateByExampleSelective(entity, example);
	}

	@Override
	public List<AccessTokenEntity> queryUserValidTokens(String idUser, TerminalChannel channel) {
		AccessTokenEntityExample example = new AccessTokenEntityExample();
        Criteria criteria = example.createCriteria();
        // 用户id匹配
        criteria.andUserIdEqualTo(idUser);
        // 未过期
        criteria.andIsInvalidEqualTo(Boolean.FALSE);
        criteria.andExpiredDateGreaterThan(new Date());
        if (null != channel){
            // 指定特定类型的token
            criteria.andTokenTypeEqualTo(channel.strCode());
        }
        return baseEntityDao.selectByExample(example);
	}
}
