package com.easybasic.kaoqin.service;

import com.easybasic.kaoqin.dao.IUserGroupDao;
import com.easybasic.kaoqin.model.UserGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("userGroupService")
public class UserGroupService {
    private static Logger logger = LoggerFactory.getLogger(UserGroupService.class);

    @Resource
    private IUserGroupDao userGroupDao;

    public UserGroup getByPkId(Integer pkId)
    {
        try
        {
            return userGroupDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“UserGroupService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(UserGroup usergroup)
    {
        try {
            return userGroupDao.insert(usergroup);
        }
        catch (Exception ex){
            logger.error("“UserGroupService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(UserGroup usergroup)
    {
        try {
            int re = userGroupDao.updateByPrimaryKey(usergroup);
            return re;
        }
        catch (Exception ex){
            logger.error("“UserGroupService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            UserGroup usergroup = getByPkId(pkId);
            if(usergroup!=null) {
                int re = userGroupDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“UserGroupService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<UserGroup> getAllList()
    {
        try {
            return userGroupDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“UserGroupService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<UserGroup> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return userGroupDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“UserGroupService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
}
