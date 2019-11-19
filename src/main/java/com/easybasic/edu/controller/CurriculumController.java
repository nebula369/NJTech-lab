package com.easybasic.edu.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ExcelUtil;
import com.easybasic.component.Utils.Pinyin4jUtil;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.edu.dto.SubjectTeacherDTO;
import com.easybasic.edu.model.SchoolClassCurriculum;
import com.easybasic.edu.model.Subject;
import com.easybasic.edu.service.SchoolClassCurriculumService;
import com.easybasic.edu.service.SchoolScheduleService;
import com.easybasic.edu.service.SchoolScheduleWeekTimeService;
import com.easybasic.edu.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/edu/curriculum")
public class CurriculumController extends BaseController {

    @Resource
    private UnitService unitService;
    @Resource
    private DataDicValDetailService dataDicValDetailService;
    @Resource
    private SchoolClassCurriculumService schoolClassCurriculumService;
    @Resource
    private SchoolClassService schoolClassService;
    @Resource
    private UserService userService;
    @Resource
    private SubjectService subjectService;
    @Resource
    private TeacherService teacherService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String curriculumList(Model model)
    {
        String re = setPageCommonModel(model, "edu","/manage/edu/curriculum", false);
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
            return "manage/edu/set/curr/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getStageGradeList", method = RequestMethod.POST)
    @ResponseBody
    public List<DataDicValDetail> getStageGradeList(int stageId)
    {
        List<DataDicValDetail> list = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
        list = list.stream().filter(x->x.getValcode().equalsIgnoreCase(String.valueOf(stageId))).collect(Collectors.toList());
        return list;
    }

    @RequestMapping(value = "/getSchoolClassList", method = RequestMethod.POST)
    @ResponseBody
    public List<SchoolClass> getSchoolClassList(int schoolId, int stageId, int gradeId)
    {
        List<SchoolClass> list = schoolClassService.getListBySchoolIdAndStageIdAndGradeId(schoolId, stageId, gradeId);
        return list;
    }

    @RequestMapping(value = "/getSchoolClassCurriculumList", method = RequestMethod.POST)
    @ResponseBody
    public List<SchoolClassCurriculum> getSchoolClassCurriculumList(int schoolId, int classId)
    {
        List<SchoolClassCurriculum> list = schoolClassCurriculumService.getListBySchoolIdAndClassId(schoolId, classId);
        for (SchoolClassCurriculum schoolClassCurriculum : list) {
            User user = userService.getUserForCache(schoolClassCurriculum.getTeacherid());
            if(user!=null)
            {
                schoolClassCurriculum.setTeacherName(user.getName());
            }
            Subject subject = subjectService.getSubjectForCache(schoolClassCurriculum.getSubjectid());
            if(subject!=null)
            {
                schoolClassCurriculum.setSubjectName(subject.getName());
            }
        }
        return list;
    }

    @RequestMapping(value = "/selectSubjectTeacher", method = RequestMethod.GET)
    public String selectSubjectTeacher(@RequestParam(value = "schoolId", required = false, defaultValue = "0")String schoolId,
                                       @RequestParam(value = "stageId", required = false, defaultValue = "0")String stageId,
                                       @RequestParam(value = "gradeId", required = false, defaultValue = "0")String gradeId,
                                       @RequestParam(value = "classId", required = false, defaultValue = "0")String classId,
                                       @RequestParam(value = "week", required = false, defaultValue = "0")String week,
                                       @RequestParam(value = "session", required = false, defaultValue = "0")String session,
                                       Model model)
    {
        if(TypeConverter.strToInt(schoolId)==0 || TypeConverter.strToInt(stageId)==0 || TypeConverter.strToInt(gradeId)==0 || TypeConverter.strToInt(classId)==0)
        {
            model.addAttribute("msg","传入的参数不能为空");
            return "/error/error";
        }
        List<Teacher> list = teacherService.getListBySearch("gradeids like '%"+stageId+"@"+gradeId+"%'");
        List<SubjectTeacherDTO> subjectTeacherDTOList = new ArrayList<>();
        for (Teacher teacher : list) {
            User user = userService.getUserForCache(teacher.getUserid());
            if(user!=null)
            {
                String[] subjectStrList = teacher.getSubjects().split(",");
                for (String s : subjectStrList) {
                    int subjectId = TypeConverter.strToInt(s);
                    Subject subject = subjectService.getSubjectForCache(subjectId);
                    if(subject!=null) {
                        SubjectTeacherDTO subjectTeacherDTO = new SubjectTeacherDTO();
                        subjectTeacherDTO.setTeacherId(teacher.getUserid());
                        subjectTeacherDTO.setTeacherName(user.getName());
                        subjectTeacherDTO.setSubjectId(subjectId);
                        subjectTeacherDTO.setSubjectName(subject.getName());
                        subjectTeacherDTOList.add(subjectTeacherDTO);
                    }
                }
            }
        }
        model.addAttribute("list", subjectTeacherDTOList);
        model.addAttribute("schoolId", TypeConverter.strToInt(schoolId));
        model.addAttribute("stageId", TypeConverter.strToInt(stageId));
        model.addAttribute("gradeId", TypeConverter.strToInt(gradeId));
        model.addAttribute("classId", TypeConverter.strToInt(classId));
        model.addAttribute("week", TypeConverter.strToInt(week));
        model.addAttribute("session", TypeConverter.strToInt(session));
        //查询老师
        SchoolClassCurriculum schoolclasscurriculum = schoolClassCurriculumService.getBySchoolIdAndClassIdAndWeekAndSession( TypeConverter.strToInt(schoolId),  TypeConverter.strToInt(classId),TypeConverter.strToInt(week),TypeConverter.strToInt(session));
        int teacherId=0;
        int subjectId=0;
        if(schoolclasscurriculum!=null)
        {
            teacherId=schoolclasscurriculum.getTeacherid();
            subjectId=schoolclasscurriculum.getSubjectid();
        }
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("subjectId", subjectId);
        return "/manage/edu/set/curr/selectsubjectteacher";
    }

