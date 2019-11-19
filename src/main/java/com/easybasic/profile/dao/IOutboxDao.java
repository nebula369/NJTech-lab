package com.easybasic.profile.dao;

import com.easybasic.profile.model.Outbox;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOutboxDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_outbox
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_outbox
     *
     * @mbggenerated
     */
    int insert(Outbox record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_outbox
     *
     * @mbggenerated
     */
    Outbox selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_outbox
     *
     * @mbggenerated
     */
    List<Outbox> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profile_outbox
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Outbox record);
    List<Outbox> selectListBySearch(@Param("searchStr")String searchStr, @Param("orderStr")String orderStr);
}