package com.platform.auth.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "access_token")
public class AccessTokenEntity implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String tokenId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 是否已失效(0:否;1:是)
     */
    @Column(name = "is_invalid")
    private Boolean isInvalid;

    /**
     * 创建时间
     */
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * 过期时间
     */
    @Column(name = "expired_date")
    private Date expiredDate;

    /**
     * 平台(1:安卓;2:IOS)
     */
    private String platform;

    /**
     * token类型(1:APP;2:微信小程序;3:web)
     */
    @Column(name = "token_type")
    private String tokenType;

    /**
     * 用户类型(1:普通用户;2:小程序用户)
     */
    @Column(name = "user_type")
    private String userType;

    /**
     * 创建时间
     */
    @Column(name = "updated_date")
    private Date updatedDate;

    /**
     * 创建人id
     */
    @Column(name = "created_user")
    private String createdUser;

    /**
     * 修改人id
     */
    @Column(name = "updated_user")
    private String updatedUser;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id
     *
     * @return token_id - 主键id
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * 设置主键id
     *
     * @param tokenId 主键id
     */
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取是否已失效(0:否;1:是)
     *
     * @return is_invalid - 是否已失效(0:否;1:是)
     */
    public Boolean getIsInvalid() {
        return isInvalid;
    }

    /**
     * 设置是否已失效(0:否;1:是)
     *
     * @param isInvalid 是否已失效(0:否;1:是)
     */
    public void setIsInvalid(Boolean isInvalid) {
        this.isInvalid = isInvalid;
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
     * 获取过期时间
     *
     * @return expired_date - 过期时间
     */
    public Date getExpiredDate() {
        return expiredDate;
    }

    /**
     * 设置过期时间
     *
     * @param expiredDate 过期时间
     */
    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    /**
     * 获取平台(1:安卓;2:IOS)
     *
     * @return platform - 平台(1:安卓;2:IOS)
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 设置平台(1:安卓;2:IOS)
     *
     * @param platform 平台(1:安卓;2:IOS)
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * 获取token类型(1:APP;2:微信小程序;3:web)
     *
     * @return token_type - token类型(1:APP;2:微信小程序;3:web)
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * 设置token类型(1:APP;2:微信小程序;3:web)
     *
     * @param tokenType token类型(1:APP;2:微信小程序;3:web)
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * 获取用户类型(1:普通用户;2:小程序用户)
     *
     * @return user_type - 用户类型(1:普通用户;2:小程序用户)
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 设置用户类型(1:普通用户;2:小程序用户)
     *
     * @param userType 用户类型(1:普通用户;2:小程序用户)
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 获取创建时间
     *
     * @return updated_date - 创建时间
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * 设置创建时间
     *
     * @param updatedDate 创建时间
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * 获取创建人id
     *
     * @return created_user - 创建人id
     */
    public String getCreatedUser() {
        return createdUser;
    }

    /**
     * 设置创建人id
     *
     * @param createdUser 创建人id
     */
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * 获取修改人id
     *
     * @return updated_user - 修改人id
     */
    public String getUpdatedUser() {
        return updatedUser;
    }

    /**
     * 设置修改人id
     *
     * @param updatedUser 修改人id
     */
    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
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
        AccessTokenEntity other = (AccessTokenEntity) that;
        return (this.getTokenId() == null ? other.getTokenId() == null : this.getTokenId().equals(other.getTokenId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTokenId() == null) ? 0 : getTokenId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tokenId=").append(tokenId);
        sb.append(", userId=").append(userId);
        sb.append(", isInvalid=").append(isInvalid);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", expiredDate=").append(expiredDate);
        sb.append(", platform=").append(platform);
        sb.append(", tokenType=").append(tokenType);
        sb.append(", userType=").append(userType);
        sb.append(", updatedDate=").append(updatedDate);
        sb.append(", createdUser=").append(createdUser);
        sb.append(", updatedUser=").append(updatedUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}