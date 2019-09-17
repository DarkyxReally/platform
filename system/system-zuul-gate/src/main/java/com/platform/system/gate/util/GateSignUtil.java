package com.platform.system.gate.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.platform.system.common.sort.SpellComparator;


/** 网关签名工具类
 * 
 * @version: 1.0 
 * */
public final class GateSignUtil {

    /** 字符排序比较器 */
    private volatile static SpellComparator spellComparator;
    /** 分割符 */
    private static final String SPLITER = "&";

    /** 获取字符排序比较器
     * 
     * @return */
    private static SpellComparator getSpellComparator() {
        if(spellComparator == null) {
            synchronized(GateSignUtil.class) {
                if(null == spellComparator) {
                    spellComparator = new SpellComparator();
                }
            }
        }
        return spellComparator;
    }

    private GateSignUtil() {

    }

    /** 转换成原始待签名数据
     * 
     * @param treeMap 有序的参数集合
     * @param signKey 签名key
     * @param appendValues 不参与排序，直接拼接在字符串之后的数据
     * @return */
    private static final String toRawUnSignDataString(TreeMap<String, String> treeMap, String signKey, String... appendValues) {
        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String, String> entry : treeMap.entrySet()) {
            if(StringUtils.isNotBlank(entry.getKey())) {
                String key = entry.getKey();
                if(StringUtils.isBlank(key)) {
                    continue;
                }
                String val = entry.getValue();
                if(StringUtils.isBlank(val)) {
                    continue;
                }
                sb.append(String.format("%s=%s", key, val));
                sb.append(SPLITER);
            }
        }

        for(String vl : appendValues) {
            if(StringUtils.isBlank(vl)) {
                continue;
            }
            sb.append(vl).append(SPLITER);
        }
        if(sb.length() == 0) {
            return signKey;
        } else {
            return sb.append(signKey).toString();
        }
    }
    
    /** 计算签名值
     * 
     * @param data 数据
     * @param signKey 签名key
     * @param urlEncode 是否进行url编码
     * @param appendValues 不参与排序，直接拼接在字符串之后的数据
     * @return */
    public static final String sign(Map<String, String> data, String signKey, boolean urlEncode, String... appendValues) {
        SpellComparator comparator = getSpellComparator();
        // ◆ 参数名ASCII码从小到大排序（字典序）；
        // ◆ 如果参数的值为空不参与签名；
        // ◆ 参数名区分大小写；
        TreeMap<String, String> sortMap = new TreeMap<String, String>(comparator);
        for(Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sortMap.put(key, value);
        }
        String rawUnSignDataString = toRawUnSignDataString(sortMap, signKey, appendValues);
        if (urlEncode){
            // 原始字符串进行编码
            try {
                rawUnSignDataString = URLEncoder.encode(rawUnSignDataString, "UTF-8");
            } catch(UnsupportedEncodingException e) {
                // 编码异常, 忽略
            }
        }
        return DigestUtils.md5Hex(rawUnSignDataString).toUpperCase();
    }

    /** 计算签名值
     * 
     * @param data 数据
     * @param signKey 签名key
     * @param urlEncode 是否进行url编码
     * @param appendValues 不参与排序，直接拼接在字符串之后的数据
     * @return */
    public static final String signWithListValue(Map<String, List<String>> data, String signKey, boolean urlEncode, String... appendValues) {
        SpellComparator comparator = getSpellComparator();
        // ◆ 参数名ASCII码从小到大排序（字典序）；
        // ◆ 如果参数的值为空不参与签名；
        // ◆ 参数名区分大小写；
        TreeMap<String, String> sortMap = new TreeMap<String, String>(comparator);
        for(Entry<String, List<String>> entry : data.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if(CollectionUtils.isEmpty(value)) {
                continue;
            }

            Collections.sort(value, comparator);
            StringBuffer sb = new StringBuffer();
            for(String v : value) {
                if(StringUtils.isNotBlank(v)) {
                    sb.append(v);
                }
            }
            if(sb.length() == 0) {
                // 值为空, 不参与
                continue;
            }
            sortMap.put(key, sb.toString());
        }
        String rawUnSignDataString = toRawUnSignDataString(sortMap, signKey, appendValues);
        if (urlEncode){
            // 原始字符串进行编码
            try {
                rawUnSignDataString = URLEncoder.encode(rawUnSignDataString, "UTF-8");
            } catch(UnsupportedEncodingException e) {
                // 编码异常, 忽略
            }
        }
        return DigestUtils.md5Hex(rawUnSignDataString).toUpperCase();
    }
}
