package com.easybasic.kaoqin.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
public class OperatLog implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_operatlog.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_operatlog.type
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_operatlog.TableNames
     *
     * @mbggenerated
     */
    private String tablenames;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_operatlog.Code
     *
     * @mbggenerated
     */
    private Integer code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column kq_operatlog.OperatTime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operattime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table kq_operatlog
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_operatlog.pkid
     *
     * @return the value of kq_operatlog.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_operatlog.pkid
     *
     * @param pkid the value for kq_operatlog.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_operatlog.type
     *
     * @return the value of kq_operatlog.type
     *
     * @mbggenerated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_operatlog.type
     *
     * @param type the value for kq_operatlog.type
     *
     * @mbggenerated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_operatlog.TableNames
     *
     * @return the value of kq_operatlog.TableNames
     *
     * @mbggenerated
     */
    public String getTablenames() {
        return tablenames;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_operatlog.TableNames
     *
     * @param tablenames the value for kq_operatlog.TableNames
     *
     * @mbggenerated
     */
    public void setTablenames(String tablenames) {
        this.tablenames = tablenames == null ? null : tablenames.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_operatlog.Code
     *
     * @return the value of kq_operatlog.Code
     *
     * @mbggenerated
     */
    public Integer getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_operatlog.Code
     *
     * @param code the value for kq_operatlog.Code
     *
     * @mbggenerated
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column kq_operatlog.OperatTime
     *
     * @return the value of kq_operatlog.OperatTime
     *
     * @mbggenerated
     */
    public Date getOperattime() {
        return operattime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column kq_operatlog.OperatTime
     *
     * @param operattime the value for kq_operatlog.OperatTime
     *
     * @mbggenerated
     */
    public void setOperattime(Date operattime) {
        this.operattime = operattime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_operatlog
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
        OperatLog other = (OperatLog) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getTablenames() == null ? other.getTablenames() == null : this.getTablenames().equals(other.getTablenames()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getOperattime() == null ? other.getOperattime() == null : this.getOperattime().equals(other.getOperattime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_operatlog
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getTablenames() == null) ? 0 : getTablenames().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getOperattime() == null) ? 0 : getOperattime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_operatlog
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
        sb.append(", type=").append(type);
        sb.append(", tablenames=").append(tablenames);
        sb.append(", code=").append(code);
        sb.append(", operattime=").append(operattime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}