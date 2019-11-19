package com.easybasic.eclassbrand.service;
import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("classTaskService")
public class ClassTaskService {
    private static Logger logger = LoggerFactory.getLogger(ClassTaskService.class);

    @Resource
    private IClassTaskDao classTaskDao;

    public ClassTask getByPkId(Integer pkId)
    {
        try
        {
            return classTaskDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“ClassTaskService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(ClassTask classTask)
    {
        try {
            return classTaskDao.insert(classTask);
        }
        catch (Exception ex){
            logger.error("“ClassTaskService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(ClassTask classTask)
    {
        try {
            int re = classTaskDao.updateByPrimaryKey(classTask);
            return re;
        }
        catch (Exception ex){
            logger.error("“ClassTaskService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            ClassTask classTask = getByPkId(pkId);
            if(classTask!=null) {
                int re = classTaskDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“ClassTaskService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<ClassTask> getAllList()
    {
        try {
            return classTaskDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“ClassTaskService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }
    public List<ClassTask> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return classTaskDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“ClassTaskService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
    public ClassTask getByClassIdAndTaskId(int classid,int taskid)
    {
        try
        {
            return classTaskDao.getByClassIdAndTaskId(classid,taskid);
        }
        catch (Exception ex)
        {
            logger.error("“ClassTaskService”类执行方法“getByClassIdAndTaskId”错误", ex);
            throw ex;
        }
    }
}
