package com.easybasic.basic.model;

import java.io.Serializable;
import java.util.Date;

public class AppModule implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.apptype
     *
     * @mbggenerated
     */
    private Integer apptype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.link
     *
     * @mbggenerated
     */
    private String link;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.linktarget
     *
     * @mbggenerated
     */
    private Integer linktarget;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.smallicon
     *
     * @mbggenerated
     */
    private String smallicon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.bigicon
     *
     * @mbggenerated
     */
    private String bigicon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.sortnum
     *
     * @mbggenerated
     */
    private Integer sortnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_appmodule.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * 0为管理员默认App；1为单位管理员默认App；2为普通用户默认App
     */
    private int usetype;

    public int getUsetype() {
        return usetype;
    }

    public void setUsetype(int usetype) {
        this.usetype = usetype;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table basic_appmodule
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.pkid
     *
     * @return the value of basic_appmodule.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.pkid
     *
     * @param pkid the value for basic_appmodule.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.name
     *
     * @return the value of basic_appmodule.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.name
     *
     * @param name the value for basic_appmodule.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.code
     *
     * @return the value of basic_appmodule.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.code
     *
     * @param code the value for basic_appmodule.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.apptype
     *
     * @return the value of basic_appmodule.apptype
     *
     * @mbggenerated
     */
    public Integer getApptype() {
        return apptype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.apptype
     *
     * @param apptype the value for basic_appmodule.apptype
     *
     * @mbggenerated
     */
    public void setApptype(Integer apptype) {
        this.apptype = apptype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.link
     *
     * @return the value of basic_appmodule.link
     *
     * @mbggenerated
     */
    public String getLink() {
        return link;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.link
     *
     * @param link the value for basic_appmodule.link
     *
     * @mbggenerated
     */
    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.linktarget
     *
     * @return the value of basic_appmodule.linktarget
     *
     * @mbggenerated
     */
    public Integer getLinktarget() {
        return linktarget;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.linktarget
     *
     * @param linktarget the value for basic_appmodule.linktarget
     *
     * @mbggenerated
     */
    public void setLinktarget(Integer linktarget) {
        this.linktarget = linktarget;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.smallicon
     *
     * @return the value of basic_appmodule.smallicon
     *
     * @mbggenerated
     */
    public String getSmallicon() {
        return smallicon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.smallicon
     *
     * @param smallicon the value for basic_appmodule.smallicon
     *
     * @mbggenerated
     */
    public void setSmallicon(String smallicon) {
        this.smallicon = smallicon == null ? null : smallicon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.bigicon
     *
     * @return the value of basic_appmodule.bigicon
     *
     * @mbggenerated
     */
    public String getBigicon() {
        return bigicon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.bigicon
     *
     * @param bigicon the value for basic_appmodule.bigicon
     *
     * @mbggenerated
     */
    public void setBigicon(String bigicon) {
        this.bigicon = bigicon == null ? null : bigicon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.sortnum
     *
     * @return the value of basic_appmodule.sortnum
     *
     * @mbggenerated
     */
    public Integer getSortnum() {
        return sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.sortnum
     *
     * @param sortnum the value for basic_appmodule.sortnum
     *
     * @mbggenerated
     */
    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.status
     *
     * @return the value of basic_appmodule.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.status
     *
     * @param status the value for basic_appmodule.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_appmodule.createtime
     *
     * @return the value of basic_appmodule.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_appmodule.createtime
     *
     * @param createtime the value for basic_appmodule.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_appmodule
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
        AppModule other = (AppModule) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getApptype() == null ? other.getApptype() == null : this.getApptype().equals(other.getApptype()))
            && (this.getLink() == null ? other.getLink() == null : this.getLink().equals(other.getLink()))
            && (this.getLinktarget() == null ? other.getLinktarget() == null : this.getLinktarget().equals(other.getLinktarget()))
            && (this.getSmallicon() == null ? other.getSmallicon() == null : this.getSmallicon().equals(other.getSmallicon()))
            && (this.getBigicon() == null ? other.getBigicon() == null : this.getBigicon().equals(other.getBigicon()))
            && (this.getSortnum() == null ? other.getSortnum() == null : this.getSortnum().equals(other.getSortnum()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_appmodule
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getApptype() == null) ? 0 : getApptype().hashCode());
        result = prime * result + ((getLink() == null) ? 0 : getLink().hashCode());
        result = prime * result + ((getLinktarget() == null) ? 0 : getLinktarget().hashCode());
        result = prime * result + ((getSmallicon() == null) ? 0 : getSmallicon().hashCode());
        result = prime * result + ((getBigicon() == null) ? 0 : getBigicon().hashCode());
        result = prime * result + ((getSortnum() == null) ? 0 : getSortnum().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_appmodule
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
        sb.append(", code=").append(code);
        sb.append(", apptype=").append(apptype);
        sb.append(", link=").append(link);
        sb.append(", linktarget=").append(linktarget);
        sb.append(", smallicon=").append(smallicon);
        sb.append(", bigicon=").append(bigicon);
        sb.append(", sortnum=").append(sortnum);
        sb.append(", status=").append(status);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}