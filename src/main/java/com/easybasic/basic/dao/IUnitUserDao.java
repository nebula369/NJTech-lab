package com.easybasic.basic.dao;

import com.easybasic.basic.model.UnitUser;
import java.util.List;

public interface IUnitUserDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_unituser
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_unituser
     *
     * @mbggenerated
     */
    int insert(UnitUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_unituser
     *
     * @mbggenerated
     */
    UnitUser selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_unituser
     *
     * @mbggenerated
     */
    List<UnitUser> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_unituser
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UnitUser record);
    UnitUser selectByUnitIdAndUserId(int unitId, int userId);
    List<UnitUser> selectListByUserId(int userId);
}