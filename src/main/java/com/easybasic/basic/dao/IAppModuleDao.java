package com.easybasic.basic.dao;

import com.easybasic.basic.model.AppModule;
import java.util.List;

public interface IAppModuleDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_appmodule
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_appmodule
     *
     * @mbggenerated
     */
    int insert(AppModule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_appmodule
     *
     * @mbggenerated
     */
    AppModule selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_appmodule
     *
     * @mbggenerated
     */
    List<AppModule> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table basic_appmodule
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AppModule record);

    AppModule selectByCode(String code);
}