package com.easybasic.resourceget;

import com.easybasic.basic.model.BasicDs;
import com.easybasic.basic.service.BasicDsService;
import com.easybasic.resourceget.model.TerminalPageModel;
import com.easybasic.resourceget.model.VideoCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public abstract class IResourceGet {

    @Resource
    private BasicDsService basicDsService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String getResourceServiceUrl()
    {
        BasicDs siteConfig = basicDsService.getBasicDsForCache();
        return siteConfig.getDsUrl();
    }

    /**
     * 分页获取当前分类下视频列表
     * @param categoryId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public abstract TerminalPageModel getTerminalDetailListForPass(Integer pageIndex, Integer pageSize,String  categoryId,String searchKey);

    /**
     * 分页获取视频分类
     * @return
     */
    public abstract List<VideoCategory> getVideoCategoryList();

    /**
     * 分页获取视频分类
     * @return
     */
    public abstract String setTerminalModel(Integer terminalId,Integer mode,Boolean isPatrol,Boolean isAttendance);


    protected int caculatePageCount(int recourdCount, int pageSize)
    {
        int count = (int)Math.ceil((recourdCount * 1.0) / pageSize);
        if (count == 0) count = 1;
        return count;
    }
}
