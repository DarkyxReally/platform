package com.platform.system.common.web.response.entity;

import java.util.List;

/**
 * 分页结果响应
 * @version: 1.0
 * @param <T>
 */
public class ExtPageResponse<T> extends PageResponse<T>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 数据
     */
    private Object obj;

    /**
     * 分页结果
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @param total 总数量
     * @param pages 总页数
     * @param data 数据
     * @param obj 数据
     */
    public ExtPageResponse(int pageNo, int pageSize, long total, int pages, List<T> data, Object obj){
        super(pageNo, pageSize, total, pages, data);
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
