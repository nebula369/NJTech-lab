package com.easybasic.basic.service;

import com.easybasic.basic.dao.IAppModuleDao;
import com.easybasic.basic.model.AppModule;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service("appModuleService")
public class AppModuleService {

    private static Logger logger = LoggerFactory.getLogger(AppModuleService.class);

    @Resource
    private IAppModuleDao appModuleDao;

    public List<AppModule> getAppModuleListForCache()
    {
        String key = RedisCache.CachePrex + "_getAppModuleListForCache";
        List<AppModule> data = (List<AppModule>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getAllList().stream().filter(x->!x.getCode().equalsIgnoreCase("profile")).collect(Collectors.toList());
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }

        }

        return data;
    }

    public void clearAppModuleListCache()
    {
        String key = RedisCache.CachePrex + "_getAppModuleListForCache";
        RedisCache.getInstance().removeObject(key);
    }

    public AppModule getByPkId(Integer pkId)
    {
        try
        {
            return appModuleDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“AppModuleService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(AppModule appModule)
    {
        try {
            int re = appModuleDao.insert(appModule);
            clearAppModuleListCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“AppModuleService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(AppModule appModule)
    {
        try {
            int re = appModuleDao.updateByPrimaryKey(appModule);
            clearAppModuleListCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“AppModuleService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = appModuleDao.deleteByPrimaryKey(pkId);
            clearAppModuleListCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“AppModuleService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<AppModule> getAllList()
    {
        try {
            return appModuleDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“AppModuleService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public AppModule getByCode(String code)
    {
        try {
            return appModuleDao.selectByCode(code);
        }
        catch (Exception ex)
        {
            logger.error("“AppModuleService”类执行方法“getByCode”错误", ex);
            throw ex;
        }
    }

}

