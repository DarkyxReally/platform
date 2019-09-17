package com.platform.system.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 公共工具类
 * @version: 1.0
 */
public class CommonUtil {

    /**
     * 空集合
     * @param t
     * @return
     */
    public static final <T> List<T> emptyList(Class<T> clazz){
        return new ArrayList<T>(0);
    }

    /**
     * 字符串转list
     * @param str
     * @param sep 分隔符
     * @return
     */
    public static final List<String> str2List(String str, String sep){
        return str2List(str, sep, true, false);
    }

    /**
     * 字符串转list
     * @param str
     * @param sep 分隔符
     * @param filterBlank 过滤纯空白
     * @return
     */
    public static final List<String> str2List(String str, String sep, boolean filterBlank){
        return str2List(str, sep, filterBlank, false);
    }

    /**
     * 字符串转list
     * @param str
     * @param sep 分隔符
     * @param filterBlank 过滤纯空白
     * @param trim 去掉首尾空白
     * @return
     */
    public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim){
        List<String> list = new ArrayList<String>();
        if(StringUtils.isEmpty(str)){ return list; }

        // 过滤空白字符串
        if(filterBlank && StringUtils.isBlank(str)){ return list; }
        String[] split = str.split(sep);
        for(String string : split){
            if(filterBlank && StringUtils.isBlank(string)){
                continue;
            }
            if(trim){
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * 字符串转set
     * @param str
     * @param sep 分隔符
     * @return
     */
    public static final Set<String> str2Set(String str, String sep){
        return new HashSet<String>(str2List(str, sep, true, false));
    }

    /**
     * 字符串转set
     * @param str
     * @param sep 分隔符
     * @param filterBlank 过滤纯空白
     * @return
     */
    public static final Set<String> str2Set(String str, String sep, boolean filterBlank){
        return new HashSet<String>(str2List(str, sep, filterBlank, false));
    }

    /**
     * 字符串转set
     * @param str
     * @param sep 分隔符
     * @param filterBlank 过滤纯空白
     * @param trim 去掉首尾空白
     * @return
     */
    public static final Set<String> str2Set(String str, String sep, boolean filterBlank, boolean trim){
        Set<String> set = new HashSet<String>();
        if(StringUtils.isEmpty(str)){ return set; }

        // 过滤空白字符串
        if(filterBlank && StringUtils.isBlank(str)){ return set; }
        String[] split = str.split(sep);
        for(String string : split){
            if(filterBlank && StringUtils.isBlank(string)){
                continue;
            }
            if(trim){
                string = string.trim();
            }
            set.add(string);
        }

        return set;
    }

    /**
     * list转字符串
     * @param list
     * @param sep 分隔符
     * @return
     */
    public static final String list2Str(List<String> list, String sep){
        return list2Str(list, sep, false);
    }

    /**
     * list转字符串
     * @param list
     * @param sep 分隔符
     * @param filterBlank 过滤空白
     * @return
     */
    public static final String list2Str(List<String> list, String sep, boolean filterBlank){
        return list2Str(list, sep, filterBlank, false);
    }

    /**
     * list转字符串
     * @param list
     * @param sep 分隔符
     * @param filterBlank 过滤空白
     * @param trim 去掉前后空格
     * @return
     */
    public static final String list2Str(List<String> list, String sep, boolean filterBlank, boolean trim){
        if(CollectionUtils.isEmpty(list)){ return ""; }
        StringBuilder sBuilder = new StringBuilder();
        for(String str : list){
            // 过滤空白
            if(filterBlank){
                if(StringUtils.isBlank(str)){
                    continue;
                }
            }

            // 去掉前后空格
            if(trim){
                str = str.trim();
            }

            if(StringUtils.isNotEmpty(str)){
                sBuilder.append(str).append(sep);
            }
        }

        if(sBuilder.length() > 0){ return sBuilder.substring(0, sBuilder.lastIndexOf(sep)); }
        return "";
    }

    /**
     * 获取url的域名
     * @param url
     * @return
     */
    public static final String urlToDomain(String url){
        String temp = url;
        int protocol = temp.indexOf("//");
        if(-1 < protocol){
            temp = temp.substring(protocol);
        }

        int port = temp.indexOf(":");
        if(-1 < port){
            temp = temp.substring(0, port);
        }
        return temp;
    }

    /**
     * 去除最后斜杠
     * @param url
     * @return
     */
    public static final String removeLastPath(String url){
        if(null != url){
            while(url.endsWith("/")){
                url = url.substring(0, url.length() - 1);
            }
        }
        return url;
    }

    /**
     * 去除最前的斜杠
     * @param url
     * @return
     */
    public static final String removeFirstPath(String url){
        if(null != url){
            while(url.startsWith("/")){
                url = url.substring(1);
            }
        }
        return url;
    }

    /**
     * 拼接url路径
     * @param url
     * @return
     */
    public static final String urlAppend(String url, String... path){
        if(null == url){ return null; }
        url = removeLastPath(url);
        StringBuilder sb = new StringBuilder(url);
        for(String s : path){
            if(null != s){
                s = removeFirstPath(s);
                s = removeLastPath(s);
                sb.append("/").append(s);
            }
        }

        return sb.toString();
    }

    /**
     * 拆分list成n个小list,每个小的最大长度限制
     * @param list
     * @param pageSize
     * @return
     */
    public static final <T> List<List<T>> splitList(List<T> list, int pageSize){
        List<List<T>> listArr = new ArrayList<List<T>>();
        if(list.size() <= pageSize){
            // 不需要拆分
            listArr.add(list);
            return listArr;
        }
        int total = list.size();
        // 获取被拆分的数组个数
        int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        for(int i = 0; i < pageCount; i++){
            int start = i * pageSize;
            // 最后一条可能超出总数
            int end = start + pageSize > total ? total : start + pageSize;
            List<T> subList = list.subList(start, end);
            listArr.add(subList);
        }

        return listArr;
    }

    /**
     * 拆分list成n个小list,每个小的最大长度限制
     * @param list
     * @param pageSize
     * @return
     */
    public static final <T> List<List<T>> splitNewList(List<T> list, int pageSize){
        List<List<T>> listArr = new ArrayList<List<T>>();
        if(list.size() <= pageSize){
            // 不需要拆分
            listArr.add(new ArrayList<T>(list));
            return listArr;
        }
        int total = list.size();
        // 获取被拆分的数组个数
        int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        for(int i = 0; i < pageCount; i++){
            int start = i * pageSize;
            // 最后一条可能超出总数
            int end = start + pageSize > total ? total : start + pageSize;
            List<T> subList = list.subList(start, end);
            listArr.add(new ArrayList<T>(subList));
        }

        return listArr;
    }

    /**
     * 数值值是否相等（1维）
     * @param a
     * @param b
     * @return
     */
    public static boolean isEqualsIntArray(int[] a, int[] b){
        if(a.length != b.length){ return false; }
        Arrays.sort(a);
        Arrays.sort(b);
        if(!Arrays.equals(a, b)){ return false; }
        return true;
    }

    /**
     * 多个字符转int[]
     * @param arr
     * @return
     */
    public static int[] strArray2intArray(String... arr){
        int[] intArr = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            try{
                intArr[i] = Integer.parseInt(arr[i]);
            }catch(Exception e){
                intArr[0] = -1;
                return intArr;
            }
        }
        return intArr;
    }

    /**
     * int[]转为以“，”相隔的字符串
     */
    public static String intArrayToString(int[] a){
        int[] ints = a;
        String str = null;
        if(ints != null && ints.length != 0){
            str = Arrays.toString(ints);
            str = str.substring(1, str.length() - 1);
        }
        return str;
    }

    /**
     * 去除小数点后为0的情况    eg: 666.0 -> 666;  66.060 -> 66.06
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 格式化数字
     * 四舍五入 保留两位小数点
     * @param value
     * @return
     */
    public static String formatNumber(double value){
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }

    /**
     * M*100 转换未 M  eg： 66600 -> 666M;  666 -> 6.66;
     * @param i
     * @return
     */
    public static String flowTransReal(Integer i){
        return subZeroAndDot((i / 100.0) + "");
    }

    /**
     * 字符串的布尔值 
     * 
     * @return true: 1; false: 0
     */
    public static final String strBoolean(boolean bool){
        return bool ? "1" : "0";
    }
    
    /**
     * 字符串 0 1 转为 boolean
     * @param str
     * @return
     */
    public static final boolean strToBoolean(String str) {
        return str.equals("0") ? false : true;
    }

    
    /**
     * 比较两个对象数值是否相等
     * @version: 1.0
     * @param value1
     * @param value2
     * @return
     */
    public static final boolean isEqualValue(BigDecimal value1, BigDecimal value2){
        return value1.compareTo(value2) == 0;
    }
    
    /**
     * 小于
     * @version: 1.0
     * @param value1
     * @param value2
     * @return
     */
    public static final boolean lessThan(BigDecimal value1, BigDecimal value2){
        return value1.compareTo(value2) < 0;
    }
    
    /**
     * 小于或等于
     * @version: 1.0
     * @param value1
     * @param value2
     * @return
     */
    public static final boolean lessOrEqualThan(BigDecimal value1, BigDecimal value2){
        return value1.compareTo(value2) <= 0;
    }
    

    /**
     * 大于
     * @version: 1.0
     * @param value1
     * @param value2
     * @return
     */
    public static final boolean greatThan(BigDecimal value1, BigDecimal value2){
        return value1.compareTo(value2) > 0;
    }
    
    /**
     * 大于或等于
     * @version: 1.0
     * @param value1
     * @param value2
     * @return
     */
    public static final boolean greatOrEqualThan(BigDecimal value1, BigDecimal value2){
        return value1.compareTo(value2) >= 0;
    }
}
