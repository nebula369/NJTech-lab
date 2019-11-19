package com.easybasic.basic.model;

import java.io.Serializable;

public class DataDicVal implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_datadicval.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_datadicval.dataid
     *
     * @mbggenerated
     */
    private Integer dataid;

    private String datacode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_datadicval.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_datadicval.code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column basic_datadicval.sortnum
     *
     * @mbggenerated
     */
    private Integer sortnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table basic_datadicval
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_datadicval.pkid
     *
     * @return the value of basic_datadicval.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_datadicval.pkid
     *
     * @param pkid the value for basic_datadicval.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_datadicval.dataid
     *
     * @return the value of basic_datadicval.dataid
     *
     * @mbggenerated
     */
    public Integer getDataid() {
        return dataid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_datadicval.dataid
     *
     * @param dataid the value for basic_datadicval.dataid
     *
     * @mbggenerated
     */
    public void setDataid(Integer dataid) {
        this.dataid = dataid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_datadicval.name
     *
     * @return the value of basic_datadicval.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_datadicval.name
     *
     * @param name the value for basic_datadicval.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_datadicval.code
     *
     * @return the value of basic_datadicval.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_datadicval.code
     *
     * @param code the value for basic_datadicval.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column basic_datadicval.sortnum
     *
     * @return the value of basic_datadicval.sortnum
     *
     * @mbggenerated
     */
    public Integer getSortnum() {
        return sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column basic_datadicval.sortnum
     *
     * @param sortnum the value for basic_datadicval.sortnum
     *
     * @mbggenerated
     */
    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    public String getDatacode() {
        return datacode;
    }

    public void setDatacode(String datacode) {
        this.datacode = datacode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_datadicval
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
        DataDicVal other = (DataDicVal) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getDataid() == null ? other.getDataid() == null : this.getDataid().equals(other.getDataid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getSortnum() == null ? other.getSortnum() == null : this.getSortnum().equals(other.getSortnum()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_datadicval
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getDataid() == null) ? 0 : getDataid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getSortnum() == null) ? 0 : getSortnum().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_datadicval
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
        sb.append(", dataid=").append(dataid);
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", sortnum=").append(sortnum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}