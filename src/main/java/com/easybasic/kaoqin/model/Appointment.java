package com.easybasic.kaoqin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_appointment.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_appointment.userid
     *
     * @mbggenerated
     */
    private Integer userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_appointment.begintime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begintime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_appointment.endtime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_appointment.subject
     *
     * @mbggenerated
     */
    private String subject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_appointment.placeID
     *
     * @mbggenerated
     */
    private Integer placeid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_appointment.isSync
     *
     * @mbggenerated
     */
    private Boolean issync;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_appointment.photo
     *
     * @mbggenerated
     */
    private String photo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_appointment.reaname
     *
     * @mbggenerated
     */
    private String reaname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kq_appointment
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_appointment.pkid
     *
     * @return the value of kq_appointment.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_appointment.pkid
     *
     * @param pkid the value for kq_appointment.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_appointment.userid
     *
     * @return the value of kq_appointment.userid
     *
     * @mbggenerated
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_appointment.userid
     *
     * @param userid the value for kq_appointment.userid
     *
     * @mbggenerated
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_appointment.begintime
     *
     * @return the value of kq_appointment.begintime
     *
     * @mbggenerated
     */
    public Date getBegintime() {
        return begintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_appointment.begintime
     *
     * @param begintime the value for kq_appointment.begintime
     *
     * @mbggenerated
     */
    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_appointment.endtime
     *
     * @return the value of kq_appointment.endtime
     *
     * @mbggenerated
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_appointment.endtime
     *
     * @param endtime the value for kq_appointment.endtime
     *
     * @mbggenerated
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_appointment.subject
     *
     * @return the value of kq_appointment.subject
     *
     * @mbggenerated
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_appointment.subject
     *
     * @param subject the value for kq_appointment.subject
     *
     * @mbggenerated
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_appointment.placeID
     *
     * @return the value of kq_appointment.placeID
     *
     * @mbggenerated
     */
    public Integer getPlaceid() {
        return placeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_appointment.placeID
     *
     * @param placeid the value for kq_appointment.placeID
     *
     * @mbggenerated
     */
    public void setPlaceid(Integer placeid) {
        this.placeid = placeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_appointment.isSync
     *
     * @return the value of kq_appointment.isSync
     *
     * @mbggenerated
     */
    public Boolean getIssync() {
        return issync;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_appointment.isSync
     *
     * @param issync the value for kq_appointment.isSync
     *
     * @mbggenerated
     */
    public void setIssync(Boolean issync) {
        this.issync = issync;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_appointment.photo
     *
     * @return the value of kq_appointment.photo
     *
     * @mbggenerated
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_appointment.photo
     *
     * @param photo the value for kq_appointment.photo
     *
     * @mbggenerated
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_appointment.reaname
     *
     * @return the value of kq_appointment.reaname
     *
     * @mbggenerated
     */
    public String getReaname() {
        return reaname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_appointment.reaname
     *
     * @param reaname the value for kq_appointment.reaname
     *
     * @mbggenerated
     */
    public void setReaname(String reaname) {
        this.reaname = reaname == null ? null : reaname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_appointment
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
        Appointment other = (Appointment) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getBegintime() == null ? other.getBegintime() == null : this.getBegintime().equals(other.getBegintime()))
            && (this.getEndtime() == null ? other.getEndtime() == null : this.getEndtime().equals(other.getEndtime()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getPlaceid() == null ? other.getPlaceid() == null : this.getPlaceid().equals(other.getPlaceid()))
            && (this.getIssync() == null ? other.getIssync() == null : this.getIssync().equals(other.getIssync()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getReaname() == null ? other.getReaname() == null : this.getReaname().equals(other.getReaname()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_appointment
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getBegintime() == null) ? 0 : getBegintime().hashCode());
        result = prime * result + ((getEndtime() == null) ? 0 : getEndtime().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getPlaceid() == null) ? 0 : getPlaceid().hashCode());
        result = prime * result + ((getIssync() == null) ? 0 : getIssync().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getReaname() == null) ? 0 : getReaname().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_appointment
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
        sb.append(", userid=").append(userid);
        sb.append(", begintime=").append(begintime);
        sb.append(", endtime=").append(endtime);
        sb.append(", subject=").append(subject);
        sb.append(", placeid=").append(placeid);
        sb.append(", issync=").append(issync);
        sb.append(", photo=").append(photo);
        sb.append(", reaname=").append(reaname);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}