    @RequestMapping(value = "/setClassSubjectTeacher", method = RequestMethod.POST)
    @ResponseBody
    public int setClassSubjectTeacher(int schoolId, int classId, int week, int session, int teacherId, int subjectId,int type)
    {   SchoolClassCurriculum curriculum = schoolClassCurriculumService.getBySchoolIdAndClassIdAndWeekAndSession(schoolId, classId, week, session);
        if(type==1)//新增和修改
        {
          if(curriculum==null)//新增
          {
            curriculum = new SchoolClassCurriculum();
            curriculum.setSchoolid(schoolId);
            curriculum.setClassid(classId);
            curriculum.setWeek(week);
            curriculum.setSession(session);
            curriculum.setTeacherid(teacherId);
            curriculum.setSubjectid(subjectId);
            schoolClassCurriculumService.insert(curriculum);
        }
        else//修改
        {
            curriculum.setTeacherid(teacherId);
            curriculum.setSubjectid(subjectId);
            schoolClassCurriculumService.update(curriculum);
            }
             setOpLog("edu","设置课程表","设置班级课程表");
        }
        else{//删除
            if(curriculum!=null)
            {
                setOpLog("edu","删除课程表","删除班级课程表"+curriculum.getPkid());
                schoolClassCurriculumService.delete(curriculum.getPkid());
            }
        }
        return 1;
    }

    @RequestMapping(value = "/currInport", method = RequestMethod.GET)
    public String currInport(@RequestParam(value = "schoolId", required = false, defaultValue = "0")String schoolIdStr,
                             @RequestParam(value = "classId", required = false, defaultValue = "0")String classIdStr,
                             Model model)
    {
        int schoolId = TypeConverter.strToInt(schoolIdStr);
        int classId = TypeConverter.strToInt(classIdStr);
        if(schoolId == 0 || classId == 0)
        {
            model.addAttribute("msg","传入的参数不能为空");
            return "/error/error";
        }
        model.addAttribute("schoolId", schoolId);
        model.addAttribute("classId", classId);
        return "/manage/edu/set/curr/inport";
    }

    @RequestMapping(value = "/doInportTemplate", method = RequestMethod.POST)
    @ResponseBody
    public int doInportTemplate(String fileName,int schoolId, int classId)
    {
        Unit unit = unitService.getByPkId(schoolId);
        if(unit == null)
        {
            return -1;
        }
        if(classId == 0)
        {
            return -1;
        }
        String filePath = ToolsUtil.getUploadPath("/upload/temp/" + fileName);
        try {
            List<String[]> result = ExcelUtil.readExcel(filePath, 0);
            schoolClassCurriculumService.deleteBySchoolIdAndClassId(schoolId, classId);
            int session = 1;
            for (String[] strings : result) {
                int count = 0;
                for (String s : strings) {
                    if(count<=1){
                        count++;
                        continue;
                    }
                    if(StringUtils.isEmpty(s))
                    {
                        count++;
                        continue;
                    }
                    if(count>8) break;
                    int week = count - 1;
                    if(week == 7) week =0;
                    String[] st = s.trim().split("/");
                    if(st.length!=2)
                    {
                        count++;
                        continue;
                    }
                    String subjectName = st[0];
                    String teacherName = st[1];
                    Subject subject = subjectService.getByName(subjectName);
                    if(subject == null)
                    {
                        //不导入
                        count++;
                        continue;
                    }
                    int teacherId = initCurrTeacher(schoolId, teacherName, subject.getPkid());
                    SchoolClassCurriculum curriculum = new SchoolClassCurriculum();
                    curriculum.setSchoolid(schoolId);
                    curriculum.setClassid(classId);
                    curriculum.setWeek(week);
                    curriculum.setSession(session);
                    curriculum.setTeacherid(teacherId);
                    curriculum.setSubjectid(subject.getPkid());
                    schoolClassCurriculumService.insert(curriculum);
                    count++;
                }
                session ++;
            }
            setOpLog("edu","课程表管理", "学校“"+unit.getName()+"”导入课程表数据");
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int initCurrTeacher(int schoolId, String teacherName, int subjectId)
    {
        User user = userService.getBySchoolIdAndName(schoolId, teacherName);
        if(user == null)
        {
            //新建
            user = new User();
            user.setName(teacherName);
            user.setLoginname(userService.initUserLoginName(teacherName));
            user.setPassword(ToolsUtil.getMd5("123456"));
            user.setStatus(1);
            user.setCreatetime(new Date());
            user.setRegisterip(ToolsUtil.getRequestIP());
            user.setLastlogintime(new Date());
            user.setLastloginip(ToolsUtil.getRequestIP());
            user.setLogintime(new Date());
            user.setLogincount(0);
            user.setUsertype(0);
            user.setMobile("");
            user.setEmail("");
            user.setSex(1);
            user.setFpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName().substring(0,1)));
            user.setSpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName()));
            userService.createTeacher(user,schoolId, "", "", String.valueOf(subjectId));
        }
        return user.getPkid();
    }
}
