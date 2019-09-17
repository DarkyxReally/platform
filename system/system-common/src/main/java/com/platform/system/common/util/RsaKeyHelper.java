package com.platform.system.common.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;

/** 非对称加密工具类 <br>
 * 常见错误解决办法：<br>
 * 1:javax.crypto.BadPaddingException: Decryption error<br>
 * 解读: 最大解密长度不正确导致报错，MAX_DECRYPT_BLOCK应等于密钥长度/8（1byte=8bit），所以当密钥位数为2048时，最大解密长度应为256.
 * 
 * @version: 1.0 */
@Slf4j
public class RsaKeyHelper {

    /** RSA算法 */
    private static final String RSA = "RSA";

    /** UTF8编码 */
    private static final String CHARSET_UTF_8 = "UTF-8";

    /** 密钥长度(字节数)1024 */
    private static final Integer KEY_LENGTH_1024 = 1024;

    /** 密钥长度(字节数)2048 */
    private static final Integer KEY_LENGTH_2048 = 2048;

    /** RSA最大解密密文大小128 */
    private static final int MAX_DECRYPT_BLOCK_128 = 128;

    /** RSA最大解密密文大小256 */
    private static final int MAX_DECRYPT_BLOCK_256 = 256;

    /** RSA最大加密明文大小 */
    private static final int MAX_ENCRYPT_BLOCK_117 = 117;

    /** 获取公钥
     * 
     * @param filename
     * @return
     * @throws Exception */
    public static PublicKey getPublicKey(String filename) throws Exception {
        byte[] keyBytes = FileUtil.getFileByte(filename);
        return getPublicKey(keyBytes);
    }

    /** 获取公钥
     *
     * @param keyBytes
     * @return
     * @throws Exception */
    public static PublicKey getPublicKey(byte[] keyBytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        return kf.generatePublic(spec);
    }

    /** 获取密钥
     * 
     * @param filename
     * @return
     * @throws Exception */
    public static PrivateKey getPrivateKey(String filename) throws Exception {
        byte[] keyBytes = FileUtil.getFileByte(filename);
        return getPrivateKey(keyBytes);
    }

    /** 获取密钥
     *
     * @param privateKey
     * @return
     * @throws Exception */
    public static PrivateKey getPrivateKey(byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        return kf.generatePrivate(spec);
    }

    /** 根据密码生成rsa公钥和密钥对 默认密钥长度为1024
     * @version: 1.0
     * @param password
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException */
    public static KeyPair generateKeyPaid(String password) throws IOException,NoSuchAlgorithmException {
        return generateKeyPaid(KEY_LENGTH_1024, password);
    }

    /** 根据密码生成rsa公钥和密钥对 密钥长度为2048
     * @version: 1.0
     * @param password
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException */
    public static KeyPair generateKeyPaidBy2048(String password) throws IOException,NoSuchAlgorithmException {
        return generateKeyPaid(KEY_LENGTH_2048, password);
    }

