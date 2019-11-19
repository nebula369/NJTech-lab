package com.easybasic.edu.service;

import com.easybasic.basic.model.DataDic;
import com.easybasic.component.Utils.RedisCache;
import com.easybasic.edu.dao.ISubjectDao;
import com.easybasic.edu.model.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("subjectService")
public class SubjectService {

    private static Logger logger = LoggerFactory.getLogger(SubjectService.class);

    @Resource
    private ISubjectDao subjectDao;

    public Subject getSubjectForCache(int code)
    {
        String key = RedisCache.CachePrex + "_getSubjectForCache_" + code;
        Subject data = (Subject) RedisCache.getInstance().getObject(key);
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

    public List<Subject> getSubjectListForCache()
    {
        String key = RedisCache.CachePrex + "_getSubjectListForCache";
        List<Subject> data = (List<Subject>) RedisCache.getInstance().getObject(key);
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

    public void clearSubjectCache(int code)
    {
        String key = RedisCache.CachePrex + "_getSubjectForCache_" + code;
        RedisCache.getInstance().removeObject(key);
    }

    public void clearSubjectListCache()
    {
        String key = RedisCache.CachePrex + "_getSubjectListForCache";
        RedisCache.getInstance().removeObject(key);
    }

    public Subject getByPkId(Integer pkId)
    {
        try
        {
            return subjectDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“SubjectService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Subject subject)
    {
        try {
            int re = subjectDao.insert(subject);
            clearSubjectCache(subject.getCode());
            clearSubjectListCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“SubjectService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Subject subject)
    {
        try {
            int re = subjectDao.updateByPrimaryKey(subject);
            clearSubjectCache(subject.getCode());
            clearSubjectListCache();
            return re;
        }
        catch (Exception ex){
            logger.error("“SubjectService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            Subject subject = getByPkId(pkId);
            if(subject!=null) {
                int re = subjectDao.deleteByPrimaryKey(pkId);
                clearSubjectCache(subject.getCode());
                clearSubjectListCache();
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“SubjectService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Subject> getAllList()
    {
        try {
            return subjectDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“SubjectService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public Subject getByCode(int code)
    {
        try {
            return subjectDao.selectByCode(code);
        }
        catch (Exception ex)
        {
            logger.error("“SubjectService”类执行方法“getByCode”错误", ex);
            throw ex;
        }
    }

    public Subject getByName(String name)
    {
        List<Subject> list = getSubjectListForCache();
        Subject result = list.stream().filter(x->x.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return result;
    }

}

