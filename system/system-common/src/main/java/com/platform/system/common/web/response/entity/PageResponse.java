package com.platform.system.common.web.response.entity;

import java.util.List;

/**
 * 分页结果响应
 *
 * @param <T>
 * @version: 1.0
 */
public class PageResponse<T> extends BaseResponse<List<T>> {

    private static final long serialVersionUID = -8312599545950049381L;

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

    private Object object;

    public PageResponse() {
        super();
    }

    public PageResponse(List<T> data) {
        super(data);
    }

    public PageResponse(int pageSize, List<T> data) {
        super(data);
        this.pageSize = pageSize;
    }

    /**
     * 分页结果
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @param total    总数量
     * @param pages    总页数
     * @param data     数据
     */
    public PageResponse(int pageNo, int pageSize, long total, int pages, List<T> data) {
        super(data);
        this.pageNum = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
    }

    public PageResponse(Object object, int pageNo, int pageSize, long total, int pages, List<T> data) {
        super(data);
        this.object = object;
        this.pageNum = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
