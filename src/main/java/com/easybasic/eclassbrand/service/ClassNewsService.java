package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.dao.IClassNewsDao;
import com.easybasic.eclassbrand.model.ClassNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("classNewsService")
public class ClassNewsService {
    private static Logger logger = LoggerFactory.getLogger(ClassNewsService.class);

    @Resource
    private IClassNewsDao classNewsDao;

    public ClassNews getByPkId(Integer pkId)
    {
        try
        {
            return classNewsDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“ClassNewsService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(ClassNews classTask)
    {
        try {
            return classNewsDao.insert(classTask);
        }
        catch (Exception ex){
            logger.error("“ClassNewsService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(ClassNews classTask)
    {
        try {
            int re = classNewsDao.updateByPrimaryKey(classTask);
            return re;
        }
        catch (Exception ex){
            logger.error("“ClassNewsService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            ClassNews classTask = getByPkId(pkId);
            if(classTask!=null) {
                int re = classNewsDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“ClassNewsService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<ClassNews> getAllList()
    {
        try {
            return classNewsDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“ClassNewsService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }
    public List<ClassNews> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return classNewsDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“ClassNewsService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
    public ClassNews getByClassIdAndNewsId(int classid,int taskid)
    {
        try
        {
            return classNewsDao.getByClassIdAndNewsId(classid,taskid);
        }
        catch (Exception ex)
        {
            logger.error("“ClassNewsService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }
}
