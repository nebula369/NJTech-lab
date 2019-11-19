package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.dao.INewsTypeDao;
import com.easybasic.eclassbrand.model.NewsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("newsTypeService")
public class NewsTypeService {
    private static Logger logger = LoggerFactory.getLogger(NewsTypeService.class);

    @Resource
    private INewsTypeDao newsDao;

    public NewsType getByPkId(Integer pkId)
    {
        try
        {
            return newsDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“NewsTypeService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(NewsType website)
    {
        try {
            return newsDao.insert(website);
        }
        catch (Exception ex){
            logger.error("“NewsTypeService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(NewsType website)
    {
        try {
            int re = newsDao.updateByPrimaryKey(website);
            return re;
        }
        catch (Exception ex){
            logger.error("“NewsTypeService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            NewsType website = getByPkId(pkId);
            if(website!=null) {
                int re = newsDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“NewsTypeService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<NewsType> getAllList()
    {
        try {
            return newsDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“NewsTypeService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<NewsType> getNewsListForPage(String searchStr, String orderStr)
    {
        try {
            return newsDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“NewsTypeService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
}
