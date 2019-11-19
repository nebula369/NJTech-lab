package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.dao.IClassRecommendDao;
import com.easybasic.eclassbrand.model.ClassRecommend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("classRecommendService")
public class ClassRecommendService {
    private static Logger logger = LoggerFactory.getLogger(ClassRecommendService.class);

    @Resource
    private IClassRecommendDao classRecommendDao;

    public ClassRecommend getByPkId(Integer pkId)
    {
        try
        {
            return classRecommendDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“ClassRecommendService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(ClassRecommend classrecommend)
    {
        try {
            return classRecommendDao.insert(classrecommend);
        }
        catch (Exception ex){
            logger.error("“ClassRecommendService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(ClassRecommend classrecommend)
    {
        try {
            int re = classRecommendDao.updateByPrimaryKey(classrecommend);
            return re;
        }
        catch (Exception ex){
            logger.error("“ClassRecommendService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            ClassRecommend classrecommend = getByPkId(pkId);
            if(classrecommend!=null) {
                int re = classRecommendDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“ClassRecommendService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<ClassRecommend> getAllList()
    {
        try {
            return classRecommendDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“ClassRecommendService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<ClassRecommend> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return classRecommendDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“ClassRecommendService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }  public ClassRecommend getByClassIdAndRecommendId(int classid,int recommendid)
    {
        try
        {
            return classRecommendDao.getByClassIdAndRecommendId(classid,recommendid);
        }
        catch (Exception ex)
        {
            logger.error("“TaskService”类执行方法“getByClassIdAndTaskId”错误", ex);
            throw ex;
        }
    }
}
