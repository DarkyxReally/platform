package com.platform.system.common.web.response.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页结果
 * 
 * @version: 1.0
 */
@Getter
@Setter
public class PageResult {

    public PageResult() {

    }

    public PageResult(int pageNum, int pageSize, int pages, long total) {
        super();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pages;
        this.total = total;
    }

    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 总数量
     */
    private long total;
}
