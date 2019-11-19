package com.easybasic.basic.service;

import com.easybasic.basic.dao.IDataDicDao;
import com.easybasic.basic.model.DataDic;
import com.easybasic.component.Utils.RedisCache;
import com.easybasic.component.Utils.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("dataDicService")
public class DataDicService {

    private static Logger logger = LoggerFactory.getLogger(DataDicService.class);

    @Resource
    private IDataDicDao dataDicDao;

    public DataDic getDataDicForCache(String code)
    {
        String key = RedisCache.CachePrex + "_getDataDicForCache_" + code.toLowerCase();
        DataDic data = (DataDic) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getByCode(code);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }

        }

        return data;
    }

    public void clearDataDicCache(String code)
    {
        String key = RedisCache.CachePrex + "_getDataDicForCache_" + code.toLowerCase();
        RedisCache.getInstance().removeObject(key);
    }

    public DataDic getByPkId(Integer pkId)
    {
        try
        {
            return dataDicDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“DataDicService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public DataDic getByCode(String code)
    {
        try
        {
            return dataDicDao.selectByCode(code);
        }
        catch (Exception ex)
        {
            logger.error("“DataDicService”类执行方法“getByCode”错误", ex);
            throw ex;
        }
    }

    public int insert(DataDic dataDic)
    {
        try {
            return dataDicDao.insert(dataDic);
        }
        catch (Exception ex){
            logger.error("“DataDicService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(DataDic dataDic)
    {
        try {
            int re = dataDicDao.updateByPrimaryKey(dataDic);
            clearDataDicCache(dataDic.getCode());
            return re;
        }
        catch (Exception ex){
            logger.error("“DataDicService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public List<DataDic> getAllList()
    {
        try {
            return dataDicDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“DataDicService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<DataDic> getListBySearch(String searchStr, String orderStr)
    {
        try {
            if (!ToolsUtil.isSafeSqlString(searchStr)) return new ArrayList<>();
            return dataDicDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“DataDicService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}

