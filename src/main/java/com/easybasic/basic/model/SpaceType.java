package com.easybasic.basic.model;

import java.io.Serializable;
import java.util.Date;

public class SpaceType implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_spacetype.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_spacetype.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_spacetype.sortnum
     *
     * @mbggenerated
     */
    private Integer sortnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_spacetype.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_spacetype.remark
     *
     * @mbggenerated
     */
    private String remark;

    private int unitid;

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table basic_spacetype
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_spacetype.pkid
     *
     * @return the value of basic_spacetype.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_spacetype.pkid
     *
     * @param pkid the value for basic_spacetype.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_spacetype.name
     *
     * @return the value of basic_spacetype.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_spacetype.name
     *
     * @param name the value for basic_spacetype.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_spacetype.sortnum
     *
     * @return the value of basic_spacetype.sortnum
     *
     * @mbggenerated
     */
    public Integer getSortnum() {
        return sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_spacetype.sortnum
     *
     * @param sortnum the value for basic_spacetype.sortnum
     *
     * @mbggenerated
     */
    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_spacetype.createtime
     *
     * @return the value of basic_spacetype.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_spacetype.createtime
     *
     * @param createtime the value for basic_spacetype.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_spacetype.remark
     *
     * @return the value of basic_spacetype.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_spacetype.remark
     *
     * @param remark the value for basic_spacetype.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_spacetype
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
        SpaceType other = (SpaceType) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSortnum() == null ? other.getSortnum() == null : this.getSortnum().equals(other.getSortnum()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_spacetype
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSortnum() == null) ? 0 : getSortnum().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_spacetype
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
        sb.append(", sortnum=").append(sortnum);
        sb.append(", createtime=").append(createtime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    private int spaceCount;

    public int getSpaceCount() {
        return spaceCount;
    }

    public void setSpaceCount(int spaceCount) {
        this.spaceCount = spaceCount;
    }
}