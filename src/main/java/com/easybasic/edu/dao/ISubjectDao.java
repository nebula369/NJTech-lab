package com.easybasic.edu.dao;

import com.easybasic.edu.model.Subject;
import java.util.List;

public interface ISubjectDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table edu_subject
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table edu_subject
     *
     * @mbggenerated
     */
    int insert(Subject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table edu_subject
     *
     * @mbggenerated
     */
    Subject selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table edu_subject
     *
     * @mbggenerated
     */
    List<Subject> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table edu_subject
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Subject record);
    Subject selectByCode(int code);
}