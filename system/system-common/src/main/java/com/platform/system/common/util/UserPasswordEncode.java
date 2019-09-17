package com.platform.system.common.util;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用户密码加密
 * @version: 1.0
 */
public final class UserPasswordEncode {

    /**
     * 默认的加密强度
     */
    private static final int PW_ENCORDER_SALT = 12;

    /**
     * 计算加密强度
     * 
     * @return
     */
    private static final int calculateStrength(String idUser){
        // 强度值只能为4-31之间, 强度越大, 耗时越长
        // 综合考虑, 此处的值为12-14之间
        int MIN_ROUNDS = 12;
        int MAX_ROUNDS = 14;
        int strength = idUser.hashCode();
        if(strength >= MIN_ROUNDS && strength <= MAX_ROUNDS){
            // 大于等于4, 小于等于31
            return strength;
        }
        if(strength < 0){
            // 若小于0, 则转为正数
            strength = -strength;
        }

        // 取模
        strength = strength % 12;
        // 步进值
        int step_by_step = 3;
        while(strength < MIN_ROUNDS || strength > MAX_ROUNDS){
            strength += step_by_step;
        }
        return strength;
    }

    /**
     * 计算种子
     * @return
     */
    private static final int calculateSeed(String idUser, int strength){
        // 计算种子
        int hashCode = idUser.hashCode();
        if(hashCode < 0){
            hashCode = -hashCode;
        }
        return hashCode % strength;
    }

    /**
     * 加密
     * @param source 原始密码
     * @param idUser 用户id
     * @return
     */
    public static final String encode(String source, String idUser){
        int strength = calculateStrength(idUser);
        int seed = calculateSeed(idUser, strength);
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed);
        return new BCryptPasswordEncoder(strength, secureRandom).encode(source);
    }

    /**
     * 密码是否匹配
     * @param rawPassword 明文密码
     * @param encodePwd 加密的密码
     * @param idUser 用户id
     * @return
     */
    public static final boolean matches(String rawPassword, String encodePwd, String idUser){
        int strength = calculateStrength(idUser);
        int seed = calculateSeed(idUser, strength);
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed);
        return new BCryptPasswordEncoder(strength, secureRandom).matches(rawPassword, encodePwd);
    }

    /**
     * 加密
     * @param source 原始密码
     * @return
     */
    public static final String encode(String source){
        return new BCryptPasswordEncoder(PW_ENCORDER_SALT).encode(source);
    }

    /**
     * 密码是否匹配
     * @param rawPassword 明文密码
     * @param encodePwd 加密的密码
     * @return
     */
    public static final boolean matches(String rawPassword, String encodePwd){
        return new BCryptPasswordEncoder(PW_ENCORDER_SALT).matches(rawPassword, encodePwd);
    }
    
    public static void main(String[] args) {
		System.out.println(UserPasswordEncode.encode("123456"));
	}

}
