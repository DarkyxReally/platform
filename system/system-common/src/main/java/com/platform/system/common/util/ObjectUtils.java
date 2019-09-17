/**   
 * @version V1.0 
 */
package com.platform.system.common.util;



/**
 * @version: 1.0 
*/

public class ObjectUtils {
    
    /**
     * 是否所有对象都为空
     * @param objects
     * @return
     */
    public static boolean isAllBlank(Object...objects) {
        if(objects == null) {
            return true;
        }
        int len = 0;
        for(Object obj : objects) {
           if(obj == null) {
               len += 1;
           } 
        }
        if(len == objects.length) {
            return true;
        }else {
            return false;
        }
    }
    
    /**
     * 判断两个对象是否相等
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean equals(Object obj1,Object obj2) {
        if(obj1==obj2) {
            return true;
        }
        if(obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }
    
    public static boolean unEquals(Object obj1,Object obj2) {
        return !equals(obj1,obj2);
    }
    /**
     * 如果为空就赋新值
     * @param t
     * @param value
     * @return
     */
    public static <T> T isBlankToSet(T t,T value) {
        return t==null?value:t;
    }
}
