package com.easybasic.basic.service;

import com.easybasic.basic.dao.IPurviewUserDao;
import com.easybasic.basic.model.PurviewUser;
import com.easybasic.component.Utils.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("purviewUserService")
public class PurviewUserService {

    private static Logger logger = LoggerFactory.getLogger(PurviewUserService.class);

    @Resource
    private IPurviewUserDao purviewUserDao;

    public PurviewUser getByPkId(Integer pkId)
    {
        try
        {
            return purviewUserDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“PurviewUserService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(PurviewUser purviewUser)
    {
        try {
            return purviewUserDao.insert(purviewUser);
        }
        catch (Exception ex){
            logger.error("“PurviewUserService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(PurviewUser purviewUser)
    {
        try {
            int re = purviewUserDao.updateByPrimaryKey(purviewUser);
            return re;
        }
        catch (Exception ex){
            logger.error("“PurviewUserService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = purviewUserDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“PurviewUserService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<PurviewUser> getAllList()
    {
        try {
            return purviewUserDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“PurviewUserService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<PurviewUser> getListBySearch(String searchStr, String orderStr)
    {
        try {
            if(!ToolsUtil.isSafeSqlString(searchStr)) return new ArrayList<>();
            return purviewUserDao.selectListBySearch(searchStr,orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“PurviewUserService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

    public PurviewUser getByUserId(int userId)
    {
        try {
            return purviewUserDao.selectByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“PurviewUserService”类执行方法“getByUserId”错误", ex);
            throw ex;
        }
    }

}

