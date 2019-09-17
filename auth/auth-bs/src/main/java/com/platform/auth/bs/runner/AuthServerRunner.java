package com.platform.auth.bs.runner;

import java.security.KeyPair;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Base64Utils;

import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.common.config.AuthUserConfig;
import com.platform.system.common.util.RsaKeyHelper;

import lombok.extern.slf4j.Slf4j;


/**
 * 监听服务启动后运行配置相关密钥(根据账号密码生成)
 * @version: 1.0
 */
@Configuration
@Slf4j
public class AuthServerRunner implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    private static final String REDIS_USER_PRI_KEY = "AUTH:USER:PRI";
    
    private static final String REDIS_USER_PUB_KEY = "AUTH:USER:PUB";
    
    private static final String REDIS_SERVICE_PRI_KEY = "AUTH:CLIENT:PRI";
    
    private static final String REDIS_SERVICE_PUB_KEY = "AUTH:CLIENT:PUB";

    @Autowired
    private AuthUserConfig userConfig;
    
    @Autowired
    private AuthClientConfig clientConfig;

    /**
     * 获取redis数据
     * @param key
     * @return
     */
    private byte[] getRedisValue(String key){
        BoundValueOperations<String,String> valueOps = stringRedisTemplate.boundValueOps(key);
        String data = valueOps.get();
        if (StringUtils.isEmpty(data)){
            return null;
        }
        return Base64Utils.decodeFromString(data);
    }

    /**
     * 设置redis数据
     * @param key
     * @return
     */
    private void setRedisValue(String key,  byte[] value){
        stringRedisTemplate.boundValueOps(key).set(encodeToString(value));
    }

    /**
     * 编码
     * @param data
     * @return
     */
    private String encodeToString(byte[] data){
        return Base64Utils.encodeToString(data);
    }

    @Override
    public void run(String... args) throws Exception{
    	log.info("开始配置公私钥");
        //存在缓存
        if(stringRedisTemplate.hasKey(REDIS_USER_PRI_KEY)){
        	log.info("存在缓存");
        	log.info("用户密钥：{}",userConfig.getRsaSecret());
        	log.info("服务密钥：{}",clientConfig.getRsaSecret());
            userConfig.setPrivateKey(getRedisValue(REDIS_USER_PRI_KEY));
            userConfig.setPublicKey(getRedisValue(REDIS_USER_PUB_KEY));
            clientConfig.setPrivateKey(getRedisValue(REDIS_SERVICE_PRI_KEY));
            clientConfig.setPublicKey(getRedisValue(REDIS_SERVICE_PUB_KEY));
        }else{
        	log.info("实时获取");
        	log.info("用户密钥：{}",userConfig.getRsaSecret());
        	log.info("服务密钥：{}",clientConfig.getRsaSecret());
            // 根据密码生成用户密钥对
            KeyPair keyPair = RsaKeyHelper.generateKeyPaid(userConfig.getRsaSecret());
            byte[] privateKey = RsaKeyHelper.getPrivateKey(keyPair);
            byte[] publicKey = RsaKeyHelper.getPublicKey(keyPair);
            userConfig.setPrivateKey(privateKey);
            userConfig.setPublicKey(publicKey);
            setRedisValue(REDIS_USER_PRI_KEY, privateKey);
            setRedisValue(REDIS_USER_PUB_KEY, publicKey);
            // 服务密钥对
            keyPair = RsaKeyHelper.generateKeyPaid(clientConfig.getRsaSecret());
            privateKey = RsaKeyHelper.getPrivateKey(keyPair);
            publicKey = RsaKeyHelper.getPublicKey(keyPair);
            clientConfig.setPrivateKey(RsaKeyHelper.getPrivateKey(keyPair));
            clientConfig.setPublicKey(RsaKeyHelper.getPublicKey(keyPair));
            setRedisValue(REDIS_SERVICE_PRI_KEY, privateKey);
            setRedisValue(REDIS_SERVICE_PUB_KEY, publicKey);
        }
    }
}
