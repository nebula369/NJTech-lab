package com.easybasic.eclassbrand.controller;


import com.easybasic.basic.model.DataDicVal;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.model.User;
import com.easybasic.basic.service.UnitService;
import com.easybasic.basic.service.UserService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.kaoqin.dao.IUserGroupAndUserDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.druid.util.StringUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.service.*;
import com.easybasic.basic.service.DataDicValService;

@Controller
@RequestMapping("/manage/eclassbrand/website")
public class WebsiteController extends BaseController {
    @Resource
    private UnitService unitService;
    @Resource
    private WebsiteService websiteservice;
    /**
     * @Description: 网址列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String websiteList(Model model)
    {
        String re = setPageCommonModel(model, "eclassbrand","/manage/eclassbrand/website", false);
        if(!StringUtils.isEmpty(re))
        {
            List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
            initUnitList(list);
            model.addAttribute("unitList", list);
            return "manage/eclassbrand/website/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getWebsiteListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Website> getWebsiteListForPage(JqGridPageRequest pageRequest, Integer unitId, String searchKey)
    {
        String searchStr = "unitid=" + unitId;
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Website> list = websiteservice.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<Website> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Website> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    /**
     * @Description: 新增页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String websiteAdd(Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        return "manage/eclassbrand/website/add";
    }
    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int websiteDoAdd(Website website)
    {
        website.setCreatetime(new Date());
        websiteservice.insert(website);
        setOpLog("eclassbrand","网站地址", "增加网站地址：" + website.getName());
        return 1;
    }
    /**
     * @Description: 编辑页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String websiteEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        Website website = websiteservice.getByPkId(TypeConverter.strToInt(id));
        if(website==null)
        {
            model.addAttribute("msg","要编辑的网址不存在");
            return "/error/error";
        }
        model.addAttribute("website", website);
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        return "manage/eclassbrand/website/edit";
    }
    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int websitetDoEdit(Website websiteData)
    {
        Website website = websiteservice.getByPkId(websiteData.getPkid());
        if(website!=null) {
            website.setName(websiteData.getName());
            website.setSortnum(websiteData.getSortnum());
            website.setUrl(websiteData.getUrl());
            websiteservice.update(website);
            setOpLog("eclassbrand","网站地址", "编辑网站地址：" + website.getName());
        }
        return 1;
    }
    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int taskDoDel(int id)
    {
        Website website = websiteservice.getByPkId(id);
        if(website!=null) {
            websiteservice.delete(id);
            setOpLog("eclassbrand","网站地址", "删除网站地址：" + website.getName());
        }
        return 1;
    }
}
