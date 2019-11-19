package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.model.*;
        import com.easybasic.eclassbrand.dao.*;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.stereotype.Service;
        import javax.annotation.Resource;
        import java.util.List;

@Service("examPlanMineeService")
public class ExamPlanMineeService {
    private static Logger logger = LoggerFactory.getLogger(ExamPlanMineeService.class);

    @Resource
    private IExamPlanMineeDao examPlanMineeDao;

    public ExamPlanMinee getByPkId(Integer pkId)
    {
        try
        {
            return examPlanMineeDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“ExamPlanMineeService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(ExamPlanMinee examPlanMinee)
    {
        try {
            return examPlanMineeDao.insert(examPlanMinee);
        }
        catch (Exception ex){
            logger.error("“ExamPlanMineeService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(ExamPlanMinee examPlanMinee)
    {
        try {
            int re = examPlanMineeDao.updateByPrimaryKey(examPlanMinee);
            return re;
        }
        catch (Exception ex){
            logger.error("“ExamPlanMineeService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            ExamPlanMinee examPlanMinee = getByPkId(pkId);
            if(examPlanMinee!=null) {
                int re = examPlanMineeDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“ExamPlanMineeService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<ExamPlanMinee> getAllList()
    {
        try {
            return examPlanMineeDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“ExamPlanMineeService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<ExamPlanMinee> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return examPlanMineeDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“ExamPlanMineeService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
    public ExamPlanMinee getByPlanIdAndMineeId(int planid,int mineeid)
    {
        try
        {
            return examPlanMineeDao.getByPlanIdAndMineeId(planid,mineeid);
        }
        catch (Exception ex)
        {
            logger.error("“ExamPlanMineeService”类执行方法“getByPlanIdAndMineeId”错误", ex);
            throw ex;
        }
    }
}
