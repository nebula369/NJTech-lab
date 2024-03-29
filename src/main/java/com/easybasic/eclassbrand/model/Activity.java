package com.easybasic.eclassbrand.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.pkId
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.author
     *
     * @mbggenerated
     */
    private String author;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.placeId
     *
     * @mbggenerated
     */
    private Integer placeid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.createtime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.starttime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date starttime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.endtime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.intro
     *
     * @mbggenerated
     */
    private String intro;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.activitytype
     *
     * @mbggenerated
     */
    private Integer activitytype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.sortnum
     *
     * @mbggenerated
     */
    private Integer sortnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.photo
     *
     * @mbggenerated
     */
    private String photo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_activity.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ecb_activity
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.pkId
     *
     * @return the value of ecb_activity.pkId
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.pkId
     *
     * @param pkid the value for ecb_activity.pkId
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.author
     *
     * @return the value of ecb_activity.author
     *
     * @mbggenerated
     */
    public String getAuthor() {
        return author;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.author
     *
     * @param author the value for ecb_activity.author
     *
     * @mbggenerated
     */
    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.title
     *
     * @return the value of ecb_activity.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.title
     *
     * @param title the value for ecb_activity.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.placeId
     *
     * @return the value of ecb_activity.placeId
     *
     * @mbggenerated
     */
    public Integer getPlaceid() {
        return placeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.placeId
     *
     * @param placeid the value for ecb_activity.placeId
     *
     * @mbggenerated
     */
    public void setPlaceid(Integer placeid) {
        this.placeid = placeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.createtime
     *
     * @return the value of ecb_activity.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.createtime
     *
     * @param createtime the value for ecb_activity.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.starttime
     *
     * @return the value of ecb_activity.starttime
     *
     * @mbggenerated
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.starttime
     *
     * @param starttime the value for ecb_activity.starttime
     *
     * @mbggenerated
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.endtime
     *
     * @return the value of ecb_activity.endtime
     *
     * @mbggenerated
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.endtime
     *
     * @param endtime the value for ecb_activity.endtime
     *
     * @mbggenerated
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.intro
     *
     * @return the value of ecb_activity.intro
     *
     * @mbggenerated
     */
    public String getIntro() {
        return intro;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.intro
     *
     * @param intro the value for ecb_activity.intro
     *
     * @mbggenerated
     */
    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.activitytype
     *
     * @return the value of ecb_activity.activitytype
     *
     * @mbggenerated
     */
    public Integer getActivitytype() {
        return activitytype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.activitytype
     *
     * @param activitytype the value for ecb_activity.activitytype
     *
     * @mbggenerated
     */
    public void setActivitytype(Integer activitytype) {
        this.activitytype = activitytype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.sortnum
     *
     * @return the value of ecb_activity.sortnum
     *
     * @mbggenerated
     */
    public Integer getSortnum() {
        return sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.sortnum
     *
     * @param sortnum the value for ecb_activity.sortnum
     *
     * @mbggenerated
     */
    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.photo
     *
     * @return the value of ecb_activity.photo
     *
     * @mbggenerated
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.photo
     *
     * @param photo the value for ecb_activity.photo
     *
     * @mbggenerated
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_activity.content
     *
     * @return the value of ecb_activity.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_activity.content
     *
     * @param content the value for ecb_activity.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_activity
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
        Activity other = (Activity) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getPlaceid() == null ? other.getPlaceid() == null : this.getPlaceid().equals(other.getPlaceid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getStarttime() == null ? other.getStarttime() == null : this.getStarttime().equals(other.getStarttime()))
            && (this.getEndtime() == null ? other.getEndtime() == null : this.getEndtime().equals(other.getEndtime()))
            && (this.getIntro() == null ? other.getIntro() == null : this.getIntro().equals(other.getIntro()))
            && (this.getActivitytype() == null ? other.getActivitytype() == null : this.getActivitytype().equals(other.getActivitytype()))
            && (this.getSortnum() == null ? other.getSortnum() == null : this.getSortnum().equals(other.getSortnum()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_activity
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getPlaceid() == null) ? 0 : getPlaceid().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getStarttime() == null) ? 0 : getStarttime().hashCode());
        result = prime * result + ((getEndtime() == null) ? 0 : getEndtime().hashCode());
        result = prime * result + ((getIntro() == null) ? 0 : getIntro().hashCode());
        result = prime * result + ((getActivitytype() == null) ? 0 : getActivitytype().hashCode());
        result = prime * result + ((getSortnum() == null) ? 0 : getSortnum().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_activity
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
        sb.append(", author=").append(author);
        sb.append(", title=").append(title);
        sb.append(", placeid=").append(placeid);
        sb.append(", createtime=").append(createtime);
        sb.append(", starttime=").append(starttime);
        sb.append(", endtime=").append(endtime);
        sb.append(", intro=").append(intro);
        sb.append(", activitytype=").append(activitytype);
        sb.append(", sortnum=").append(sortnum);
        sb.append(", photo=").append(photo);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
}