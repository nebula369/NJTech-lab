package com.easybasic.kaoqin.service;

import com.easybasic.kaoqin.dao.IUserGroupAndUserDao;
import com.easybasic.kaoqin.model.UserGroupAndUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userGroupAndUserService")
public class UserGroupAndUserService {
    private static Logger logger = LoggerFactory.getLogger(UserGroupAndUserService.class);

    @Resource
    private IUserGroupAndUserDao userGroupAndUserDao;

    public UserGroupAndUser getByPkId(Integer pkId)
    {
        try
        {
            return userGroupAndUserDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“UserGroupAndUserService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(UserGroupAndUser usergroupanduser)
    {
        try {
            return userGroupAndUserDao.insert(usergroupanduser);
        }
        catch (Exception ex){
            logger.error("“UserGroupAndUserService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(UserGroupAndUser usergroupanduser)
    {
        try {
            int re = userGroupAndUserDao.updateByPrimaryKey(usergroupanduser);
            return re;
        }
        catch (Exception ex){
            logger.error("“UserGroupAndUserService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            UserGroupAndUser usergroupanduser = getByPkId(pkId);
            if(usergroupanduser!=null) {
                int re = userGroupAndUserDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“UserGroupAndUserService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<UserGroupAndUser> getAllList()
    {
        try {
            return userGroupAndUserDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“UserGroupAndUserService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<UserGroupAndUser> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return userGroupAndUserDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“UserGroupAndUserService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
    public UserGroupAndUser getByUserIdAndGroupId(int userId,int groupId)
    {
        try {
            return userGroupAndUserDao.selectByUserIdAndGroupId(userId,groupId);
        }
        catch (Exception ex)
        {
            logger.error("“UserGroupAndUserService”类执行方法“getByUserIdAndGroupId”错误", ex);
            throw ex;
        }
    }
}
