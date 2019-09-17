package com.platform.system.common.rest;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.Page;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.web.response.entity.BaseResponse;
import com.platform.system.common.web.response.entity.CollectionResponse;
import com.platform.system.common.web.response.entity.ErrorResponse;
import com.platform.system.common.web.response.entity.PageResponse;


/**
 * 基础provider
 *
 * @version: 1.0
 */
public abstract class BaseProvider {

    /**
     * 操作成功
     *
     * @return
     */
    public <T> BaseResponse<T> success() {
        BaseResponse<T> baseResponse = new BaseResponse<T>();
        baseResponse.setCode(StatusCode.OK.code());
        baseResponse.setMsg(StatusCode.OK.message());
        return baseResponse;
    }

    /**
     * 操作成功并返回签名
     *
     * @param apiSecret
     * @return
     * @version: 1.0
     */
//    public <T> SignResponse<T> successAndSign(String apiSecret) {
//        SignResponse<T> baseResponse = new SignResponse<T>();
//        baseResponse.setCode(StatusCode.OK.code());
//        baseResponse.setMsg(StatusCode.OK.message());
//        return baseResponse.sign(apiSecret);
//    }

    /**
     * 操作成功并返回签名
     *
     * @param apiSecret
     * @param data
     * @return
     * @version: 1.0
     */
//    public <T> SignResponse<T> successAndSign(String apiSecret, T data) {
//        SignResponse<T> baseResponse = new SignResponse<T>();
//        baseResponse.setCode(StatusCode.OK.code());
//        baseResponse.setMsg(StatusCode.OK.message());
//        baseResponse.setData(data);
//        return baseResponse.sign(apiSecret);
//    }

    /**
     * 操作成功并返回签名
     *
     * @param apiSecret
     * @return
     */
//    public <T> SignResponse<T> successAndSignBase(String apiSecret) {
//        SignBaseResponse<T> baseResponse = new SignBaseResponse<T>();
//        return baseResponse.sign(apiSecret);
//    }

    /**
     * 操作成功并返回签名
     *
     * @param apiSecret
     * @param data
     * @return
     */
//    public <T> SignResponse<T> successAndSignBase(String apiSecret, T data) {
//        SignBaseResponse<T> baseResponse = new SignBaseResponse<T>(data);
//        return baseResponse.sign(apiSecret);
//    }

    /**
     * 操作成功并返回签名
     *
     * @param apiSecret
     * @param pageNum
     * @param pageSize
     * @param total
     * @param pages
     * @param data
     * @return
     */
//    public <T> SignResponse<List<T>> successAndSignPage(String apiSecret, int pageNum, int pageSize, long total, int pages, List<T> data) {
//        SignPageResponse<T> baseResponse = new SignPageResponse<T>(pageNum, pageSize, total, pages, data);
//        return baseResponse.sign(apiSecret);
//    }

    /**
     * 操作失败并返回签名
     *
     * @param apiSecret
     * @param code
     * @return
     * @version: 1.0
     */
//    public <T> SignResponse<T> failAndSign(String apiSecret, RestStatus code) {
//        return failAndSign(apiSecret, code, code.message());
//    }

    /**
     * 操作失败并返回签名
     *
     * @param apiSecret
     * @param code
     * @param msg       提示语
     * @return
     * @version: 1.0
     */
//    public <T> SignResponse<T> failAndSign(String apiSecret, RestStatus code, String msg) {
//        return failAndSign(apiSecret, code, msg, null);
//    }

    /**
     * 操作失败并返回签名
     *
     * @param apiSecret
     * @param code
     * @param data
     * @return
     * @version: 1.0
     */
//    public <T> SignResponse<T> failAndSign(String apiSecret, RestStatus code, T data) {
//        return failAndSign(apiSecret, code, null, data);
//    }

    /**
     * 操作失败并返回签名
     *
     * @param apiSecret
     * @param code
     * @param msg
     * @param data
     * @return
     * @version: 1.0
     */
//    public <T> SignResponse<T> failAndSign(String apiSecret, RestStatus code, String msg, T data) {
//        SignResponse<T> baseResponse = new SignResponse<T>();
//        baseResponse.setCode(code.code());
//        baseResponse.setMsg(null == msg ? code.message() : msg);
//        baseResponse.setData(data);
//        return baseResponse.sign(apiSecret);
//    }

    /**
     * 操作成功
     *
     * @return
     */
    public <T> CollectionResponse<T> success(List<T> data) {
        return new CollectionResponse<T>(data);
    }

    /**
     * 操作成功
     *
     * @return
     */
    public <T> PageResponse<T> success(Page<T> page) {
        return new PageResponse<T>(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages(), page);
    }

    /**
     * 操作成功
     *
     * @return
     */
    public <T> PageResponse<T> success(Page<?> page, List<T> list) {
        return new PageResponse<>(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getPages(), list);
    }

    /**
     * 操作成功
     *
     * @return
     */
    public <T> PageResponse<T> successPage() {
        PageResponse<T> page = new PageResponse<T>();
        page.setCode(StatusCode.OK.code());
        page.setMsg(StatusCode.OK.message());
        return page;
    }

    /**
     * <p>返回空的数据</p>
     *
     * @param
     * @version: 1.0
     */
    public <T> PageResponse<T> emptyPage() {
        PageResponse<T> page = new PageResponse<T>();
        page.setCode(StatusCode.OK.code());
        page.setMsg(StatusCode.OK.message());
        page.setData(new ArrayList<T>());
        return page;
    }


    /**
     * 操作成功
     *
     * @return
     */
    public <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(data);
    }

    /**
     * 操作失败
     *
     * @return
     */
    public <T> ErrorResponse<T> fail(RestStatus code) {
        return new ErrorResponse<T>(code);
    }

    /**
     * 操作失败
     *
     * @return
     */
    public <T> ErrorResponse<T> fail(RestStatus code, String msg) {
        return new ErrorResponse<T>(code, msg);
    }

    /**
     * 操作失败
     *
     * @return
     */
    public <T> ErrorResponse<T> fail(RestStatus code, String msg, T data) {
        return new ErrorResponse<T>(code, msg, data);
    }
}