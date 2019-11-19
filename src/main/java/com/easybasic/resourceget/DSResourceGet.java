package com.easybasic.resourceget;

import com.alibaba.fastjson.JSON;
import com.easybasic.component.MyDictionary;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.resourceget.IResourceGet;
import com.easybasic.resourceget.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component(value = "resourceget_ds")
public class DSResourceGet extends IResourceGet {

    @Override
    public TerminalPageModel getTerminalDetailListForPass(Integer pageIndex, Integer pageSize,String  categoryId,String searchKey) {
        MyDictionary param = new MyDictionary();
        param.put("categoryId",categoryId);
        param.put("pageIndex",pageIndex.toString());
        param.put("pageSize",pageSize.toString());
        param.put("searchKey",searchKey);
        param.put("siteUrl",getResourceServiceUrl());
        TerminalPageModel re = JSON.parseObject(ResourceGetHandler.getHttpWebResponseForPost(getResourceServiceUrl() + "/service/getTerminalList", param),TerminalPageModel.class);
        if (re == null)
        {
            return null;
        }
/*        TerminalPageModel result = new TerminalPageModel();
        result.setList(re.getList());
        result.setTotal(re.getTotal());*/
        return re;
    }

    @Override
    public List<VideoCategory> getVideoCategoryList() {
        MyDictionary param = new MyDictionary();
        param.put("siteUrl", getResourceServiceUrl());
        String data = ResourceGetHandler.getHttpWebResponseForPost(getResourceServiceUrl() + "/service/getTerminalCategoryList", param);
        List<VideoCategory> list = JSON.parseArray(data, VideoCategory.class);
        return list;
    }

    @Override
    public String setTerminalModel(Integer terminalId,Integer mode,Boolean isPatrol,Boolean isAttendance){
        MyDictionary param = new MyDictionary();
        param.put("terminalId",terminalId.toString());
        param.put("mode",mode.toString());
        param.put("isPatrol", (isPatrol?"1":"0"));
        param.put("isAttendance",(isAttendance?"1":"0"));
        param.put("siteUrl",getResourceServiceUrl());
        String data= ResourceGetHandler.getHttpWebResponseForPost(getResourceServiceUrl() + "/service/setTerminalModel", param);
        return data;
    }

    private List<Terminal> convertToVod(List<Terminal> list)
    {
        List<Terminal> result = new ArrayList<>();
        for (Terminal model : list) {
            Terminal vodModel = new Terminal();
            vodModel.setName(model.getName());
            result.add(vodModel);
        }
        return result;
    }

}
