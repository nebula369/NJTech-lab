package com.easybasic.kaoqin.service;

import com.easybasic.kaoqin.dao.IOperatLogDao;
import com.easybasic.kaoqin.model.OperatLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("operatLogService")
public class OperatLogService {
    private static Logger logger = LoggerFactory.getLogger(OperatLogService.class);

    @Resource
    private IOperatLogDao operatLogDao;

    public OperatLog getByPkId(Integer pkId)
    {
        try
        {
            return operatLogDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“OperatLogService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(OperatLog operatlog)
    {
        try {
            int re = operatLogDao.insert(operatlog);
            return re;
        }
        catch (Exception ex){
            logger.error("“OperatLogService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(OperatLog operatlog)
    {
        try {
            int re = operatLogDao.updateByPrimaryKey(operatlog);
            return re;
        }
        catch (Exception ex){
            logger.error("“OperatLogService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            OperatLog operatlog = getByPkId(pkId);
            if(operatlog!=null) {
                int re = operatLogDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“OperatLogService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<OperatLog> getAllList()
    {
        try {
            return operatLogDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“OperatLogService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<OperatLog> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return operatLogDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“OperatLogService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}
