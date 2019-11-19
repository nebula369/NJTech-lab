package com.easybasic.eclassbrand.service;
import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("cleanHealthService")
public class CleanHealthService {
    private static Logger logger = LoggerFactory.getLogger(CleanHealthService.class);

    @Resource
    private ICleanHealthDao cleanHealthDao;

    public CleanHealth getByPkId(Integer pkId)
    {
        try
        {
            return cleanHealthDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“CleanHealthService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(CleanHealth cleanHealth)
    {
        try {
            return cleanHealthDao.insert(cleanHealth);
        }
        catch (Exception ex){
            logger.error("“CleanHealthService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(CleanHealth cleanHealth)
    {
        try {
            int re = cleanHealthDao.updateByPrimaryKey(cleanHealth);
            return re;
        }
        catch (Exception ex){
            logger.error("“CleanHealthService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            CleanHealth cleanHealth = getByPkId(pkId);
            if(cleanHealth!=null) {
                int re = cleanHealthDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“CleanHealthService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<CleanHealth> getAllList()
    {
        try {
            return cleanHealthDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“CleanHealthService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<CleanHealth> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return cleanHealthDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“CleanHealthService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}
