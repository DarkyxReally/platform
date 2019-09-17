package com.platform.user.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "union_user_third")
public class UserThirdEntity implements Serializable {
    /**
     * 账户表系统ID
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 关联类型 1:微信 2:QQ 3:微博
     */
    @Column(name = "third_type")
    private Integer thirdType;

    /**
     * 三方信息ID
     */
    @Column(name = "third_uuid")
    private String thirdUuid;

    /**
     * 三方头像
     */
    @Column(name = "third_url")
    private String thirdUrl;

    /**
     * 创建人
     */
    @Column(name = "created_user")
    private String createdUser;

    /**
     * 创建时间
     */
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * 修改人
     */
    @Column(name = "updated_user")
    private String updatedUser;

    /**
     * 修改时间
     */
    @Column(name = "updated_date")
    private Date updatedDate;

    /**
     * 序号标识
     */
    @Column(name = "serial_number")
    private Long serialNumber;

    private static final long serialVersionUID = 1L;

    /**
     * 获取账户表系统ID
     *
     * @return user_id - 账户表系统ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置账户表系统ID
     *
     * @param userId 账户表系统ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取关联类型 1:微信 2:QQ 3:微博
     *
     * @return third_type - 关联类型 1:微信 2:QQ 3:微博
     */
    public Integer getThirdType() {
        return thirdType;
    }

    /**
     * 设置关联类型 1:微信 2:QQ 3:微博
     *
     * @param thirdType 关联类型 1:微信 2:QQ 3:微博
     */
    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }

    /**
     * 获取三方信息ID
     *
     * @return third_uuid - 三方信息ID
     */
    public String getThirdUuid() {
        return thirdUuid;
    }

    /**
     * 设置三方信息ID
     *
     * @param thirdUuid 三方信息ID
     */
    public void setThirdUuid(String thirdUuid) {
        this.thirdUuid = thirdUuid;
    }

    /**
     * 获取三方头像
     *
     * @return third_url - 三方头像
     */
    public String getThirdUrl() {
        return thirdUrl;
    }

    /**
     * 设置三方头像
     *
     * @param thirdUrl 三方头像
     */
    public void setThirdUrl(String thirdUrl) {
        this.thirdUrl = thirdUrl;
    }

    /**
     * 获取创建人
     *
     * @return created_user - 创建人
     */
    public String getCreatedUser() {
        return createdUser;
    }

    /**
     * 设置创建人
     *
     * @param createdUser 创建人
     */
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * 获取创建时间
     *
     * @return created_date - 创建时间
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * 设置创建时间
     *
     * @param createdDate 创建时间
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 获取修改人
     *
     * @return updated_user - 修改人
     */
    public String getUpdatedUser() {
        return updatedUser;
    }

    /**
     * 设置修改人
     *
     * @param updatedUser 修改人
     */
    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    /**
     * 获取修改时间
     *
     * @return updated_date - 修改时间
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * 设置修改时间
     *
     * @param updatedDate 修改时间
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * 获取序号标识
     *
     * @return serial_number - 序号标识
     */
    public Long getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置序号标识
     *
     * @param serialNumber 序号标识
     */
    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
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
        UserThirdEntity other = (UserThirdEntity) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", thirdType=").append(thirdType);
        sb.append(", thirdUuid=").append(thirdUuid);
        sb.append(", thirdUrl=").append(thirdUrl);
        sb.append(", createdUser=").append(createdUser);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", updatedUser=").append(updatedUser);
        sb.append(", updatedDate=").append(updatedDate);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}