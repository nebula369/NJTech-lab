package com.easybasic.basic.service;

import com.easybasic.basic.dao.IDataDicValDao;
import com.easybasic.basic.model.DataDicVal;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("dataDicValService")
public class DataDicValService {

    private static Logger logger = LoggerFactory.getLogger(DataDicValService.class);

    @Resource
    private IDataDicValDao dataDicValDao;

    public List<DataDicVal> getDataDicValListForCache(String code)
    {
        String key = RedisCache.CachePrex + "_getDataDicValListForCache_" + code.toLowerCase();
        List<DataDicVal> data = (List<DataDicVal>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getListByDataDicCode(code);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }

        }

        return data;
    }

    public void clearDataDicValCache(String code)
    {
        String key = RedisCache.CachePrex + "_getDataDicValListForCache_" + code.toLowerCase();
        RedisCache.getInstance().removeObject(key);
    }

    public DataDicVal getByPkId(Integer pkId)
    {
        try
        {
            return dataDicValDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“DataDicValService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(DataDicVal dataDicVal)
    {
        try {
            return dataDicValDao.insert(dataDicVal);
        }
        catch (Exception ex){
            logger.error("“DataDicValService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(DataDicVal dataDicVal)
    {
        try {
            int re = dataDicValDao.updateByPrimaryKey(dataDicVal);
            clearDataDicValCache(dataDicVal.getCode());
            return re;
        }
        catch (Exception ex){
            logger.error("“DataDicValService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public List<DataDicVal> getAllList()
    {
        try {
            return dataDicValDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“DataDicValService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<DataDicVal> getListByDataDicCode(String code)
    {
        try {
            return dataDicValDao.selectListByDataDicCode(code);
        }
        catch (Exception ex)
        {
            logger.error("“DataDicValService”类执行方法“getListByDataDicCode”错误", ex);
            throw ex;
        }
    }

}

