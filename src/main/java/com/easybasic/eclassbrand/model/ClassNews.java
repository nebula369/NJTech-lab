package com.easybasic.eclassbrand.model;

import java.io.Serializable;

public class ClassNews implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_classnews.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_classnews.classid
     *
     * @mbggenerated
     */
    private Integer classid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ecb_classnews.newsid
     *
     * @mbggenerated
     */
    private Integer newsid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ecb_classnews
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_classnews.pkid
     *
     * @return the value of ecb_classnews.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_classnews.pkid
     *
     * @param pkid the value for ecb_classnews.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_classnews.classid
     *
     * @return the value of ecb_classnews.classid
     *
     * @mbggenerated
     */
    public Integer getClassid() {
        return classid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_classnews.classid
     *
     * @param classid the value for ecb_classnews.classid
     *
     * @mbggenerated
     */
    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ecb_classnews.newsid
     *
     * @return the value of ecb_classnews.newsid
     *
     * @mbggenerated
     */
    public Integer getNewsid() {
        return newsid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ecb_classnews.newsid
     *
     * @param newsid the value for ecb_classnews.newsid
     *
     * @mbggenerated
     */
    public void setNewsid(Integer newsid) {
        this.newsid = newsid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_classnews
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
        ClassNews other = (ClassNews) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getClassid() == null ? other.getClassid() == null : this.getClassid().equals(other.getClassid()))
            && (this.getNewsid() == null ? other.getNewsid() == null : this.getNewsid().equals(other.getNewsid()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_classnews
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getClassid() == null) ? 0 : getClassid().hashCode());
        result = prime * result + ((getNewsid() == null) ? 0 : getNewsid().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_classnews
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
        sb.append(", newsid=").append(newsid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}