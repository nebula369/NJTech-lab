package com.easybasic.eclassbrand.service;

import com.easybasic.eclassbrand.model.*;
        import com.easybasic.eclassbrand.dao.*;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.stereotype.Service;
        import javax.annotation.Resource;
        import java.util.List;

@Service("examRoomService")
public class ExamRoomService {
    private static Logger logger = LoggerFactory.getLogger(ExamRoomService.class);

    @Resource
    private IExamRoomDao examRoomDao;

    public ExamRoom getByPkId(Integer pkId)
    {
        try
        {
            return examRoomDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“ExamRoomService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(ExamRoom examRoom)
    {
        try {
            return examRoomDao.insert(examRoom);
        }
        catch (Exception ex){
            logger.error("“ExamRoomService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(ExamRoom examRoom)
    {
        try {
            int re = examRoomDao.updateByPrimaryKey(examRoom);
            return re;
        }
        catch (Exception ex){
            logger.error("“ExamRoomService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            ExamRoom examRoom = getByPkId(pkId);
            if(examRoom!=null) {
                int re = examRoomDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“ExamRoomService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<ExamRoom> getAllList()
    {
        try {
            return examRoomDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“ExamRoomService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<ExamRoom> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return examRoomDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“ExamRoomService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }
    public ExamRoom getBySpaceId(int spaceid)
    {
        try
        {
            return examRoomDao.getBySpaceId(spaceid);
        }
        catch (Exception ex)
        {
            logger.error("“ExamRoomService”类执行方法“spaceid”错误", ex);
            throw ex;
        }
    }
}
