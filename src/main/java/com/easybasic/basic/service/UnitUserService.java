package com.easybasic.basic.service;

import com.easybasic.basic.dao.IUnitUserDao;
import com.easybasic.basic.model.UnitUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("unitUserService")
public class UnitUserService {

    private static Logger logger = LoggerFactory.getLogger(UnitUserService.class);

    @Resource
    private IUnitUserDao unitUserDao;

    public UnitUser getByPkId(Integer pkId)
    {
        try
        {
            return unitUserDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“UnitUserService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(UnitUser unitUser)
    {
        try {
            return unitUserDao.insert(unitUser);
        }
        catch (Exception ex){
            logger.error("“UnitUserService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(UnitUser unitUser)
    {
        try {
            int re = unitUserDao.updateByPrimaryKey(unitUser);
            return re;
        }
        catch (Exception ex){
            logger.error("“UnitUserService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = unitUserDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“UnitUserService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<UnitUser> getAllList()
    {
        try {
            return unitUserDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“UnitUserService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<UnitUser> getListByUserId(int userId)
    {
        try {
            return unitUserDao.selectListByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“UnitUserService”类执行方法“getListByUserId”错误", ex);
            throw ex;
        }
    }

    public UnitUser getByUnitIdAndUserId(int unitId, int userId)
    {
        try {
            return unitUserDao.selectByUnitIdAndUserId(unitId, userId);
        }
        catch (Exception ex)
        {
            logger.error("“UnitUserService”类执行方法“getByUnitIdAndUserId”错误", ex);
            throw ex;
        }
    }

    public boolean isUnitUserExist(int unitId, int userId)
    {
        try {
            UnitUser unitUser = getByUnitIdAndUserId(unitId,userId);
            if(unitUser==null) return false;
            return true;
        }
        catch (Exception ex)
        {
            logger.error("“UnitUserService”类执行方法“isUnitUserExist”错误", ex);
            throw ex;
        }
    }

}

