package com.easybasic.basic.service;

import com.easybasic.basic.dao.ISchoolClassUserDao;
import com.easybasic.basic.model.SchoolClassUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schoolClassUserService")
public class SchoolClassUserService {

    private static Logger logger = LoggerFactory.getLogger(SchoolClassUserService.class);

    @Resource
    private ISchoolClassUserDao schoolClassUserDao;

    public SchoolClassUser getByPkId(Integer pkId)
    {
        try
        {
            return schoolClassUserDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassUserService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(SchoolClassUser schoolClassUser)
    {
        try {
            return schoolClassUserDao.insert(schoolClassUser);
        }
        catch (Exception ex){
            logger.error("“SchoolClassUserService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(SchoolClassUser schoolClassUser)
    {
        try {
            int re = schoolClassUserDao.updateByPrimaryKey(schoolClassUser);
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolClassUserService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = schoolClassUserDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolClassUserService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<SchoolClassUser> getAllList()
    {
        try {
            return schoolClassUserDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassUserService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<SchoolClassUser> getTeacherListByUserId(int userId)
    {
        try {
            return schoolClassUserDao.selectTeacherListByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassUserService”类执行方法“getTeacherListByUserId”错误", ex);
            throw ex;
        }
    }

    public SchoolClassUser getStudentByUserId(int userId)
    {
        try {
            return schoolClassUserDao.selectStudentByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassUserService”类执行方法“getStudentByUserId”错误", ex);
            throw ex;
        }
    }

    public int getStudentCountByClassId(int classId)
    {
        try {
            return schoolClassUserDao.selectCountByClassId(classId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassUserService”类执行方法“getStudentCountByClassId”错误", ex);
            throw ex;
        }
    }

}

