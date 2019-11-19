package com.easybasic.basic.dao;

import com.easybasic.basic.model.BasicDs;
import java.util.List;

public interface IBasicDsDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_ds
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_ds
     *
     * @mbggenerated
     */
    int insert(BasicDs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_ds
     *
     * @mbggenerated
     */
    BasicDs selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_ds
     *
     * @mbggenerated
     */
    List<BasicDs> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_ds
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BasicDs record);
}