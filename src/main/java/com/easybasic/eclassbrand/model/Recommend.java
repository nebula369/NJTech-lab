package com.easybasic.eclassbrand.model;

import java.io.Serializable;
import java.util.Date;

public class Recommend implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_recommend.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_recommend.categoryid
     *
     * @mbggenerated
     */
    private Integer categoryid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_recommend.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_recommend.picture
     *
     * @mbggenerated
     */
    private String picture;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_recommend.reason
     *
     * @mbggenerated
     */
    private String reason;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_recommend.buyurl
     *
     * @mbggenerated
     */
    private String buyurl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_recommend.userid
     *
     * @mbggenerated
     */
    private Integer userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_recommend.sortnum
     *
     * @mbggenerated
     */
    private Integer sortnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_recommend.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_recommend.detail
     *
     * @mbggenerated
     */
    private String detail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ecb_recommend
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_recommend.pkid
     *
     * @return the value of ecb_recommend.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_recommend.pkid
     *
     * @param pkid the value for ecb_recommend.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_recommend.categoryid
     *
     * @return the value of ecb_recommend.categoryid
     *
     * @mbggenerated
     */
    public Integer getCategoryid() {
        return categoryid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_recommend.categoryid
     *
     * @param categoryid the value for ecb_recommend.categoryid
     *
     * @mbggenerated
     */
    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_recommend.name
     *
     * @return the value of ecb_recommend.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_recommend.name
     *
     * @param name the value for ecb_recommend.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_recommend.picture
     *
     * @return the value of ecb_recommend.picture
     *
     * @mbggenerated
     */
    public String getPicture() {
        return picture;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_recommend.picture
     *
     * @param picture the value for ecb_recommend.picture
     *
     * @mbggenerated
     */
    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_recommend.reason
     *
     * @return the value of ecb_recommend.reason
     *
     * @mbggenerated
     */
    public String getReason() {
        return reason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_recommend.reason
     *
     * @param reason the value for ecb_recommend.reason
     *
     * @mbggenerated
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_recommend.buyurl
     *
     * @return the value of ecb_recommend.buyurl
     *
     * @mbggenerated
     */
    public String getBuyurl() {
        return buyurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_recommend.buyurl
     *
     * @param buyurl the value for ecb_recommend.buyurl
     *
     * @mbggenerated
     */
    public void setBuyurl(String buyurl) {
        this.buyurl = buyurl == null ? null : buyurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_recommend.userid
     *
     * @return the value of ecb_recommend.userid
     *
     * @mbggenerated
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_recommend.userid
     *
     * @param userid the value for ecb_recommend.userid
     *
     * @mbggenerated
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_recommend.sortnum
     *
     * @return the value of ecb_recommend.sortnum
     *
     * @mbggenerated
     */
    public Integer getSortnum() {
        return sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_recommend.sortnum
     *
     * @param sortnum the value for ecb_recommend.sortnum
     *
     * @mbggenerated
     */
    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_recommend.createtime
     *
     * @return the value of ecb_recommend.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_recommend.createtime
     *
     * @param createtime the value for ecb_recommend.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_recommend.detail
     *
     * @return the value of ecb_recommend.detail
     *
     * @mbggenerated
     */
    public String getDetail() {
        return detail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_recommend.detail
     *
     * @param detail the value for ecb_recommend.detail
     *
     * @mbggenerated
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_recommend
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
        Recommend other = (Recommend) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getCategoryid() == null ? other.getCategoryid() == null : this.getCategoryid().equals(other.getCategoryid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPicture() == null ? other.getPicture() == null : this.getPicture().equals(other.getPicture()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getBuyurl() == null ? other.getBuyurl() == null : this.getBuyurl().equals(other.getBuyurl()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getSortnum() == null ? other.getSortnum() == null : this.getSortnum().equals(other.getSortnum()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getDetail() == null ? other.getDetail() == null : this.getDetail().equals(other.getDetail()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_recommend
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getCategoryid() == null) ? 0 : getCategoryid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPicture() == null) ? 0 : getPicture().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getBuyurl() == null) ? 0 : getBuyurl().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getSortnum() == null) ? 0 : getSortnum().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getDetail() == null) ? 0 : getDetail().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_recommend
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
        sb.append(", categoryid=").append(categoryid);
        sb.append(", name=").append(name);
        sb.append(", picture=").append(picture);
        sb.append(", reason=").append(reason);
        sb.append(", buyurl=").append(buyurl);
        sb.append(", userid=").append(userid);
        sb.append(", sortnum=").append(sortnum);
        sb.append(", createtime=").append(createtime);
        sb.append(", detail=").append(detail);
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

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}