package com.easybasic.basic.dao;

import com.easybasic.basic.model.Unit;
import java.util.List;

public interface IUnitDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_unit
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_unit
     *
     * @mbggenerated
     */
    int insert(Unit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_unit
     *
     * @mbggenerated
     */
    Unit selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_unit
     *
     * @mbggenerated
     */
    List<Unit> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_unit
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Unit record);
    Unit selectByManageUser(int manageUser);
}