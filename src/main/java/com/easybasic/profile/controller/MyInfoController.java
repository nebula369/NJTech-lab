package com.easybasic.profile.controller;

import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.edu.model.Subject;
import com.easybasic.edu.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/profile")
public class MyInfoController extends BaseController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private UnitUserService unitUserService;
    @Resource
    private UnitService unitService;
    @Resource
    private DeptUserService deptUserService;
    @Resource
    private DeptService deptService;
    @Resource
    private SubjectService subjectService;
    @Resource
    private DataDicValDetailService dataDicValDetailService;
    @Resource
    private UserService userService;
    @Resource
    private StudentService studentService;
    @Resource
    private SchoolClassUserService schoolClassUserService;
    @Resource
    private SchoolClassService schoolClassService;

    @RequestMapping(value = "/myInfo", method = RequestMethod.GET)
    public String myInfo(Model model)
    {
        User user = userService.getByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        if(user.getUsertype() == 1)
        {
            return "redirect:/manage/profile/myStuInfo";
        }
        setPageCommonModelForNoAuth(model, "profile","/manage/profile/myInfo", false);
        model.addAttribute("user", user);
        Teacher teacher = teacherService.getByUserId(user.getPkid());
        model.addAttribute("teacher", teacher);
        List<UnitUser> unitUsers = unitUserService.getListByUserId(user.getPkid());
        if(unitUsers.size()>0)
        {
            Unit unit = unitService.getUnitForCache(unitUsers.get(0).getUnitid());
            model.addAttribute("unit", unit);
        }
        List<DeptUser> deptUsers = deptUserService.getListByUserId(user.getPkid());
        List<String> deptNameList = new ArrayList<>();
        for (DeptUser deptUser : deptUsers) {
            Dept dept =deptService.getUnitDeptForCache(deptUser.getDeptid());
            if(dept!=null)
            {
                deptNameList.add(dept.getName());
            }
        }
        model.addAttribute("deptNames", String.join("，",deptNameList));
        if(teacher!=null) {
            List<Subject> subjectList = subjectService.getSubjectListForCache().stream().filter(x -> ToolsUtil.InArray(x.getCode().toString(), teacher.getSubjects())).collect(Collectors.toList());
            model.addAttribute("subjectNames", subjectList.stream().map(Subject::getName).collect(Collectors.joining("，")));
            List<DataDicValDetail> xdnj = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
            String[] gradeIds = teacher.getGradeids().split(",");
            List<String> gradeNameList = new ArrayList<>();
            for (String gradeIdStr : gradeIds) {
                String[] sg = gradeIdStr.split("@");
                int stageId = TypeConverter.strToInt(sg[0]);
                int gradeId = TypeConverter.strToInt(sg[1]);
                DataDicValDetail detail = xdnj.stream().filter(x->x.getValcode().equalsIgnoreCase(String.valueOf(stageId)) && x.getCode().equalsIgnoreCase(String.valueOf(gradeId))).findFirst().orElse(null);
                if(detail!=null)
                {
                    gradeNameList.add(detail.getName());
                }
            }
            model.addAttribute("gradeNames", String.join("，", gradeNameList));
        }
        return "/manage/profile/myinfo/myinfo";
    }

    @RequestMapping(value = "/myStuInfo", method = RequestMethod.GET)
    public String myStuInfo(Model model)
    {
        User user = userService.getByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        setPageCommonModelForNoAuth(model, "profile","/manage/profile/myInfo", false);
        model.addAttribute("user", user);
        Student student = studentService.getByUserId(user.getPkid());
        model.addAttribute("student", student);
        List<UnitUser> unitUsers = unitUserService.getListByUserId(user.getPkid());
        if(unitUsers.size()>0)
        {
            Unit unit = unitService.getUnitForCache(unitUsers.get(0).getUnitid());
            model.addAttribute("unit", unit);
        }
        SchoolClassUser schoolClassUser = schoolClassUserService.getStudentByUserId(user.getPkid());
        if(schoolClassUser != null)
        {
            SchoolClass schoolClass = schoolClassService.getSchoolClassForCache(schoolClassUser.getClassid());
            if(schoolClass!=null)
            {
                List<DataDicValDetail> xdnj = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
                DataDicValDetail detail = xdnj.stream().filter(x->x.getValcode().equalsIgnoreCase(schoolClass.getStageid().toString()) && x.getCode().equalsIgnoreCase(schoolClass.getGradeid().toString())).findFirst().orElse(null);
                model.addAttribute("className", detail.getName() + schoolClass.getName());
            }
        }
        return "/manage/profile/myinfo/mystuinfo";
    }

    @RequestMapping(value = "/myInfo/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int myInfoDoEdit(String mobile, String email)
    {
        User user = userService.getByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        if(user!=null)
        {
            user.setMobile(mobile);
            user.setEmail(email);
            userService.update(user);
            setOpLog("profile","个人资料","用户“"+user.getName()+"”个人资料修改");
        }
        return 1;
    }

    @RequestMapping(value = "/pwd", method = RequestMethod.GET)
    public String pwdEdit(Model model)
    {
        setPageCommonModelForNoAuth(model, "profile","/manage/profile/pwd", false);
        return "/manage/profile/myinfo/pwd";
    }

    @RequestMapping(value = "/pwd/doPwdEdit", method = RequestMethod.POST)
    @ResponseBody
    public int pwdDoEdit(String oriPwd, String pwd)
    {
        User user = userService.getByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        if(!ToolsUtil.getMd5(oriPwd).equalsIgnoreCase(user.getPassword()))
        {
            return 0;
        }
        user.setPassword(ToolsUtil.getMd5(pwd));
        userService.update(user);
        setOpLog("profile", "修改密码", "用户（"+user.getName()+"）修改密码");
        return 1;
    }
}
