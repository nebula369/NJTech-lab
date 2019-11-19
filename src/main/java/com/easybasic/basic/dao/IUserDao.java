package com.easybasic.basic.dao;

import com.easybasic.basic.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_user
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_user
     *
     * @mbggenerated
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_user
     *
     * @mbggenerated
     */
    User selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_user
     *
     * @mbggenerated
     */
    List<User> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_user
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(User record);
    User selectByLoginName(String loginName);
    List<User> selectListBySearch(@Param("searchStr")String searchStr, @Param("orderStr")String orderStr);
    User selectBySchoolIdAndName(int schoolId, String name);
    User selectByMobile(String mobile);
}