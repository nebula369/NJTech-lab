package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.IntranetIp;
import com.easybasic.basic.model.SiteConfig;
import com.easybasic.basic.service.IntranetIpService;
import com.easybasic.basic.service.SiteConfigService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ToolsUtil;
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
@RequestMapping("/manage/basic/intranetIp")
public class IntranetIpController extends BaseController {

    @Resource
    private IntranetIpService intranetIpService;
    @Resource
    private SiteConfigService siteConfigService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String intranetIpList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/intranetIp", false);
        if(!StringUtils.isEmpty(re))
        {
            SiteConfig siteConfig = siteConfigService.getByPkId(1);
            model.addAttribute("isAllow", siteConfig.getIsonlyallowintranetip());
            return "manage/basic/set/intranetip/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }

    }

    @RequestMapping(value = "/getIntranetIpList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<IntranetIp> getIntranetIpList(JqGridPageRequest pageRequest)
    {
        List<IntranetIp> list = intranetIpService.getAllList();
        JqGridPageResponse<IntranetIp> response = new JqGridPageResponse<>();
        response.setRows(list);
        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String intranetIpAdd(Model model)
    {
        return "manage/basic/set/intranetip/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int intranetIpDoAdd(IntranetIp intranetIp)
    {
        intranetIp.setIpstart(ToolsUtil.ipToLong(intranetIp.getIpstartstr()));
        intranetIp.setIpend(ToolsUtil.ipToLong(intranetIp.getIpendstr()));
        intranetIp.setCreatetime(new Date());
        intranetIpService.insert(intranetIp);
        setOpLog("basic","内网IP段设置", "增加内网IP段数据：" + intranetIp.getIpstartstr() + "-" + intranetIp.getIpendstr());
        return 1;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String intranetIpEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                                 Model model)
    {
        IntranetIp intranetIp = intranetIpService.getByPkId(TypeConverter.strToInt(id));
        if(intranetIp==null)
        {
            model.addAttribute("msg","要编辑的内网IP段数据为空");
            return "/error/error";
        }
        model.addAttribute("data", intranetIp);
        return "manage/basic/set/intranetip/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int intranetIpDoEdit(IntranetIp editData)
    {
        IntranetIp intranetIp = intranetIpService.getByPkId(editData.getPkid());
        if(intranetIp!=null)
        {
            intranetIp.setIpstartstr(editData.getIpstartstr());
            intranetIp.setIpendstr(editData.getIpendstr());
            intranetIp.setIpstart(ToolsUtil.ipToLong(intranetIp.getIpstartstr()));
            intranetIp.setIpend(ToolsUtil.ipToLong(intranetIp.getIpendstr()));
            intranetIp.setRemark(editData.getRemark());
            intranetIpService.update(intranetIp);
            setOpLog("basic","内网IP段设置","编辑内网IP段数据：" + intranetIp.getIpstartstr() + "-" + intranetIp.getIpendstr());
        }
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int intranetIpDoDel(int id)
    {
        IntranetIp intranetIp = intranetIpService.getByPkId(id);
        if(intranetIp!=null)
        {
            intranetIpService.delete(id);
            setOpLog("basic","内网IP段设置","删除内网IP段数据：" + intranetIp.getIpstartstr() + "-" + intranetIp.getIpendstr());
        }
        return 1;
    }

    @RequestMapping(value = "/setAllowIntranetIp", method = RequestMethod.POST)
    @ResponseBody
    public int setAllowIntranetIp(int isAllow)
    {
        SiteConfig siteConfig = siteConfigService.getByPkId(1);
        if(siteConfig!=null)
        {
            siteConfig.setIsonlyallowintranetip(isAllow);
            siteConfigService.update(siteConfig);
            setOpLog("basic","内网IP段设置","设置只允许内网IP段访问：" + isAllow);
        }
        return 1;
    }
}
