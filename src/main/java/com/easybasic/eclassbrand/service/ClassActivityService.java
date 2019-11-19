package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.dao.IClassActivityDao;
import com.easybasic.eclassbrand.dao.IClassTaskDao;
import com.easybasic.eclassbrand.model.ClassActivity;
import com.easybasic.eclassbrand.model.ClassTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("classActivityService")
public class ClassActivityService {
    private static Logger logger = LoggerFactory.getLogger(ClassActivityService.class);

    @Resource
    private IClassActivityDao classTaskDao;

    public ClassActivity getByPkId(Integer pkId)
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

    public int insert(ClassActivity classTask)
    {
        try {
            return classTaskDao.insert(classTask);
        }
        catch (Exception ex){
            logger.error("“ClassTaskService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(ClassActivity classTask)
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
            ClassActivity classTask = getByPkId(pkId);
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

    public List<ClassActivity> getAllList()
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
    public List<ClassActivity> getListBySearch(String searchStr, String orderStr)
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
    public ClassActivity getByClassIdAndTaskId(int classid,int taskid)
    {
        try
        {
            return classTaskDao.getByClassIdAndTaskId(classid,taskid);
        }
        catch (Exception ex)
        {
            logger.error("“TaskService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }
}
