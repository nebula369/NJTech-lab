package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.dao.IRecommendDao;
import com.easybasic.eclassbrand.model.Recommend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("recommendService")
public class RecommendService {
    private static Logger logger = LoggerFactory.getLogger(RecommendService.class);

    @Resource
    private IRecommendDao recommendDao;

    public Recommend getByPkId(Integer pkId)
    {
        try
        {
            return recommendDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“RecommendService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Recommend recommend)
    {
        try {
            return recommendDao.insert(recommend);
        }
        catch (Exception ex){
            logger.error("“RecommendService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Recommend recommend)
    {
        try {
            int re = recommendDao.updateByPrimaryKey(recommend);
            return re;
        }
        catch (Exception ex){
            logger.error("“RecommendService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            Recommend recommend = getByPkId(pkId);
            if(recommend!=null) {
                int re = recommendDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“RecommendService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Recommend> getAllList()
    {
        try {
            return recommendDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“RecommendService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<Recommend> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return recommendDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“RecommendService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}
