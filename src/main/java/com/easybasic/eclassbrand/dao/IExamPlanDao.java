package com.easybasic.eclassbrand.dao;

import com.easybasic.eclassbrand.model.ExamPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IExamPlanDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_examplan
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_examplan
     *
     * @mbggenerated
     */
    int insert(ExamPlan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_examplan
     *
     * @mbggenerated
     */
    ExamPlan selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_examplan
     *
     * @mbggenerated
     */
    List<ExamPlan> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table exam_examplan
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ExamPlan record);
    List<ExamPlan> selectListBySearch(@Param("searchStr")String searchStr, @Param("orderStr")String orderStr);
}