package com.easybasic.basic.service;

import com.easybasic.basic.dao.IDataDicValDetailDao;
import com.easybasic.basic.model.DataDicValDetail;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("dataDicValDetailService")
public class DataDicValDetailService {

    private static Logger logger = LoggerFactory.getLogger(DataDicValDetailService.class);

    @Resource
    private IDataDicValDetailDao dataDicValDetailDao;

    public List<DataDicValDetail> getDataDicValDetailListForCache(String code)
    {
        String key = RedisCache.CachePrex + "_getDataDicValDetailListForCache_" + code.toLowerCase();
        List<DataDicValDetail> data = (List<DataDicValDetail>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getListByDCode(code);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }

        }

        return data;
    }

    public void clearDataDicValDetailCache(String dcode)
    {
        String key = RedisCache.CachePrex + "_getDataDicValDetailListForCache_" + dcode.toLowerCase();
        RedisCache.getInstance().removeObject(key);
    }

    public DataDicValDetail getByPkId(Integer pkId)
    {
        try
        {
            return dataDicValDetailDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“DataDicValDetailService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(DataDicValDetail dataDicValDetail)
    {
        try {
            return dataDicValDetailDao.insert(dataDicValDetail);
        }
        catch (Exception ex){
            logger.error("“DataDicValDetailService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(DataDicValDetail dataDicValDetail)
    {
        try {
            int re = dataDicValDetailDao.updateByPrimaryKey(dataDicValDetail);
            clearDataDicValDetailCache(dataDicValDetail.getDcode());
            return re;
        }
        catch (Exception ex){
            logger.error("“DataDicValDetailService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public List<DataDicValDetail> getAllList()
    {
        try {
            return dataDicValDetailDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“DataDicValDetailService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<DataDicValDetail> getListByDCode(String dcode)
    {
        try {
            return dataDicValDetailDao.selectListByDCode(dcode);
        }
        catch (Exception ex)
        {
            logger.error("“DataDicValDetailService”类执行方法“getListByDCode”错误", ex);
            throw ex;
        }
    }

}

