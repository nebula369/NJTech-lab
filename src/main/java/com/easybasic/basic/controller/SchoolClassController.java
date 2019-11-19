package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.Login;
import com.easybasic.component.jwt.LoginUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/basic/class")
public class SchoolClassController extends BaseController {

    @Resource
    private UnitService unitService;
    @Resource
    private DataDicValService dataDicValService;
    @Resource
    private DataDicValDetailService dataDicValDetailService;
    @Resource
    private SchoolClassService schoolClassService;
    @Resource
    private SpaceTypeService spaceTypeService;
    @Resource
    private SpaceService spaceService;
    @Resource
    private SchoolYearTermService schoolYearTermService;
    @Resource
    private SchoolClassUserService schoolClassUserService;
    @Resource
    private SchoolClassTeacherService schoolClassTeacherServiceService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String classList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/class", false);
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
            return "manage/basic/unitset/class/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getClassListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<SchoolClass> getClassListForPage(JqGridPageRequest pageRequest, Integer schoolId, String searchKey)
    {
        String searchStr = "unitid=" + schoolId;
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        List<SchoolClass> list = schoolClassService.getListBySearch(searchStr,"sortnum asc");
        for(SchoolClass schoolClass : list)
        {
            schoolClass.setName(schoolClass.getGradeName() + schoolClass.getName());
            Space space = spaceService.getByClassId(schoolClass.getPkid());
            if(space!=null)
            {
                schoolClass.setSpaceName(space.getName());
            }
            int count = schoolClassUserService.getStudentCountByClassId(schoolClass.getPkid());
            schoolClass.setStudentCount(count);
        }
        JqGridPageResponse<SchoolClass> response = new JqGridPageResponse<>();
        response.setRows(list);
        return response;
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String classAdd(@RequestParam(value = "schoolId", required = false, defaultValue = "0")String schoolId,Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        List<SpaceType> spaceTypes = spaceTypeService.getSpaceTypeListForCache(TypeConverter.strToInt(schoolId));
        model.addAttribute("spaceTypes", spaceTypes);
        model.addAttribute("schoolId", TypeConverter.strToInt(schoolId));
        return "manage/basic/unitset/class/add";
    }

    @RequestMapping(value = "/getSchoolStageList", method = RequestMethod.POST)
    @ResponseBody
    public List<DataDicVal> getSchoolStageList(int schoolId)
    {
        Unit unit = unitService.getUnitForCache(schoolId);
        if(unit==null) return new ArrayList<>();
        List<DataDicVal> stageList = dataDicValService.getDataDicValListForCache("XD");
        stageList = stageList.stream().filter(x->ToolsUtil.InArray(x.getCode(), unit.getStageids())).collect(Collectors.toList());
        return stageList;
    }

    @RequestMapping(value = "/getStageGradeList", method = RequestMethod.POST)
    @ResponseBody
    public List<DataDicValDetail> getStageGradeList(int stageId)
    {
        List<DataDicValDetail> gradeList = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
        gradeList = gradeList.stream().filter(x->x.getValcode().equalsIgnoreCase(String.valueOf(stageId))).collect(Collectors.toList());
        return gradeList;
    }

    @RequestMapping(value = "/getSpaceList", method = RequestMethod.POST)
    @ResponseBody
    public List<Space> getSpaceList(int typeId)
    {
        List<Space> spaceList = spaceService.getListBySearch("typeid="+ typeId, "sortnum asc");
        return spaceList;
    }

    private int findGradeListIndex(List<DataDicValDetail> gradeList, int gradeId)
    {
        int index = -1;
        for(int i=0; i<gradeList.size(); i++)
        {
            if(gradeList.get(i).getCode().equalsIgnoreCase(String.valueOf(gradeId)))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    @RequestMapping(value = "/initClassCode", method = RequestMethod.POST)
    @ResponseBody
    public String[] initClassCode(int schoolId, int stageId, int gradeId)
    {
        String[] result = new String[]{"", "", "", ""};//入学学年；班级代码；班级名称；学制
        SchoolYearTerm yearTermInfo = schoolYearTermService.getBySchoolCurrentYearTerm(schoolId);
        List<DataDicValDetail> gradeList = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
        gradeList = gradeList.stream().filter(x->x.getValcode().equalsIgnoreCase(String.valueOf(stageId))).sorted(
                Comparator.comparing(DataDicValDetail::getSortnum)
        ).collect(Collectors.toList());
        if (yearTermInfo != null)
        {
            result[0] = yearTermInfo.getName();
            int index = findGradeListIndex(gradeList, gradeId);
            int year = TypeConverter.strToInt(yearTermInfo.getName().split("-")[0]);
            year = year - index;
            List<SchoolClass> list1 = schoolClassService.getListBySchoolIdAndStageIdAndGradeId(schoolId, stageId, gradeId);
            String stages = String.format("%02d", stageId);
            String code = year + stages + "01";
            String name = "01班";
            String yearStr = String.valueOf(year);
            list1 = list1.stream().filter(x-> x.getClasscode().startsWith(yearStr + "01")).sorted(Comparator.comparing(SchoolClass::getClasscode).reversed()).collect(Collectors.toList());
            if (list1.size() != 0)
            {
                int s = TypeConverter.strToInt(list1.get(0).getClasscode().replace(year + "01", ""));
                String ss = String.format("%02d",(s + 1));
                code = year+ stages + ss;
                name = ss + "班";
            }
            result[1] = code;
            result[2] = name;
            result[3] = "3";
        }
        return result;
    }


    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int classDoAdd(SchoolClass schoolClass, Integer spaceId,Integer manageruser) {
        if (schoolClassService.isSchoolClassCodeExist(schoolClass.getUnitid(), schoolClass.getClasscode())) {
            return 0;
        }
        if (schoolClassService.isSchoolClassNameExist(schoolClass.getUnitid(), schoolClass.getStageid(), schoolClass.getStageid(), schoolClass.getName())) {
            return -1;
        }
        schoolClass.setCreatetime(new Date());
        schoolClass.setType(0);
        Integer pkid = schoolClassService.insert(schoolClass);
        if (spaceId != null) {
            Space space = spaceService.getByPkId(spaceId);
            if (space != null) {
                space.setClassid(schoolClass.getPkid());
                spaceService.update(space);
            }
        }

        SchoolClassTeacher teacher = new SchoolClassTeacher();
        teacher.setClassid(pkid);
        teacher.setTeacherid(manageruser);
        schoolClassTeacherServiceService.insert(teacher);

        setOpLog("basic", "班级管理", "新增班级数据：" + schoolClass.getName());
        return 1;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String classEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                           Model model)
    {
        SchoolClass schoolClass = schoolClassService.getByPkId(TypeConverter.strToInt(id));
        if(schoolClass==null)
        {
            model.addAttribute("msg","要编辑的班级信息不存在");
            return "/error/error";
        }
        model.addAttribute("schoolClass", schoolClass);
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        List<SpaceType> spaceTypes = spaceTypeService.getSpaceTypeListForCache(schoolClass.getUnitid());
        model.addAttribute("spaceTypes", spaceTypes);
        Space space = spaceService.getByClassId(schoolClass.getPkid());
        if(space!=null) {
            model.addAttribute("typeId", space.getTypeid());
            model.addAttribute("spaceId", space.getPkid());
        }
        else
        {
            model.addAttribute("typeId", "0");
            model.addAttribute("spaceId", "0");
        }
        List<SchoolClassTeacher> teachers=schoolClassTeacherServiceService.selectTeacherByClassId(schoolClass.getPkid());
        Integer pkid=0;
        for (SchoolClassTeacher item : teachers) {
            pkid=item.getTeacherid();
        }
        User user = userService.getUserForCache(pkid);
        model.addAttribute("user", user);
        return "manage/basic/unitset/class/edit";
    }


    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int classDoEdit(SchoolClass editData, Integer spaceId,Integer manageuser)
    {
        SchoolClass dept = schoolClassService.getByPkId(editData.getPkid());
        if(dept!=null) {
            dept.setSchoolsystem(editData.getSchoolsystem());

            dept.setSortnum(editData.getSortnum());
            schoolClassService.update(dept);
            Space oriSpace = spaceService.getByClassId(dept.getPkid());
            boolean isNeedAdd = true;
            if(oriSpace!=null)
            {
                if(oriSpace.getPkid().intValue() == spaceId.intValue())
                {
                    isNeedAdd = false;
                }
                else
                {
                    oriSpace.setClassid(0);
                    spaceService.update(oriSpace);
                }
            }
            if(isNeedAdd && spaceId!=null)
            {
                Space space = spaceService.getByPkId(spaceId);
                if(space!=null)
                {
                    space.setClassid(dept.getPkid());
                    spaceService.update(space);
                }
            }

            List<SchoolClassTeacher> teachers=schoolClassTeacherServiceService.selectTeacherByClassId(dept.getPkid());
            for (SchoolClassTeacher item : teachers) {
                schoolClassTeacherServiceService.delete(item.getPkid());
            }

            SchoolClassTeacher teacher=new SchoolClassTeacher();
            teacher.setClassid(dept.getPkid());
            teacher.setTeacherid(manageuser);
            schoolClassTeacherServiceService.insert(teacher);

            setOpLog("basic","班级管理", "编辑班级数据：" + dept.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int classDoDel(int id)
    {
        SchoolClass schoolClass = schoolClassService.getByPkId(id);
        if(schoolClass!=null) {
            int count = schoolClassUserService.getStudentCountByClassId(schoolClass.getPkid());
            if(count>0)
            {
                return 0;
            }
            schoolClassService.delete(id);
            setOpLog("basic","班级管理", "删除班级数据：" + schoolClass.getName());
        }
        return 1;
    }
}
