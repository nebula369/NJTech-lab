package com.easybasic.basic.dao;

import com.easybasic.basic.model.PurviewUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPurviewUserDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_purviewuser
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_purviewuser
     *
     * @mbggenerated
     */
    int insert(PurviewUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_purviewuser
     *
     * @mbggenerated
     */
    PurviewUser selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_purviewuser
     *
     * @mbggenerated
     */
    List<PurviewUser> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_purviewuser
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PurviewUser record);

    List<PurviewUser> selectListBySearch(@Param("searchStr")String searchStr, @Param("orderStr")String orderStr);
    PurviewUser selectByUserId(int userId);
}