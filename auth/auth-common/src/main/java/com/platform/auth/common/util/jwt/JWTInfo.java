package com.platform.auth.common.util.jwt;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.platform.system.common.util.StringHelper;



/**
 * jwt token信息
 * @version: 1.0
 */
public class JWTInfo implements Serializable, IJWTInfo {

    private static final long serialVersionUID = 1L;

    private Map<String, String> infos;

    /**
     * id
     */
    private String id;
    
    public JWTInfo(){
        
    }
    
    public JWTInfo(String id){
        super();
        this.id = id;
    }

    public JWTInfo(String id, Map<String, Object> claims) {
        super();
        this.id = id;
        if(null != claims){
            infos = new HashMap<String, String>();
            for(Entry<String, Object> entry : claims.entrySet()){
                if(entry.getValue() != null){
                    infos.put(entry.getKey(), StringHelper.getObjectValue(entry.getValue()));
                }
            }
        }
    }

    /**
     * 获取其他属性
     * @param key
     * @return
     */
    @Override
    public String get(String key){
        if(null == infos){ return null; }
        return infos.get(key);
    }

    @Override
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public Map<String, String> getInfos(){
        return infos;
    }

    public void setInfos(Map<String, String> infos){
        this.infos = infos;
    }

    @Override
    public Map<String, String> infos(){
        return this.infos;
    }
}
