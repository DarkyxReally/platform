package com.platform.user.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user")
public class UserEntity implements Serializable {
    /**
     * 系统ID
     */
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态 1:正常   99:删除
     */
    private Integer state;

    /**
     * 电话
     */
    private String phone;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 头像
     */
    @Column(name = "image_url")
    private String imageUrl;

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
     * 序号标识 自增
     */
    @Column(name = "serial_number")
    private Long serialNumber;

    private static final long serialVersionUID = 1L;

    /**
     * 获取系统ID
     *
     * @return user_id - 系统ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置系统ID
     *
     * @param userId 系统ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取用户类型
     *
     * @return type - 用户类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置用户类型
     *
     * @param type 用户类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取状态 1:正常   99:删除
     *
     * @return state - 状态 1:正常   99:删除
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态 1:正常   99:删除
     *
     * @param state 状态 1:正常   99:删除
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取出生日期
     *
     * @return birthday - 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     *
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取头像
     *
     * @return image_url - 头像
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置头像
     *
     * @param imageUrl 头像
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
     * 获取序号标识 自增
     *
     * @return serial_number - 序号标识 自增
     */
    public Long getSerialNumber() {
        return serialNumber;
    }

    /**
     * 设置序号标识 自增
     *
     * @param serialNumber 序号标识 自增
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
        UserEntity other = (UserEntity) that;
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
        sb.append(", account=").append(account);
        sb.append(", type=").append(type);
        sb.append(", name=").append(name);
        sb.append(", password=").append(password);
        sb.append(", state=").append(state);
        sb.append(", phone=").append(phone);
        sb.append(", sex=").append(sex);
        sb.append(", birthday=").append(birthday);
        sb.append(", imageUrl=").append(imageUrl);
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