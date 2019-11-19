package com.easybasic.kaoqin.service;

import com.easybasic.kaoqin.dao.IPlanWeekDao;
import com.easybasic.kaoqin.model.PlanWeek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("planWeekService")
public class PlanWeekService {
    private static Logger logger = LoggerFactory.getLogger(PlanWeekService.class);

    @Resource
    private IPlanWeekDao planWeekDao;

    public PlanWeek getByPkId(Integer pkId)
    {
        try
        {
            return planWeekDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“PlanWeekService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(PlanWeek planweek)
    {
        try {
            return planWeekDao.insert(planweek);
        }
        catch (Exception ex){
            logger.error("“PlanWeekService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(PlanWeek planweek)
    {
        try {
            int re = planWeekDao.updateByPrimaryKey(planweek);
            return re;
        }
        catch (Exception ex){
            logger.error("“PlanWeekService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            PlanWeek planweek = getByPkId(pkId);
            if(planweek!=null) {
                int re = planWeekDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“PlanWeekService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<PlanWeek> getAllList()
    {
        try {
            return planWeekDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“PlanWeekService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<PlanWeek> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return planWeekDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“PlanWeekService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
}
