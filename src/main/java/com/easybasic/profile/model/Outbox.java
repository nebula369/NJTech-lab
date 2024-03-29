package com.easybasic.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Outbox implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_outbox.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_outbox.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_outbox.userid
     *
     * @mbggenerated
     */
    private Integer userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_outbox.createtime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_outbox.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_outbox.touserids
     *
     * @mbggenerated
     */
    private String touserids;

    /**
     * 消息来源：0为平台内部发送；1为第三方应用通过服务接口发送
     */
    private int fromtype;

    public int getFromtype() {
        return fromtype;
    }

    public void setFromtype(int fromtype) {
        this.fromtype = fromtype;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table profile_outbox
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_outbox.pkid
     *
     * @return the value of profile_outbox.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_outbox.pkid
     *
     * @param pkid the value for profile_outbox.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_outbox.title
     *
     * @return the value of profile_outbox.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_outbox.title
     *
     * @param title the value for profile_outbox.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_outbox.userid
     *
     * @return the value of profile_outbox.userid
     *
     * @mbggenerated
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_outbox.userid
     *
     * @param userid the value for profile_outbox.userid
     *
     * @mbggenerated
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_outbox.createtime
     *
     * @return the value of profile_outbox.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_outbox.createtime
     *
     * @param createtime the value for profile_outbox.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_outbox.content
     *
     * @return the value of profile_outbox.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_outbox.content
     *
     * @param content the value for profile_outbox.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_outbox.touserids
     *
     * @return the value of profile_outbox.touserids
     *
     * @mbggenerated
     */
    public String getTouserids() {
        return touserids;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_outbox.touserids
     *
     * @param touserids the value for profile_outbox.touserids
     *
     * @mbggenerated
     */
    public void setTouserids(String touserids) {
        this.touserids = touserids == null ? null : touserids.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_outbox
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
        Outbox other = (Outbox) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getTouserids() == null ? other.getTouserids() == null : this.getTouserids().equals(other.getTouserids()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_outbox
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getTouserids() == null) ? 0 : getTouserids().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_outbox
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
        sb.append(", title=").append(title);
        sb.append(", userid=").append(userid);
        sb.append(", createtime=").append(createtime);
        sb.append(", content=").append(content);
        sb.append(", touserids=").append(touserids);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}