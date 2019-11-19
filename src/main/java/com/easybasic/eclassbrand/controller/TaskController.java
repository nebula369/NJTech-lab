package com.easybasic.eclassbrand.controller;


import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.UnitService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.service.*;
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
import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import javax.annotation.Resource;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/eclassbrand/task")
public class TaskController extends BaseController {
    @Resource
    private TaskService taskservice;
    @Resource
    private SchoolClassService schoolClassService;
    @Resource
    private ClassTaskService classTaskService;
    @Resource
    private DataDicValDetailService dataDicValDetailService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private SubjectService subjectService;
    @Resource
    private UserService userService;
    /**
     * @Description: 网址列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String taskList(Model model)
    {
        String re = setPageCommonModel(model, "eclassbrand","/manage/eclassbrand/task", false);
        if(!StringUtils.isEmpty(re))
        {

            return "manage/eclassbrand/task/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getTaskListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Task> getTaskListForPage(JqGridPageRequest pageRequest, Integer unitId, String searchKey)
    {
       // String searchStr = " unitid=" + unitId;
        String searchStr = "  pkid>0";
        if(LoginUtil.getCurrentLoginUserProperty().isUnitManager())//单位管理员
        {
            searchStr+=" and   pkid IN (SELECT taskid FROM `ecb_classtask` WHERE classid" +
                     " IN (SELECT pkid FROM `basic_schoolclass` WHERE   " +
                     "unitid="+LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId()+"))";
        }
        else{//普通老师,所管理的班级
            searchStr+=" and  pkid " +
                    "  IN (SELECT taskid FROM    `ecb_classtask` WHERE classid" +
                    "  IN (SELECT pkid FROM basic_schoolclass WHERE  pkid" +
                     " IN (SELECT  classid FROM  `edu_schoolclasscurriculum`  WHERE teacherid="+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid()+")))" ;
        }
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and content like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Task> list = taskservice.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for(Task task: list)
        {
             String  classNams=  getClassName(task.getPkid(),0);
             task.setClassName(classNams);
             List<Subject> subjectList = subjectService.getSubjectListForCache().stream().filter(x -> ToolsUtil.InArray(x.getCode().toString(), task.getSubject().toString())).collect(Collectors.toList());
            if(subjectList.size()>0)
            {
                String subjectName=subjectList.get(0).getName();
                task.setSubjectName(subjectName);
            }
         }
        PageInfo<Task> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Task> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    /**
     * @Description: 方法是
     * @param: [pkid, type]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/28 0028 15:28
     */
    public String  getClassName(int pkid,int type)
    {
        String searchStr=" pkid IN (SELECT  classid FROM ecb_classtask WHERE taskid="+pkid+")";
        List<SchoolClass> schoolClasslist = schoolClassService.getListBySearch(searchStr,"pkid desc");
        String  classname="";
        String  classid="";
        for(SchoolClass schoolclass: schoolClasslist)
        {
            String name= schoolclass.getGradeName() + schoolclass.getName();
            if(classname=="")
            {
                classid=schoolclass.getPkid().toString();
                classname=name;
            }
            else{
                classid=classid+","+schoolclass.getName();
                classname=classname+","+name;
            }
        }
        if(type==1)
        {
            return classid;
        }
        else{
            return classname;
        }
    }
    /**
     * @Description: 获取班级
     * @param: []
     * @return: java.util.List<com.easybasic.basic.model.SchoolClass>
     * @auther: tangy
     * @date: 2019/5/28 0028 8:59
     */
    @RequestMapping(value = "/getSchoolClassList", method = RequestMethod.POST)
    @ResponseBody
    public  List<SchoolClass>  getSchoolClass()
    {
        String searchStr="pkid IN (SELECT  classid FROM  `edu_schoolclasscurriculum`  WHERE teacherid="+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid()+")";
        List<SchoolClass> classlist =  schoolClassService.getListBySearch(searchStr,"pkid desc");
        for (SchoolClass schoolclass:classlist)
        {
            schoolclass.setName(schoolclass.getGradeName() + schoolclass.getName());
        }
        return classlist;
    }

