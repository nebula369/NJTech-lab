package com.easybasic.edu.service;

import com.easybasic.edu.dao.ISchoolScheduleWeekTimeDao;
import com.easybasic.edu.model.SchoolScheduleWeekTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("schoolScheduleWeekTimeService")
public class SchoolScheduleWeekTimeService {

    private static Logger logger = LoggerFactory.getLogger(SchoolScheduleWeekTimeService.class);

    @Resource
    private ISchoolScheduleWeekTimeDao schoolScheduleWeekTimeDao;

    public SchoolScheduleWeekTime getByPkId(Integer pkId)
    {
        try
        {
            return schoolScheduleWeekTimeDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolScheduleWeekTimeService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(SchoolScheduleWeekTime schoolScheduleWeekTime)
    {
        try {
            int re = schoolScheduleWeekTimeDao.insert(schoolScheduleWeekTime);
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolScheduleWeekTimeService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(SchoolScheduleWeekTime schoolScheduleWeekTime)
    {
        try {
            int re = schoolScheduleWeekTimeDao.updateByPrimaryKey(schoolScheduleWeekTime);
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolScheduleWeekTimeService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            SchoolScheduleWeekTime schoolScheduleWeekTime = getByPkId(pkId);
            if(schoolScheduleWeekTime!=null) {
                int re = schoolScheduleWeekTimeDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“SchoolScheduleWeekTimeService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<SchoolScheduleWeekTime> getAllList()
    {
        try {
            return schoolScheduleWeekTimeDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“SchoolScheduleWeekTimeService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<SchoolScheduleWeekTime> getListByScheduleId(int scheduleId)
    {
        try {
            return schoolScheduleWeekTimeDao.selectListBySchedule(scheduleId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolScheduleWeekTimeService”类执行方法“getListByScheduleId”错误", ex);
            throw ex;
        }
    }

}

