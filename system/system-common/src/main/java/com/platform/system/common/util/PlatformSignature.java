package com.platform.system.common.util;

import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.platform.system.common.exception.SignatureException;


/**
 * ligu签名工具类
 * @version: 1.0
 */
public class PlatformSignature {

    /**
     * 签名字段
     */
    private static final String SIGN_KEY = "sign";

    /**
     * 获取随机字符串
     * @return
     */
    public static String getNonceStr(){
        return DigestUtils.md5Hex(UUID.randomUUID().toString());
    }

    /**
     * 获取当前时间戳
     * @return
     */
    public static long getTimeStamp(){
        return System.currentTimeMillis();
    }

    /**
     * 排序比较器
     */
    static class SpellComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2){
            // 按字符串自然序(升序)排序
            // 按照参数名ASCII码从小到大排序（字典序）
            return o1.compareTo(o2);
        }
    }

    /**
     * 将参数排序化
     * @param params
     * @return
     */
    private static final Map<String, String> toSortMap(Map<String, String> params){
        if(params instanceof TreeMap){
            // TreeMap的不动返回
            return params;
        }else{
            Map<String, String> sortMap = new TreeMap<String, String>(new SpellComparator());
            sortMap.putAll(params);
            return sortMap;
        }
    }

    /**
     * 签名
     * 规则:
     * ◆ 1:参数名区分大小写；
     * ◆ 2:参数名ASCII码从小到大排序（字典序）；
     * ◆ 3:如果参数的值为空不参与签名；
     * ◆ 4:完成上述规则后，将各个参数拼接成字符串,中间用"&"连接, 最后拼接&key=传入的key
     * 拼接结果例如: paramA=paramAValue&paramB=paramBValue...&key=传入的key
     * ◆ 5:将步骤4得到的字符串进行MD5加密得到16进制的字符串，并将字符串转为大写  即为最终的签名
     * 
     * @param params 参数
     * @param priKey 签名私钥
     * @return
     */
    public static final String sign(Map<String, String> params, String priKey){
        try{
            Map<String, String> sortMap = toSortMap(params);

            StringBuffer stringA = new StringBuffer();
            int index = 0;
            for(Map.Entry<String, String> entry : sortMap.entrySet()){
                if(null == entry.getKey()){
                    // 键为空, 不参与计算
                    continue;
                }
                String value = entry.getValue();
                if(StringUtils.isBlank(value)){
                    // 值为空, 不参与计算
                    continue;
                }
                if(index != 0){
                    stringA.append("&");
                }
                stringA.append(entry.getKey()).append("=").append(value);
                index++;
            }
            //
            stringA.append("&key=").append(priKey);
            String mySignature = DigestUtils.md5Hex(stringA.toString());
            return mySignature.toUpperCase();
        }catch(Exception e){
            throw new SignatureException("计算签名异常:" + e.getMessage(), e);
        }
    }

    /**
     * 校验签名
     * ◆ 验证签名时，传送的签名字段不参与签名计算，将生成的签名与该签名字段的值相比校验。
     * @param params 参数
     * @param priKey 密钥
     * @return
     */
    public static final boolean checkSign(Map<String, String> params, String priKey){
        return checkSign(params, priKey, SIGN_KEY);
    }

    /**
     * 校验签名
     * ◆ 验证签名时，传送的签名字段参与签名计算，将生成的签名与该签名字段的值相比校验。
     * @param params 参数
     * @param priKey 密钥
     * @param signKey 签名字段
     * @return
     */
    public static final boolean checkSign(Map<String, String> params, String priKey, String signKey){
        // 需要验证的签名
        String validateSign = params.get(signKey);
        Map<String, String> sortMap = new TreeMap<String, String>(new SpellComparator());
        sortMap.putAll(params);
        sortMap.remove(signKey);
        return sign(sortMap, priKey).equals(validateSign);
    }

    public static void main(String[] args){
        Map<String, String> params = new HashMap<String, String>();
        params.put("serialNo", "hz-888-120003");
        params.put("appId", "1832d9c8ae3e43db8fe8482c05193f33");
        params.put("nonceStr", "aaaa3");
        params.put("timestamp", "1558344870801");
        params.put("goodsId", "4");
        params.put("mobileNo", "18565879369");
        params.put("chargeType", "1");
        params.put("volume", "10");




        System.out.println(sign(params, "e44e2032e47848088f150ef208161d2e"));

//        params.put("sign", "BECF490816605D309DF47FE6CD4C9EA6");
//        System.out.println(checkSign(params, "5f0620ba23254a0ea9b238e2b0150c33"));

//        Map<String, String> params = new TreeMap<>();
//        params.put("apiKey", "516a67614f174b2797f8526c83fb1f7c");
//        params.put("accountId", "100000");
//        params.put("nonceStr", "30b299517c784c7cb2f0294785f28645");
//        params.put("timestamp", "1551771579000");
//        System.out.println(sign(params, "aa042d0d12f6426e8c427ed8b69272d0"));
    }
}
