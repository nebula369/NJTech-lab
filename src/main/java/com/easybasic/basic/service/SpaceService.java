package com.easybasic.basic.service;

import com.easybasic.basic.dao.ISpaceDao;
import com.easybasic.basic.model.Space;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("spaceService")
public class SpaceService {

    private static Logger logger = LoggerFactory.getLogger(SpaceService.class);

    @Resource
    private ISpaceDao spaceDao;

    public Space getByPkId(Integer pkId)
    {
        try
        {
            return spaceDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“SpaceService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(Space space)
    {
        try {
            return spaceDao.insert(space);
        }
        catch (Exception ex){
            logger.error("“SpaceService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(Space space)
    {
        try {
            int re = spaceDao.updateByPrimaryKey(space);
            return re;
        }
        catch (Exception ex){
            logger.error("“SpaceService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            Space space = getByPkId(pkId);
            if(space!=null) {
                int re = spaceDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“SpaceService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<Space> getAllList()
    {
        try {
            return spaceDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“SpaceService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<Space> getListBySearch(String searchStr, String orderStr)
    {
        try {
            return spaceDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“SpaceService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

    public Space getByClassId(int classId)
    {
        try {
            return spaceDao.selectByClassId(classId);
        }
        catch (Exception ex)
        {
            logger.error("“SpaceService”类执行方法“getByClassId”错误", ex);
            throw ex;
        }
    }

}

