package com.easybasic.basic.service;

import com.easybasic.basic.dao.IAppMenuDao;
import com.easybasic.basic.model.AppMenu;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("appMenuService")
public class AppMenuService {

    private static Logger logger = LoggerFactory.getLogger(AppMenuService.class);

    @Resource
    private IAppMenuDao appMenuDao;

    public List<AppMenu> getAppMenuListForCache(int appId, int parentId)
    {
        String key = RedisCache.CachePrex + "_getAppMenuListForCache_" + appId +"_" + parentId;
        List<AppMenu> data = (List<AppMenu>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            if(parentId==0)
            {
                data = getListByAppIdForMenu(appId);
            }
            else
            {
                data = getListByParentId(parentId);
            }
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }

        }

        return data;
    }

    public List<AppMenu> getAppMenuListForCache()
    {
        String key = RedisCache.CachePrex + "_getAppMenuListForCache";
        List<AppMenu> data = (List<AppMenu>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getAllList();
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }

        }

        return data;
    }

    public void clearAppMenuListCache(int appId, int parentId)
    {
        String key = RedisCache.CachePrex + "_getAppMenuListForCache_" + appId +"_" + parentId;
        RedisCache.getInstance().removeObject(key);

        String key1 = RedisCache.CachePrex + "_getAppMenuListForCache";
        RedisCache.getInstance().removeObject(key1);

    }

    public AppMenu getByPkId(Integer pkId)
    {
        try
        {
            return appMenuDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“AppMenuService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(AppMenu appMenu)
    {
        try {
            int re = appMenuDao.insert(appMenu);
            clearAppMenuListCache(appMenu.getAppid(), appMenu.getParentid());
            return re;
        }
        catch (Exception ex){
            logger.error("“AppMenuService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(AppMenu appMenu)
    {
        try {
            int re = appMenuDao.updateByPrimaryKey(appMenu);
            clearAppMenuListCache(appMenu.getAppid(), appMenu.getParentid());
            return re;
        }
        catch (Exception ex){
            logger.error("“AppMenuService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            AppMenu appMenu = getByPkId(pkId);
            if(appMenu!=null) {
                int re = appMenuDao.deleteByPrimaryKey(pkId);
                clearAppMenuListCache(appMenu.getAppid(), appMenu.getParentid());
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“AppMenuService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<AppMenu> getAllList()
    {
        try {
            return appMenuDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“AppMenuService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<AppMenu> getListByAppId(int appId)
    {
        try {
            return appMenuDao.selectListByAppId(appId);
        }
        catch (Exception ex)
        {
            logger.error("“AppMenuService”类执行方法“getListByAppId”错误", ex);
            throw ex;
        }
    }

    public List<AppMenu> getListByAppIdForMenu(int appId)
    {
        try {
            return appMenuDao.selectListByAppIdForMenu(appId);
        }
        catch (Exception ex)
        {
            logger.error("“AppMenuService”类执行方法“getListByAppIdForMenu”错误", ex);
            throw ex;
        }
    }

    public List<AppMenu> getListByParentId(int parentId)
    {
        try {
            return appMenuDao.selectListByParentId(parentId);
        }
        catch (Exception ex)
        {
            logger.error("“AppMenuService”类执行方法“getListByParentId”错误", ex);
            throw ex;
        }
    }
}

