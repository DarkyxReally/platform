package com.platform.system.common.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.util.ResourceUtils;

/**
 * 文件工具类
 * @version: 1.0
 */
public class FileUtil {
    
    /**
     * 读取文件字节码
     * @throws Exception
     */
    public static final byte[] getFileByte(String filename) throws Exception{
        File f = new File(filename);
        FileInputStream fis = null;
        DataInputStream dis = null;
        try{
            fis = new FileInputStream(f);
            dis = new DataInputStream(fis);
            byte[] bytes = new byte[(int)f.length()];
            dis.readFully(bytes);
            return bytes;
        }finally{
            StreamUtils.close(dis, fis);
        }
    }
    
    /**
     * 写入文件字节码
     * @return
     * @throws Exception
     */
    public static final void writeFileByte(String filename, byte[] bytes) throws Exception{
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(filename);
            fos.write(bytes);
            fos.flush();
        }finally{
            StreamUtils.close(fos);
        }
    }
    
    /**
     * 写入文件字节码
     * @version: 1.0
     * @param filename
     * @param bytes
     * @throws Exception
     */
    public static final void writeFileByte(File file, byte[] bytes) throws Exception{
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
        }finally{
            StreamUtils.close(fos);
        }
    }
    
    /**
     * 读取文件
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(String filePath) throws FileNotFoundException{
        File file = ResourceUtils.getFile(filePath);
        return file;
    }
    
    /**
     * 读取腾讯云通信公私钥文件
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getTLSKeyFile(String filePath) throws IOException{
        File file = getFile(filePath);
        StringBuilder strBuilder = new StringBuilder();
        String s = "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((s = br.readLine()) != null) {
            strBuilder.append(s + '\n');
        }
        br.close();
        String key = strBuilder.toString();  
        return key;
    }
    
}
