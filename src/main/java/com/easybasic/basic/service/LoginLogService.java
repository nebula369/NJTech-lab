package com.easybasic.basic.service;

import com.easybasic.basic.dao.ILoginLogDao;
import com.easybasic.basic.model.LoginLog;
import com.easybasic.component.Utils.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("loginLogService")
public class LoginLogService {

    private static Logger logger = LoggerFactory.getLogger(LoginLogService.class);

    @Resource
    private ILoginLogDao loginLogDao;

    public LoginLog getByPkId(Integer pkId)
    {
        try
        {
            return loginLogDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“LoginLogService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(LoginLog loginLog)
    {
        try {
            return loginLogDao.insert(loginLog);
        }
        catch (Exception ex){
            logger.error("“LoginLogService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(LoginLog loginLog)
    {
        try {
            int re = loginLogDao.updateByPrimaryKey(loginLog);
            return re;
        }
        catch (Exception ex){
            logger.error("“LoginLogService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            return loginLogDao.deleteByPrimaryKey(pkId);
        }
        catch (Exception ex){
            logger.error("“LoginLogService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<LoginLog> getAllList()
    {
        try {
            return loginLogDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“LoginLogService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<LoginLog> getListBySearch(String searchStr, String orderStr)
    {
        try {
            if (!ToolsUtil.isSafeSqlString(searchStr)) return new ArrayList<>();
            return loginLogDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“LoginLogService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

    public int deleteByUnValidate()
    {
        try {
            return loginLogDao.deleteByUnValidate();
        }
        catch (Exception ex){
            logger.error("“LoginLogService”类执行方法“deleteByUnValidate”错误", ex);
            throw ex;
        }
    }

}

