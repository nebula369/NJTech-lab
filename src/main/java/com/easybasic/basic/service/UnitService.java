package com.easybasic.basic.service;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.dao.IUnitDao;
import com.easybasic.basic.model.Unit;
import com.easybasic.component.Utils.Pinyin4jUtil;
import com.easybasic.component.Utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("unitService")
public class UnitService {

    private static Logger logger = LoggerFactory.getLogger(UnitService.class);

    @Resource
    private IUnitDao unitDao;

    public void initTopUnit()
    {
        Unit unit = getByPkId(1);
        if(unit == null)
        {
            unit = new Unit();
            unit.setPkid(1);
            unit.setName("顶级单位");
            unit.setParentid(0);
            unit.setSortnum(0);
            unit.setType(0);
            unit.setPath("1");
            unit.setStageids("");
            unit.setFpinyin(Pinyin4jUtil.converterToFirstSpell(unit.getName().substring(0,1)));
            unit.setSpinyin(Pinyin4jUtil.converterToFirstSpell(unit.getName()));
            unit.setCreatetime(new Date());
            insert(unit);
        }
    }


    public List<Unit> getUnitListForCache()
    {
        String key = RedisCache.CachePrex + "_getUnitListForCache";
        List<Unit> data = (List<Unit>) RedisCache.getInstance().getObject(key);
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

    public Unit getUnitForCache(int pkId)
    {
        String key = RedisCache.CachePrex + "_getUnitForCache_" + pkId;
        Unit data = (Unit) RedisCache.getInstance().getObject(key);
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

    public void clearUnitCache()
    {
        List<Unit> list = getUnitListForCache();
        for (Unit unit : list)
        {
            RedisCache.getInstance().removeObject(RedisCache.CachePrex + "_getUnitForCache_" + unit.getPkid());
        }
        String key = RedisCache.CachePrex + "_getUnitListForCache";
        RedisCache.getInstance().removeObject(key);
    }

    public String getPath(int parentId, int pkId)
    {
        String result = initPath(parentId);
        if (StringUtils.isEmpty(result))
        {
            result = String.valueOf(pkId);
        }
        else
        {
            result += "," + pkId;
        }
        return result;
    }

    private String initPath(int parentId)
    {
        String result = "";
        Unit orn = getUnitForCache(parentId);
        if (orn != null)
        {
            if (StringUtils.isEmpty(result))
            {
                result = orn.getPkid().toString();
            }
            else
            {
                result = orn.getPkid() + "," + result;
            }
            String r = initPath(orn.getParentid());
            if (StringUtils.isEmpty(r))
            {
                return result;
            }
            return r + "," + result;
        }
        return result;
    }

    public int createUnit(Unit unit)
    {
        unit.setFpinyin(Pinyin4jUtil.converterToFirstSpell(unit.getName().substring(0,1)));
        unit.setSpinyin(Pinyin4jUtil.converterToFirstSpell(unit.getName()));
        insert(unit);
        unit.setPath(getPath(unit.getParentid(), unit.getPkid()));
        update(unit);
        return unit.getPkid();
    }

    public void modifyUnit(Unit unit)
    {
        unit.setFpinyin(Pinyin4jUtil.converterToFirstSpell(unit.getName().substring(0,1)));
        unit.setSpinyin(Pinyin4jUtil.converterToFirstSpell(unit.getName()));
        unit.setPath(getPath(unit.getParentid(), unit.getPkid()));
        update(unit);
    }
    
    public List<Unit> getOrderedUnitList(int parentId)
    {
        List<Unit> result = new ArrayList<>();
        List<Unit> list = getUnitListForCache();
        list = list.stream().sorted(Comparator.comparing(Unit::getType)).collect(Collectors.toList());
        list = list.stream().sorted(Comparator.comparing(Unit::getSortnum)).collect(Collectors.toList());
        Unit parentUnit = getUnitForCache(parentId);
        int level = 0;
        if(parentUnit!= null)
        {
            result.add(parentUnit);
            level = 1;
        }
        initOrderedUnitList(parentId, level, list, result);
        return result;
    }

    private void initOrderedUnitList(int parentId, int level, List<Unit> list, List<Unit> result)
    {
        for (Unit model : list)
        {
            if (model.getParentid() == parentId)
            {
                model.setLevel(level);
                if(list.stream().anyMatch(x->x.getParentid().intValue() == model.getPkid().intValue())) {
                    model.setLast(false);
                }
                else
                {
                    model.setLast(true);
                }
                result.add(model);
                initOrderedUnitList(model.getPkid(), level + 1, list, result);
            }
        }
    }


    public Unit getByPkId(Integer pkId)
    {
        try
        {
            return unitDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“UnitService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Unit unit)
    {
        try {
            int re = unitDao.insert(unit);
            clearUnitCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“UnitService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Unit unit)
    {
        try {
            int re = unitDao.updateByPrimaryKey(unit);
            clearUnitCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“UnitService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = unitDao.deleteByPrimaryKey(pkId);
            clearUnitCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“UnitService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Unit> getAllList()
    {
        try {
            return unitDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“UnitService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public Unit getByManageUser(int userId)
    {
        try {
            return unitDao.selectByManageUser(userId);
        }
        catch (Exception ex)
        {
            logger.error("“UnitService”类执行方法“getByManageUser”错误", ex);
            throw ex;
        }
    }

}

