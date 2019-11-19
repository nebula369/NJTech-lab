package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.dao.INewsDao;
import com.easybasic.eclassbrand.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("newsService")
public class NewsService {
    private static Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Resource
    private INewsDao newsDao;

    public News getByPkId(Integer pkId)
    {
        try
        {
            return newsDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“NewsService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(News website)
    {
        try {
            return newsDao.insert(website);
        }
        catch (Exception ex){
            logger.error("“NewsService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(News website)
    {
        try {
            int re = newsDao.updateByPrimaryKey(website);
            return re;
        }
        catch (Exception ex){
            logger.error("“NewsService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            News website = getByPkId(pkId);
            if(website!=null) {
                int re = newsDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“NewsService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<News> getAllList()
    {
        try {
            return newsDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“NewsService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<News> getNewsListForPage(String searchStr, String orderStr)
    {
        try {
            return newsDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“NewsService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
}
