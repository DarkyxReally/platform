package com.platform.system.common.model.mq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * token销毁模型
 * @version: 1.0
 */
@Getter
@Setter
public class TokenDestoryModel implements Serializable {

    private static final long serialVersionUID = -6533595606641364288L;
    
    /**
     * 用户token映射
     * key:用户id, value:指定的token, 当value为null或size等于0时, 销毁该用户所有的token
     */
    private Map<String, List<String>> userTokenMap;
    
    /**
     * 销毁指定用户的所有token
     */
    private List<String> idUsers;
    
    /**
     * 销毁指定的token
     */
    private List<String> idTokens;
    
    /**
     * 添加用户
     * @param idUser
     */
    public void addUser(String idUser){
        if (null == idUsers){
            idUsers = new ArrayList<String>();
        }
        idUsers.add(idUser);
    }

    /**
     * 添加指定的用户和token(两个条件同时满足时才会执行)
     * @param idUser 
     * @param idToken
     */
    public void addUser(String idUser, String idToken){
        if (null == userTokenMap){
            userTokenMap = new HashMap<String, List<String>>();
        }
        List<String> tokens = userTokenMap.get(idUser);
        if (null == tokens){
            tokens = new ArrayList<String>();
            userTokenMap.put(idUser, tokens);
        }
        tokens.add(idToken);
    }
    /**
     * 添加指定的用户和token(两个条件同时满足时才会执行)
     * @param idUser 
     * @param idTokens
     */
    public void addUser(String idUser, List<String> idTokens){
        if (null == userTokenMap){
            userTokenMap = new HashMap<String, List<String>>();
        }
        List<String> tokens = userTokenMap.get(idUser);
        if (null == tokens){
            tokens = new ArrayList<String>();
            userTokenMap.put(idUser, tokens);
        }
        tokens.addAll(idTokens);
    }
    
    
    /**
     * 添加用户
     * @param idUser
     */
    public void addUser(List<String> userIds){
        if (null == idUsers){
            idUsers = new ArrayList<String>();
        }
        idUsers.addAll(userIds);
    }
    
    /**
     * 添加token
     * @param idToken
     */
    public void addToken(String idToken){
        if (null == idTokens){
            idTokens = new ArrayList<String>();
        }
        idTokens.add(idToken);
    }
    
    /**
     * 添加token
     * @param tokenIds
     */
    public void addToken(List<String> tokenIds){
        if (null == idTokens){
            idTokens = new ArrayList<String>();
        }
        idTokens.addAll(tokenIds);
    }
}
