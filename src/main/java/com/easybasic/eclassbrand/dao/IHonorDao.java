package com.easybasic.eclassbrand.dao;

import com.easybasic.eclassbrand.model.Honor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IHonorDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_honor
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_honor
     *
     * @mbggenerated
     */
    int insert(Honor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_honor
     *
     * @mbggenerated
     */
    Honor selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_honor
     *
     * @mbggenerated
     */
    List<Honor> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_honor
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Honor record);
    List<Honor> selectListBySearch(@Param("searchStr")String searchStr, @Param("orderStr")String orderStr);
}