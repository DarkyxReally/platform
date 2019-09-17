package com.platform.auth.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.util.CollectionUtils;

import com.platform.system.common.json.JsonUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * REST操作记录
 * @version: 1.0
 */
@Slf4j
public class RestOperatorLog extends Thread {

    /**
     * 单例
     */
    private static RestOperatorLog dblog = null;
    
    /**
     * 日志队列
     */
    private static BlockingQueue<RequestData> logInfoQueue = new LinkedBlockingQueue<RequestData>(4096);

    /**
     * 日志记录服务客户端
     */
    private IRequestDataLogService requestDataLogService;
    
    /**
     * 是否已启动
     */
    private boolean isStart;
    
    public IRequestDataLogService getLogService(){
        return requestDataLogService;
    }

    public RestOperatorLog setLogService(IRequestDataLogService requestDataLogService){
        if(this.requestDataLogService == null){
            this.requestDataLogService = requestDataLogService;
        }
        return this;
    }

    /**
     * 获取实例
     */
    public static synchronized RestOperatorLog getInstance(){
        if(dblog == null){
            dblog = new RestOperatorLog();
            dblog.setStart(true);
            dblog.start();
        }
        return dblog;
    }

    private RestOperatorLog() {
        super("RestOperatorLog");
    }

    /**
     * 队列压入
     * @param logInfo
     */
    public void offerQueue(RequestData logInfo){
        try{
            logInfoQueue.offer(logInfo);
        }catch(Exception e){
            log.error("REST请求日志:" + JsonUtil.toJSONString(logInfo)+ ">>>压入队列异常:" +e.getMessage(), e);
        }
    }

    @Override
    public void run(){
        // 缓冲队列
        List<RequestData> bufferedLogList = new ArrayList<RequestData>();
        while(this.isStart){
            try{
                bufferedLogList.add(logInfoQueue.take());
                logInfoQueue.drainTo(bufferedLogList);
                if(bufferedLogList != null && bufferedLogList.size() > 0){
                    // 写入日志
                    for(RequestData logReq : bufferedLogList){
                        requestDataLogService.save(logReq);
                    }
                }
            }catch(Exception e){
                log.error(e.getMessage(), e);
                // 防止缓冲队列填充数据出现异常时不断刷屏
                try{
                    Thread.sleep(1000);
                }catch(Exception eee){}
            }finally{
                if(!CollectionUtils.isEmpty(bufferedLogList)){
                    try{
                        bufferedLogList.clear();
                    }catch(Exception e){
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
    }
    
    public boolean isStart(){
        return isStart;
    }

    public void setStart(boolean isStart){
        this.isStart = isStart;
    }
    
    /**
     * 停止运行
     */
    public void stopRun(){
        this.isStart = false;
    }
    
}