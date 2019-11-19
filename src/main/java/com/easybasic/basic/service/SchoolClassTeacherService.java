package com.easybasic.basic.service;

import com.easybasic.basic.dao.ISchoolClassTeacherDao;
import com.easybasic.basic.model.SchoolClassTeacher;
import com.easybasic.basic.model.SchoolClassUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schoolClassTeacherService")
public class SchoolClassTeacherService {

    private static Logger logger = LoggerFactory.getLogger(SchoolClassTeacherService.class);

    @Resource
    private ISchoolClassTeacherDao schoolClassTeacherDao;

    public SchoolClassTeacher getByPkId(Integer pkId)
    {
        try
        {
            return schoolClassTeacherDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“schoolClassTeacherService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(SchoolClassTeacher SchoolClassTeacher)
    {
        try {
            return schoolClassTeacherDao.insert(SchoolClassTeacher);
        }
        catch (Exception ex){
            logger.error("“schoolClassTeacherService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(SchoolClassTeacher SchoolClassTeacher)
    {
        try {
            int re = schoolClassTeacherDao.updateByPrimaryKey(SchoolClassTeacher);
            return re;
        }
        catch (Exception ex){
            logger.error("“schoolClassTeacherService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = schoolClassTeacherDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“schoolClassTeacherService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<SchoolClassTeacher> getAllList()
    {
        try {
            return schoolClassTeacherDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“schoolClassTeacherService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<SchoolClassTeacher> getTeacherListByUserId(int userId)
    {
        try {
            return schoolClassTeacherDao.selectTeacherListByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“schoolClassTeacherService”类执行方法“getTeacherListByUserId”错误", ex);
            throw ex;
        }
    }

    public SchoolClassTeacher getStudentByUserId(int userId)
    {
        try {
            return schoolClassTeacherDao.selectStudentByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“schoolClassTeacherService”类执行方法“getStudentByUserId”错误", ex);
            throw ex;
        }
    }

    public List<SchoolClassTeacher> selectTeacherByClassId(int classId)
    {
        try {
            return schoolClassTeacherDao.selectTeacherByClassId(classId);
        }
        catch (Exception ex)
        {
            logger.error("“schoolClassTeacherService”类执行方法“getStudentCountByClassId”错误", ex);
            throw ex;
        }
    }

}

