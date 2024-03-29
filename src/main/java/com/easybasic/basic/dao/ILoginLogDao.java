package com.easybasic.basic.dao;

import com.easybasic.basic.model.LoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ILoginLogDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_loginlog
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_loginlog
     *
     * @mbggenerated
     */
    int insert(LoginLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_loginlog
     *
     * @mbggenerated
     */
    LoginLog selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_loginlog
     *
     * @mbggenerated
     */
    List<LoginLog> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_loginlog
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LoginLog record);
    List<LoginLog> selectListBySearch(@Param("searchStr")String searchStr, @Param("orderStr")String orderStr);
    int deleteByUnValidate();
}