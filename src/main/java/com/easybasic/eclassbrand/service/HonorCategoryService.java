package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("honorCategoryService")
public class HonorCategoryService {
    private static Logger logger = LoggerFactory.getLogger(HonorCategoryService.class);

    @Resource
    private IHonorCategoryDao honorCategoryDao;

    public HonorCategory getByPkId(Integer pkId)
    {
        try
        {
            return honorCategoryDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“HonorCategoryService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(HonorCategory honorCategory)
    {
        try {
            return honorCategoryDao.insert(honorCategory);
        }
        catch (Exception ex){
            logger.error("“HonorCategoryService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(HonorCategory honorCategory)
    {
        try {
            int re = honorCategoryDao.updateByPrimaryKey(honorCategory);
            return re;
        }
        catch (Exception ex){
            logger.error("“HonorCategoryService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            HonorCategory honorCategory = getByPkId(pkId);
            if(honorCategory!=null) {
                int re = honorCategoryDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“HonorCategoryService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<HonorCategory> getAllList()
    {
        try {
            return honorCategoryDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“HonorCategoryService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<HonorCategory> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return honorCategoryDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“HonorCategoryService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}
