package com.easybasic.eclassbrand.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class ExamPlan implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.pkid
     *
     * @mbggenerated
     */
    private Integer pkid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.examroomid
     *
     * @mbggenerated
     */
    private Integer examroomid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.starttime
     *
     * @mbggenerated
     */

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.endtime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.subject
     *
     * @mbggenerated
     */
    private String subject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.grade
     *
     * @mbggenerated
     */
    private String grade;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.examiner
     *
     * @mbggenerated
     */
    private String examiner;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.usercount
     *
     * @mbggenerated
     */
    private Integer usercount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.ticket
     *
     * @mbggenerated
     */
    private String ticket;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.userid
     *
     * @mbggenerated
     */
    private Integer userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.sortnum
     *
     * @mbggenerated
     */
    private Integer sortnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.createtime
     *
     * @mbggenerated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam_examplan.isimport
     *
     * @mbggenerated
     */
    private Integer isimport;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table exam_examplan
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.pkid
     *
     * @return the value of exam_examplan.pkid
     *
     * @mbggenerated
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.pkid
     *
     * @param pkid the value for exam_examplan.pkid
     *
     * @mbggenerated
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.examroomid
     *
     * @return the value of exam_examplan.examroomid
     *
     * @mbggenerated
     */
    public Integer getExamroomid() {
        return examroomid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.examroomid
     *
     * @param examroomid the value for exam_examplan.examroomid
     *
     * @mbggenerated
     */
    public void setExamroomid(Integer examroomid) {
        this.examroomid = examroomid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.starttime
     *
     * @return the value of exam_examplan.starttime
     *
     * @mbggenerated
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.starttime
     *
     * @param starttime the value for exam_examplan.starttime
     *
     * @mbggenerated
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.endtime
     *
     * @return the value of exam_examplan.endtime
     *
     * @mbggenerated
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.endtime
     *
     * @param endtime the value for exam_examplan.endtime
     *
     * @mbggenerated
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.subject
     *
     * @return the value of exam_examplan.subject
     *
     * @mbggenerated
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.subject
     *
     * @param subject the value for exam_examplan.subject
     *
     * @mbggenerated
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.grade
     *
     * @return the value of exam_examplan.grade
     *
     * @mbggenerated
     */
    public String getGrade() {
        return grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.grade
     *
     * @param grade the value for exam_examplan.grade
     *
     * @mbggenerated
     */
    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.examiner
     *
     * @return the value of exam_examplan.examiner
     *
     * @mbggenerated
     */
    public String getExaminer() {
        return examiner;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.examiner
     *
     * @param examiner the value for exam_examplan.examiner
     *
     * @mbggenerated
     */
    public void setExaminer(String examiner) {
        this.examiner = examiner == null ? null : examiner.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.usercount
     *
     * @return the value of exam_examplan.usercount
     *
     * @mbggenerated
     */
    public Integer getUsercount() {
        return usercount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.usercount
     *
     * @param usercount the value for exam_examplan.usercount
     *
     * @mbggenerated
     */
    public void setUsercount(Integer usercount) {
        this.usercount = usercount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.ticket
     *
     * @return the value of exam_examplan.ticket
     *
     * @mbggenerated
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.ticket
     *
     * @param ticket the value for exam_examplan.ticket
     *
     * @mbggenerated
     */
    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.userid
     *
     * @return the value of exam_examplan.userid
     *
     * @mbggenerated
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.userid
     *
     * @param userid the value for exam_examplan.userid
     *
     * @mbggenerated
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.sortnum
     *
     * @return the value of exam_examplan.sortnum
     *
     * @mbggenerated
     */
    public Integer getSortnum() {
        return sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.sortnum
     *
     * @param sortnum the value for exam_examplan.sortnum
     *
     * @mbggenerated
     */
    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.createtime
     *
     * @return the value of exam_examplan.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.createtime
     *
     * @param createtime the value for exam_examplan.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam_examplan.isimport
     *
     * @return the value of exam_examplan.isimport
     *
     * @mbggenerated
     */
    public Integer getIsimport() {
        return isimport;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam_examplan.isimport
     *
     * @param isimport the value for exam_examplan.isimport
     *
     * @mbggenerated
     */
    public void setIsimport(Integer isimport) {
        this.isimport = isimport;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_examplan
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
        ExamPlan other = (ExamPlan) that;
        return (this.getPkid() == null ? other.getPkid() == null : this.getPkid().equals(other.getPkid()))
            && (this.getExamroomid() == null ? other.getExamroomid() == null : this.getExamroomid().equals(other.getExamroomid()))
            && (this.getStarttime() == null ? other.getStarttime() == null : this.getStarttime().equals(other.getStarttime()))
            && (this.getEndtime() == null ? other.getEndtime() == null : this.getEndtime().equals(other.getEndtime()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getGrade() == null ? other.getGrade() == null : this.getGrade().equals(other.getGrade()))
            && (this.getExaminer() == null ? other.getExaminer() == null : this.getExaminer().equals(other.getExaminer()))
            && (this.getUsercount() == null ? other.getUsercount() == null : this.getUsercount().equals(other.getUsercount()))
            && (this.getTicket() == null ? other.getTicket() == null : this.getTicket().equals(other.getTicket()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getSortnum() == null ? other.getSortnum() == null : this.getSortnum().equals(other.getSortnum()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getIsimport() == null ? other.getIsimport() == null : this.getIsimport().equals(other.getIsimport()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_examplan
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPkid() == null) ? 0 : getPkid().hashCode());
        result = prime * result + ((getExamroomid() == null) ? 0 : getExamroomid().hashCode());
        result = prime * result + ((getStarttime() == null) ? 0 : getStarttime().hashCode());
        result = prime * result + ((getEndtime() == null) ? 0 : getEndtime().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getGrade() == null) ? 0 : getGrade().hashCode());
        result = prime * result + ((getExaminer() == null) ? 0 : getExaminer().hashCode());
        result = prime * result + ((getUsercount() == null) ? 0 : getUsercount().hashCode());
        result = prime * result + ((getTicket() == null) ? 0 : getTicket().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getSortnum() == null) ? 0 : getSortnum().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getIsimport() == null) ? 0 : getIsimport().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_examplan
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
        sb.append(", examroomid=").append(examroomid);
        sb.append(", starttime=").append(starttime);
        sb.append(", endtime=").append(endtime);
        sb.append(", subject=").append(subject);
        sb.append(", grade=").append(grade);
        sb.append(", examiner=").append(examiner);
        sb.append(", usercount=").append(usercount);
        sb.append(", ticket=").append(ticket);
        sb.append(", userid=").append(userid);
        sb.append(", sortnum=").append(sortnum);
        sb.append(", createtime=").append(createtime);
        sb.append(", isimport=").append(isimport);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
   //考试日期
    private String examDate;
    public String getExamDate() {
        return examDate;
    }
    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }
    //考试时间
    private String examTime;
    public String getExamTime() {
        return examTime;
    }
    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }
    //考试考场名称
    private String examRoomName;
    public String getExamRoomName() {
        return examRoomName;
    }
    public void setExamRoomName(String examRoomName) {
        this.examRoomName = examRoomName;
    }
}