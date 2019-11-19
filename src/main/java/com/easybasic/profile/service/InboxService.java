package com.easybasic.profile.service;

import com.easybasic.profile.dao.IInboxDao;
import com.easybasic.profile.model.Inbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("inboxService")
public class InboxService {

    private static Logger logger = LoggerFactory.getLogger(InboxService.class);

    @Resource
    private IInboxDao inboxDao;

    public Inbox getByPkId(Integer pkId)
    {
        try
        {
            return inboxDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“InboxService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Inbox inbox)
    {
        try {
            int re = inboxDao.insert(inbox);
            return re;
        }
        catch (Exception ex){
            logger.error("“InboxService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Inbox inbox)
    {
        try {
            int re = inboxDao.updateByPrimaryKey(inbox);
            return re;
        }
        catch (Exception ex){
            logger.error("“InboxService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            Inbox inbox = getByPkId(pkId);
            if(inbox!=null) {
                int re = inboxDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“InboxService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Inbox> getAllList()
    {
        try {
            return inboxDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“InboxService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<Inbox> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return inboxDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“InboxService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
}

