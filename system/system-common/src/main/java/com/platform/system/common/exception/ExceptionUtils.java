package com.platform.system.common.exception;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import org.apache.commons.lang.StringUtils;


/**
 * 异常工具类
 * @version: 1.0
 */
public class ExceptionUtils {

    /**
     * 获取指定的异常
     * @version: V1.0
     * @param e
     * @param clazz
     * @return
     */
    public static final <T> Throwable getCause(Throwable e, Class<T> clazz){
        if(null == e){ return e; }
        // 类型一致
        if(clazz.isAssignableFrom(e.getClass())){ return e; }
        if(e.getCause() == null){ return e; }
        return getCause(e.getCause(), clazz);
    }

    /**
     * 是否为连接异常
     * @param e
     * @return
     */
    public static final boolean isConnectException(Exception e){
        if(e instanceof ConnectException){ return true; }
        return false;
    }

    /**
     * 是否为超时异常
     * @param e
     * @return
     */
    public static final boolean isTimeOutException(Exception e){
        if(e instanceof SocketTimeoutException){ return true; }
        return false;
    }

    /**
     * 获取最根的错误信息
     * @version: 1.0
     * @param e
     * @return
     */
    public static final String getRootMessage(Throwable e){
        Throwable cause = e.getCause();
        if(cause == null){ return e.getMessage(); }
        return getRootMessage(cause);
    }

    /**
     * 获取错误信息
     * @version: 1.0
     * @param e
     * @return
     */
    public static final String getMessage(Throwable e){
        Throwable cause = e.getCause();
        if(cause == null){ return e.getMessage(); }
        String rootMessage = getRootMessage(e);
        if(StringUtils.isNotBlank(rootMessage)){ return e.getMessage() + "[" + rootMessage + "]"; }
        return e.getMessage();
    }
}
