package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.Dept;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.DataDicValService;
import com.easybasic.basic.service.DeptService;
import com.easybasic.basic.service.DeptUserService;
import com.easybasic.basic.service.UnitService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.LoginUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/manage/basic/dept")
public class DeptController extends BaseController {

    @Resource
    private UnitService unitService;
    @Resource
    private DeptUserService deptUserService;
    @Resource
    private DeptService deptService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String deptList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/dept", false);
        if(!StringUtils.isEmpty(re))
        {
            List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
            initUnitList(list);
            model.addAttribute("unitList", list);
            return "manage/basic/unitset/dept/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getDeptListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Dept> getDeptListForPage(JqGridPageRequest pageRequest, Integer unitId, String searchKey)
    {
        String searchStr = "unitid=" + unitId;
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Dept> list = deptService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for(Dept dept: list)
        {
            int count = deptUserService.getCountByDeptId(dept.getPkid());
            dept.setUserCount(count);
        }
        PageInfo<Dept> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Dept> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String deptAdd(Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        return "manage/basic/unitset/dept/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int unitDoAdd(Dept dept)
    {
        dept.setCreatetime(new Date());
        deptService.insert(dept);
        setOpLog("basic","部门管理", "增加部门数据：" + dept.getName());
        return 1;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String deptEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                           Model model)
    {
        Dept dept = deptService.getByPkId(TypeConverter.strToInt(id));
        if(dept==null)
        {
            model.addAttribute("msg","要编辑的部门信息不存在");
            return "/error/error";
        }
        model.addAttribute("dept", dept);
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        return "manage/basic/unitset/dept/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int deptDoEdit(Dept editData)
    {
        Dept dept = deptService.getByPkId(editData.getPkid());
        if(dept!=null) {
            dept.setName(editData.getName());
            dept.setSortnum(editData.getSortnum());
            deptService.update(dept);
            setOpLog("basic","部门管理", "编辑部门数据：" + dept.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int depDoDel(int id)
    {
        Dept dept = deptService.getByPkId(id);
        if(dept!=null) {
            int count = deptUserService.getCountByDeptId(dept.getPkid());
            if(count>0) return 0;
            deptService.delete(id);
            setOpLog("basic","部门管理", "删除部门数据：" + dept.getName());
        }
        return 1;
    }
}