    /**
     * @Description: 新增页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String taskAdd(Model model)
    {
        model.addAttribute("classList",  getSchoolClass());
        model.addAttribute("subjectList",  GetSubject());
        return "manage/eclassbrand/task/add";
    }

    public List<Subject> GetSubject()
    {
        User user = userService.getByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        Teacher teacher = teacherService.getByUserId(user.getPkid());
        List<Subject> subjectList = subjectService.getSubjectListForCache().stream().filter(x -> ToolsUtil.InArray(x.getCode().toString(), teacher.getSubjects())).collect(Collectors.toList());
         return subjectList;
    }
    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    /*public int taskDoAdd(String content ,int type,int sortnum,String subjectid,String classid,)*/
    public int taskDoAdd(Task taskData,String classid)
    {
        Task task=new Task();
        task.setCreatetime(new Date());
        task.setContent(taskData.getContent());
        task.setType(taskData.getType());
        task.setSubject(taskData.getSubject());
        task.setSortnum(taskData.getSortnum());
        task.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        taskservice.insert(task);
        for (String id:classid.split(","))
        {
            ClassTask classtask=new ClassTask();
            classtask.setClassid(TypeConverter.strToInt(id));
            classtask.setTaskid(task.getPkid());
            classTaskService.insert(classtask);
        }
        setOpLog("eclassbrand","作业", "增加作业：" + task.getContent());
        return 1;
    }
    /**
     * @Description: 编辑页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String taskEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        Task task = taskservice.getByPkId(TypeConverter.strToInt(id));
        if(task==null)
        {
            model.addAttribute("msg","要编辑的作业不存在");
            return "/error/error";
        }
        model.addAttribute("task", task);
        String searchStr=" pkid IN (SELECT  classid FROM ecb_classtask WHERE taskid="+task.getPkid()+")";
        List<SchoolClass> classlist =  schoolClassService.getListBySearch(searchStr,"pkid desc");
        for (SchoolClass schoolclass:classlist)
        {
            List<DataDicValDetail> xdnj = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
            DataDicValDetail detail = xdnj.stream().filter(x->x.getValcode().equalsIgnoreCase(schoolclass.getStageid().toString()) && x.getCode().equalsIgnoreCase(schoolclass.getGradeid().toString())).findFirst().orElse(null);
            schoolclass.setName(detail.getName()+"["+schoolclass.getName()+"]");
        }
        model.addAttribute("classList", classlist);
        String classid="";
        List<ClassTask> classTaskList=classTaskService.getListBySearch(" taskid="+task.getPkid()+" "," pkid desc");
        for (ClassTask classtask:classTaskList)
        {
            if(classid=="")
            {
                classid=classtask.getClassid().toString();
            }
            else{
                classid=classid+","+classtask.getClassid().toString();
            }
        }
        model.addAttribute("classid",  classid);
        model.addAttribute("subjectList",  GetSubject());
        return "manage/eclassbrand/task/edit";
    }
    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int tasktDoEdit(Task taskData,String classid)
    {
        Task task = taskservice.getByPkId(taskData.getPkid());
        if(task!=null) {
            task.setContent(taskData.getContent());
            task.setSortnum(taskData.getSortnum());
            task.setType(taskData.getType());
            task.setSubject(taskData.getSubject());
            taskservice.update(task);
            //新增作业与班级关联
            for (String id:classid.split(","))
            {
                ClassTask classtask=classTaskService.getByClassIdAndTaskId(TypeConverter.strToInt(id),task.getPkid());
                if(classtask==null)
                {
                    classtask=new  ClassTask();
                    classtask.setClassid(TypeConverter.strToInt(id));
                    classtask.setTaskid(task.getPkid());
                    classTaskService.insert(classtask);
                }
            }
            //删除作业与班级关联
            List<ClassTask> classTaskList=classTaskService.getListBySearch("classid not in ("+classid+") and taskid="+task.getPkid()+"","pkid desc");
            for (ClassTask classtask:classTaskList)
            {
                classTaskService.delete(classtask.getPkid());
            }
            setOpLog("eclassbrand","作业", "编辑作业：" + task.getPkid());
        }
        return 1;
    }
    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int taskDoDel(int id)
    {
        Task task = taskservice.getByPkId(id);
        if(task!=null) {
            taskservice.delete(id);
            setOpLog("eclassbrand","作业", "删除作业：" + task.getPkid());
        }
        return 1;
    }
}
