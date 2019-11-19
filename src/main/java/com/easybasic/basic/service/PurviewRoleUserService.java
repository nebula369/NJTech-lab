package com.easybasic.basic.service;

import com.easybasic.basic.dao.IPurviewRoleUserDao;
import com.easybasic.basic.model.PurviewRoleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("purviewRoleUserService")
public class PurviewRoleUserService {

    private static Logger logger = LoggerFactory.getLogger(PurviewRoleUserService.class);

    @Resource
    private IPurviewRoleUserDao purviewRoleUserDao;

    public PurviewRoleUser getByPkId(Integer pkId)
    {
        try
        {
            return purviewRoleUserDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“PurviewRoleUserService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(PurviewRoleUser purviewRoleUser)
    {
        try {
            return purviewRoleUserDao.insert(purviewRoleUser);
        }
        catch (Exception ex){
            logger.error("“PurviewRoleUserService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(PurviewRoleUser purviewRoleUser)
    {
        try {
            int re = purviewRoleUserDao.updateByPrimaryKey(purviewRoleUser);
            return re;
        }
        catch (Exception ex){
            logger.error("“PurviewRoleUserService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = purviewRoleUserDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“PurviewRoleUserService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<PurviewRoleUser> getAllList()
    {
        try {
            return purviewRoleUserDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“PurviewRoleUserService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<PurviewRoleUser> getListByUserId(int userId)
    {
        try {
            return purviewRoleUserDao.selectListByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“PurviewRoleUserService”类执行方法“getListByUserId”错误", ex);
            throw ex;
        }
    }

    public List<PurviewRoleUser> getListByRoleId(int roleId)
    {
        try {
            return purviewRoleUserDao.selectListByRoleId(roleId);
        }
        catch (Exception ex)
        {
            logger.error("“PurviewRoleUserService”类执行方法“getListByRoleId”错误", ex);
            throw ex;
        }
    }

    public PurviewRoleUser getByRoleIdAndUserId(int roleId, int userId)
    {
        try {
            return purviewRoleUserDao.selectByRoleIdAndUserId(roleId, userId);
        }
        catch (Exception ex)
        {
            logger.error("“PurviewRoleUserService”类执行方法“getByRoleIdAndUserId”错误", ex);
            throw ex;
        }
    }

    public boolean isRoleUserExist(int roleId, int userId)
    {
        PurviewRoleUser purviewRoleUser = getByRoleIdAndUserId(roleId, userId);
        if(purviewRoleUser == null) return false;
        return true;
    }
}

