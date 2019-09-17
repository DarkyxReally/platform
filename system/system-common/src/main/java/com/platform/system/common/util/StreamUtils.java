package com.platform.system.common.util;

import java.io.Closeable;

public class StreamUtils {

    /**
     * 关闭流
     * @param closeable
     */
    public static final void close(Closeable... closeable){
        if(null != closeable){
            for(Closeable ca : closeable){
                try{
                    ca.close();
                    ca = null;
                }catch(Exception e){}
            }
        }
    }
}
