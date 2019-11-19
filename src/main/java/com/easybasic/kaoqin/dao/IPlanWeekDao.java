package com.easybasic.kaoqin.dao;

import com.easybasic.kaoqin.model.PlanWeek;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IPlanWeekDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_planweek
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_planweek
     *
     * @mbggenerated
     */
    int insert(PlanWeek record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_planweek
     *
     * @mbggenerated
     */
    PlanWeek selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_planweek
     *
     * @mbggenerated
     */
    List<PlanWeek> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_planweek
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PlanWeek record);
    List<PlanWeek> selectListBySearch(@Param("searchStr")String searchStr, @Param("orderStr")String orderStr);
}