package com.easybasic.kaoqin.service;

import com.easybasic.kaoqin.dao.IPlanWeekDao;
import com.easybasic.kaoqin.dao.IUserCheckInfoDao;
import com.easybasic.kaoqin.model.PlanWeek;
import com.easybasic.kaoqin.model.UserCheckInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("UserCheckInfoService")
public class UserCheckInfoService {
    private static Logger logger = LoggerFactory.getLogger(UserCheckInfoService.class);

    @Resource
    private IUserCheckInfoDao userCheckInfoDao;

    public UserCheckInfo getByPkId(Integer pkId)
    {
        try
        {
            return userCheckInfoDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("”UserCheckInfoService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(UserCheckInfo userCheckInfo)
    {
        try {
            return userCheckInfoDao.insert(userCheckInfo);
        }
        catch (Exception ex){
            logger.error("“UserCheckInfoService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(UserCheckInfo userCheckInfo)
    {
        try {
            int re = userCheckInfoDao.updateByPrimaryKey(userCheckInfo);
            return re;
        }
        catch (Exception ex){
            logger.error("“UserCheckInfoService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            UserCheckInfo userCheckInfo = getByPkId(pkId);
            if(userCheckInfo!=null) {
                int re = userCheckInfoDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“UserCheckInfoService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<UserCheckInfo> getAllList()
    {
        try {
            return userCheckInfoDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“UserCheckInfoService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<UserCheckInfo> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return userCheckInfoDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“UserCheckInfoService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
}
