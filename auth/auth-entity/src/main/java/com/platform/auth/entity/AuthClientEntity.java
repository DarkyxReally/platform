package com.platform.auth.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "auth_client")
public class AuthClientEntity implements Serializable {
    /**
     * 主键id
     */
    @Id
    private String id;

    /**
     * 服务编码
     */
    @Column(name = "client_code")
    private String clientCode;

    /**
     * 服务密钥
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * 服务名
     */
    @Column(name = "service_name")
    private String serviceName;

    /**
     * 是否锁定
     */
    @Column(name = "is_locked")
    private Boolean isLocked;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "date_created")
    private Date dateCreated;

    /**
     * 创建人
     */
    @Column(name = "created_user")
    private String createdUser;

    /**
     * 创建人姓名
     */
    @Column(name = "created_user_name")
    private String createdUserName;

    /**
     * 更新时间
     */
    @Column(name = "date_updated")
    private Date dateUpdated;

    /**
     * 更新人
     */
    @Column(name = "updated_user")
    private String updatedUser;

    /**
     * 更新姓名
     */
    @Column(name = "updated_user_name")
    private String updatedUserName;

    /**
     * 是否已删除(0:否;1:是)
     */
    @Column(name = "is_del")
    private Boolean isDel;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取服务编码
     *
     * @return client_code - 服务编码
     */
    public String getClientCode() {
        return clientCode;
    }

    /**
     * 设置服务编码
     *
     * @param clientCode 服务编码
     */
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    /**
     * 获取服务密钥
     *
     * @return client_secret - 服务密钥
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * 设置服务密钥
     *
     * @param clientSecret 服务密钥
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * 获取服务名
     *
     * @return service_name - 服务名
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 设置服务名
     *
     * @param serviceName 服务名
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * 获取是否锁定
     *
     * @return is_locked - 是否锁定
     */
    public Boolean getIsLocked() {
        return isLocked;
    }

    /**
     * 设置是否锁定
     *
     * @param isLocked 是否锁定
     */
    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 获取创建人姓名
     *
     * @return created_user_name - 创建人姓名
     */
    public String getCreatedUserName() {
        return createdUserName;
    }

    /**
     * 设置创建人姓名
     *
     * @param createdUserName 创建人姓名
     */
    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    /**
     * 获取更新时间
     *
     * @return date_updated - 更新时间
     */
    public Date getDateUpdated() {
        return dateUpdated;
    }

    /**
     * 设置更新时间
     *
     * @param dateUpdated 更新时间
     */
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    /**
     * 获取更新人
     *
     * @return updated_user - 更新人
     */
    public String getUpdatedUser() {
        return updatedUser;
    }

    /**
     * 设置更新人
     *
     * @param updatedUser 更新人
     */
    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    /**
     * 获取更新姓名
     *
     * @return updated_user_name - 更新姓名
     */
    public String getUpdatedUserName() {
        return updatedUserName;
    }

    /**
     * 设置更新姓名
     *
     * @param updatedUserName 更新姓名
     */
    public void setUpdatedUserName(String updatedUserName) {
        this.updatedUserName = updatedUserName;
    }

    /**
     * 获取是否已删除(0:否;1:是)
     *
     * @return is_del - 是否已删除(0:否;1:是)
     */
    public Boolean getIsDel() {
        return isDel;
    }

    /**
     * 设置是否已删除(0:否;1:是)
     *
     * @param isDel 是否已删除(0:否;1:是)
     */
    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
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
        AuthClientEntity other = (AuthClientEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clientCode=").append(clientCode);
        sb.append(", clientSecret=").append(clientSecret);
        sb.append(", serviceName=").append(serviceName);
        sb.append(", isLocked=").append(isLocked);
        sb.append(", description=").append(description);
        sb.append(", dateCreated=").append(dateCreated);
        sb.append(", createdUser=").append(createdUser);
        sb.append(", createdUserName=").append(createdUserName);
        sb.append(", dateUpdated=").append(dateUpdated);
        sb.append(", updatedUser=").append(updatedUser);
        sb.append(", updatedUserName=").append(updatedUserName);
        sb.append(", isDel=").append(isDel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}