package com.easybasic.profile.service;

import com.easybasic.profile.dao.IOutboxDao;
import com.easybasic.profile.model.Outbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("outboxService")
public class OutboxService {

    private static Logger logger = LoggerFactory.getLogger(OutboxService.class);

    @Resource
    private IOutboxDao outboxDao;

    public Outbox getByPkId(Integer pkId)
    {
        try
        {
            return outboxDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“OutboxService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Outbox outbox)
    {
        try {
            int re = outboxDao.insert(outbox);
            return re;
        }
        catch (Exception ex){
            logger.error("“OutboxService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Outbox outbox)
    {
        try {
            int re = outboxDao.updateByPrimaryKey(outbox);
            return re;
        }
        catch (Exception ex){
            logger.error("“OutboxService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            Outbox outbox = getByPkId(pkId);
            if(outbox!=null) {
                int re = outboxDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“OutboxService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Outbox> getAllList()
    {
        try {
            return outboxDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“OutboxService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<Outbox> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return outboxDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“OutboxService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
}

