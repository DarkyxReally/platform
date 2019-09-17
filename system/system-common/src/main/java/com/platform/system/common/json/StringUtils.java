package com.platform.system.common.json;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class StringUtils  extends org.apache.commons.lang3.StringUtils{
    
	/**
	 * 获取UUID,返回32字符串 
	 */
	public static String uuid(){
       String uuid=java.util.UUID.randomUUID().toString();
       return uuid.replaceAll("-", "");
	}
	
	private static final double EARTH_RADIUS = 6378137;
    
	private static double rad(double d)
    {
       return d * Math.PI / 180.0;
    }

	public static Object getValueFromBean(Object object, String name) {
		Class obj = object.getClass();
		Class[] cls = null;
		Object[] arrValue = null;
		Method myMethod;
		Object value = null;
		try {
			myMethod = obj.getMethod("get" + getName(name), cls);
			value = myMethod.invoke(object, arrValue);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if (value == null)
			value = "";
		return value;
	}

	private static String getName(String name) {
		if (name == null)
			return "";
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public static Object setValueToBean(Object object, Class[] clsArr, String name, Object value) {
		Class obj = object.getClass();
		Method method = null;
		try {
			if(name.indexOf(".") != -1){
				String[] nameArr  = name.split("\\.");
				Object o = clsArr[0].newInstance();
				method = o.getClass().getMethod("set" + getName(nameArr[1]), new Class[]{String.class});
				method.invoke(o, value);
				name = nameArr[0];
				value = o;
			}
			method = obj.getMethod("set" + getName(name), clsArr);
			method.invoke(object, value);
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	public static Map forSelect(List arrList, String id, String name, String childName) {
		Map map = new LinkedHashMap();
		for (Iterator iter = arrList.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			String key = String.valueOf(getValueFromBean(obj, id));
			String keyName = (String) getValueFromBean(obj, name);
			map.put(key, keyName);
			forSelect(map, id, name, childName, obj, 0);
		}
		return map;
	}

	private static void forSelect(Map map, String id, String name, String childName, Object parentObj, int layer) {
		layer += 2;
		Set childs = (Set) getValueFromBean(parentObj, childName);
		for (Iterator iter = childs.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			String key = String.valueOf(getValueFromBean(obj, id));
			String keyName = (String) getValueFromBean(obj, name);
			for (int i = 0; i < layer; i++)
				keyName = "　" + keyName;
			map.put(key, keyName);
			forSelect(map, id, name, childName, obj, layer);
		}
	}

	public static Set toSet(List list) {
		Set set = new HashSet();
		if (list == null)
			return set;
		for (Iterator iter = list.iterator(); iter.hasNext();)
			set.add(iter.next());
		return set;
	}

	public static Set toSet(List list, String keyId) {
		Set set = new HashSet();
		if (list == null)
			return set;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			set.add(obj);
		}
		return set;
	}

	public static List toList(Set set) {
		List list = new ArrayList();
		if (set == null)
			return list;
		for (Iterator iter = set.iterator(); iter.hasNext();)
			list.add(iter.next());
		return list;
	}

	public static String listToString(List list, String keyName) {
		StringBuffer sbVal = new StringBuffer();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			String val = (String) getValueFromBean(obj, keyName);
			sbVal.append(val + ",");
		}
		return sbVal.toString();
	}
	public static String listToString(List list) {
		StringBuffer sbVal = new StringBuffer();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			sbVal.append(iter.next() + ",");
		}
		return sbVal.toString();
	}

	public static String chinaFilter(String str) {
		String s = "";
		if (str == null || str.equals(""))
			return s;
		try {
			s = new String(str.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	public static int maxInt(int num1,int num2,int num3) {
		int max = num1;
		if(num2 > max){
			max = num2;
		}
		if(num3 > max){
			max = num3;
		}
		return max;
	}
		
    public static double GetDistance(double lng1, double lat1, double lng2, double lat2){
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
         Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    
    public static String getDecodeString(String value){
		if(StringUtils.isNotBlank(value)){
			try {
				return URLDecoder.decode(value, "utf8-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return value;
		}
		return "";
	}
    
    public static Map<String,String> extractString(String result){
		Map<String,String> map = new HashMap<String,String>();
		String[] values = result.split("&");
		for(int i=0; i<values.length; i++){
		    String extract = values[i];
		    String key = extract.substring(0, extract.indexOf("="));
		    String value = extract.substring(extract.indexOf("=")+1, extract.length());
		    map.put(key, value);
		}
		return map;
	}
    
    public static String getRandom(int num){
		Random random = new Random();
        String result="";
        for(int i=0;i<num;i++){
        	result += random.nextInt(10);
		}
        return result;
	}
    
    public static String replaceAll(String str){
        str=str.replaceAll("\"", "\"\"\"");
        str=str.replaceAll(",", "\",\"");
        return str;
    }
    
    /**
	 * 获取操作系统,浏览器及浏览器版本信息
	 * @param request
	 * @return
	 */
	public static Map<String,String> getOsAndBrowserInfo(HttpServletRequest request){
		Map<String,String> map = Maps.newHashMap();
		String  browserDetails  =   request.getHeader("User-Agent");
		String  userAgent       =   browserDetails;
		String  user            =   userAgent.toLowerCase();

		String os = "";
		String browser = "";

		//=================OS Info=======================
		if (userAgent.toLowerCase().contains("windows"))
		{
			os = "Windows";
		} else if(userAgent.toLowerCase().contains("mac"))
		{
			os = "Mac";
		} else if(userAgent.toLowerCase().contains("x11"))
		{
			os = "Unix";
		} else if(userAgent.toLowerCase().contains("android"))
		{
			os = "Android";
		} else if(userAgent.toLowerCase().contains("iphone"))
		{
			os = "IPhone";
		}else{
			os = "UnKnown, More-Info: "+userAgent;
		}
		//===============Browser===========================
		if (user.contains("edge"))
		{
			browser=(userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("msie"))
		{
			String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version"))
		{
			browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
					+ "-" +(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if ( user.contains("opr") || user.contains("opera"))
		{
			if(user.contains("opera")){
				browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
						+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			}else if(user.contains("opr")){
				browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
						.replace("OPR", "Opera");
			}

		} else if (user.contains("chrome"))
		{
			browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6"))  ||
				(user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) ||
				(user.contains("mozilla/4.08")) || (user.contains("mozilla/3")) )
		{
			browser = "Netscape-?";

		} else if (user.contains("firefox"))
		{
			browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if(user.contains("rv"))
		{
			String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
			browser="IE" + IEVersion.substring(0,IEVersion.length() - 1);
		} else
		{
			browser = "UnKnown, More-Info: "+userAgent;
		}
		map.put("os",os);
		map.put("browser",browser);
		return map;
	}
	
	/**
	 * 判断请求是否是ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		String accept = request.getHeader("accept");
        return accept != null && accept.contains("application/json") || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
    }
    
    /**
	 * 获取客户端ip地址
	 * @param request
	 * @return
	 */
	public  static String getClientAddress(HttpServletRequest request) {    
		 String ip = request.getHeader("X-Real-IP"); 
		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			  ip = request.getHeader("x-forwarded-for"); 
	     }    
		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			  ip = request.getHeader("Proxy-Client-IP"); 
	     }    
		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			 ip = request.getHeader("WL-Proxy-Client-IP"); 
		 }
		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			 ip = request.getRemoteAddr(); 
		 }
		 return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip; 
	}
	public static String sortData(Map<String,Object> map){
//    	Map<String, Object> sortMap = new TreeMap<String, Object>(KeyData.spellComparator());
//		sortMap.putAll(map);
//		String stringA = "";
//		int index = 0;
//		for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
//			// System.out.println(entry.getKey() + " " + entry.getValue());
//			if (index == 0) {
//				stringA = stringA + entry.getKey() + "=" + entry.getValue();
//			} else {
//				stringA = stringA + "&" + entry.getKey() + "="
//						+ entry.getValue();
//			}
//			index++;
//		}
		return null;
    }
	
	public static String strfilter(String str){
        if(!StringUtils.isNotEmpty(str)){ return str; }
        str = str.replaceAll("&", "&gt;");
        str = str.replaceAll("<", "");
        str = str.replaceAll(">", "");
        str = str.replaceAll("\"", "");
        str = str.replaceAll("\\/", "");
        str = str.replaceAll("\'", "");
        str = str.replaceAll("\n", "");
        str = str.replaceAll("\\;", "");
        str = str.replaceAll("\\$", "");
        str = str.replaceAll("\\%", "");
        str = str.replaceAll("\\'", "");
        str = str.replaceAll("\\(", "");
        str = str.replaceAll("\\)", "");
        str = str.replaceAll("\\,", "");
        str = str.replaceAll("\\+", "");

        return str;
    }
	
	/** 
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
     * 此方法中前三位格式有： 
     * 13+任意数 
     * 15+除4的任意数 
     * 18+除1和4的任意数 
     * 17+除9的任意数 
     * 147 
     */  
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }
    
    /**
     * 将字符串中MySQL通配符转义
     * @param str
     * @return
     */
    public static String escapeStrToMysql(String str){
    	if(StringUtils.isNotBlank(str)){
        	if(str.indexOf("\\")>-1){
        		str=str.replaceAll("\\\\", "\\\\\\\\");
        	}
        	if(str.indexOf("%")>-1){
        		str=str.replaceAll("%", "\\\\%");
        	}
        	if(str.indexOf("_")>-1){
        		str=str.replaceAll("_", "\\\\_");
        	}
        }
    	return str;
    }
	
	public static void main(String[] args) {
		Map<String,Object> datas = new HashMap<String,Object>();
        datas.put("za","zgyd");
        datas.put("ccc","2017-01-01");
        datas.put("c", "2017-01-02");
        System.out.println(sortData(datas));
        String random = getRandom(7);
        System.out.println(random);
	}
}
