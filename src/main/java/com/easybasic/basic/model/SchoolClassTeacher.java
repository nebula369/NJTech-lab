package com.easybasic.basic.model;

import java.io.Serializable;

public class SchoolClassTeacher implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_schoolclassteacher.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_schoolclassteacher.classid
     *
     * @mbggenerated
     */
    private Integer classid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_schoolclassteacher.teacherid
     *
     * @mbggenerated
     */
    private Integer teacherid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table basic_schoolclassteacher
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_schoolclassteacher.pkid
     *
     * @return the value of basic_schoolclassteacher.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_schoolclassteacher.pkid
     *
     * @param pkid the value for basic_schoolclassteacher.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_schoolclassteacher.classid
     *
     * @return the value of basic_schoolclassteacher.classid
     *
     * @mbggenerated
     */
    public Integer getClassid() {
        return classid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_schoolclassteacher.classid
     *
     * @param classid the value for basic_schoolclassteacher.classid
     *
     * @mbggenerated
     */
    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_schoolclassteacher.teacherid
     *
     * @return the value of basic_schoolclassteacher.teacherid
     *
     * @mbggenerated
     */
    public Integer getTeacherid() {
        return teacherid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_schoolclassteacher.teacherid
     *
     * @param teacherid the value for basic_schoolclassteacher.teacherid
     *
     * @mbggenerated
     */
    public void setTeacherid(Integer teacherid) {
        this.teacherid = teacherid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_schoolclassteacher
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
        SchoolClassTeacher other = (SchoolClassTeacher) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getClassid() == null ? other.getClassid() == null : this.getClassid().equals(other.getClassid()))
            && (this.getTeacherid() == null ? other.getTeacherid() == null : this.getTeacherid().equals(other.getTeacherid()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_schoolclassteacher
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getClassid() == null) ? 0 : getClassid().hashCode());
        result = prime * result + ((getTeacherid() == null) ? 0 : getTeacherid().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_schoolclassteacher
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
        sb.append(", classid=").append(classid);
        sb.append(", teacherid=").append(teacherid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}