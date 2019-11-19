package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.model.*;
        import com.easybasic.eclassbrand.dao.*;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.stereotype.Service;
        import javax.annotation.Resource;
        import java.util.List;

@Service("examPlanService")
public class ExamPlanService {
    private static Logger logger = LoggerFactory.getLogger(ExamPlanService.class);

    @Resource
    private IExamPlanDao examPlanDao;

    public ExamPlan getByPkId(Integer pkId)
    {
        try
        {
            return examPlanDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“ExamPlanService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(ExamPlan examPlan)
    {
        try {
            return examPlanDao.insert(examPlan);
        }
        catch (Exception ex){
            logger.error("“ExamPlanService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(ExamPlan examPlan)
    {
        try {
            int re = examPlanDao.updateByPrimaryKey(examPlan);
            return re;
        }
        catch (Exception ex){
            logger.error("“ExamPlanService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            ExamPlan examPlan = getByPkId(pkId);
            if(examPlan!=null) {
                int re = examPlanDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“ExamPlanService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<ExamPlan> getAllList()
    {
        try {
            return examPlanDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“ExamPlanService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<ExamPlan> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return examPlanDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“ExamPlanService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}
