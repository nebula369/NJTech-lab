package com.easybasic.kaoqin.service;

import com.easybasic.kaoqin.dao.IAppointmentDao;
import com.easybasic.kaoqin.model.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("AppointmentService")
public class AppointmentService {
    private static Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @Resource
    private IAppointmentDao AppointmentDao;

    public Appointment getByPkId(Integer pkId)
    {
        try
        {
            return AppointmentDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“OperatLogService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Appointment operatlog)
    {
        try {
            int re = AppointmentDao.insert(operatlog);
            return re;
        }
        catch (Exception ex){
            logger.error("“OperatLogService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Appointment operatlog)
    {
        try {
            int re = AppointmentDao.updateByPrimaryKey(operatlog);
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
            Appointment operatlog = getByPkId(pkId);
            if(operatlog!=null) {
                int re = AppointmentDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“OperatLogService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Appointment> getAllList()
    {
        try {
            return AppointmentDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“OperatLogService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<Appointment> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return AppointmentDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“OperatLogService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

}
