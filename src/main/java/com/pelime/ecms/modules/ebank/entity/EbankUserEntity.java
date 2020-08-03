package com.pelime.ecms.modules.ebank.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;


@Entity(name = "ebank_user")
public class EbankUserEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 核心客户号
     */
    @Id
    private String userId;
    /***
     * 机构编号
     */
    private int deptNum;
    /**
     * 数据时间
     */
    private Date updateTime;
    /**
     * 用户名称
     */
    private String name;

    /**
     * 身份证号
     */
    private String idCardNumber;
    /**
     * 手机号码
     */
    private String phone;
    /***
     * 注册日期
     */
    private Date registerTime;
    /**
     * 认证等级
     */
    private String grade;
    /**
     * 最近登录日期
     */
    private Date lastLoginTime;
    /**
     * 最近动账时间
     */
    private Date lastEffectTime;

    public EbankUserEntity(String userId, int deptNum, Date updateTime, String name, String idCardNumber, String phone, Date registerTime, String grade, Date lastLoginTime, Date lastEffectTime) {
        this.userId = userId;
        this.deptNum = deptNum;
        this.updateTime = updateTime;
        this.name = name;
        this.idCardNumber = idCardNumber;
        this.phone = phone;
        this.registerTime = registerTime;
        this.grade = grade;
        this.lastLoginTime = lastLoginTime;
        this.lastEffectTime = lastEffectTime;
    }

    public EbankUserEntity() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(int deptNum) {
        this.deptNum = deptNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastEffectTime() {
        return lastEffectTime;
    }

    public void setLastEffectTime(Date lastEffectTime) {
        this.lastEffectTime = lastEffectTime;
    }
}
