package com.easybasic.basic.service;

import com.easybasic.basic.dao.ISpaceTypeDao;
import com.easybasic.basic.model.SpaceType;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("spaceTypeService")
public class SpaceTypeService {

    private static Logger logger = LoggerFactory.getLogger(SpaceTypeService.class);

    @Resource
    private ISpaceTypeDao spaceTypeDao;

    public List<SpaceType> getSpaceTypeListForCache(int unitId)
    {
        String key = RedisCache.CachePrex + "_getSpaceTypeListForCache_" + unitId;
        List<SpaceType> data = (List<SpaceType>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getListByUnitId(unitId);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public void clearSpaceTypeCache(int unitId)
    {
        String key = RedisCache.CachePrex + "_getSpaceTypeListForCache_" + unitId;
        RedisCache.getInstance().removeObject(key);
    }

    public SpaceType getByPkId(Integer pkId)
    {
        try
        {
            return spaceTypeDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“SpaceTypeService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(SpaceType spaceType)
    {
        try {
            int re= spaceTypeDao.insert(spaceType);
            clearSpaceTypeCache(spaceType.getUnitid());
            return re;
        }
        catch (Exception ex){
            logger.error("“SpaceTypeService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(SpaceType spaceType)
    {
        try {
            int re = spaceTypeDao.updateByPrimaryKey(spaceType);
            clearSpaceTypeCache(spaceType.getUnitid());
            return re;
        }
        catch (Exception ex){
            logger.error("“SpaceTypeService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            SpaceType spaceType = getByPkId(pkId);
            if(spaceType!=null) {
                int re = spaceTypeDao.deleteByPrimaryKey(pkId);
                clearSpaceTypeCache(spaceType.getUnitid());
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“SpaceTypeService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<SpaceType> getAllList()
    {
        try {
            return spaceTypeDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“SpaceTypeService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<SpaceType> getListByUnitId(int unitId)
    {
        try {
            return spaceTypeDao.selectListByUnitId(unitId);
        }
        catch (Exception ex)
        {
            logger.error("“SpaceTypeService”类执行方法“getListByUnitId”错误", ex);
            throw ex;
        }
    }

}

