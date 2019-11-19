package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.DataDic;
import com.easybasic.basic.model.DataDicVal;
import com.easybasic.basic.service.DataDicService;
import com.easybasic.basic.service.DataDicValService;
import com.easybasic.component.BaseController;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/basic/dataDic")
public class DataDicController extends BaseController {

    @Resource
    private DataDicService dataDicService;
    @Resource
    private DataDicValService dataDicValService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String dataDicList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/dataDic", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/basic/set/datadic/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getDataDicPageList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<DataDic> getDataDicPageList(JqGridPageRequest pageRequest, String searchName)
    {
        String searchStr = "pkid>0";
        if(!StringUtils.isEmpty(searchName))
        {
            searchStr += " and (name like '%"+searchName+"%' or code like '%"+searchName+"%')";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<DataDic> list = dataDicService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<DataDic> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<DataDic> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    @RequestMapping(value = "/valList", method = RequestMethod.GET)
    public String dataDicValList(@RequestParam(value = "code", required = false, defaultValue = "")String code,
                                 Model model)
    {
        DataDic dataDic = dataDicService.getByCode(code);
        if(dataDic==null)
        {
            model.addAttribute("msg", "当前数据字典不存在");
            return "/error/error";
        }
        model.addAttribute("code", code);
        model.addAttribute("dicName", dataDic.getName());
        String re = setPageCommonModel(model, "basic","/manage/basic/dataDic", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/basic/set/datadic/vallist";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getDataDicValList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<DataDicVal> getDataDicValList(JqGridPageRequest pageRequest, String code)
    {
        List<DataDicVal> list = dataDicValService.getListByDataDicCode(code);
        list = list.stream().sorted(Comparator.comparing(DataDicVal::getSortnum)).collect(Collectors.toList());
        JqGridPageResponse<DataDicVal> response = new JqGridPageResponse<>();
        response.setRows(list);
        return response;
    }
}
