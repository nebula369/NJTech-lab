package com.easybasic.basic.service;

import com.easybasic.basic.dao.ISchoolClassDao;
import com.easybasic.basic.model.DataDicVal;
import com.easybasic.basic.model.DataDicValDetail;
import com.easybasic.basic.model.SchoolClass;
import com.easybasic.basic.model.SchoolClassUser;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schoolClassService")
public class SchoolClassService {

    private static Logger logger = LoggerFactory.getLogger(SchoolClassService.class);

    @Resource
    private ISchoolClassDao schoolClassDao;
    @Resource
    private DataDicValDetailService dataDicValDetailService;
    @Resource
    private DataDicValService dataDicValService;

    public SchoolClass initClassName(SchoolClass schoolClass)
    {
        if(schoolClass==null) return schoolClass;
        List<DataDicValDetail> gradeList = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
        DataDicValDetail grade = gradeList.stream().filter(x->x.getValcode().equalsIgnoreCase(schoolClass.getStageid().toString())
         && x.getCode().equalsIgnoreCase(schoolClass.getGradeid().toString())).findFirst().orElse(null);
        if(grade !=null)
        {
            schoolClass.setGradeName(grade.getName());
        }
        List<DataDicVal> stageList = dataDicValService.getListByDataDicCode("XD");
        DataDicVal stage = stageList.stream().filter(x->x.getCode().equalsIgnoreCase(schoolClass.getStageid().toString())).findFirst().orElse(null);
        if(stage!=null)
        {
            schoolClass.setStageName(stage.getName());
        }
        return schoolClass;
    }

    public List<SchoolClass> getSchoolClassListForCache(int schoolId, int stageId, int gradeId)
    {
        String key = RedisCache.CachePrex + "_getSchoolClassListForCache_" + schoolId +"_" + stageId +"_" + gradeId;
        List<SchoolClass> data = (List<SchoolClass>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getListBySchoolIdAndStageIdAndGradeId(schoolId, stageId, gradeId);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public SchoolClass getSchoolClassForCache(int pkId)
    {
        String key = RedisCache.CachePrex + "_getSchoolClassForCache_" + pkId;
        SchoolClass data = (SchoolClass) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getByPkId(pkId);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public void clearSchoolClassListCache(int schoolId, int stageId, int gradeId)
    {
        String key = RedisCache.CachePrex + "_getSchoolClassListForCache_" + schoolId +"_" + stageId +"_" + gradeId;
        RedisCache.getInstance().removeObject(key);
    }

    public void clearSchoolClassCache(int pkId)
    {
        String key = RedisCache.CachePrex + "_getSchoolClassForCache_" + pkId;
        RedisCache.getInstance().removeObject(key);
    }

    public SchoolClass getByPkId(Integer pkId)
    {
        try
        {
            return initClassName(schoolClassDao.selectByPrimaryKey(pkId));
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(SchoolClass schoolClass)
    {
        try {
            int re = schoolClassDao.insert(schoolClass);
            clearSchoolClassListCache(schoolClass.getUnitid(), schoolClass.getStageid(), schoolClass.getGradeid());
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolClassService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(SchoolClass schoolClass)
    {
        try {
            SchoolClass oriClass = getByPkId(schoolClass.getPkid());
            int re = schoolClassDao.updateByPrimaryKey(schoolClass);
            clearSchoolClassListCache(oriClass.getUnitid(), oriClass.getStageid(), oriClass.getGradeid());
            clearSchoolClassListCache(schoolClass.getUnitid(), schoolClass.getStageid(), schoolClass.getGradeid());
            clearSchoolClassCache(schoolClass.getPkid());
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolClassService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            SchoolClass schoolClass = getByPkId(pkId);
            if(schoolClass != null) {
                int re = schoolClassDao.deleteByPrimaryKey(pkId);
                clearSchoolClassListCache(schoolClass.getUnitid(), schoolClass.getStageid(), schoolClass.getGradeid());
                clearSchoolClassCache(schoolClass.getPkid());
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“SchoolClassService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<SchoolClass> getAllList()
    {
        try {
            List<SchoolClass> list = schoolClassDao.selectAll();
            for(SchoolClass schoolClass : list)
            {
                initClassName(schoolClass);
            }
            return list;
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<SchoolClass> getListBySearch(String searchStr, String orderStr)
    {
        try {
            List<SchoolClass> list = schoolClassDao.selectListBySearch(searchStr, orderStr);
            for(SchoolClass schoolClass : list)
            {
                initClassName(schoolClass);
            }
            return list;
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

    public List<SchoolClass> getListBySchoolIdAndStageIdAndGradeId(int schoolId, int stageId, int gradeId)
    {
        try {
            List<SchoolClass> list = schoolClassDao.selectListBySchoolIdAndStageIdAndGradeId(schoolId, stageId, gradeId);
            for(SchoolClass schoolClass : list)
            {
                initClassName(schoolClass);
            }
            return list;
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassService”类执行方法“getListBySchoolIdAndGradeId”错误", ex);
            throw ex;
        }
    }

    public SchoolClass getBySchoolIdAndClassCode(int schoolId, String classCode)
    {
        try {
            return initClassName(schoolClassDao.selectBySchoolIdAndClassCode(schoolId, classCode));
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassService”类执行方法“getBySchoolIdAndClassCode”错误", ex);
            throw ex;
        }
    }

    public SchoolClass getBySchoolIdAndStageIdAndGradeIdAndName(int schoolId, int stageId, int gradeId, String name)
    {
        try {
            return initClassName(schoolClassDao.selectBySchoolIdAndStageIdAndGradeIdAndName(schoolId, stageId, gradeId, name));
        }
        catch (Exception ex)
        {
            logger.error("“SchoolClassService”类执行方法“getBySchoolIdAndGradeIdAndName”错误", ex);
            throw ex;
        }
    }

    public boolean isSchoolClassCodeExist(int schoolId, String classCode)
    {
        SchoolClass schoolClass= getBySchoolIdAndClassCode(schoolId, classCode);
        if(schoolClass == null)
        {
            return false;
        }
        return true;
    }

    public boolean isSchoolClassNameExist(int schoolId, int stageId, int gradeId, String name)
    {
        SchoolClass schoolClass= getBySchoolIdAndStageIdAndGradeIdAndName(schoolId, stageId, gradeId, name);
        if(schoolClass == null)
        {
            return false;
        }
        return true;
    }

}

