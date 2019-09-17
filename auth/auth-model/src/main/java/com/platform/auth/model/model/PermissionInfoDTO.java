package com.platform.auth.model.model;

import java.io.Serializable;

/**
 * 权限信息
 * @version: 1.0
 */
public class PermissionInfoDTO implements Serializable {

    private static final long serialVersionUID = -2700844952128357838L;
    
    /**
     * 权限id
     */
    private String id;
    
    /**
     * 上级id
     */
    private String parentId;
    
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限编码
     */
    private String code;
    /**
     * 权限类型(0：菜单; 1: 按钮; 2: 接口)
     */
    private String type;
    /**
     * URI
     */
    private String uri;
    /**
     * 请求方式
     */
    private String method;
    
    /**
     * 图标
     */
    private String icon;
    
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }

    public String getParentId(){
        return parentId;
    }
    
    public void setParentId(String parentId){
        this.parentId = parentId;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMethod(){
        return method;
    }

    public void setMethod(String method){
        this.method = method;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getUri(){
        return uri;
    }

    public void setUri(String uri){
        this.uri = uri;
    }
    
    public String getIcon(){
        return icon;
    }
    
    public void setIcon(String icon){
        this.icon = icon;
    }

    @Override
    public String toString(){
        return "PermissionInfoVo [name=" + name + ", code=" + code + ", type=" + type + ", uri=" + uri + ", method="
                + method + ", icon=" + icon + "]";
    }
}
