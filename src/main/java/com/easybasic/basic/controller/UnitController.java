package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.DataDicVal;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.model.User;
import com.easybasic.basic.service.DataDicValService;
import com.easybasic.basic.service.UnitService;
import com.easybasic.basic.service.UserService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.LoginUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/basic/unit")
public class UnitController extends BaseController {

    @Resource
    private UnitService unitService;
    @Resource
    private DataDicValService dataDicValService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String unitList(Model model)
    {
        String re = setPageCommonModel(model, "basic", "/manage/basic/unit", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/basic/unitset/unit/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getUnitList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Unit> getUnitList(JqGridPageRequest pageRequest)
    {
        List<DataDicVal> unitTypeList = dataDicValService.getDataDicValListForCache("DWLX");
        List<DataDicVal> stageList = dataDicValService.getDataDicValListForCache("XD");
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        for (Unit unit : list)
        {
            DataDicVal unitType = unitTypeList.stream().filter(x->x.getCode().equalsIgnoreCase(unit.getType().toString())).findFirst().orElse(null);
            if(unitType!=null)
            {
                unit.setUnitTypeStr(unitType.getName());
            }
            String stageIds = stageList.stream().filter(x->ToolsUtil.InArray(x.getCode(), unit.getStageids())).map(DataDicVal::getName).collect(Collectors.joining(","));
            unit.setStageIdsStr(stageIds);
            User user = userService.getUserForCache(unit.getManageuser());
            unit.setManageUserName(user==null?"":user.getName());
        }
        initUnitList(list);
        JqGridPageResponse<Unit> response = new JqGridPageResponse<>();
        response.setRows(list);
        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String unitAdd(Model model)
    {
        List<DataDicVal> unitType = dataDicValService.getDataDicValListForCache("DWLX");
        model.addAttribute("unitType", unitType);
        List<DataDicVal> stageList = dataDicValService.getDataDicValListForCache("XD");
        model.addAttribute("stageList", stageList);
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        list = list.stream().filter(x->x.getType() != 1).collect(Collectors.toList());
        initUnitList(list);
        model.addAttribute("unitList", list);
        return "manage/basic/unitset/unit/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int unitDoAdd(Unit unit)
    {
        if(unit.getManageuser()!=0) {
            Unit tempUnit = unitService.getByManageUser(unit.getManageuser());
            if(tempUnit !=null)
            {
                return 0;
            }
        }
        unit.setSortnum(0);
        unit.setCreatetime(new Date());
        unitService.createUnit(unit);
        setOpLog("basic","单位信息", "增加单位数据：" + unit.getName());
        return 1;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String unitEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                           Model model)
    {
        Unit unit = unitService.getByPkId(TypeConverter.strToInt(id));
        if(unit==null)
        {
            model.addAttribute("msg","要编辑的单位信息不存在");
            return "/error/error";
        }
        model.addAttribute("unit", unit);
        List<DataDicVal> unitType = dataDicValService.getDataDicValListForCache("DWLX");
        model.addAttribute("unitType", unitType);
        List<DataDicVal> stageList = dataDicValService.getDataDicValListForCache("XD");
        model.addAttribute("stageList", stageList);
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        list = list.stream().filter(x->x.getType() != 1).collect(Collectors.toList());
        initUnitList(list);
        model.addAttribute("unitList", list);
        User user = userService.getUserForCache(unit.getManageuser());
        model.addAttribute("user", user);
        return "manage/basic/unitset/unit/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int unitDoEdit(Unit editData)
    {
        if(editData.getManageuser()!=0) {
            Unit tempUnit = unitService.getByManageUser(editData.getManageuser());
            if(tempUnit !=null && tempUnit.getPkid().intValue() != editData.getPkid().intValue())
            {
                return 0;
            }
        }
        Unit unit = unitService.getByPkId(editData.getPkid());
        if(unit!=null) {
            unit.setName(editData.getName());
            unit.setParentid(editData.getParentid());
            unit.setStageids(editData.getStageids());
            unit.setManageuser(editData.getManageuser());
            unitService.modifyUnit(unit);
            setOpLog("basic","单位信息", "编辑单位数据：" + unit.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int unitDoDel(int id)
    {
        Unit unit = unitService.getByPkId(id);
        if(unit!=null) {
            List<Unit> list = unitService.getUnitListForCache();
            if(list.stream().anyMatch(x->x.getParentid().intValue() == unit.getPkid().intValue()))
            {
                return 0;
            }
            unitService.delete(id);
            setOpLog("basic","单位信息", "删除单位数据：" + unit.getName());
        }
        return 1;
    }
}
