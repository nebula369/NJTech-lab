package com.easybasic.basic.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.loginname
     *
     * @mbggenerated
     */
    private String loginname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.password
     *
     * @mbggenerated
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.registerip
     *
     * @mbggenerated
     */
    private String registerip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.lastlogintime
     *
     * @mbggenerated
     */
    private Date lastlogintime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.lastloginip
     *
     * @mbggenerated
     */
    private String lastloginip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.logintime
     *
     * @mbggenerated
     */
    private Date logintime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.logincount
     *
     * @mbggenerated
     */
    private Integer logincount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.mobile
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.email
     *
     * @mbggenerated
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.sex
     *
     * @mbggenerated
     */
    private Integer sex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.usertype
     *
     * @mbggenerated
     */
    private Integer usertype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.fpinyin
     *
     * @mbggenerated
     */
    private String fpinyin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_user.spinyin
     *
     * @mbggenerated
     */
    private String spinyin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table basic_user
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.pkid
     *
     * @return the value of basic_user.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.pkid
     *
     * @param pkid the value for basic_user.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.name
     *
     * @return the value of basic_user.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.name
     *
     * @param name the value for basic_user.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.loginname
     *
     * @return the value of basic_user.loginname
     *
     * @mbggenerated
     */
    public String getLoginname() {
        return loginname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.loginname
     *
     * @param loginname the value for basic_user.loginname
     *
     * @mbggenerated
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.password
     *
     * @return the value of basic_user.password
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.password
     *
     * @param password the value for basic_user.password
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.status
     *
     * @return the value of basic_user.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.status
     *
     * @param status the value for basic_user.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.createtime
     *
     * @return the value of basic_user.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.createtime
     *
     * @param createtime the value for basic_user.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.registerip
     *
     * @return the value of basic_user.registerip
     *
     * @mbggenerated
     */
    public String getRegisterip() {
        return registerip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.registerip
     *
     * @param registerip the value for basic_user.registerip
     *
     * @mbggenerated
     */
    public void setRegisterip(String registerip) {
        this.registerip = registerip == null ? null : registerip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.lastlogintime
     *
     * @return the value of basic_user.lastlogintime
     *
     * @mbggenerated
     */
    public Date getLastlogintime() {
        return lastlogintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.lastlogintime
     *
     * @param lastlogintime the value for basic_user.lastlogintime
     *
     * @mbggenerated
     */
    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.lastloginip
     *
     * @return the value of basic_user.lastloginip
     *
     * @mbggenerated
     */
    public String getLastloginip() {
        return lastloginip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.lastloginip
     *
     * @param lastloginip the value for basic_user.lastloginip
     *
     * @mbggenerated
     */
    public void setLastloginip(String lastloginip) {
        this.lastloginip = lastloginip == null ? null : lastloginip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.logintime
     *
     * @return the value of basic_user.logintime
     *
     * @mbggenerated
     */
    public Date getLogintime() {
        return logintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.logintime
     *
     * @param logintime the value for basic_user.logintime
     *
     * @mbggenerated
     */
    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.logincount
     *
     * @return the value of basic_user.logincount
     *
     * @mbggenerated
     */
    public Integer getLogincount() {
        return logincount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.logincount
     *
     * @param logincount the value for basic_user.logincount
     *
     * @mbggenerated
     */
    public void setLogincount(Integer logincount) {
        this.logincount = logincount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.mobile
     *
     * @return the value of basic_user.mobile
     *
     * @mbggenerated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.mobile
     *
     * @param mobile the value for basic_user.mobile
     *
     * @mbggenerated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.email
     *
     * @return the value of basic_user.email
     *
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.email
     *
     * @param email the value for basic_user.email
     *
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.sex
     *
     * @return the value of basic_user.sex
     *
     * @mbggenerated
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.sex
     *
     * @param sex the value for basic_user.sex
     *
     * @mbggenerated
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.usertype
     *
     * @return the value of basic_user.usertype
     *
     * @mbggenerated
     */
    public Integer getUsertype() {
        return usertype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.usertype
     *
     * @param usertype the value for basic_user.usertype
     *
     * @mbggenerated
     */
    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.fpinyin
     *
     * @return the value of basic_user.fpinyin
     *
     * @mbggenerated
     */
    public String getFpinyin() {
        return fpinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.fpinyin
     *
     * @param fpinyin the value for basic_user.fpinyin
     *
     * @mbggenerated
     */
    public void setFpinyin(String fpinyin) {
        this.fpinyin = fpinyin == null ? null : fpinyin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_user.spinyin
     *
     * @return the value of basic_user.spinyin
     *
     * @mbggenerated
     */
    public String getSpinyin() {
        return spinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_user.spinyin
     *
     * @param spinyin the value for basic_user.spinyin
     *
     * @mbggenerated
     */
    public void setSpinyin(String spinyin) {
        this.spinyin = spinyin == null ? null : spinyin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_user
     *
     * @mbggenerated
     */
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
        User other = (User) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getLoginname() == null ? other.getLoginname() == null : this.getLoginname().equals(other.getLoginname()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getRegisterip() == null ? other.getRegisterip() == null : this.getRegisterip().equals(other.getRegisterip()))
            && (this.getLastlogintime() == null ? other.getLastlogintime() == null : this.getLastlogintime().equals(other.getLastlogintime()))
            && (this.getLastloginip() == null ? other.getLastloginip() == null : this.getLastloginip().equals(other.getLastloginip()))
            && (this.getLogintime() == null ? other.getLogintime() == null : this.getLogintime().equals(other.getLogintime()))
            && (this.getLogincount() == null ? other.getLogincount() == null : this.getLogincount().equals(other.getLogincount()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getUsertype() == null ? other.getUsertype() == null : this.getUsertype().equals(other.getUsertype()))
            && (this.getFpinyin() == null ? other.getFpinyin() == null : this.getFpinyin().equals(other.getFpinyin()))
            && (this.getSpinyin() == null ? other.getSpinyin() == null : this.getSpinyin().equals(other.getSpinyin()))
           /* 新增字段 20190626 tangy*/
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getProfile() == null ? other.getProfile() == null : this.getProfile().equals(other.getProfile())) ;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_user
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getLoginname() == null) ? 0 : getLoginname().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getRegisterip() == null) ? 0 : getRegisterip().hashCode());
        result = prime * result + ((getLastlogintime() == null) ? 0 : getLastlogintime().hashCode());
        result = prime * result + ((getLastloginip() == null) ? 0 : getLastloginip().hashCode());
        result = prime * result + ((getLogintime() == null) ? 0 : getLogintime().hashCode());
        result = prime * result + ((getLogincount() == null) ? 0 : getLogincount().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getUsertype() == null) ? 0 : getUsertype().hashCode());
        result = prime * result + ((getFpinyin() == null) ? 0 : getFpinyin().hashCode());
        result = prime * result + ((getSpinyin() == null) ? 0 : getSpinyin().hashCode());
        /* 新增字段 20190626 tangy*/
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getProfile() == null) ? 0 : getProfile().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_user
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pkid=").append(pkid);
        sb.append(", name=").append(name);
        sb.append(", loginname=").append(loginname);
        sb.append(", password=").append(password);
        sb.append(", status=").append(status);
        sb.append(", createtime=").append(createtime);
        sb.append(", registerip=").append(registerip);
        sb.append(", lastlogintime=").append(lastlogintime);
        sb.append(", lastloginip=").append(lastloginip);
        sb.append(", logintime=").append(logintime);
        sb.append(", logincount=").append(logincount);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", sex=").append(sex);
        sb.append(", usertype=").append(usertype);
        sb.append(", fpinyin=").append(fpinyin);
        sb.append(", spinyin=").append(spinyin);
        sb.append(", photo=").append(photo);
        sb.append(", profile=").append(profile);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    private String gradeName;
    private String className;
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    /* 新增字段 tangy 2019/6/26 0026 17:01*/
    private String photo;
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    private String profile;
    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }
}