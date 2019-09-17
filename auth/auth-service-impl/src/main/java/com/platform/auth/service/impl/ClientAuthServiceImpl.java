package com.platform.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.platform.auth.common.constants.AuthConstants;
import com.platform.auth.common.util.jwt.JWTInfo;
import com.platform.auth.dao.AuthClientDao;
import com.platform.auth.dao.AuthClientEntityDao;
import com.platform.auth.entity.AuthClientEntity;
import com.platform.auth.model.model.response.Token;
import com.platform.auth.service.AuthClientService;
import com.platform.auth.service.AuthService;
import com.platform.auth.service.impl.util.client.ClientTokenUtil;
import com.platform.system.common.util.GuidGenerator;

/**
 * 客户端认证
 * 
 * @version: 1.0
 */
@Service
@Slf4j
public class ClientAuthServiceImpl implements AuthClientService, AuthService {

	@Autowired
	private AuthClientDao authClientDao;
	@Autowired
	private AuthClientEntityDao clientEntityDao;

	@Autowired
	private ClientTokenUtil clientTokenUtil;

	@Autowired
	public ClientAuthServiceImpl(ApplicationContext context) {
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Token apply(String clientId, String secret) throws Exception {
		log.info("clientId：{}，secret：{}", clientId, secret);
		AuthClientEntity client = getClient(clientId, secret);
		if (null == client) {
			return null;
		}
		log.info("查询结果：{}", client);
		Map<String, Object> info = new HashMap<String, Object>();
		info.put(AuthConstants.JWT_ID, client.getClientCode());
		info.put(AuthConstants.JWT_UNIQUE_NAME, client.getClientCode());
		return clientTokenUtil.generateToken(new JWTInfo(client.getId(), info));
	}

	/**
	 * 根据客户端code和密码查询
	 * 
	 * @param clientId
	 * @param secret
	 * @return
	 */
	private AuthClientEntity getClient(String clientId, String secret) {
		AuthClientEntity client = new AuthClientEntity();
		client.setClientCode(clientId);
		client.setClientSecret(secret);
		client.setIsDel(false);
		client.setIsLocked(false);
		return clientEntityDao.selectOne(client);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean validate(String clientId, String secret) throws Exception {
		AuthClientEntity info = getClient(clientId, secret);
		System.out.println(
				"来这里了.................................................................................................来这里了.................");
		if (null != info) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> getAllowedClient(String clientId, String secret) {
		AuthClientEntity info = this.getClient(clientId, secret);
		if (null != info) {
			List<String> clients = authClientDao.selectAllowedClient(info.getId());
			if (clients != null) {
				return clients;
			}
		}
		return new ArrayList<String>();
	}

	@Override
	public List<String> getAllowedClient(String serviceId) {
		AuthClientEntity info = getClient(serviceId);
		List<String> clients = authClientDao.selectAllowedClient(info.getId());
		if (clients == null) {
			return new ArrayList<String>();
		}
		return clients;
	}

	private AuthClientEntity getClient(String clientId) {
		AuthClientEntity client = new AuthClientEntity();
		client.setClientCode(clientId);
		client = clientEntityDao.selectOne(client);
		return client;
	}

	/**
	 * 注册客户端
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void registryClient(String clientId,String secret) {
		// 自动注册节点
		AuthClientEntity client = new AuthClientEntity();
		client.setClientCode(clientId);
		client.setIsDel(false);
		AuthClientEntity dbClient = clientEntityDao.selectOne(client);
		if (dbClient == null) {
			client.setId(GuidGenerator.generate32());
			client.setServiceName(clientId);
			client.setIsLocked(false);
			client.setClientSecret(secret);
			clientEntityDao.insert(client);
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Token login(String username, String password) throws Exception {
		return apply(username, password);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Token refresh(String oldToken) throws Exception {
		return clientTokenUtil.refresh(oldToken);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean validate(String token) throws Exception {
		clientTokenUtil.getInfoFromToken(token);
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Boolean invalid(String token) {
		clientTokenUtil.invalid(token);
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Token createTokenByIdUser(String idUser) throws Exception {
		throw new IllegalAccessError("不支持");
	}

	@Override
	public void registryClient() {
		
	}
}
