package com.easybasic.basic.dao;

import com.easybasic.basic.model.DeptUser;
import java.util.List;

public interface IDeptUserDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_deptuser
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_deptuser
     *
     * @mbggenerated
     */
    int insert(DeptUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_deptuser
     *
     * @mbggenerated
     */
    DeptUser selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_deptuser
     *
     * @mbggenerated
     */
    List<DeptUser> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_deptuser
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DeptUser record);
    int selectCountByDeptId(int deptId);
    DeptUser selectByDeptIdAndUserId(int deptId, int userId);
    List<DeptUser> selectListByUserId(int userId);
}