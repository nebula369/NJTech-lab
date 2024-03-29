package com.easybasic.eclassbrand.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class ActivityDetail implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activitydetail.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activitydetail.activityid
     *
     * @mbggenerated
     */
    private Integer activityid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activitydetail.photo
     *
     * @mbggenerated
     */
    private String photo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activitydetail.describe
     *
     * @mbggenerated
     */
    private String describe;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activitydetail.createtime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ecb_activitydetail
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activitydetail.pkid
     *
     * @return the value of ecb_activitydetail.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activitydetail.pkid
     *
     * @param pkid the value for ecb_activitydetail.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activitydetail.activityid
     *
     * @return the value of ecb_activitydetail.activityid
     *
     * @mbggenerated
     */
    public Integer getActivityid() {
        return activityid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activitydetail.activityid
     *
     * @param activityid the value for ecb_activitydetail.activityid
     *
     * @mbggenerated
     */
    public void setActivityid(Integer activityid) {
        this.activityid = activityid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activitydetail.photo
     *
     * @return the value of ecb_activitydetail.photo
     *
     * @mbggenerated
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activitydetail.photo
     *
     * @param photo the value for ecb_activitydetail.photo
     *
     * @mbggenerated
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activitydetail.describe
     *
     * @return the value of ecb_activitydetail.describe
     *
     * @mbggenerated
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activitydetail.describe
     *
     * @param describe the value for ecb_activitydetail.describe
     *
     * @mbggenerated
     */
    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activitydetail.createtime
     *
     * @return the value of ecb_activitydetail.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activitydetail.createtime
     *
     * @param createtime the value for ecb_activitydetail.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_activitydetail
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
        ActivityDetail other = (ActivityDetail) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getActivityid() == null ? other.getActivityid() == null : this.getActivityid().equals(other.getActivityid()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getDescribe() == null ? other.getDescribe() == null : this.getDescribe().equals(other.getDescribe()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_activitydetail
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getActivityid() == null) ? 0 : getActivityid().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getDescribe() == null) ? 0 : getDescribe().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_activitydetail
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
        sb.append(", activityid=").append(activityid);
        sb.append(", photo=").append(photo);
        sb.append(", describe=").append(describe);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}