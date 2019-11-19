package com.easybasic.basic.service;

import com.easybasic.basic.dao.IOpLogDao;
import com.easybasic.basic.model.OpLog;
import com.easybasic.component.Utils.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("opLogService")
public class OpLogService {

    private static Logger logger = LoggerFactory.getLogger(OpLogService.class);

    @Resource
    private IOpLogDao opLogDao;

    public OpLog getByPkId(Integer pkId)
    {
        try
        {
            return opLogDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“OpLogService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(OpLog opLog)
    {
        try {
            return opLogDao.insert(opLog);
        }
        catch (Exception ex){
            logger.error("“OpLogService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(OpLog opLog)
    {
        try {
            int re = opLogDao.updateByPrimaryKey(opLog);
            return re;
        }
        catch (Exception ex){
            logger.error("“OpLogService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            return opLogDao.deleteByPrimaryKey(pkId);
        }
        catch (Exception ex){
            logger.error("“OpLogService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public int deleteByUnValidate()
    {
        try {
            return opLogDao.deleteByUnValidate();
        }
        catch (Exception ex){
            logger.error("“OpLogService”类执行方法“deleteByUnValidate”错误", ex);
            throw ex;
        }
    }

    public List<OpLog> getAllList()
    {
        try {
            return opLogDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“OpLogService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<OpLog> getListBySearch(String searchStr, String orderStr)
    {
        try {
            if (!ToolsUtil.isSafeSqlString(searchStr)) return new ArrayList<>();
            return opLogDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“OpLogService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}

