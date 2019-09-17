package com.platform.system.common.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * UUID生成器
 * @version: 1.0
 */
public final class GuidGenerator {

    /**
     * 短uuid字典表
     */
    private static final String[] CHARS = new String[] { "a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
        "W", "X", "Y", "Z" };

    private static final long leastSigBits;
    private static final ReentrantLock lock = new ReentrantLock();

    private static boolean IS_THREADLOCALRANDOM_AVAILABLE = false;
    private static Random random;
    private static long lastTime;

    static{
        try{
            IS_THREADLOCALRANDOM_AVAILABLE = null != GuidGenerator.class.getClassLoader().loadClass(
                    "java.util.concurrent.ThreadLocalRandom");
        }catch(ClassNotFoundException e){}

        byte[] seed = new SecureRandom().generateSeed(8);
        leastSigBits = new BigInteger(seed).longValue();
        if(!IS_THREADLOCALRANDOM_AVAILABLE){
            random = new Random(leastSigBits);
        }
    }
    
    private GuidGenerator() {}

    /**
     * 生成32位uuid
     * @return
     */
    public static String generate32(){
        return Pattern.compile("-").matcher(UUID.randomUUID().toString()).replaceAll("");
    }

    /**
     * 32位uuid转成8位的uuid
     * @param uuid32
     * @return
     */
    public static String generate8(String uuid32) {
        StringBuffer shortBuffer = new StringBuffer();
        final String uuid = uuid32;
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(CHARS[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
    
    /**
     * 生成8位的uuid
     * @return
     */
    public static String generate8() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = generate32();
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(CHARS[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
    
    public static void main(String[] args) {
    	StringBuffer shortBuffer = new StringBuffer();
        String uuid = generate32();
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(CHARS[x % 0x3E]);
        }
        System.out.println(shortBuffer.toString());
	}

    /**
     * Create a new random UUID.
     * 随机UUID
     * @return the new UUID
     */
    public static UUID random(){
        byte[] randomBytes = new byte[16];
        if(IS_THREADLOCALRANDOM_AVAILABLE){
            java.util.concurrent.ThreadLocalRandom.current().nextBytes(randomBytes);
        }else{
            random.nextBytes(randomBytes);
        }

        long mostSigBits = 0;
        for(int i = 0; i < 8; i++){
            mostSigBits = (mostSigBits << 8) | (randomBytes[i] & 0xff);
        }
        long leastSigBits = 0;
        for(int i = 8; i < 16; i++){
            leastSigBits = (leastSigBits << 8) | (randomBytes[i] & 0xff);
        }

        return new UUID(mostSigBits, leastSigBits);
    }

    /**
     * Create a new time-based UUID.
     * 创建一个基于时间戳的UUID
     * @return the new UUID
     */
    public static UUID create(){
        long timeMillis = (System.currentTimeMillis() * 10000) + 0x01B21DD213814000L;

        lock.lock();
        try{
            if(timeMillis > lastTime){
                lastTime = timeMillis;
            }else{
                timeMillis = ++lastTime;
            }
        }finally{
            lock.unlock();
        }

        // time low
        long mostSigBits = timeMillis << 32;

        // time mid
        mostSigBits |= (timeMillis & 0xFFFF00000000L) >> 16;

        // time hi and version
        mostSigBits |= 0x1000 | ((timeMillis >> 48) & 0x0FFF); // version 1

        return new UUID(mostSigBits, leastSigBits);
    }

}
