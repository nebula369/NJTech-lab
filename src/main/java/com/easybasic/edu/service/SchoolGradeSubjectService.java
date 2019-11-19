package com.easybasic.edu.service;

import com.easybasic.edu.dao.ISchoolGradeSubjectDao;
import com.easybasic.edu.model.SchoolGradeSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schoolGradeSubjectService")
public class SchoolGradeSubjectService {

    private static Logger logger = LoggerFactory.getLogger(SchoolGradeSubjectService.class);

    @Resource
    private ISchoolGradeSubjectDao schoolGradeSubjectDao;

    public SchoolGradeSubject getByPkId(Integer pkId)
    {
        try
        {
            return schoolGradeSubjectDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolGradeSubjectService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(SchoolGradeSubject schoolGradeSubject)
    {
        try {
            int re = schoolGradeSubjectDao.insert(schoolGradeSubject);
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolGradeSubjectService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(SchoolGradeSubject schoolGradeSubject)
    {
        try {
            int re = schoolGradeSubjectDao.updateByPrimaryKey(schoolGradeSubject);
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolGradeSubjectService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            SchoolGradeSubject schoolGradeSubject = getByPkId(pkId);
            if(schoolGradeSubject!=null) {
                int re = schoolGradeSubjectDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“SchoolGradeSubjectService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<SchoolGradeSubject> getAllList()
    {
        try {
            return schoolGradeSubjectDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“SchoolGradeSubjectService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

}

