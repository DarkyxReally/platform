package com.platform.auth.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "rest_operator_log")
public class RestOperatorLogEntity implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "id_log")
    private Long idLog;

    /**
     * 请求id
     */
    @Column(name = "id_request")
    private String idRequest;

    /**
     * 请求地址
     */
    @Column(name = "req_url")
    private String reqUrl;

    /**
     * 请求方式
     */
    @Column(name = "req_method")
    private String reqMethod;

    /**
     * 请求参数
     */
    @Column(name = "req_param")
    private String reqParam;

    /**
     * 请求头
     */
    @Column(name = "req_header")
    private String reqHeader;

    /**
     * 请求ip
     */
    @Column(name = "req_ip")
    private String reqIp;

    /**
     * 用户的IP
     */
    @Column(name = "user_ip")
    private String userIp;

    /**
     * 请求时间
     */
    @Column(name = "req_date")
    private Date reqDate;

    /**
     * 用户id
     */
    @Column(name = "id_user")
    private String idUser;

    /**
     * 应用名称
     */
    @Column(name = "service_name")
    private String serviceName;

    /**
     * 系统code
     */
    @Column(name = "system_code")
    private String systemCode;

    /**
     * 模块
     */
    private String module;

    /**
     * 接口描述
     */
    private String description;

    /**
     * 客户端ID
     */
    @Column(name = "id_client")
    private String idClient;

    /**
     * 创建时间
     */
    @Column(name = "date_created")
    private Date dateCreated;

    /**
     * 请求内容
     */
    @Column(name = "req_body")
    private String reqBody;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id
     *
     * @return id_log - 主键id
     */
    public Long getIdLog() {
        return idLog;
    }

    /**
     * 设置主键id
     *
     * @param idLog 主键id
     */
    public void setIdLog(Long idLog) {
        this.idLog = idLog;
    }

    /**
     * 获取请求id
     *
     * @return id_request - 请求id
     */
    public String getIdRequest() {
        return idRequest;
    }

    /**
     * 设置请求id
     *
     * @param idRequest 请求id
     */
    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    /**
     * 获取请求地址
     *
     * @return req_url - 请求地址
     */
    public String getReqUrl() {
        return reqUrl;
    }

    /**
     * 设置请求地址
     *
     * @param reqUrl 请求地址
     */
    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    /**
     * 获取请求方式
     *
     * @return req_method - 请求方式
     */
    public String getReqMethod() {
        return reqMethod;
    }

    /**
     * 设置请求方式
     *
     * @param reqMethod 请求方式
     */
    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    /**
     * 获取请求参数
     *
     * @return req_param - 请求参数
     */
    public String getReqParam() {
        return reqParam;
    }

    /**
     * 设置请求参数
     *
     * @param reqParam 请求参数
     */
    public void setReqParam(String reqParam) {
        this.reqParam = reqParam;
    }

    /**
     * 获取请求头
     *
     * @return req_header - 请求头
     */
    public String getReqHeader() {
        return reqHeader;
    }

    /**
     * 设置请求头
     *
     * @param reqHeader 请求头
     */
    public void setReqHeader(String reqHeader) {
        this.reqHeader = reqHeader;
    }

    /**
     * 获取请求ip
     *
     * @return req_ip - 请求ip
     */
    public String getReqIp() {
        return reqIp;
    }

    /**
     * 设置请求ip
     *
     * @param reqIp 请求ip
     */
    public void setReqIp(String reqIp) {
        this.reqIp = reqIp;
    }

    /**
     * 获取用户的IP
     *
     * @return user_ip - 用户的IP
     */
    public String getUserIp() {
        return userIp;
    }

    /**
     * 设置用户的IP
     *
     * @param userIp 用户的IP
     */
    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    /**
     * 获取请求时间
     *
     * @return req_date - 请求时间
     */
    public Date getReqDate() {
        return reqDate;
    }

    /**
     * 设置请求时间
     *
     * @param reqDate 请求时间
     */
    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    /**
     * 获取用户id
     *
     * @return id_user - 用户id
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * 设置用户id
     *
     * @param idUser 用户id
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * 获取应用名称
     *
     * @return service_name - 应用名称
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 设置应用名称
     *
     * @param serviceName 应用名称
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * 获取系统code
     *
     * @return system_code - 系统code
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * 设置系统code
     *
     * @param systemCode 系统code
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * 获取模块
     *
     * @return module - 模块
     */
    public String getModule() {
        return module;
    }

    /**
     * 设置模块
     *
     * @param module 模块
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * 获取接口描述
     *
     * @return description - 接口描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置接口描述
     *
     * @param description 接口描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取客户端ID
     *
     * @return id_client - 客户端ID
     */
    public String getIdClient() {
        return idClient;
    }

    /**
     * 设置客户端ID
     *
     * @param idClient 客户端ID
     */
    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    /**
     * 获取创建时间
     *
     * @return date_created - 创建时间
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * 设置创建时间
     *
     * @param dateCreated 创建时间
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * 获取请求内容
     *
     * @return req_body - 请求内容
     */
    public String getReqBody() {
        return reqBody;
    }

    /**
     * 设置请求内容
     *
     * @param reqBody 请求内容
     */
    public void setReqBody(String reqBody) {
        this.reqBody = reqBody;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RestOperatorLogEntity other = (RestOperatorLogEntity) that;
        return (this.getIdLog() == null ? other.getIdLog() == null : this.getIdLog().equals(other.getIdLog()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIdLog() == null) ? 0 : getIdLog().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", idLog=").append(idLog);
        sb.append(", idRequest=").append(idRequest);
        sb.append(", reqUrl=").append(reqUrl);
        sb.append(", reqMethod=").append(reqMethod);
        sb.append(", reqParam=").append(reqParam);
        sb.append(", reqHeader=").append(reqHeader);
        sb.append(", reqIp=").append(reqIp);
        sb.append(", userIp=").append(userIp);
        sb.append(", reqDate=").append(reqDate);
        sb.append(", idUser=").append(idUser);
        sb.append(", serviceName=").append(serviceName);
        sb.append(", systemCode=").append(systemCode);
        sb.append(", module=").append(module);
        sb.append(", description=").append(description);
        sb.append(", idClient=").append(idClient);
        sb.append(", dateCreated=").append(dateCreated);
        sb.append(", reqBody=").append(reqBody);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}