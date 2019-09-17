package com.platform.system.common.util;

/**
 * 字符串工具类
 * @version: 1.0
 */
public class StringHelper {

    /**
     * 获取对象值
     * 1:对象为空时,为"";
     * 2:若为Long, Double, Float, Integer则为对应的字符数字; 
     * 3:为object.toString()
     * @param obj
     * @return 
     */
    public static String getObjectValue(Object obj){
        if (null == obj){
            return "";
        }
        
        if(obj instanceof Long
                ||obj instanceof Double
                ||obj instanceof Float
                ||obj instanceof Integer){
            return  String.valueOf(obj);
        }
        
        return obj.toString();
    }
}
