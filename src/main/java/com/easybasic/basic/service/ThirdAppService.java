package com.easybasic.basic.service;

import com.easybasic.basic.dao.IThirdAppDao;
import com.easybasic.basic.model.ThirdApp;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("thirdAppService")
public class ThirdAppService {

    private static Logger logger = LoggerFactory.getLogger(ThirdAppService.class);

    @Resource
    private IThirdAppDao thirdAppDao;

    public List<ThirdApp> getThirdAppListForCache()
    {
        String key = RedisCache.CachePrex + "_getThirdAppListForCache";
        List<ThirdApp> data = (List<ThirdApp>) RedisCache.getInstance().getObject(key);
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

    public ThirdApp getThirdAppForCache(String appKey)
    {
        String key = RedisCache.CachePrex + "_getThirdAppForCache_" + appKey;
        ThirdApp data = (ThirdApp) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getByAppKey(appKey);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public void clearThirdAppListCache()
    {
        String key = RedisCache.CachePrex + "_getThirdAppListForCache";
        RedisCache.getInstance().removeObject(key);
    }

    public void clearThirdAppCache(String appKey)
    {
        String key = RedisCache.CachePrex + "_getThirdAppForCache_" + appKey;
        RedisCache.getInstance().removeObject(key);
    }

    public ThirdApp getByPkId(Integer pkId)
    {
        try
        {
            return thirdAppDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“ThirdAppService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(ThirdApp thirdApp)
    {
        try {
            int re = thirdAppDao.insert(thirdApp);
            clearThirdAppListCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“ThirdAppService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(ThirdApp thirdApp)
    {
        try {
            int re = thirdAppDao.updateByPrimaryKey(thirdApp);
            clearThirdAppListCache();
            clearThirdAppCache(thirdApp.getAppkey());
            return re;
        }
        catch (Exception ex){
            logger.error("“ThirdAppService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            ThirdApp thirdApp = getByPkId(pkId);
            if(thirdApp!=null) {
                int re = thirdAppDao.deleteByPrimaryKey(pkId);
                clearThirdAppListCache();
                clearThirdAppCache(thirdApp.getAppkey());
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“ThirdAppService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<ThirdApp> getAllList()
    {
        try {
            return thirdAppDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“ThirdAppService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public ThirdApp getByAppKey(String appKey)
    {
        try {
            return thirdAppDao.selectByAppKey(appKey);
        }
        catch (Exception ex)
        {
            logger.error("“ThirdAppService”类执行方法“getByAppKey”错误", ex);
            throw ex;
        }
    }

    public List<ThirdApp> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return thirdAppDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“ThirdAppService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
}

