package com.easybasic.edu.service;

import com.easybasic.component.Utils.RedisCache;
import com.easybasic.edu.dao.ISchoolClassCurriculumDao;
import com.easybasic.edu.model.SchoolClassCurriculum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schoolClassCurriculumService")
public class SchoolClassCurriculumService {

    private static Logger logger = LoggerFactory.getLogger(SchoolClassCurriculumService.class);

    @Resource
    private ISchoolClassCurriculumDao schoolClassCurriculumDao;

    public List<SchoolClassCurriculum> getSchoolClassCurriculumListForCache(int schoolId, int classId)
    {
        String key = RedisCache.CachePrex + "_getSchoolClassCurriculumListForCache_" + schoolId +"_" + classId;
        List<SchoolClassCurriculum> data = (List<SchoolClassCurriculum>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getListBySchoolIdAndClassId(schoolId, classId);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public void clearSchoolClassCurriculumListCache(int schoolId, int classId)
    {
        String key = RedisCache.CachePrex + "_getSchoolClassCurriculumListForCache_" + schoolId +"_" + classId;
        RedisCache.getInstance().removeObject(key);
    }

    public SchoolClassCurriculum getByPkId(Integer pkId)
    {
        try
        {
            return schoolClassCurriculumDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassCurriculumService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(SchoolClassCurriculum schoolClassCurriculum)
    {
        try {
            int re = schoolClassCurriculumDao.insert(schoolClassCurriculum);
            clearSchoolClassCurriculumListCache(schoolClassCurriculum.getSchoolid(), schoolClassCurriculum.getClassid());
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolClassCurriculumService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(SchoolClassCurriculum schoolClassCurriculum)
    {
        try {
            int re = schoolClassCurriculumDao.updateByPrimaryKey(schoolClassCurriculum);
            clearSchoolClassCurriculumListCache(schoolClassCurriculum.getSchoolid(), schoolClassCurriculum.getClassid());
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolClassCurriculumService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            SchoolClassCurriculum schoolClassCurriculum = getByPkId(pkId);
            if(schoolClassCurriculum!=null) {
                int re = schoolClassCurriculumDao.deleteByPrimaryKey(pkId);
                clearSchoolClassCurriculumListCache(schoolClassCurriculum.getSchoolid(), schoolClassCurriculum.getClassid());
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“SchoolClassCurriculumService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<SchoolClassCurriculum> getAllList()
    {
        try {
            return schoolClassCurriculumDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassCurriculumService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<SchoolClassCurriculum> getListBySchoolIdAndClassId(int schoolId, int classId)
    {
        try {
            return schoolClassCurriculumDao.selectListBySchoolIdAndClassId(schoolId, classId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassCurriculumService”类执行方法“getListBySchoolIdAndClassId”错误", ex);
            throw ex;
        }
    }

    public SchoolClassCurriculum getBySchoolIdAndClassIdAndWeekAndSession(int schoolId, int classId, int week, int sessioin)
    {
        try {
            return schoolClassCurriculumDao.selectBySchoolIdAndClassIdAndWeekAndSession(schoolId, classId, week, sessioin);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassCurriculumService”类执行方法“getBySchoolIdAndClassIdAndWeekAndSession”错误", ex);
            throw ex;
        }
    }
    public void deleteBySchoolIdAndClassId(int schoolId, int classId)
    {
        List<SchoolClassCurriculum> list = getListBySchoolIdAndClassId(schoolId, classId);
        for (SchoolClassCurriculum classCurriculum : list) {
            delete(classCurriculum.getPkid());
        }
    }


}

