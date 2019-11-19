package com.easybasic.kaoqin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.easybasic.kaoqin.dao.IUserPhotoDao;
import com.easybasic.kaoqin.model.UserPhoto;
import javax.annotation.Resource;
import java.util.List;

@Service("userPhotoService")
public class UserPhotoService {
    private static Logger logger = LoggerFactory.getLogger(UserPhotoService.class);

    @Resource
    private IUserPhotoDao userPhotoDao;
    public UserPhoto getByPkId(Integer pkId)
    {
        try
        {
            return userPhotoDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“UserPhotoService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(UserPhoto userphoto)
    {
        try {
            int re = userPhotoDao.insert(userphoto);
            return re;
        }
        catch (Exception ex){
            logger.error("“UserPhotoService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(UserPhoto userphoto)
    {
        try {
            int re = userPhotoDao.updateByPrimaryKey(userphoto);
            return re;
        }
        catch (Exception ex){
            logger.error("“UserPhotoService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            UserPhoto userphoto = getByPkId(pkId);
            if(userphoto!=null) {
                int re = userPhotoDao.deleteByPrimaryKey(pkId);
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“UserPhotoService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<UserPhoto> getAllList()
    {
        try {
            return userPhotoDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“UserPhotoService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }


    public UserPhoto getByUserId(int userId)
    {
        try {
            return userPhotoDao.selectByUserId(userId);
        }
        catch (Exception ex)
        {
            logger.error("“UserPhotoService”类执行方法“getByUserId”错误", ex);
            throw ex;
        }
    }
}
