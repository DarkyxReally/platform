package com.platform.api.gate.configuration.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 网关免登陆白名单配置
 * @version: 1.0
 */
@Component
@ConfigurationProperties(GateIgnoreProperties.PREFIX)
public class GateIgnoreProperties {
    
    public static final String PREFIX = "gate.ignore";

    /** 后项模糊匹配 */
    private List<String> startWith = new ArrayList<String>();
    /** 全匹配 */
    private List<String> contain = new ArrayList<String>();

    public List<String> getStartWith(){
        return startWith;
    }

    public void setStartWith(List<String> startWith){
        this.startWith = startWith;
    }

    public List<String> getContain(){
        return contain;
    }

    public void setContain(List<String> contain){
        this.contain = contain;
    }

}
