package com.easybasic.basic.service;

import com.easybasic.basic.dao.IBasicDsDao;
import com.easybasic.basic.model.BasicDs;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("BasicDsService")
public class BasicDsService {

    private static Logger logger = LoggerFactory.getLogger(BasicDsService.class);

    @Resource
    private IBasicDsDao basicDsDao;

    public BasicDs getBasicDsForCache()
    {
        String key = RedisCache.CachePrex + "_getbasicDsForCache";
        BasicDs data = (BasicDs) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getByPkId(1);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public void clearSiteConfigCache()
    {
        String key = RedisCache.CachePrex + "_getbasicDsForCache" ;
        RedisCache.getInstance().removeObject(key);
    }

    public BasicDs getByPkId(Integer pkId)
    {
        try
        {
            return basicDsDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“BasicDsService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(BasicDs siteConfig)
    {
        try {
            return basicDsDao.insert(siteConfig);
        }
        catch (Exception ex){
            logger.error("“BasicDsService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(BasicDs siteConfig)
    {
        try {
            int re = basicDsDao.updateByPrimaryKey(siteConfig);
            clearSiteConfigCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“BasicDsService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = basicDsDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“BasicDsService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }
}
