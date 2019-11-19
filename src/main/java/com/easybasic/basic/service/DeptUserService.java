package com.easybasic.basic.service;

import com.easybasic.basic.dao.IDeptUserDao;
import com.easybasic.basic.model.DeptUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("deptUserService")
public class DeptUserService {

    private static Logger logger = LoggerFactory.getLogger(DeptUserService.class);

    @Resource
    private IDeptUserDao deptUserDao;

    public DeptUser getByPkId(Integer pkId)
    {
        try
        {
            return deptUserDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“DeptUserService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(DeptUser deptUser)
    {
        try {
            return deptUserDao.insert(deptUser);
        }
        catch (Exception ex){
            logger.error("“DeptUserService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(DeptUser deptUser)
    {
        try {
            int re = deptUserDao.updateByPrimaryKey(deptUser);
            return re;
        }
        catch (Exception ex){
            logger.error("“DeptUserService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = deptUserDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“DeptUserService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<DeptUser> getAllList()
    {
        try {
            return deptUserDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“DeptUserService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }
    public List<DeptUser> getListByUserId(int userId)
    {
        try {
            return deptUserDao.selectListByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“DeptUserService”类执行方法“getListByUserId”错误", ex);
            throw ex;
        }
    }

    public DeptUser getByDeptIdAndUserId(int deptId, int userId)
    {
        try {
            return deptUserDao.selectByDeptIdAndUserId(deptId, userId);
        }
        catch (Exception ex)
        {
            logger.error("“DeptUserService”类执行方法“getByDeptIdAndUserId”错误", ex);
            throw ex;
        }
    }

    public boolean isDeptUserExist(int deptId, int userId)
    {
        try {
            DeptUser deptUser = getByDeptIdAndUserId(deptId, userId);
            if(deptUser == null) return false;
            return true;
        }
        catch (Exception ex)
        {
            logger.error("“DeptUserService”类执行方法“isDeptUserExist”错误", ex);
            throw ex;
        }
    }

    public int getCountByDeptId(int deptId)
    {
        try {
            return deptUserDao.selectCountByDeptId(deptId);
        }
        catch (Exception ex)
        {
            logger.error("“DeptUserService”类执行方法“getCountByDeptId”错误", ex);
            throw ex;
        }
    }

}

