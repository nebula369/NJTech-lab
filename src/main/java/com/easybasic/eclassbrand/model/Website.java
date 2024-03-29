package com.easybasic.eclassbrand.model;

import java.io.Serializable;
import java.util.Date;

public class Website implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_website.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_website.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_website.url
     *
     * @mbggenerated
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_website.categoryId
     *
     * @mbggenerated
     */
    private Integer categoryid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_website.unitid
     *
     * @mbggenerated
     */
    private Integer unitid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_website.sortnum
     *
     * @mbggenerated
     */
    private Integer sortnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_website.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ecb_website
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_website.pkid
     *
     * @return the value of ecb_website.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_website.pkid
     *
     * @param pkid the value for ecb_website.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_website.name
     *
     * @return the value of ecb_website.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_website.name
     *
     * @param name the value for ecb_website.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_website.url
     *
     * @return the value of ecb_website.url
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_website.url
     *
     * @param url the value for ecb_website.url
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_website.categoryId
     *
     * @return the value of ecb_website.categoryId
     *
     * @mbggenerated
     */
    public Integer getCategoryid() {
        return categoryid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_website.categoryId
     *
     * @param categoryid the value for ecb_website.categoryId
     *
     * @mbggenerated
     */
    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_website.unitid
     *
     * @return the value of ecb_website.unitid
     *
     * @mbggenerated
     */
    public Integer getUnitid() {
        return unitid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_website.unitid
     *
     * @param unitid the value for ecb_website.unitid
     *
     * @mbggenerated
     */
    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_website.sortnum
     *
     * @return the value of ecb_website.sortnum
     *
     * @mbggenerated
     */
    public Integer getSortnum() {
        return sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_website.sortnum
     *
     * @param sortnum the value for ecb_website.sortnum
     *
     * @mbggenerated
     */
    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_website.createtime
     *
     * @return the value of ecb_website.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_website.createtime
     *
     * @param createtime the value for ecb_website.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_website
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
        Website other = (Website) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getCategoryid() == null ? other.getCategoryid() == null : this.getCategoryid().equals(other.getCategoryid()))
            && (this.getUnitid() == null ? other.getUnitid() == null : this.getUnitid().equals(other.getUnitid()))
            && (this.getSortnum() == null ? other.getSortnum() == null : this.getSortnum().equals(other.getSortnum()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_website
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getCategoryid() == null) ? 0 : getCategoryid().hashCode());
        result = prime * result + ((getUnitid() == null) ? 0 : getUnitid().hashCode());
        result = prime * result + ((getSortnum() == null) ? 0 : getSortnum().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_website
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
        sb.append(", url=").append(url);
        sb.append(", categoryid=").append(categoryid);
        sb.append(", unitid=").append(unitid);
        sb.append(", sortnum=").append(sortnum);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}