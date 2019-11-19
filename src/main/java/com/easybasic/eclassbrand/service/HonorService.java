package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("honorService")
public class HonorService {
    private static Logger logger = LoggerFactory.getLogger(HonorService.class);

    @Resource
    private IHonorDao honorDao;

    public Honor getByPkId(Integer pkId)
    {
        try
        {
            return honorDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“HonorService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Honor honor)
    {
        try {
            return honorDao.insert(honor);
        }
        catch (Exception ex){
            logger.error("“HonorService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Honor honor)
    {
        try {
            int re = honorDao.updateByPrimaryKey(honor);
            return re;
        }
        catch (Exception ex){
            logger.error("“HonorService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            Honor honor = getByPkId(pkId);
            if(honor!=null) {
                int re = honorDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“HonorService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Honor> getAllList()
    {
        try {
            return honorDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“HonorService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<Honor> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return honorDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“HonorService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}
