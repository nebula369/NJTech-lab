package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("examineeService")
public class ExamineeService {
    private static Logger logger = LoggerFactory.getLogger(ExamineeService.class);

    @Resource
    private IExamineeDao examineeDao;

    public Examinee getByPkId(Integer pkId)
    {
        try
        {
            return examineeDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“ExamineeService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Examinee examinee)
    {
        try {
            return examineeDao.insert(examinee);
        }
        catch (Exception ex){
            logger.error("“ExamineeService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Examinee examinee)
    {
        try {
            int re = examineeDao.updateByPrimaryKey(examinee);
            return re;
        }
        catch (Exception ex){
            logger.error("“ExamineeService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            Examinee examinee = getByPkId(pkId);
            if(examinee!=null) {
                int re = examineeDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“ExamineeService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Examinee> getAllList()
    {
        try {
            return examineeDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“ExamineeService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<Examinee> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return examineeDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“ExamineeService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}
