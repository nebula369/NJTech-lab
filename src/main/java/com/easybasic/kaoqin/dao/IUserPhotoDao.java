package com.easybasic.kaoqin.dao;

import com.easybasic.kaoqin.model.UserPhoto;
import java.util.List;

public interface IUserPhotoDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_userphoto
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_userphoto
     *
     * @mbggenerated
     */
    int insert(UserPhoto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_userphoto
     *
     * @mbggenerated
     */
    UserPhoto selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_userphoto
     *
     * @mbggenerated
     */
    List<UserPhoto> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table kq_userphoto
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserPhoto record);
    UserPhoto selectByUserId(int userId);
}