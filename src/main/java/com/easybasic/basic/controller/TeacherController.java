package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ExcelUtil;
import com.easybasic.component.Utils.Pinyin4jUtil;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.edu.model.SchoolSchedule;
import com.easybasic.edu.model.SchoolScheduleWeekTime;
import com.easybasic.edu.model.Subject;
import com.easybasic.edu.service.SubjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/basic/teacher")
public class TeacherController extends BaseController {

    @Resource
    private DataDicValDetailService dataDicValDetailService;
    @Resource
    private UnitService unitService;
    @Resource
    private DeptService deptService;
    @Resource
    private UserService userService;
    @Resource
    private DataDicValService dataDicValService;
    @Resource
    private UnitUserService unitUserService;
    @Resource
    private DeptUserService deptUserService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private SubjectService subjectService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String teacherList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/teacher", false);
        if(!StringUtils.isEmpty(re))
        {
            Unit unit = unitService.getUnitForCache(1);
            if(unit!=null)
            {
                model.addAttribute("topUnitName", unit.getName());
            }
            else
            {
                model.addAttribute("topUnitName","未知");
            }
            return "/manage/basic/user/teacher/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getUnitDeptList")
    @ResponseBody
    public String getUnitDeptList(@RequestParam("id") String id, @RequestParam(value = "level", required = false, defaultValue = "0") String level)
    {
        List<Unit> totalList = unitService.getUnitListForCache();
        int parentId = TypeConverter.strToInt(id);
        if(id.equalsIgnoreCase("root"))
        {
            parentId = LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId();
        }
        int pid = parentId;
        List<Unit> list = totalList.stream().filter(x -> x.getParentid()==pid).sorted(Comparator.comparing(Unit::getType)).collect(Collectors.toList());
        String data = "";
        for (Unit unit : list)
        {
            List<Unit> subList = totalList.stream().filter(x->x.getParentid().intValue()==unit.getPkid().intValue()).collect(Collectors.toList());
            List<Dept> subDeptList = deptService.getUnitDeptListForCache(unit.getPkid());
            int subCount = subList.size() + subDeptList.size();
            String title = unit.getName();
            String icon = getUnitIcon(unit.getType());
            if (StringUtils.isEmpty(data))
            {
                data = "{\"key\":\"" + unit.getPkid() + "\",\"title\":\"" +
                        title + "\",\"code\":\"0\",\"name\":\"" +
                        unit.getName() + "\",\"parent\":\"" + parentId +
                        "\",\"path\":null,\"childCount\":" + subCount + ",\"level\":" + (TypeConverter.strToInt(level) + 1) +
                        ",\"isFolder\":\"true\"," + icon + "\"isLazy\":" + (subCount > 0 ? "true" : "false") + "}";
            }
            else
            {
                data += ",{\"key\":\"" + unit.getPkid() + "\",\"title\":\"" +
                        title + "\",\"code\":\"0\",\"name\":\"" +
                        unit.getName() + "\",\"parent\":\"" + parentId +
                        "\",\"path\":null,\"childCount\":" + subCount + ",\"level\":" + (TypeConverter.strToInt(level) + 1) +
                        ",\"isFolder\":\"true\"," + icon + "\"isLazy\":" + (subCount > 0 ? "true" : "false") + "}";
            }
        }
        List<Dept> deptList = deptService.getUnitDeptListForCache(pid);
        for (Dept dept : deptList)
        {
            String title = dept.getName();
            String icon = "\"icon\": '../css/dept.gif',";
            if (StringUtils.isEmpty(data))
            {
                data = "{\"key\":\"" + dept.getPkid() + "\",\"title\":\"" +
                        title + "\",\"code\":\"1\",\"name\":\"" +
                        dept.getName() + "\",\"parent\":\"" + parentId +
                        "\",\"path\":null,\"childCount\":0,\"level\":" + (TypeConverter.strToInt(level) + 1) +
                        ",\"isFolder\":\"false\"," + icon + "\"isLazy\":false}";
            }
            else
            {
                data += ",{\"key\":\"" + dept.getPkid() + "\",\"title\":\"" +
                        title + "\",\"code\":\"1\",\"name\":\"" +
                        dept.getName() + "\",\"parent\":\"" + parentId +
                        "\",\"path\":null,\"childCount\":0,\"level\":" + (TypeConverter.strToInt(level) + 1) +
                        ",\"isFolder\":\"false\"," + icon + "\"isLazy\":false}";
            }
        }
        return "{\"success\":true,\"data\":["+data+"]}";
    }

    private String getUnitIcon(int type)
    {
        if(type==0)
        {//教育局
            return "\"icon\": '../css/edu.gif',";
        }
        //学校
        return "\"icon\": '../css/school.gif',";
    }

