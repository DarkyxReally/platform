package com.platform.system.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;


public class HttpWxUtils {
	private static final int TIMEOUT =  180000;
	private static final String POST = "post";
	private static final String GET = "get";
	public static final String UTF8_CHARSET = "UTF-8";
	public static final String GBK_CHARSET = "GBK";
	public static final String GB2312_CHARSET = "GB2312";
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param url   发送请求的 URL
	 * @param param 请求参数
	 * @return      所代表远程资源的响应结果
	 * @throws Exception 
	 */
	public static String post(String url,Map<String,Object>param) throws Exception {
		PostMethod  method = new PostMethod(url);
		if(param == null){
			param = new HashMap<String,Object>();
		}
		return execute(method,url,param,UTF8_CHARSET,POST,TIMEOUT);
	}
	
	/**
	 * 向指定 URL 发送get方法的请求
	 * @param url   发送请求的 URL
	 * @param param 请求参数
	 * @return      所代表远程资源的响应结果
	 * @throws Exception 
	 */
	public static String get(String url,Map<String,Object> param) throws Exception{
		GetMethod  method = new GetMethod(url);
		return execute(method,url,param,UTF8_CHARSET,GET,TIMEOUT);
    }
	
	
	private static String execute(HttpMethodBase method,String url,Map<String,Object> param,
			String charset,String type,Integer timer) throws Exception{
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setContentCharset(charset);			
			httpClient.getParams().setConnectionManagerTimeout(timer);
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timer);
			method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset="+charset);
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			
			if(param!=null && param.size()>0){
				//请求信息封装
				NameValuePair[] data = new NameValuePair[param.size()]; 
				Iterator<Entry<String, Object>> iter = param.entrySet().iterator();
				int i=0;
				while(iter.hasNext()){
					Entry<String, Object> entry = iter.next();
					data[i] = new NameValuePair(entry.getKey(), entry.getValue().toString()); 
					i++;
				}
				if(type.equals(GET)){
					method.setQueryString(data);
				}else{
					PostMethod postMethod = (PostMethod)method;
					postMethod.setRequestBody(data);
				}
			}
			httpClient.executeMethod(method);
			String submitResult = IOUtils.toString(method.getResponseBodyAsStream(), charset);
			return submitResult;
		} catch (Exception e) {
		    throw new Exception(getRequestString(url,param), e);
		} 	
	}
	
	/**
	 * 拼装请求参数信息
	 */
	private static String getRequestString(String url, Map<String,Object> param){
		Iterator<Entry<String, Object>> iter = param.entrySet().iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("HTTPClient请求URL地址:").append(url).append(",请求参数:");
		sb.append("[");
		while(iter.hasNext()){
			Entry<String, Object> entry = iter.next();
			sb.append(entry.getKey()).append("=").append(entry.getValue().toString());
			if(iter.hasNext()){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
