package com.easybasic.basic.service;

import com.easybasic.basic.dao.IStudentDao;
import com.easybasic.basic.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("studentService")
public class StudentService {

    private static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Resource
    private IStudentDao studentDao;

    public Student getByPkId(Integer pkId)
    {
        try
        {
            return studentDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“StudentService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Student student)
    {
        try {
            return studentDao.insert(student);
        }
        catch (Exception ex){
            logger.error("“StudentService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Student student)
    {
        try {
            int re = studentDao.updateByPrimaryKey(student);
            return re;
        }
        catch (Exception ex){
            logger.error("“StudentService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = studentDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“StudentService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Student> getAllList()
    {
        try {
            return studentDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“StudentService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public Student getByUserId(int userId)
    {
        try {
            return studentDao.selectByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“StudentService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}

