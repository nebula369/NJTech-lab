package com.easybasic.basic.service;

import com.easybasic.basic.dao.IDeptDao;
import com.easybasic.basic.model.Dept;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("deptService")
public class DeptService {

    private static Logger logger = LoggerFactory.getLogger(DeptService.class);

    @Resource
    private IDeptDao deptDao;

    public List<Dept> getUnitDeptListForCache(int unitId)
    {
        String key = RedisCache.CachePrex + "_getUnitDeptListForCache_" + unitId;
        List<Dept> data = (List<Dept>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getListBySearch("unitid="+ unitId,"sortnum asc");
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public Dept getUnitDeptForCache(int pkId)
    {
        String key = RedisCache.CachePrex + "_getUnitDeptForCache_" + pkId;
        Dept data = (Dept) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getByPkId(pkId);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public void clearUnitDeptListCache(int unitId)
    {
        String key = RedisCache.CachePrex + "_getUnitDeptListForCache_" + unitId;
        RedisCache.getInstance().removeObject(key);
    }

    public void clearUnitDeptCache(int pkId)
    {
        String key = RedisCache.CachePrex + "_getUnitDeptForCache_" + pkId;
        RedisCache.getInstance().removeObject(key);
    }

    public Dept getByPkId(Integer pkId)
    {
        try
        {
            return deptDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“DeptService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Dept dept)
    {
        try {
            int re = deptDao.insert(dept);
            clearUnitDeptListCache(dept.getUnitid());
            return re;
        }
        catch (Exception ex){
            logger.error("“DeptService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Dept dept)
    {
        try {
            Dept oriDept = getByPkId(dept.getPkid());
            int re = deptDao.updateByPrimaryKey(dept);
            clearUnitDeptListCache(oriDept.getUnitid());
            clearUnitDeptListCache(dept.getUnitid());
            clearUnitDeptCache(dept.getPkid());
            return re;
        }
        catch (Exception ex){
            logger.error("“DeptService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            Dept user = getByPkId(pkId);
            if(user!=null) {
                int re = deptDao.deleteByPrimaryKey(pkId);
                clearUnitDeptListCache(user.getUnitid());
                clearUnitDeptCache(user.getPkid());
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“DeptService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Dept> getAllList()
    {
        try {
            return deptDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“DeptService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<Dept> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return deptDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“DeptService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}

