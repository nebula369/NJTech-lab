package com.easybasic.basic.service;

import com.easybasic.basic.dao.IOnlineUserDao;
import com.easybasic.basic.model.OnlineUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("onlineUserService")
public class OnlineUserService {

    private static Logger logger = LoggerFactory.getLogger(OnlineUserService.class);

    @Resource
    private IOnlineUserDao onlineUserDao;


    public OnlineUser getByPkId(Integer pkId)
    {
        try
        {
            return onlineUserDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“OnlineUserService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public OnlineUser getByUserId(Integer userId)
    {
        try
        {
            return onlineUserDao.selectByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“OnlineUserService”类执行方法“getByUserId”错误", ex);
            throw ex;
        }
    }

    public OnlineUser getByCode(String code)
    {
        try
        {
            return onlineUserDao.selectByCode(code);
        }
        catch (Exception ex)
        {
            logger.error("“OnlineUserService”类执行方法“getByCode”错误", ex);
            throw ex;
        }
    }

    public int insert(OnlineUser onlineUser)
    {
        try {
            return onlineUserDao.insert(onlineUser);
        }
        catch (Exception ex){
            logger.error("“OnlineUserService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(OnlineUser onlineUser)
    {
        try {
            int re = onlineUserDao.updateByPrimaryKey(onlineUser);
            return re;
        }
        catch (Exception ex){
            logger.error("“OnlineUserService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = onlineUserDao.deleteByPrimaryKey(pkId);
            return 0;
        }
        catch (Exception ex){
            logger.error("“OnlineUserService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<OnlineUser> getAllList()
    {
        try {
            return onlineUserDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“OnlineUserService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public void deleteByValidate()
    {
        try {
            onlineUserDao.deleteByValidate();
        }
        catch (Exception ex)
        {
            logger.error("“OnlineUserService”类执行方法“deleteByValidate”错误", ex);
            throw ex;
        }
    }

}

