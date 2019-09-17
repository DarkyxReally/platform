package com.platform.system.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.platform.system.common.constant.CommonConstants;


/**
 * URL编码解码
 * @version: 1.0
 */
public class UrlEnDecode {
    
    /**
     * URL解码
     * @param str
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String decode(String str){
        try{
            return URLDecoder.decode(str, CommonConstants.UTF8);
        }catch(UnsupportedEncodingException e){
            return URLDecoder.decode(str);
        }
    }
    /**
     * URL编码
     * @param str
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String encode(String str){
        try{
            return URLEncoder.encode(str, CommonConstants.UTF8);
        }catch(UnsupportedEncodingException e){
            return URLEncoder.encode(str);
        }
    }
}
