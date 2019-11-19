package com.easybasic.basic.service;

import com.easybasic.basic.dao.IIntranetIpDao;
import com.easybasic.basic.model.IntranetIp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("intranetIpService")
public class IntranetIpService {

    private static Logger logger = LoggerFactory.getLogger(IntranetIpService.class);

    @Resource
    private IIntranetIpDao iIntranetIpDao;

    public IntranetIp getByPkId(Integer pkId)
    {
        try
        {
            return iIntranetIpDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“IntranetIpService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(IntranetIp intranetIp)
    {
        try {
            return iIntranetIpDao.insert(intranetIp);
        }
        catch (Exception ex){
            logger.error("“IntranetIpService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(IntranetIp intranetIp)
    {
        try {
            int re = iIntranetIpDao.updateByPrimaryKey(intranetIp);
            return re;
        }
        catch (Exception ex){
            logger.error("“IntranetIpService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = iIntranetIpDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“IntranetIpService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<IntranetIp> getAllList()
    {
        try {
            return iIntranetIpDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“IntranetIpService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

}

