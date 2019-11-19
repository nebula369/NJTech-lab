package com.easybasic.eclassbrand.service;
import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("taskService")
public class TaskService {
    private static Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Resource
    private ITaskDao taskDao;

    public Task getByPkId(Integer pkId)
    {
        try
        {
            return taskDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“TaskService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Task task)
    {
        try {
            return taskDao.insert(task);
        }
        catch (Exception ex){
            logger.error("“TaskService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Task task)
    {
        try {
            int re = taskDao.updateByPrimaryKey(task);
            return re;
        }
        catch (Exception ex){
            logger.error("“TaskService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            Task task = getByPkId(pkId);
            if(task!=null) {
                int re = taskDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“TaskService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Task> getAllList()
    {
        try {
            return taskDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“TaskService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<Task> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return taskDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“TaskService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}