    @RequestMapping(value = "/getTeacherListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<User> getTeacherListForPage(JqGridPageRequest pageRequest,Integer unitId, Integer deptId,String searchKey)
    {
        String searchStr = "usertype=0";
        if(unitId == null)
        {
            unitId = LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId();
        }
        if(unitId!=null)
        {
            Unit unit = unitService.getUnitForCache(unitId);
            if(unit!=null) {
                searchStr += " and pkid in (select userid from basic_unituser where unitid in (select pkid from basic_unit where pkid="+unitId+" or path like '"+unit.getPath()+",%'))";
            }
            else
            {
                searchStr += " and pkid in (select userid from basic_unituser where unitid=" + unitId + ")";
            }
        }
        if(deptId!=null)
        {
            searchStr +=" and pkid in (select userid from basic_deptuser where deptid="+deptId+")";
        }
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and (name like '%"+searchKey+"%' or loginname like '%"+searchKey+"%')";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<User> list = userService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<User> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<User> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String teacherAdd(Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        List<DataDicVal> sexList = dataDicValService.getDataDicValListForCache("XB");
        model.addAttribute("sexList", sexList);
        List<Subject> subjectList = subjectService.getSubjectListForCache();
        model.addAttribute("subjectList", subjectList);
        return "manage/basic/user/teacher/add";
    }

    @RequestMapping(value = "/getDeptList", method = RequestMethod.POST)
    @ResponseBody
    public List<Dept> getDeptList(int unitId)
    {
        List<Dept> list = deptService.getUnitDeptListForCache(unitId);
        return list;
    }

    @RequestMapping(value = "/getGradeList", method = RequestMethod.POST)
    @ResponseBody
    public List<DataDicValDetail> getGradeList(int unitId)
    {
        Unit unit = unitService.getUnitForCache(unitId);
        if(unit==null) return new ArrayList<>();
        List<DataDicValDetail> list = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
        list = list.stream().filter(x->ToolsUtil.InArray(x.getValcode(), unit.getStageids())).collect(Collectors.toList());
        return list;
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int teacherDoAdd(User user, int unitId, String deptIds, String gradeIds, String subjectIds)
    {
        User tempUser = userService.getByLoginName(user.getLoginname());
        if(tempUser!=null)
        {
            return 0;
        }
        user.setPassword(ToolsUtil.getMd5(user.getPassword()));
        user.setStatus(1);
        user.setCreatetime(new Date());
        user.setRegisterip(ToolsUtil.getRequestIP());
        user.setLastlogintime(new Date());
        user.setLastloginip(ToolsUtil.getRequestIP());
        user.setLogintime(new Date());
        user.setLogincount(0);
        user.setUsertype(0);
        user.setFpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName().substring(0,1)));
        user.setSpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName()));
        userService.createTeacher(user,unitId, deptIds, gradeIds, subjectIds);
        setOpLog("basic","教职工管理", "新增教职工：" + user.getName());
        SetKaoQinOpLog("basic_user",user.getPkid(),1);
        return 1;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String teacherEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,Model model)
    {
        User user = userService.getByUserId(TypeConverter.strToInt(id));
        if(user == null)
        {
            model.addAttribute("msg","当前教职工信息不存在");
            return "/error/error";
        }
        Teacher teacher = teacherService.getByUserId(user.getPkid());
        if(teacher == null)
        {
            model.addAttribute("msg","当前教职工信息不存在");
            return "/error/error";
        }
        model.addAttribute("user", user);
        model.addAttribute("teacher", teacher);
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        List<DataDicVal> sexList = dataDicValService.getDataDicValListForCache("XB");
        model.addAttribute("sexList", sexList);
        List<Subject> subjectList = subjectService.getSubjectListForCache();
        model.addAttribute("subjectList", subjectList);
        List<UnitUser> unitUsers = unitUserService.getListByUserId(user.getPkid());
        if(unitUsers.size()>0)
        {
            model.addAttribute("unitId", unitUsers.get(0).getUnitid());
        }
        else{
            model.addAttribute("unitId", "0");
        }
        List<DeptUser> deptUsers = deptUserService.getListByUserId(user.getPkid());
        model.addAttribute("deptIds", deptUsers.stream().map(x->x.getDeptid().toString()).collect(Collectors.joining(",")));
        return "manage/basic/user/teacher/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int teacherDoEdit(User editDate, int unitId, String deptIds, String gradeIds, String subjectIds)
    {
        User user = userService.getByUserId(editDate.getPkid());
        if(user==null)
        {
            return 0;
        }
        user.setName(editDate.getName());
        user.setMobile(editDate.getMobile());
        user.setEmail(editDate.getEmail());
        user.setSex(editDate.getSex());
        user.setFpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName().substring(0,1)));
        user.setSpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName()));
        user.setPhoto(editDate.getPhoto());
        user.setProfile(editDate.getProfile());
        userService.update(user);
        Teacher teacher = teacherService.getByUserId(user.getPkid());
        teacher.setGradeids(gradeIds);
        teacher.setSubjects(subjectIds);
        teacherService.update(teacher);
        userService.createUnitUser(user, unitId);
        userService.createDeptUser(user, unitId, deptIds);
        setOpLog("basic","教职工管理", "编辑教职工：" + user.getName());
        SetKaoQinOpLog("basic_user",user.getPkid(),2);
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int teacherDoDel(int id)
    {
        if(id ==1) return 1;
        User user = userService.getByUserId(id);
        if(user==null)
        {
            return 0;
        }
        userService.delete(id);
        setOpLog("basic","教职工管理", "删除教职工信息：" + user.getName());
        SetKaoQinOpLog("basic_user",user.getPkid(),3);
        return 1;
    }

    @RequestMapping(value = "/doBatchDel", method = RequestMethod.POST)
    @ResponseBody
    public int teacherDoBatchDel(String ids)
    {
        String[] idsList = ids.split(",");
        for (String idStr: idsList)
        {
            int id = TypeConverter.strToInt(idStr);
            teacherDoDel(id);
        }
        return 1;
    }

    @RequestMapping(value = "/editPwd", method = RequestMethod.GET)
    public String teacherEditPwd(@RequestParam(value = "id", required = false, defaultValue = "0")String id,Model model)
    {
        User user = userService.getByUserId(TypeConverter.strToInt(id));
        if(user == null)
        {
            model.addAttribute("msg","当前教职工信息不存在");
            return "/error/error";
        }
        model.addAttribute("user", user);

        return "manage/basic/user/teacher/editpwd";
    }

    @RequestMapping(value = "/doEditPwd", method = RequestMethod.POST)
    @ResponseBody
    public int teacherDoEditPwd(int id, String pwd)
    {
        User user = userService.getByUserId(id);
        if(user!=null)
        {
            user.setPassword(ToolsUtil.getMd5(pwd));
            userService.update(user);
            setOpLog("basic","教职工管理", "修改教职工登录密码：" + user.getName());
            SetKaoQinOpLog("basic_user",user.getPkid(),2);
        }
        return 1;
    }

    @RequestMapping(value = "/doSetStatus", method = RequestMethod.POST)
    @ResponseBody
    public int teacherDoSetStatus(int id, int status)
    {
        User user = userService.getByUserId(id);
        if(user!=null)
        {
            user.setStatus(status);
            userService.update(user);
            setOpLog("basic","教职工管理", "修改教职工状态：" + user.getName());
            SetKaoQinOpLog("basic_user",user.getPkid(),2);
        }
        return 1;
    }

    @RequestMapping(value = "/inport", method = RequestMethod.GET)
    public String inport(Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        return "/manage/basic/user/teacher/inport";
    }

    @RequestMapping(value = "/doInport", method = RequestMethod.POST)
    @ResponseBody
    public int doInport(String fileName,int unitId)
    {
        Unit unit = unitService.getByPkId(unitId);
        if(unit == null)
        {
            return -1;
        }
        String filePath = ToolsUtil.getUploadPath("/upload/temp/" + fileName);
        try {
            List<String[]> result = ExcelUtil.readExcel(filePath, 0);
            for (String[] strings : result) {
                String name = strings[0].trim();
                String loginName = strings[1].trim();
                String pwd = strings[2].trim();
                String sex = strings[3].trim();
                String gradeName = "";
                if(strings.length>4){
                    gradeName = strings[4].trim();
                }
                String subjectName = "";
                if(strings.length>5){
                    subjectName = strings[5].trim();
                }
                String mobile = "";
                if(strings.length>6){
                    mobile = strings[6].trim();
                }
                String email = "";
                if(strings.length>7){
                    email = strings[7].trim();
                }
                String intro = "";
                if(strings.length>8){
                    intro = strings[8].trim();
                }
                String subjectIds = "";
                String gradeIds = "";
                Subject subject = subjectService.getByName(subjectName);
                if(subject!=null)
                {
                    subjectIds = subject.getPkid().toString();
                }
                List<DataDicValDetail> list = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
                String gn = gradeName;
                DataDicValDetail valDetail = list.stream().filter(x->x.getName().equalsIgnoreCase(gn)).findFirst().orElse(null);
                if(valDetail!=null)
                {
                    gradeIds = valDetail.getValcode() +"@" + valDetail.getCode();
                }
                if(StringUtils.isEmpty(name) || StringUtils.isEmpty(loginName) || StringUtils.isEmpty(pwd) || StringUtils.isEmpty(sex))
                {
                    continue;
                }
                User user = userService.getByLoginName(loginName);
                if(user !=null)
                {
                    continue;
                }
                user = new User();
                user.setName(name);
                user.setLoginname(loginName);
                user.setPassword(ToolsUtil.getMd5(pwd));
                user.setStatus(1);
                user.setCreatetime(new Date());
                user.setRegisterip(ToolsUtil.getRequestIP());
                user.setLastlogintime(new Date());
                user.setLastloginip(ToolsUtil.getRequestIP());
                user.setLogintime(new Date());
                user.setLogincount(0);
                user.setMobile(mobile);
                user.setEmail(email);
                user.setSex(sex=="男"?0:1);
                user.setUsertype(0);
                user.setFpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName().substring(0,1)));
                user.setSpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName()));
                user.setPhoto("");
                user.setProfile(intro);
                userService.createTeacher(user,unitId, "", gradeIds, subjectIds);
                SetKaoQinOpLog("basic_user",user.getPkid(),1);
            }
            ToolsUtil.deleteFile(filePath);
            setOpLog("basic","教职工管理", "学校“"+unit.getName()+"”导入教职工数据");
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
