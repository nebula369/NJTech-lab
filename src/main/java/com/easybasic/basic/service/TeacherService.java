package com.easybasic.basic.service;

import com.easybasic.basic.dao.ITeacherDao;
import com.easybasic.basic.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("teacherService")
public class TeacherService {

    private static Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Resource
    private ITeacherDao teacherDao;

    public Teacher getByPkId(Integer pkId)
    {
        try
        {
            return teacherDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“TeacherService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Teacher teacher)
    {
        try {
            return teacherDao.insert(teacher);
        }
        catch (Exception ex){
            logger.error("“TeacherService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Teacher teacher)
    {
        try {
            int re = teacherDao.updateByPrimaryKey(teacher);
            return re;
        }
        catch (Exception ex){
            logger.error("“TeacherService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = teacherDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“TeacherService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Teacher> getAllList()
    {
        try {
            return teacherDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“TeacherService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public Teacher getByUserId(int userId)
    {
        try {
            return teacherDao.selectByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“TeacherService”类执行方法“getByUserId”错误", ex);
            throw ex;
        }
    }

    public List<Teacher> getListBySearch(String searchStr)
    {
        try {
            return teacherDao.selectListBySearch(searchStr);
        }
        catch (Exception ex)
        {
            logger.error("“TeacherService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}

