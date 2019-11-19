package com.easybasic.eclassbrand.service;
import com.easybasic.eclassbrand.model.Website;
import com.easybasic.eclassbrand.dao.IWebsiteDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("websiteService")
public class WebsiteService {
    private static Logger logger = LoggerFactory.getLogger(WebsiteService.class);

    @Resource
    private IWebsiteDao websiteDao;

    public Website getByPkId(Integer pkId)
    {
        try
        {
            return websiteDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“WebsiteService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Website website)
    {
        try {
            return websiteDao.insert(website);
        }
        catch (Exception ex){
            logger.error("“WebsiteService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Website website)
    {
        try {
            int re = websiteDao.updateByPrimaryKey(website);
            return re;
        }
        catch (Exception ex){
            logger.error("“WebsiteService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            Website website = getByPkId(pkId);
            if(website!=null) {
                int re = websiteDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“WebsiteService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Website> getAllList()
    {
        try {
            return websiteDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“WebsiteService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<Website> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return websiteDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“WebsiteService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
}
