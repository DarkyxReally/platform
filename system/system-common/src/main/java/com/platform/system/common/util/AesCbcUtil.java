package com.platform.system.common.util;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * AES-128-CBC 解密
 * 注：
 * AES-128-CBC可以自己定义“密钥”和“偏移量“。
 * @version: V1.0
 */
public class AesCbcUtil {

    static{
        // BouncyCastle是一个开源的加解密解决方案，主页在http://www.bouncycastle.org/
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 解密
     * @param data 密文，被加密的数据
     * @param key 秘钥
     * @param iv 偏移量
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidParameterSpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     */
    public static final byte[] decrypt(String data, String key, String iv) throws NoSuchAlgorithmException,NoSuchPaddingException,InvalidParameterSpecException,IllegalBlockSizeException,
            BadPaddingException,InvalidKeyException,InvalidAlgorithmParameterException{
        // 被加密的数据
        byte[] dataByte = Base64.decodeBase64(data);
        // 加密秘钥
        byte[] keyByte = Base64.decodeBase64(key);
        // 偏移量
        byte[] ivByte = Base64.decodeBase64(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
        return cipher.doFinal(dataByte);
    }

    /**
     * 解密
     * @param data 密文，被加密的数据
     * @param key 秘钥
     * @param iv 偏移量
     * @param encodingFormat 解密后的结果字符串编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidParameterSpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws UnsupportedEncodingException 
     */
    public static final String decrypt(String data, String key, String iv, String encodingFormat) throws NoSuchAlgorithmException,NoSuchPaddingException,InvalidParameterSpecException,
            IllegalBlockSizeException,BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException{
        byte[] resultByte = decrypt(data, key, iv);
        if(null != resultByte && resultByte.length > 0){
            String result = new String(resultByte, encodingFormat);
            return result;
        }
        return null;
    }

}