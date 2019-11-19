package com.easybasic.edu.dao;

import com.easybasic.edu.model.SchoolScheduleWeekTime;
import java.util.List;

public interface ISchoolScheduleWeekTimeDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table edu_schoolscheduleweektime
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table edu_schoolscheduleweektime
     *
     * @mbggenerated
     */
    int insert(SchoolScheduleWeekTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table edu_schoolscheduleweektime
     *
     * @mbggenerated
     */
    SchoolScheduleWeekTime selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table edu_schoolscheduleweektime
     *
     * @mbggenerated
     */
    List<SchoolScheduleWeekTime> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table edu_schoolscheduleweektime
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SchoolScheduleWeekTime record);
    List<SchoolScheduleWeekTime> selectListBySchedule(int scheduleId);
}