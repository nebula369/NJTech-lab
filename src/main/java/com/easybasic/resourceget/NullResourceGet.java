package com.easybasic.resourceget;

import com.easybasic.resourceget.model.TerminalPageModel;
import com.easybasic.resourceget.model.VideoCategory;

import java.util.List;

public class NullResourceGet extends IResourceGet {

    @Override
    public TerminalPageModel getTerminalDetailListForPass(Integer pageIndex, Integer pageSize,String  categoryId,String searchKey) {
        return null;
    }

    @Override
    public List<VideoCategory> getVideoCategoryList() {
        return null;
    }

    @Override
    public String setTerminalModel(Integer terminalId,Integer mode,Boolean isPatrol,Boolean isAttendance){
        return null;
    }
}
