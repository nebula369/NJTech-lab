package com.easybasic.eclassbrand.controller;


import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.BasicDs;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.BasicDsService;
import com.easybasic.basic.service.UnitService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.eclassbrand.model.Website;
import com.easybasic.eclassbrand.service.WebsiteService;
import com.easybasic.resourceget.IResourceGet;
import com.easybasic.resourceget.model.Terminal;
import com.easybasic.resourceget.model.TerminalPageModel;
import com.easybasic.resourceget.model.VideoCategory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.easybasic.resourceget.ResourceGetFactory;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/eclassbrand/mode")
public class ModelController extends BaseController {
    @Resource
    private UnitService unitService;
    @Resource
    private WebsiteService websiteservice;
    @Resource
    private ResourceGetFactory resourceGetFactory;
    @Resource
    private BasicDsService basicDsService;
    /**
     * @Description: 终端列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String TerminalList(Model model)
    {
        String re = setPageCommonModel(model, "eclassbrand","/manage/eclassbrand/mode", false);
        if(!StringUtils.isEmpty(re)) {
            Unit unit = unitService.getUnitForCache(1);
            if (unit != null) {
                model.addAttribute("topUnitName", unit.getName());
            } else {
                model.addAttribute("topUnitName", "未知");
            }
            BasicDs basicDs = basicDsService.getBasicDsForCache();
            model.addAttribute("siteUrl", basicDs.getDsUrl());

            IResourceGet resourceGet = resourceGetFactory.getResourceGetInstance("ds");
            TerminalPageModel list = resourceGet.getTerminalDetailListForPass(1, 20, "1", "");
            if (list != null)
                model.addAttribute("terminallist", list.getList());
            else
                model.addAttribute("terminallist", null);

            return "/manage/eclassbrand/mode/terminallist";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }


    /**
     * @Description: 新增页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/setmode", method = RequestMethod.GET)
    public String setmode(Model model,String terminalids)
    {
        List<Terminal> list=new ArrayList<>();
        String[] ids=terminalids.split(",");
        IResourceGet resourceGet = resourceGetFactory.getResourceGetInstance("ds");
        TerminalPageModel data = resourceGet.getTerminalDetailListForPass(1,200,"0","");
        for(Terminal item:data.getList()){
            if(Arrays.asList(ids).contains(item.getPkid().toString())){
                list.add(item);
            }
        }
        model.addAttribute("terminalids", terminalids);
        model.addAttribute("terminallist", list);
        model.addAttribute("terminalcount", list.size());
        return "manage/eclassbrand/mode/setmode";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int activitytDoEdit(String ids,Integer mode,Boolean isAttendence,Boolean isPatrol) throws ParseException {
        String[] terminalidd=ids.split(",");
        IResourceGet resourceGet = resourceGetFactory.getResourceGetInstance("ds");
        TerminalPageModel data = resourceGet.getTerminalDetailListForPass(1,200,"0","");
        for(Terminal item:data.getList()){
            if(Arrays.asList(terminalidd).contains(item.getPkid().toString())){
                resourceGet.setTerminalModel(item.getPkid(),mode,isPatrol,isAttendence);
            }
        }

        return 1;
    }


    @RequestMapping(value = "/getCategoryList")
    @ResponseBody
    public String getCategoryList(@RequestParam("id") String id, @RequestParam(value = "level", required = false, defaultValue = "0") String level) {
        String data = "";
        int pkid = TypeConverter.strToInt(id);
        int levelId = TypeConverter.strToInt(level);
        IResourceGet resourceGet = resourceGetFactory.getResourceGetInstance("ds");
        List<VideoCategory> list = resourceGet.getVideoCategoryList();
        if (levelId == 0) {
            data = getSubNodeHtml(list, 0, levelId + 1);
        } else {
            data = getSubNodeHtml(list, pkid, levelId + 1);
        }
        return "{\"success\":true,\"data\":[" + data + "]}";
    }

    @RequestMapping(value = "/getTerminalList")
    @ResponseBody
    public TerminalPageModel getTerminalList(@RequestParam("categoryid") String categoryid,@RequestParam("key") String key,@RequestParam("pageindex") Integer pageindex) {
        IResourceGet resourceGet = resourceGetFactory.getResourceGetInstance("ds");
        TerminalPageModel list = resourceGet.getTerminalDetailListForPass(pageindex, 20, categoryid, key);
        return list;
    }

    public Integer GetChildCount(List<VideoCategory> list,VideoCategory model){
        Integer childCount=0;
        for(VideoCategory vodcate: list) {
            if (vodcate.getParentid().equals( model.getPkid().toString()))
                childCount++;
        }
        return childCount;
    }

    public String getSubNodeHtml(List<VideoCategory> list,Integer pkid,Integer level){
        String data="";
        for(VideoCategory vodcate: list) {
            if(vodcate.getParentid().equals(pkid.toString())) {
                Integer childCount = GetChildCount(list, vodcate);
                if (StringUtils.isEmpty(data)) {
                    data = "{\"key\":\"" + vodcate.getPkid() + "\",\"title\":\"" +
                            vodcate.getName() + "\",\"code\":\"" + level + "\",\"name\":\"" +
                            vodcate.getName() + "\",\"parent\":\"" + vodcate.getParentid() +
                            "\",\"path\":null,\"childCount\":"+childCount+",\"level\":" + (level + 1) +
                            ",\"isFolder\":" + (level == 1 ? "true" : "false") + ",\"isLazy\":" + (childCount > 0 ? "true" : "false") + "}";
                } else {
                    data += ",{\"key\":\"" + vodcate.getPkid() + "\",\"title\":\"" +
                            vodcate.getName() + "\",\"code\":\"" + level + "\",\"name\":\"" +
                            vodcate.getName() + "\",\"parent\":\"" + vodcate.getParentid() +
                            "\",\"path\":null,\"childCount\":"+childCount+",\"level\":" + (level + 1) +
                            ",\"isFolder\":" + (level == 1 ? "true" : "false") + ",\"isLazy\":" + (childCount > 0 ? "true" : "false") + "}";
                }
            }
        }
        return  data;
    }
}