    /** 根据密码生成rsa公钥和密钥对 密钥长度(秘钥字节数)自行指定
     * @version: 1.0
     * @param keySize 秘钥字节数
     * @param password 密码
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException */
    public static KeyPair generateKeyPaid(int keySize, String password) throws IOException,NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(keySize, secureRandom);
        return keyPairGenerator.genKeyPair();
    }

    /** 根据密钥对获取公钥
     * 
     * @param keyPair
     * @return */
    public static final byte[] getPublicKey(KeyPair keyPair) {
        return keyPair.getPublic().getEncoded();
    }

    /** 根据私钥对获取公钥
     * 
     * @param keyPair
     * @return */
    public static final byte[] getPrivateKey(KeyPair keyPair) {
        return keyPair.getPrivate().getEncoded();
    }

    /** 根据密码生成rsa公钥和密钥
     * 
     * @param publicKeyFilename
     * @param privateKeyFilename
     * @param password
     * @throws IOException
     * @throws NoSuchAlgorithmException */
    public static Map<String, byte[]> generateKey(String password) throws IOException,NoSuchAlgorithmException {
        KeyPair keyPair = generateKeyPaid(password);
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        Map<String, byte[]> map = new HashMap<String, byte[]>();
        map.put("pub", publicKeyBytes);
        map.put("pri", privateKeyBytes);
        return map;
    }

    /** 根据密码生成rsa公钥和密钥 默认密钥长度为1024
     * 
     * @param publicKeyFilename 保存公钥文件路径
     * @param privateKeyFilename 保存私钥文件路径
     * @param password 密码
     * @throws IOException
     * @throws NoSuchAlgorithmException */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String password) throws IOException,
            NoSuchAlgorithmException {
        generateKey(KEY_LENGTH_1024, publicKeyFilename, privateKeyFilename, password);
    }

    /** 根据密码生成rsa公钥和密钥 密钥长度为2048
     * 
     * @param publicKeyFilename 保存公钥文件路径
     * @param privateKeyFilename 保存私钥文件路径
     * @param password 密码
     * @throws IOException
     * @throws NoSuchAlgorithmException */
    public static void generateKeyBy2048(String publicKeyFilename, String privateKeyFilename, String password) throws IOException,
            NoSuchAlgorithmException {
        generateKey(KEY_LENGTH_2048, publicKeyFilename, privateKeyFilename, password);
    }

    /** 根据密码生成rsa公钥和密钥
     * 
     * @param keySize 密钥长度(秘钥字节数)
     * @param publicKeyFilename 保存公钥文件路径
     * @param privateKeyFilename 保存私钥文件路径
     * @param password 密码
     * @throws IOException
     * @throws NoSuchAlgorithmException */
    public static void generateKey(int keySize, String publicKeyFilename, String privateKeyFilename, String password) throws IOException,
            NoSuchAlgorithmException {
        KeyPair keyPair = generateKeyPaid(keySize, password);
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(publicKeyFilename);
            fos.write(publicKeyBytes);
        } finally {
            StreamUtils.close(fos);
        }
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        try {
            fos = new FileOutputStream(privateKeyFilename);
            fos.write(privateKeyBytes);
        } finally {
            StreamUtils.close(fos);
        }
    }

    /** 获取字符串类型的私钥
     * 
     * @param privateKeyBytes
     * @return */
    public static String getPrivateKeyStr(byte[] privateKeyBytes) {
        return Base64.encodeBase64String(privateKeyBytes);
    }

    /** 获取字符串类型的公钥
     * 
     * @param publicKeyBytes
     * @return */
    public static String getPublicKeyStr(byte[] publicKeyBytes) {
        return Base64.encodeBase64String(publicKeyBytes);
    }

    /** 获取私钥
     * @version: 1.0
     * @param privateKeyStr
     * @return
     * @throws Exception */
    public static PrivateKey getRsaPrivateKey(String privateKeyStr) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /** 获取公钥
     * @version: 1.0
     * @param pubKeyStr
     * @return
     * @throws Exception */
    public static PublicKey getRsaPublicKey(String pubKeyStr) throws Exception {
        // 对公钥解密
        byte[] keyBytes = Base64.decodeBase64(pubKeyStr);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(x509KeySpec);
    }

    /** 使用私钥解密 默认解密分段128
     * @version: 1.0
     * @param content
     * @param privateKeyStr
     * @return */
    public static String decryptByPrivateKey(String content, String privateKeyStr) {
        return decryptByPrivateKey(MAX_DECRYPT_BLOCK_128, content, privateKeyStr);
    }

    /** 使用私钥解密 解密分段为256
     * @version: 1.0
     * @param content
     * @param privateKeyStr
     * @return */
    public static String decryptByPrivateKeyBy256(String content, String privateKeyStr) {
        return decryptByPrivateKey(MAX_DECRYPT_BLOCK_256, content, privateKeyStr);
    }

    /** 使用私钥解密
     * @version: 1.0
     * @param maxDecryptBlock 最大解密分段大小
     * @param content
     * @param privateKeyStr
     * @return
     * @throws Exception */
    public static String decryptByPrivateKey(int maxDecryptBlock, String content, String privateKeyStr) {
        try {
            PrivateKey privateKey = getRsaPrivateKey(privateKeyStr);
            return decrypt(maxDecryptBlock, content, privateKey);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /** 使用KEY解密
     * @version: 1.0
     * @param maxDecryptBlock 最大解密分段大小
     * @param content
     * @param key
     * @return
     * @throws Exception */
    public static String decrypt(int maxDecryptBlock, String content, Key key) throws Exception {
        ByteArrayOutputStream out = null;
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedData = Base64.decodeBase64(content);
            int inputLen = encryptedData.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while(inputLen - offSet > 0) {
                if(inputLen - offSet > maxDecryptBlock) {
                    cache = cipher.doFinal(encryptedData, offSet, maxDecryptBlock);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * maxDecryptBlock;
            }
            byte[] decryptedData = out.toByteArray();
            return new String(decryptedData, CHARSET_UTF_8);
        } finally {
            StreamUtils.close(out);
        }
    }

    /** 公钥解密 默认解密分段大小为128
     * @version: 1.0
     * @param content
     * @param publicKey
     * @return
     * @throws Exception */
    public static String decrypt(String content, PublicKey publicKey) throws Exception {
        return decrypt(MAX_DECRYPT_BLOCK_128, content, publicKey);
    }

    /** 使用私钥解密 默认解密分段大小为128
     * @version: 1.0
     * @param content
     * @param privateKeyStr
     * @return
     * @throws Exception */
    public static String decrypt(String content, PrivateKey privateKey) throws Exception {
        return decrypt(MAX_DECRYPT_BLOCK_128, content, privateKey);
    }

    /** 私钥加密 默认分段长度为117
     * 
     * @param content
     * @param privateKey
     * @return
     * @throws Exception */
    public static String encryptByPrivateKey(String content, String privateKeyStr) {
        return encryptByPrivateKey(MAX_ENCRYPT_BLOCK_117, content, privateKeyStr);
    }

    /** 私钥加密
     * 
     * @param maxEncryptBlock 最大分段长度
     * @param content
     * @param privateKey
     * @return
     * @throws Exception */
    public static String encryptByPrivateKey(int maxEncryptBlock, String content, String privateKeyStr) {
        String encryptedDataStr = null;
        try {
            PrivateKey rsaPrivateKey = getRsaPrivateKey(privateKeyStr);
            encryptedDataStr = encrypt(maxEncryptBlock, content, rsaPrivateKey);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        return encryptedDataStr;
    }

    /** 私钥加密 默认分段长度为117
     * @param content
     * @param privateKey
     * @return
     * @throws Exception */
    public static String encrypt(String content, PrivateKey privateKey) throws Exception {
        return encrypt(MAX_ENCRYPT_BLOCK_117, content, privateKey);
    }

    /** 使用KEY加密
     * @param maxEncryptBlock 最大分段长度
     * @param content
     * @param key
     * @return
     * @throws Exception */
    public static String encrypt(int maxEncryptBlock, String content, Key key) throws Exception {
        ByteArrayOutputStream out = null;
        try {
            byte[] data = content.getBytes(CHARSET_UTF_8);
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while(inputLen - offSet > 0) {
                // 数据分段加密
                if(inputLen - offSet > maxEncryptBlock) {
                    cache = cipher.doFinal(data, offSet, maxEncryptBlock);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * maxEncryptBlock;
            }
            byte[] encryptedData = out.toByteArray();
            return Base64.encodeBase64String(encryptedData);
        } finally {
            StreamUtils.close(out);
        }
    }

    /** 使用公钥加密 默认分段长度为117
     * @param content
     * @param publicKey
     * @return
     * @throws Exception */
    public static String encrypt(String content, PublicKey publicKey) throws Exception {
        return encrypt(MAX_ENCRYPT_BLOCK_117, content, publicKey);
    }

    /** 使用公钥加密 默认分段长度为117
     * @param content
     * @param pubKeyStr
     * @return */
    public static String encryptByPublicKey(String content, String pubKeyStr) {
        return encryptByPublicKey(MAX_ENCRYPT_BLOCK_117, content, pubKeyStr);
    }
    
    /** 使用公钥加密
     * 
     * @param maxEncryptBlock 最大分段长度
     * @param content
     * @param pubKeyStr
     * @return */
    public static String encryptByPublicKey(int maxEncryptBlock, String content, String pubKeyStr) {
        // 要加密的数据
        String encryptedDataStr = null;
        try {
            PublicKey rsaPublicKey = getRsaPublicKey(pubKeyStr);
            encryptedDataStr = encrypt(maxEncryptBlock, content, rsaPublicKey);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        return encryptedDataStr;
    }

    public static void main(String[] args) throws Exception {
        test1024();
        test2048();
    }
    
    public static void test1024() throws Exception{
        KeyPair generateKeyPaid = generateKeyPaid("uploadAddressBook");
        String publicKeyStr = getPublicKeyStr(getPublicKey(generateKeyPaid));
        String privateKeyStr = getPrivateKeyStr(getPrivateKey(generateKeyPaid));
        System.out.println(publicKeyStr);
        System.out.println(privateKeyStr);
        // String pri =
        JSONObject json = new JSONObject();
        json.put("a", "1");
        json.put("aB", "2");
        json.put("1c", "1");
        json.put("d", "3");
        PublicKey publicKey = getRsaPublicKey(publicKeyStr);
        PrivateKey privateKey = getRsaPrivateKey(privateKeyStr);
        String encrypt = encrypt(json.toJSONString(), privateKey);
        System.out.println("1024私钥, 最大加密块117加密:" + encrypt);
        String decrypt = decrypt(encrypt, publicKey);
        System.out.println("1024公钥, 最大解密块128解密:" + decrypt);
    }
    
    
    public static void test2048() throws Exception{
        KeyPair generateKeyPaid = generateKeyPaidBy2048("test");
        String publicKeyStr = getPublicKeyStr(getPublicKey(generateKeyPaid));
        String privateKeyStr = getPrivateKeyStr(getPrivateKey(generateKeyPaid));
        System.out.println(publicKeyStr);
        System.out.println(privateKeyStr);
        // String pri =
        JSONObject json = new JSONObject();
        json.put("1a", "121");
        json.put("a1B", "212");
        json.put("111c", "1231");
        json.put("d12", "123");
        PublicKey publicKey = getRsaPublicKey(publicKeyStr);
        PrivateKey privateKey = getRsaPrivateKey(privateKeyStr);
        String encrypt = encrypt(117, json.toJSONString(), privateKey);
        System.out.println("2048私钥, 最大加密块117加密:" + encrypt);
        String decrypt = decrypt(256, encrypt, publicKey);
        System.out.println("2048公钥, 最大解密块256解密:" + decrypt);
    }

}
