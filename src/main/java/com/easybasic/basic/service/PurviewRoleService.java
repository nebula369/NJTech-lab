package com.easybasic.basic.service;

import com.easybasic.basic.dao.IPurviewRoleDao;
import com.easybasic.basic.model.PurviewRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("purviewRoleService")
public class PurviewRoleService {

    private static Logger logger = LoggerFactory.getLogger(PurviewRoleService.class);

    @Resource
    private IPurviewRoleDao purviewRoleDao;

    public PurviewRole getByPkId(Integer pkId)
    {
        try
        {
            return purviewRoleDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“PurviewRoleService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(PurviewRole purviewRole)
    {
        try {
            return purviewRoleDao.insert(purviewRole);
        }
        catch (Exception ex){
            logger.error("“PurviewRoleService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(PurviewRole purviewRole)
    {
        try {
            int re = purviewRoleDao.updateByPrimaryKey(purviewRole);
            return re;
        }
        catch (Exception ex){
            logger.error("“PurviewRoleService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = purviewRoleDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“PurviewRoleService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<PurviewRole> getAllList()
    {
        try {
            return purviewRoleDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“PurviewRoleService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<PurviewRole> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return purviewRoleDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“PurviewRoleService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}

