package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.dao.IActivityDao;
import com.easybasic.eclassbrand.dao.IActivityDetailDao;
import com.easybasic.eclassbrand.model.Activity;
import com.easybasic.eclassbrand.model.ActivityDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("activityDetailService")
public class ActivityDetailService {
    private static Logger logger = LoggerFactory.getLogger(ActivityDetailService.class);

    @Resource
    private IActivityDetailDao ActivityDao;

    public ActivityDetail getByPkId(Integer pkId)
    {
        try
        {
            return ActivityDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“ActivityService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(ActivityDetail website)
    {
        try {
            return ActivityDao.insert(website);
        }
        catch (Exception ex){
            logger.error("“ActivityService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(ActivityDetail website)
    {
        try {
            int re = ActivityDao.updateByPrimaryKey(website);
            return re;
        }
        catch (Exception ex){
            logger.error("“ActivityService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            ActivityDetail website = getByPkId(pkId);
            if(website!=null) {
                int re = ActivityDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“ActivityService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<ActivityDetail> getAllList()
    {
        try {
            return ActivityDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“ActivityService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<ActivityDetail> getActivityDetailListForPage(String searchStr, String orderStr)
    {
        try {
            return ActivityDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“ActivityService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
}
