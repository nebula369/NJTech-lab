package com.easybasic.edu.service;

import com.easybasic.component.Utils.RedisCache;
import com.easybasic.edu.dao.ISchoolScheduleDao;
import com.easybasic.edu.model.SchoolSchedule;
import com.easybasic.edu.model.SchoolScheduleWeekTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schoolScheduleService")
public class SchoolScheduleService {

    private static Logger logger = LoggerFactory.getLogger(SchoolScheduleService.class);

    @Resource
    private ISchoolScheduleDao schoolScheduleDao;
    @Resource
    private SchoolScheduleWeekTimeService schoolScheduleWeekTimeService;

    public List<SchoolSchedule> getSchoolScheduleListForCache(int schoolId, int stageId)
    {
        String key = RedisCache.CachePrex + "_getSchoolScheduleListForCache_" + schoolId +"_" + stageId;
        List<SchoolSchedule> data = (List<SchoolSchedule>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getListBySchoolAndStage(schoolId, stageId);
            if (data != null)
            {
                for (SchoolSchedule schoolSchedule : data) {
                    List<SchoolScheduleWeekTime> weekTimeList = schoolScheduleWeekTimeService.getListByScheduleId(schoolSchedule.getPkid());
                    schoolSchedule.setWeekTimeList(weekTimeList);
                }
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public void clearSchoolScheduleCache(int schoolId, int stageId)
    {
        String key = RedisCache.CachePrex + "_getSchoolScheduleListForCache_" + schoolId +"_" + stageId;
        RedisCache.getInstance().removeObject(key);
    }

    public SchoolSchedule getByPkId(Integer pkId)
    {
        try
        {
            return schoolScheduleDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolScheduleService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(SchoolSchedule schoolSchedule)
    {
        try {
            int re = schoolScheduleDao.insert(schoolSchedule);
            clearSchoolScheduleCache(schoolSchedule.getSchoolid(), schoolSchedule.getStageid());
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolScheduleService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(SchoolSchedule schoolSchedule)
    {
        try {
            int re = schoolScheduleDao.updateByPrimaryKey(schoolSchedule);
            clearSchoolScheduleCache(schoolSchedule.getSchoolid(), schoolSchedule.getStageid());
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolScheduleService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            SchoolSchedule schoolSchedule = getByPkId(pkId);
            if(schoolSchedule!=null) {
                int re = schoolScheduleDao.deleteByPrimaryKey(pkId);
                clearSchoolScheduleCache(schoolSchedule.getSchoolid(), schoolSchedule.getStageid());
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“SchoolScheduleService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<SchoolSchedule> getAllList()
    {
        try {
            return schoolScheduleDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“SchoolScheduleService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public SchoolSchedule getBySchoolAndStageAndSesson(int schoolId, int stageId, int session)
    {
        try {
            return schoolScheduleDao.selectBySchoolAndStageAndSession(schoolId, stageId, session);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolScheduleService”类执行方法“getBySchoolAndStageAndSesson”错误", ex);
            throw ex;
        }
    }

    public List<SchoolSchedule> getListBySchoolAndStage(int schoolId, int stageId)
    {
        try {
            return schoolScheduleDao.selectListBySchoolAndStage(schoolId, stageId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolScheduleService”类执行方法“getListBySchoolAndStage”错误", ex);
            throw ex;
        }
    }

    public void deleteBySchoolIdAndStageId(int schoolId, int stageId)
    {
        List<SchoolSchedule> list = getListBySchoolAndStage(schoolId, stageId);
        for (SchoolSchedule schoolSchedule : list) {
            delete(schoolSchedule.getPkid());
        }
    }

}

