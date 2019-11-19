package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.DataDicVal;
import com.easybasic.basic.model.SchoolYearTerm;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.DataDicValService;
import com.easybasic.basic.service.SchoolYearTermService;
import com.easybasic.basic.service.UnitService;
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
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/basic/yearterm")
public class YearTermController extends BaseController {

    @Resource
    private SchoolYearTermService schoolYearTermService;
    @Resource
    private DataDicValService dataDicValService;
    @Resource
    private UnitService unitService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String yearTermList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/yearterm", false);
        if(!StringUtils.isEmpty(re))
        {
            List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
            initUnitList(list);
            model.addAttribute("unitList", list);
            Unit school = list.stream().filter(x->x.getType()==1).findFirst().orElse(null);
            if(school!=null)
            {
                model.addAttribute("schoolId", school.getPkid());
            }
            else
            {
                model.addAttribute("schoolId","0");
            }
            return "manage/basic/unitset/yearterm/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getYearTermList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<SchoolYearTerm> getYearTermList(JqGridPageRequest pageRequest, Integer schoolId, String year)
    {
        List<SchoolYearTerm> list = schoolYearTermService.getListBySchoolIdAndYear(schoolId, year);
        List<DataDicVal> termDicList = dataDicValService.getDataDicValListForCache("XQZ");
        list = list.stream().sorted(Comparator.comparing(SchoolYearTerm::getSchoolterm)).collect(Collectors.toList());
        for(SchoolYearTerm yearTerm : list)
        {
            DataDicVal val = termDicList.stream().filter(x->x.getCode().equalsIgnoreCase(yearTerm.getSchoolterm().toString()))
                    .findFirst().orElse(null);
            if(val!=null)
            {
                yearTerm.setSchoolTermName(val.getName());
            }
        }
        JqGridPageResponse<SchoolYearTerm> response = new JqGridPageResponse<>();
        response.setRows(list);
        return response;
    }

    @RequestMapping(value = "/getYearList", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getYearList(int schoolId)
    {
        List<SchoolYearTerm> list = schoolYearTermService.getListBySchoolId(schoolId);
        List<String> result = list.stream().sorted(Comparator.comparing(SchoolYearTerm::getName)).map(SchoolYearTerm::getName).distinct().collect(Collectors.toList());
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String yearTermAdd(@RequestParam(value = "schoolId", required = false, defaultValue = "0")String schoolId,Model model)
    {
        List<DataDicVal> termList = dataDicValService.getDataDicValListForCache("XQZ");
        model.addAttribute("termList", termList);
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        List<String> yearList = new ArrayList<>();
        int currentYear = ToolsUtil.getCalendarInstance(new Date()).get(Calendar.YEAR);
        for (int d = currentYear-2; d < currentYear; d++)
        {
            yearList.add(d + "-" + (d + 1));
        }
        model.addAttribute("yearList", yearList);
        model.addAttribute("schoolId", TypeConverter.strToInt(schoolId));
        return "manage/basic/unitset/yearterm/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int yearTermDoAdd(SchoolYearTerm schoolYearTerm)
    {
        if(schoolYearTermService.isSchoolYearTermExist(schoolYearTerm.getSchoolid(), schoolYearTerm.getName(), schoolYearTerm.getSchoolterm()))
        {
            return 0;
        }
        schoolYearTerm.setWeekdays(0);
        schoolYearTerm.setCourselen(0);
        schoolYearTerm.setCreatetime(new Date());
        schoolYearTermService.insert(schoolYearTerm);
        setOpLog("basic","年度学期", "增加学校年度学期：" + schoolYearTerm.getName());
        return 1;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String yearTermEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                           Model model)
    {
        SchoolYearTerm schoolYearTerm = schoolYearTermService.getByPkId(TypeConverter.strToInt(id));
        if(schoolYearTerm==null)
        {
            model.addAttribute("msg","要编辑的学校学期数据不存在");
            return "/error/error";
        }
        List<DataDicVal> termList = dataDicValService.getDataDicValListForCache("XQZ");
        model.addAttribute("termList", termList);
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        List<String> yearList = new ArrayList<>();
        int currentYear = ToolsUtil.getCalendarInstance(new Date()).get(Calendar.YEAR);
        for (int d = currentYear-2; d < currentYear; d++)
        {
            yearList.add(d + "-" + (d + 1));
        }
        model.addAttribute("yearList", yearList);
        model.addAttribute("yearTerm", schoolYearTerm);
        return "manage/basic/unitset/yearterm/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int yearTermDoEdit(SchoolYearTerm editData)
    {
        SchoolYearTerm schoolYearTerm = schoolYearTermService.getByPkId(editData.getPkid());
        if(schoolYearTerm!=null) {
            schoolYearTerm.setMorningsessions(editData.getMorningsessions());
            schoolYearTerm.setAfternoonsessions(editData.getAfternoonsessions());
            schoolYearTerm.setEveningsessions(editData.getEveningsessions());
            schoolYearTerm.setWorkstartdate(editData.getWorkstartdate());
            schoolYearTerm.setWorkenddate(editData.getWorkenddate());
            schoolYearTerm.setLearnstartdate(editData.getLearnstartdate());
            schoolYearTerm.setLearnenddate(editData.getLearnenddate());
            schoolYearTermService.update(schoolYearTerm);
            setOpLog("basic","年度学期", "编辑学期数据：" + schoolYearTerm.getName());
        }
        return 1;
    }
}
