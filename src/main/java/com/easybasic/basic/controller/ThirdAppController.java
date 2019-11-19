package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.ThirdApp;
import com.easybasic.basic.service.ThirdAppService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manage/basic/thirdApp")
public class ThirdAppController extends BaseController {

    @Resource
    private ThirdAppService thirdAppService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String thirdAppList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/thirdApp", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/basic/set/thirdapp/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getThirdAppList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<ThirdApp> getThirdAppList(JqGridPageRequest pageRequest, String searchName)
    {
        String searchStr = "pkid>0";
        if(!StringUtils.isEmpty(searchName))
        {
            searchStr += " and name like '%"+searchName+"%'";
        }
        List<ThirdApp> list = thirdAppService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        JqGridPageResponse<ThirdApp> response = new JqGridPageResponse<>();
        response.setRows(list);
        return response;
    }

    @RequestMapping(value = "/doSetStatus", method = RequestMethod.POST)
    @ResponseBody
    public int thirdAppDoSetStatus(int id, int status)
    {
        ThirdApp thirdApp = thirdAppService.getByPkId(id);
        if(thirdApp!=null)
        {
            thirdApp.setStatus(status);
            thirdAppService.update(thirdApp);
            setOpLog("basic","第三方应用", "修改第三方应用状态：" + thirdApp.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String thirdAppAdd(Model model)
    {
        return "/manage/basic/set/thirdapp/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int thirdAppDoAdd(ThirdApp thirdApp)
    {
        if(StringUtils.isEmpty(thirdApp.getAppkey()))
        {
            return 0;
        }
        ThirdApp temp = thirdAppService.getByAppKey(thirdApp.getAppkey());
        if(temp != null)
        {
            return -1;
        }
        thirdApp.setCreatetime(new Date());
        thirdApp.setAppsecret(getUUID().replace("-",""));
        thirdAppService.insert(thirdApp);
        setOpLog("basic","第三方应用","增加第三方应用模块（"+thirdApp.getName()+"）");
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int thirdAppDoDel(int id)
    {
        ThirdApp thirdApp = thirdAppService.getByPkId(id);
        if(thirdApp!=null) {
            thirdAppService.delete(id);
            setOpLog("basic","第三方应用", "删除第三方应用模块("+thirdApp.getName()+")");
        }
        return 1;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String thirdAppEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                          Model model)
    {
        ThirdApp thirdApp = thirdAppService.getByPkId(TypeConverter.strToInt(id));
        if(thirdApp==null)
        {
            model.addAttribute("msg","要修改的应用信息不存在");
            return "/error/error";
        }
        model.addAttribute("app", thirdApp);
        return "/manage/basic/set/thirdapp/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int thirdAppDoEdit(ThirdApp editData)
    {
        ThirdApp thirdApp = thirdAppService.getByPkId(editData.getPkid());
        if(thirdApp!=null)
        {
            thirdApp.setName(editData.getName());
            thirdApp.setAppurl(editData.getAppurl());
            thirdApp.setLinktarget(editData.getLinktarget());
            thirdApp.setIcon(editData.getIcon());
            thirdApp.setStatus(editData.getStatus());
            thirdAppService.update(thirdApp);
            setOpLog("basic","第三方应用","修改第三方应用("+thirdApp.getName()+")参数");
        }
        return 1;
    }
}
