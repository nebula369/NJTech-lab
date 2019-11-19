package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.LoginLog;
import com.easybasic.basic.model.OpLog;
import com.easybasic.basic.service.LoginLogService;
import com.easybasic.basic.service.OpLogService;
import com.easybasic.component.BaseController;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/manage/basic")
public class LogController extends BaseController {

    @Resource
    private LoginLogService loginLogService;
    @Resource
    private OpLogService opLogService;

    @RequestMapping(value = "/loginLog", method = RequestMethod.GET)
    public String loginLog(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/loginLog", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/basic/log/loginlog";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getLoginLogPageList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<LoginLog> getLoginLogPageList(JqGridPageRequest pageRequest,String searchDate, String searchName)
    {
        String searchStr = "pkid>0";
        if(!StringUtils.isEmpty(searchDate)) {
            String[] dateStr = searchDate.split("--");
            searchStr += " and Date(createtime)>='" + dateStr[0] + "' and Date(createtime)<='" + dateStr[1] + "'";
        }
        if(!StringUtils.isEmpty(searchName))
        {
            searchStr += " and username like '%"+searchName+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<LoginLog> list = loginLogService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<LoginLog> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<LoginLog> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    @RequestMapping(value = "/opLog", method = RequestMethod.GET)
    public String opLog(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/opLog", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/basic/log/oplog";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getOpLogPageList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<OpLog> getOpLogPageList(JqGridPageRequest pageRequest,String searchDate, String searchName)
    {
        String searchStr = "pkid>0";
        if(!StringUtils.isEmpty(searchDate)) {
            String[] dateStr = searchDate.split("--");
            searchStr += " and Date(createtime)>='" + dateStr[0] + "' and Date(createtime)<='" + dateStr[1] + "'";
        }
        if(!StringUtils.isEmpty(searchName))
        {
            searchStr += " and username like '%"+searchName+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<OpLog> list = opLogService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<OpLog> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<OpLog> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }
}
