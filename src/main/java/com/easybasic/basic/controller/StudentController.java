package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.*;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.easybasic.kaoqin.service.*;
import com.easybasic.kaoqin.model.*;
@Controller
@RequestMapping("/manage/basic/student")
public class StudentController extends BaseController {

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
    private SchoolClassService schoolClassService;
    @Resource
    private SchoolClassUserService schoolClassUserService;
    @Resource
    private StudentService studentService;
    @Resource
    private  UserPhotoService userPhotoService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String studentList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/student", false);
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
            return "/manage/basic/user/student/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getSchoolClassListForTree")
    @ResponseBody
    public String getSchoolClassListForTree(@RequestParam("id") String id,
                                     @RequestParam(value = "level", required = false, defaultValue = "0") String level)
    {
        String data = "";
        if(id.indexOf("_")>0)
        {
            //进来的为年级
            String[] idList = id.split("_");
            int gradeId = TypeConverter.strToInt(idList[0]);
            int schoolId = TypeConverter.strToInt(idList[1]);
            int stageId = TypeConverter.strToInt(idList[2]);
            List<SchoolClass> subList = schoolClassService.getSchoolClassListForCache(schoolId, stageId, gradeId);
            for (SchoolClass schoolClass : subList)
            {
                String title = schoolClass.getName();
                String icon = "\"icon\": '../css/class.gif',";
                String key = schoolClass.getPkid().toString();
                if (StringUtils.isEmpty(data))
                {
                    data = "{\"key\":\"" + key + "\",\"schoolId\":\""+schoolId+"\",\"stageId\":\""+stageId+"\",\"title\":\"" +
                            title + "\",\"code\":\"2\",\"name\":\"" +
                            schoolClass.getName() + "\",\"parent\":\"" + gradeId +
                            "\",\"path\":null,\"childCount\":"+subList.size()+",\"level\":" + (TypeConverter.strToInt(level) + 1) +
                            ",\"isFolder\":\"false\"," + icon + "\"isLazy\":false}";
                }
                else
                {
                    data += ",{\"key\":\"" + key + "\",\"schoolId\":\""+schoolId+"\",\"stageId\":\""+gradeId+"\",\"title\":\"" +
                            title + "\",\"code\":\"2\",\"name\":\"" +
                            schoolClass.getName() + "\",\"parent\":\"" + gradeId +
                            "\",\"path\":null,\"childCount\":"+subList.size()+",\"level\":" + (TypeConverter.strToInt(level) + 1) +
                            ",\"isFolder\":\"false\"," + icon + "\"isLazy\":false}";
                }
            }
            return "{\"success\":true,\"data\":["+data+"]}";
        }
        List<Unit> totalList = unitService.getUnitListForCache();
        int parentId = TypeConverter.strToInt(id);
        if(id.equalsIgnoreCase("root"))
        {
            parentId = LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId();
        }
        int pid = parentId;
        List<Unit> list = totalList.stream().filter(x -> x.getParentid()==pid).sorted(Comparator.comparing(Unit::getType)).collect(Collectors.toList());
        for (Unit unit : list)
        {
            List<Unit> subList = totalList.stream().filter(x->x.getParentid().intValue()==unit.getPkid().intValue()).collect(Collectors.toList());
            List<DataDicValDetail> gradeList = dataDicValDetailService.getListByDCode("XD_NJ").stream().filter(x->ToolsUtil.InArray(x.getValcode(), unit.getStageids())).collect(Collectors.toList());
            int subCount = subList.size() + gradeList.size();
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
        Unit school = unitService.getUnitForCache(pid);
        if(school.getType() == 1)
        {
            List<DataDicValDetail> gradeList = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ").stream().filter(x->ToolsUtil.InArray(x.getValcode(), school.getStageids())).collect(Collectors.toList());
            for (DataDicValDetail grade : gradeList)
            {
                List<SchoolClass> subList = schoolClassService.getSchoolClassListForCache(school.getPkid(), TypeConverter.strToInt(grade.getValcode()), TypeConverter.strToInt(grade.getCode()));
                String title = grade.getName();
                String icon = "\"icon\": '../css/folder.gif',";
                String key = grade.getCode() +"_" + school.getPkid() +"_" + grade.getValcode();
                if (StringUtils.isEmpty(data))
                {
                    data = "{\"key\":\"" + key + "\",\"schoolId\":\""+school.getPkid()+"\",\"stageId\":\""+grade.getValcode()+"\",\"title\":\"" +
                            title + "\",\"code\":\"1\",\"name\":\"" +
                            grade.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":"+subList.size()+",\"level\":" + (TypeConverter.strToInt(level) + 1) +
                            ",\"isFolder\":\"true\"," + icon + "\"isLazy\":"+(subList.size() > 0 ? "true" : "false")+"}";
                }
                else
                {
                    data += ",{\"key\":\"" + key + "\",\"schoolId\":\""+school.getPkid()+"\",\"stageId\":\""+grade.getValcode()+"\",\"title\":\"" +
                            title + "\",\"code\":\"1\",\"name\":\"" +
                            grade.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":"+subList.size()+",\"level\":" + (TypeConverter.strToInt(level) + 1) +
                            ",\"isFolder\":\"true\"," + icon + "\"isLazy\":"+(subList.size() > 0 ? "true" : "false")+"}";
                }
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

    @RequestMapping(value = "/getStudentListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<User> getStudentListForPage(JqGridPageRequest pageRequest,Integer schoolId, Integer stageId, Integer gradeId, Integer classId,String searchKey)
    {
        String searchStr = "usertype=1";
        if(schoolId == null) schoolId = LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId();
        if(classId != null)
        {
            //选择具体班级
            searchStr += " and pkid in (select userid from basic_schoolclassuser where classid="+classId+")";
        }
        else if(schoolId != null)
        {
            if(gradeId !=null && stageId !=null && schoolId !=null)
            {
                //选择年级
                searchStr += " and pkid in (select userid from basic_schoolclassuser where classid in (select pkid from basic_schoolclasss where unitid="+schoolId+" and stageid="+stageId+" and gradeid="+gradeId+"))";
            }
            else
            {
                Unit school = unitService.getUnitForCache(schoolId);
                if(school!=null) {
                    searchStr += " and pkid in (select userid from basic_schoolclassuser where unitid in (select pkid from basic_unit where pkid="+schoolId+" or path like '"+school.getPath()+",%'))";
                }
                else
                {
                    searchStr += " and pkid in (select userid from basic_schoolclassuser where unitid=" + schoolId + ")";
                }
            }
        }
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and (name like '%"+searchKey+"%' or loginname like '%"+searchKey+"%')";
        }
        List<DataDicValDetail> gradeList = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<User> list = userService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for(User user : list)
        {
            SchoolClassUser schoolClassUser = schoolClassUserService.getStudentByUserId(user.getPkid());
            if(schoolClassUser!=null)
            {
                SchoolClass schoolClass = schoolClassService.getSchoolClassForCache(schoolClassUser.getClassid());
                if(schoolClass!=null)
                {
                    user.setClassName(schoolClass.getName());
                    DataDicValDetail grade = gradeList.stream().filter(x->x.getValcode().equalsIgnoreCase(schoolClass.getStageid().toString()) && x.getCode().equalsIgnoreCase(schoolClass.getGradeid().toString())).findFirst().orElse(null);
                    if(grade!=null)
                    {
                        user.setGradeName(grade.getName());
                    }
                }
            }
            Student student = studentService.getByUserId(user.getPkid());
            user.setStudent(student);
        }
        PageInfo<User> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<User> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String studentAdd(Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        List<DataDicVal> sexList = dataDicValService.getDataDicValListForCache("XB");
        model.addAttribute("sexList", sexList);
        return "manage/basic/user/student/add";
    }


    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int studentDoAdd(User user, int unitId, int stageId, int gradeId, int classId, String stuId, String stuNum,String photoRealistic)
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
        user.setUsertype(1);
        user.setFpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName().substring(0,1)));
        user.setSpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName()));
        userService.createStudent(user,unitId, stageId, gradeId, classId, stuId, stuNum);
        //设置考勤头像 add tangy  20190521
        UserPhoto userPhoto=new  UserPhoto();
        userPhoto.setUserid(user.getPkid());
        userPhoto.setPhoto(photoRealistic);
        userPhotoService.insert(userPhoto);
        setOpLog("basic","学生管理", "新增学生：" + user.getName());
        SetKaoQinOpLog("basic_user",user.getPkid(),1);
        return 1;
    }

    @RequestMapping(value = "/getSchoolClassList", method = RequestMethod.POST)
    @ResponseBody
    public List<SchoolClass> getSchoolClassList(int schoolId, int stageId, int gradeId)
    {
        List<SchoolClass> list = schoolClassService.getSchoolClassListForCache(schoolId, stageId, gradeId);
        list = list.stream().sorted(Comparator.comparing(SchoolClass::getSortnum)).collect(Collectors.toList());
        return list;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String studentEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,Model model)
    {
        User user = userService.getByUserId(TypeConverter.strToInt(id));
        if(user == null)
        {
            model.addAttribute("msg","当前学生信息不存在");
            return "/error/error";
        }
        Student student = studentService.getByUserId(user.getPkid());
        if(student == null)
        {
            model.addAttribute("msg","当前学生信息不存在");
            return "/error/error";
        }
        model.addAttribute("user", user);
        model.addAttribute("student", student);

        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        List<DataDicVal> sexList = dataDicValService.getDataDicValListForCache("XB");
        model.addAttribute("sexList", sexList);
        List<UnitUser> unitUsers = unitUserService.getListByUserId(user.getPkid());
        if(unitUsers.size()>0)
        {
            model.addAttribute("unitId", unitUsers.get(0).getUnitid());
        }
        else{
            model.addAttribute("unitId", "0");
        }
        model.addAttribute("classId","0");
        model.addAttribute("stageId","0");
        model.addAttribute("gradeId", "0");
        SchoolClassUser schoolClassUser = schoolClassUserService.getStudentByUserId(user.getPkid());
        if(schoolClassUser != null)
        {
            SchoolClass schoolClass = schoolClassService.getSchoolClassForCache(schoolClassUser.getClassid());
            if(schoolClass!=null)
            {
                model.addAttribute("classId",schoolClass.getPkid());
                model.addAttribute("gradeId", schoolClass.getGradeid());
                model.addAttribute("stageId", schoolClass.getStageid());
            }
        }
        //考勤图片 add tangy  20190521
        UserPhoto userPhoto=userPhotoService.getByUserId(user.getPkid());
        if(userPhoto==null)
        {
            userPhoto=new  UserPhoto();
            userPhoto.setUserid(user.getPkid());
            userPhoto.setPhoto("/assets/lib/images/avatars/avatar2.png");
        }
        model.addAttribute("userPhoto", userPhoto);
        return "manage/basic/user/student/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int studentDoEdit(User editDate, int unitId, int stageId, int gradeId, int classId, String stuId, String stuNum,String  photoRealistic)
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
        userService.update(user);
        Student student = studentService.getByUserId(user.getPkid());
        student.setStuid(stuId);
        student.setStunum(stuNum);
        studentService.update(student);
        userService.createUnitUser(user, unitId);
        userService.createSchoolClassUserForStudent(user, unitId, classId);
        //修改考勤图片 add tangy  20190521
        UserPhoto userPhoto=userPhotoService.getByUserId(user.getPkid());
        if(userPhoto==null)
        {
            userPhoto=new  UserPhoto();
            userPhoto.setUserid(user.getPkid());
            userPhoto.setPhoto("/assets/lib/images/avatars/avatar2.png");
            userPhotoService.insert(userPhoto);
        }
        else{
            userPhoto.setPhoto(photoRealistic);
            userPhotoService.update(userPhoto);
        }
        setOpLog("basic","学生管理", "编辑学生：" + user.getName());
        SetKaoQinOpLog("basic_user",user.getPkid(),2);
        return 1;
    }

    @RequestMapping(value = "/inport", method = RequestMethod.GET)
    public String inport(Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        return "/manage/basic/user/student/inport";
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
                String className = "";
                if(strings.length>4){
                    className = strings[4].trim();
                }
                String stuNum = "";
                if(strings.length>5){
                    stuNum = strings[5].trim();
                }
                String stuId = "";
                if(strings.length>6){
                    stuId = strings[6].trim();
                }
                String mobile = "";
                if(strings.length>7){
                    mobile = strings[7].trim();
                }
                String email = "";
                if(strings.length>8){
                    email = strings[8].trim();
                }
                className = className.replace("（", "(");
                className = className.replace("）", ")");
                String gradeName = className.substring(0, className.indexOf("("));
                className = className.replace(gradeName,"");
                className = className.replace("(","");
                className = className.replace(")","");
                int classIndex = TypeConverter.strToInt(className.replace("班",""));
                className = String.format("%02d", classIndex) +"班";
                int stageId = 0;
                int gradeId = 0;
                int classId = 0;
                List<DataDicValDetail> list = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
                DataDicValDetail valDetail = list.stream().filter(x->x.getName().equalsIgnoreCase(gradeName)).findFirst().orElse(null);
                if(valDetail!=null)
                {
                    stageId = TypeConverter.strToInt(valDetail.getValcode());
                    gradeId = TypeConverter.strToInt(valDetail.getCode());
                }
                SchoolClass schoolClass = schoolClassService.getBySchoolIdAndStageIdAndGradeIdAndName(unitId, stageId, gradeId, className);
                if(schoolClass!=null)
                {
                    classId = schoolClass.getPkid();
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
                user.setUsertype(1);
                user.setFpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName().substring(0,1)));
                user.setSpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName()));
                user.setPhoto("");
                user.setProfile("");
                userService.createStudent(user,unitId, stageId, gradeId, classId, stuId, stuNum);
                SetKaoQinOpLog("basic_user",user.getPkid(),1);
                UserPhoto userPhoto=userPhotoService.getByUserId(user.getPkid());
                if(userPhoto==null)
                {
                    userPhoto=new  UserPhoto();
                    userPhoto.setUserid(user.getPkid());
                    userPhoto.setPhoto("/assets/lib/images/avatars/avatar2.png");
                    userPhotoService.insert(userPhoto);
                }
            }
            ToolsUtil.deleteFile(filePath);
            setOpLog("basic","教职工管理", "学校“"+unit.getName()+"”导入学生数据");
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping(value = "/inportPhoto", method = RequestMethod.GET)
    public String inportPhoto(Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        return "/manage/basic/user/student/inportphoto";
    }

    @RequestMapping(value = "/doInportPhoto", method = RequestMethod.POST)
    @ResponseBody
    public int doInportPhoto(String fileName,int unitId)
    {
        Unit unit = unitService.getByPkId(unitId);
        if(unit == null)
        {
            return -1;
        }
        String filePath = ToolsUtil.getUploadPath("/upload/temp/" + fileName);
        try {
            String fileSavePath = ToolsUtil.getUploadPath("/upload/temp/" + ToolsUtil.getFileNameWithoutExt(new File(filePath)));
            ZipUtil.unZip(filePath, fileSavePath, true);
            File[] files = new File(fileSavePath).listFiles();
            for (File file : files) {
                if(file.isDirectory())
                {
                    String className = file.getName();
                    className = className.replace("（", "(");
                    className = className.replace("）", ")");
                    if(className.indexOf("(")<0) continue;
                    String gradeName = className.substring(0, className.indexOf("("));
                    className = className.replace(gradeName,"");
                    className = className.replace("(","");
                    className = className.replace(")","");
                    int classIndex = TypeConverter.strToInt(className.replace("班",""));
                    className = String.format("%02d", classIndex) +"班";
                    int stageId = 0;
                    int gradeId = 0;
                    int classId = 0;
                    List<DataDicValDetail> list = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
                    DataDicValDetail valDetail = list.stream().filter(x->x.getName().equalsIgnoreCase(gradeName)).findFirst().orElse(null);
                    if(valDetail!=null)
                    {
                        stageId = TypeConverter.strToInt(valDetail.getValcode());
                        gradeId = TypeConverter.strToInt(valDetail.getCode());
                    }
                    SchoolClass schoolClass = schoolClassService.getBySchoolIdAndStageIdAndGradeIdAndName(unitId, stageId, gradeId, className);
                    if(schoolClass!=null)
                    {
                        classId = schoolClass.getPkid();
                    }
                    if(classId!=0)
                    {
                        //对应班级存在，可以导入
                        String targetSavePath = ToolsUtil.getUploadPath("/upload/studentphoto/" + classId);
                        if(ToolsUtil.existsDirectory(targetSavePath))
                        {
                            ToolsUtil.deleteDirectory(targetSavePath);
                        }
                        ToolsUtil.copyFolderFileForCoverForNew(fileSavePath+"/" + file.getName(), targetSavePath);
                    }
                }
            }
            ToolsUtil.deleteDirectory(fileSavePath);
            setOpLog("basic","教职工管理", "学校“"+unit.getName()+"”导入学生照片数据");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
