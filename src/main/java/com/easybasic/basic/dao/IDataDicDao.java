package com.easybasic.basic.dao;

import com.easybasic.basic.model.DataDic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IDataDicDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_datadic
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_datadic
     *
     * @mbggenerated
     */
    int insert(DataDic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_datadic
     *
     * @mbggenerated
     */
    DataDic selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_datadic
     *
     * @mbggenerated
     */
    List<DataDic> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_datadic
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DataDic record);
    DataDic selectByCode(String code);
    List<DataDic> selectListBySearch(@Param("searchStr")String searchStr, @Param("orderStr")String orderStr);
}