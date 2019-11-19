package com.easybasic.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Inbox implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_inbox.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_inbox.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_inbox.type
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_inbox.userid
     *
     * @mbggenerated
     */
    private Integer userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_inbox.fromuserid
     *
     * @mbggenerated
     */
    private Integer fromuserid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_inbox.status
     *
     * @mbggenerated
     * 收件箱状态：0为未读；1为已读
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_inbox.createtime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createtime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date readtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column profile_inbox.content
     *
     * @mbggenerated
     */
    private String content;


    private int outboxid;

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
     * This field corresponds to the database table profile_inbox
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_inbox.pkid
     *
     * @return the value of profile_inbox.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_inbox.pkid
     *
     * @param pkid the value for profile_inbox.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_inbox.title
     *
     * @return the value of profile_inbox.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_inbox.title
     *
     * @param title the value for profile_inbox.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_inbox.type
     *
     * @return the value of profile_inbox.type
     *
     * @mbggenerated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_inbox.type
     *
     * @param type the value for profile_inbox.type
     *
     * @mbggenerated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_inbox.userid
     *
     * @return the value of profile_inbox.userid
     *
     * @mbggenerated
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_inbox.userid
     *
     * @param userid the value for profile_inbox.userid
     *
     * @mbggenerated
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_inbox.fromuserid
     *
     * @return the value of profile_inbox.fromuserid
     *
     * @mbggenerated
     */
    public Integer getFromuserid() {
        return fromuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_inbox.fromuserid
     *
     * @param fromuserid the value for profile_inbox.fromuserid
     *
     * @mbggenerated
     */
    public void setFromuserid(Integer fromuserid) {
        this.fromuserid = fromuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_inbox.status
     *
     * @return the value of profile_inbox.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_inbox.status
     *
     * @param status the value for profile_inbox.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_inbox.createtime
     *
     * @return the value of profile_inbox.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_inbox.createtime
     *
     * @param createtime the value for profile_inbox.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column profile_inbox.content
     *
     * @return the value of profile_inbox.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column profile_inbox.content
     *
     * @param content the value for profile_inbox.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }


    public int getOutboxid() {
        return outboxid;
    }

    public void setOutboxid(int outboxid) {
        this.outboxid = outboxid;
    }

    public Date getReadtime() {
        return readtime;
    }

    public void setReadtime(Date readtime) {
        this.readtime = readtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_inbox
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
        Inbox other = (Inbox) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getFromuserid() == null ? other.getFromuserid() == null : this.getFromuserid().equals(other.getFromuserid()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_inbox
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getFromuserid() == null) ? 0 : getFromuserid().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_inbox
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
        sb.append(", type=").append(type);
        sb.append(", userid=").append(userid);
        sb.append(", fromuserid=").append(fromuserid);
        sb.append(", status=").append(status);
        sb.append(", createtime=").append(createtime);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    private String fromUserName;

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }
}