package com.easybasic.basic.service;

import com.easybasic.basic.dao.ISiteConfigDao;
import com.easybasic.basic.model.SiteConfig;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("siteConfigService")
public class SiteConfigService {

    private static Logger logger = LoggerFactory.getLogger(SiteConfigService.class);

    @Resource
    private ISiteConfigDao siteConfigDao;

    public void initSiteConfig()
    {
        SiteConfig siteConfig = getByPkId(1);
        if(siteConfig==null)
        {
            siteConfig = new SiteConfig();
            siteConfig.setPkid(1);
            siteConfig.setIsonlyallowintranetip(0);
            insert(siteConfig);
        }
    }

    public SiteConfig getSiteConfigForCache()
    {
        String key = RedisCache.CachePrex + "_getSiteConfigForCache";
        SiteConfig data = (SiteConfig) RedisCache.getInstance().getObject(key);
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
        String key = RedisCache.CachePrex + "_getSiteConfigForCache" ;
        RedisCache.getInstance().removeObject(key);
    }

    public SiteConfig getByPkId(Integer pkId)
    {
        try
        {
            return siteConfigDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“SiteConfigService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(SiteConfig siteConfig)
    {
        try {
            return siteConfigDao.insert(siteConfig);
        }
        catch (Exception ex){
            logger.error("“SiteConfigService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(SiteConfig siteConfig)
    {
        try {
            int re = siteConfigDao.updateByPrimaryKey(siteConfig);
            clearSiteConfigCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“SiteConfigService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = siteConfigDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“SiteConfigService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }


}

