package com.easybasic.eclassbrand.dao;

import com.easybasic.eclassbrand.model.ClassNews;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IClassNewsDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_classnews
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_classnews
     *
     * @mbggenerated
     */
    int insert(ClassNews record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_classnews
     *
     * @mbggenerated
     */
    ClassNews selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_classnews
     *
     * @mbggenerated
     */
    List<ClassNews> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ecb_classnews
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ClassNews record);
    List<ClassNews> selectListBySearch(@Param("searchStr")String searchStr, @Param("orderStr")String orderStr);
    ClassNews getByClassIdAndNewsId(int classid,int taskid);
}