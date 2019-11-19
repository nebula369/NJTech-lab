package com.easybasic.eclassbrand.controller;

import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.service.*;
import com.easybasic.edu.model.SchoolClassCurriculum;
import com.easybasic.edu.model.SchoolSchedule;
import com.easybasic.edu.model.SchoolScheduleWeekTime;
import com.easybasic.edu.model.Subject;
import com.easybasic.edu.service.SchoolClassCurriculumService;
import com.easybasic.edu.service.SchoolScheduleService;
import com.easybasic.edu.service.SchoolScheduleWeekTimeService;
import com.easybasic.edu.service.SubjectService;
import com.easybasic.kaoqin.controller.PlanWeekController;
import com.easybasic.kaoqin.model.PlanWeek;
import com.easybasic.kaoqin.model.UserPhoto;
import com.easybasic.kaoqin.service.PlanWeekService;
import com.easybasic.kaoqin.service.UserPhotoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/service/eclassbrand")
public class EClassBrandServiceController  extends BaseController {
    @Resource
    private CleanHealthService cleanhealthservice;
    @Resource
    private TaskService taskservice;
    @Resource
    private HonorCategoryService honorcategoryservice;
    @Resource
    private HonorService honorservice;
    @Resource
    private RecommendService recommendservice;
    @Resource
    private UserService userService;
    @Resource
    private UserPhotoService userPhotoService;
    @Resource
    private SubjectService subjectService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private SchoolClassCurriculumService schoolClassCurriculumService;
    @Resource
    private NewsTypeService newstypeservice;
    @Resource
    private NewsService newsservice;
    @Resource
    private ExamPlanService examplanservice;
    @Resource
    private ExamineeService examineeService;
    @Resource
    private ExamRoomService examroomservice;
    @Resource
    private ActivityService activityService;
    @Resource
    private ActivityDetailService activitydetailService;
    @Resource
    private ExamPlanMineeService examPlanMineeService;
    @Resource
    private SchoolScheduleService schoolScheduleService;
    @Resource
    private SchoolScheduleWeekTimeService schoolScheduleWeekTimeService;
    @Resource
    private SpaceService spaceService;
    @Resource
    private SchoolClassService schoolClassService;
    @Resource
    private UnitService unitService;
    @Resource
    private WebsiteService websiteservice;
    @Resource
    private SchoolClassTeacherService schoolclassteacherservice;
    @Resource
    private SchoolClassUserService schoolClassUserService;
    @Resource
    private PlanWeekService planWeekService;
    /**
     * @Description: 获取值日数据
     * @param: [classid:班级编号, beginday：开始周几, endday：结束周几]
     * @return: java.util.List<com.easybasic.eclassbrand.model.CleanHealth>
     * @auther: tangy
     * @date: 2019/6/20 0020 9:29
     */
    @RequestMapping(value = "/getcleanhealthlist", method = RequestMethod.POST)
    @ResponseBody
    public List<CleanHealth> GetCleanHealthList(String classid,String beginday,String endday)
    {
        List<CleanHealth> list=new ArrayList<CleanHealth>();
        if(!StringUtils.isNotBlank(classid))
        {
            return  list ;
        }
        String sql=" classid="+classid;
        if( StringUtils.isNotBlank(beginday)&& StringUtils.isNotBlank(endday))
        {
            sql+=" and  day BETWEEN "+ TypeConverter.strToInt(beginday) +" AND "+TypeConverter.strToInt(endday)+" ";
        }
        else if( StringUtils.isNotBlank(endday)&&!StringUtils.isNotBlank(beginday))
        {
            sql+=" and  day = "+TypeConverter.strToInt(endday)+" ";
        }
        list=cleanhealthservice.getListBySearch(sql,"  `day` ASC ");
        return  list;
    }

    /**
     * @Description: 方法是 作业数据(今日和昨日)
     * @param: [classid:班级编号, begintime：开始日期, endtime：结束日期]
     * @return: java.util.List<com.easybasic.eclassbrand.model.Task>
     * @auther: tangy
     * @date: 2019/6/20 0020 11:32
     */
    @RequestMapping(value = "/gettasklist", method = RequestMethod.POST)
    @ResponseBody
    public List<Task> GetTaskList(String classid,String begintime,String endtime) {
        List<Task> list = new ArrayList<Task>();
        if (!StringUtils.isNotBlank(classid)) {
            return list;
        }
        String sql = "  pkid IN (SELECT  taskid FROM  `ecb_classtask` WHERE classid=" + classid + ")";
        if (StringUtils.isNotBlank(begintime) && StringUtils.isNotBlank(endtime)) {
            sql += " and  createtime BETWEEN '" + begintime + "' AND  '" + endtime + "' ";
        } else if (StringUtils.isNotBlank(endtime) && !StringUtils.isNotBlank(begintime)) {
            sql += " and  createtime = '" + endtime + "' ";
        } else {
            List<Task> newlist = taskservice.getListBySearch(" pkid = (SELECT MAX(pkid) FROM ecb_task)", "createtime desc");
            if (newlist.size() > 0) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(newlist.get(0).getCreatetime());
                sql += " and createtime like '%" + dateString + "%'";
            }
        }
        list = taskservice.getListBySearch(sql, " createtime desc");
        for (Task task : list) {
            List<Subject> subjectList = subjectService.getSubjectListForCache().stream().filter(x -> ToolsUtil.InArray(x.getCode().toString(), task.getSubject().toString())).collect(Collectors.toList());
            if (subjectList.size() > 0) {
                String subjectName = subjectList.get(0).getName();
                task.setSubjectName(subjectName);
            }
        }
        return list;
    }

    /**
     * @Description: 获取荣誉分类
     * @param: [classid:班级编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.HonorCategory>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/gethonorcategorylist", method = RequestMethod.POST)
    @ResponseBody
    public List<HonorCategory> GetHonorCategoryList(String classid)
    {

        List<HonorCategory> list=new ArrayList<HonorCategory>();
        if(!StringUtils.isNotBlank(classid))
        {
            return  list ;
        }
        String sql=" classid="+classid;
        list=honorcategoryservice.getListBySearch(sql," sortnum asc");
        return  list;
    }
    /**
     * @Description: 获取荣誉
     * @param: [categoryid：荣誉分类编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.Honor>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/gethonorlist", method = RequestMethod.POST)
    @ResponseBody
    public List<Honor> GetHonorList(String categoryid)
    {
        List<Honor> list=new ArrayList<Honor>();
        if(!StringUtils.isNotBlank(categoryid))
        {
            return  list ;
        }
        String sql=" categoryid="+TypeConverter.strToInt(categoryid);
        list=honorservice.getListBySearch(sql," sortnum asc");
        return  list;
    }

    /**
     * @Description: 获取推荐
     * @param: [categoryid：荣誉分类编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.Honor>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/getrecommendlist", method = RequestMethod.POST)
    @ResponseBody
    public List<Recommend> GetRecommendList(String classid)
    {
        List<Recommend> list=new ArrayList<Recommend>();
        if(!StringUtils.isNotBlank(classid))
        {
            return  list ;
        }
        String sql="  pkid IN (SELECT  recommendid FROM  `ecb_classrecommend` WHERE classid="+classid+")" ;
        list=recommendservice.getListBySearch(sql," sortnum asc");
        for (Recommend recommend:list)
        {
            User user=userService.getByUserId(recommend.getUserid());
            if(user!=null)
            {
                recommend.setUserName(user.getName());
            }
        }
        return  list;
    }

    /**
     * @Description: 获取班级教师
     * @param: [classid：班级编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.Honor>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/getteacherlist", method = RequestMethod.POST)
    @ResponseBody
    public List<UserInfo> GetTeacherList(String classid)
    {   List<UserInfo> list=new ArrayList<UserInfo>();
        if(!StringUtils.isNotBlank(classid))
        {
            return  list ;
        }
        String sql=" pkid IN (SELECT  teacherid FROM  `edu_schoolclasscurriculum` WHERE classid="+classid+")";
        List<User>  userlist=userService.getListBySearch(sql," pkid asc");
        for (User user:userlist)
        {
            //  UserPhoto userPhoto=userPhotoService.getByUserId(user.getPkid());
            Teacher teacher = teacherService.getByUserId(user.getPkid());
            UserInfo userinfo=new UserInfo();
            userinfo.pkid=user.getPkid();
            userinfo.name=user.getName();
            userinfo.photo="assets/lib/images/avatars/avatar2.png";
            if(user.getPhoto()!=null)
            {
                userinfo.photo=user.getPhoto();
            }
            userinfo.motto=user.getProfile();
            String subjectinfo="";
            List<Subject> subjectList = subjectService.getAllList();
            for (Subject subject:subjectList)
            {
                String [] Subjects=teacher.getSubjects().split(",");
                if(Subjects.length>0)
                {
                    for (String id : Subjects)
                    {
                        if(subject.getPkid()==TypeConverter.strToInt(id))
                        {
                            if(subjectinfo=="")
                            {
                                subjectinfo=subject.getName();
                            }
                            else{
                                subjectinfo=subjectinfo+','+subject.getName();
                            }
                        }
                    }
                }
            }
            userinfo.subject=subjectinfo;
            list.add(userinfo);
        }
        return  list;
    }
    /**
     * @Description: 获取班级学生
     * @param: [classid：班级编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.Honor>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/getstudentlist", method = RequestMethod.POST)
    @ResponseBody
    public List<UserInfo> GetStudentList(String classid)
    {
        List<UserInfo> list=new ArrayList<UserInfo>();
        if(!StringUtils.isNotBlank(classid))
        {
            return  list ;
        }
        String sql=" pkid IN (SELECT  userid FROM  `basic_schoolclassuser`  WHERE classid="+classid+")";
        List<User>  userlist=userService.getListBySearch(sql," pkid asc");
        for (User user:userlist)
        {
            UserInfo userinfo=new UserInfo();
            userinfo.pkid=user.getPkid();
            userinfo.name=user.getName();
            UserPhoto userPhoto=userPhotoService.getByUserId(user.getPkid());
            userinfo.photo=null;
            if(userPhoto!=null)
            {
                userinfo.photo=userPhoto.getPhoto();
            }
            userinfo.motto=null;
            userinfo.subject=null;
            list.add(userinfo);
        }
        return  list;
    }

    @RequestMapping(value = "/getSchoolClassCurriculumList", method = RequestMethod.POST)
    @ResponseBody
    public List<SubjectClass> getSchoolClassCurriculumList(int schoolId, int classId)
    {
        List<SubjectClass> list = new ArrayList<SubjectClass>();
        List<SchoolClassCurriculum> classlist = schoolClassCurriculumService.getListBySchoolIdAndClassId(schoolId, classId);
        for (SchoolClassCurriculum schoolClassCurriculum : classlist) {
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

    @RequestMapping(value = "/getSchoolClassCurriculumLists", method = RequestMethod.POST)
    @ResponseBody
    public List<SchoolClassCurriculum> getSchoolClassCurriculumLists(int schoolId, int classId)
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

    /**
     * @Description: 获取班级班主任
     * @param: [classid:班级编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.HonorCategory>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/getteacher", method = RequestMethod.POST)
    @ResponseBody
    public UserInfo GetTeacher(Integer classid) {
        List<SchoolClassTeacher> list = new ArrayList<SchoolClassTeacher>();
        list = schoolclassteacherservice.selectTeacherByClassId(classid);
        Integer pkid = 0;
        for (SchoolClassTeacher item : list) {
            pkid = item.getTeacherid();
        }
        UserInfo userinfo=new UserInfo();
        User user= userService.getByUserId(pkid);
        userinfo.photo=user.getPhoto();
        userinfo.name=user.getName();
        userinfo.motto=user.getProfile();
        Teacher teacher = teacherService.getByUserId(user.getPkid());
        String subjectinfo="";
        List<Subject> subjectList = subjectService.getAllList();
        for (Subject subject:subjectList)
        {
            String [] Subjects=teacher.getSubjects().split(",");
            if(Subjects.length>0)
            {
                for (String id : Subjects)
                {
                    if(subject.getPkid()==TypeConverter.strToInt(id))
                    {
                        if(subjectinfo=="")
                        {
                            subjectinfo=subject.getName();
                        }
                        else{
                            subjectinfo=subjectinfo+','+subject.getName();
                        }
                    }
                }
            }
        }
        userinfo.subject=subjectinfo;
        return userinfo;
    }

    /**
     * @Description: 获取教师信息
     * @param: [classid:班级编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.HonorCategory>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/getteacherinfo", method = RequestMethod.POST)
    @ResponseBody
    public UserInfo GetTeacherInfo(Integer teacherid) {
        UserInfo userinfo=new UserInfo();
        User user= userService.getByUserId(teacherid);
        userinfo.photo=user.getPhoto();
        userinfo.name=user.getName();
        userinfo.motto=user.getProfile();
        Teacher teacher = teacherService.getByUserId(user.getPkid());
        String subjectinfo="";
        List<Subject> subjectList = subjectService.getAllList();
        for (Subject subject:subjectList)
        {
            String [] Subjects=teacher.getSubjects().split(",");
            if(Subjects.length>0)
            {
                for (String id : Subjects)
                {
                    if(subject.getPkid()==TypeConverter.strToInt(id))
                    {
                        if(subjectinfo=="")
                        {
                            subjectinfo=subject.getName();
                        }
                        else{
                            subjectinfo=subjectinfo+','+subject.getName();
                        }
                    }
                }
            }
        }
        userinfo.subject=subjectinfo;
        return userinfo;
    }


    /**
     * @Description: 获取新闻分类
     * @param: [classid:班级编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.HonorCategory>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/getnewstypelist", method = RequestMethod.POST)
    @ResponseBody
    public List<NewsType> GetNewsTypeList(String classid)
    {

        List<NewsType> list=new ArrayList<NewsType>();
        if(!StringUtils.isNotBlank(classid))
        {
            return  list ;
        }
        String sql=" classid="+classid;
        list=newstypeservice.getNewsListForPage(sql," pkid asc");
        return  list;
    }

    /**
     * @Description: 获取新闻列表
     * @param: [categoryid：新闻分类编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.News>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/getnewslist", method = RequestMethod.POST)
    @ResponseBody
    public List<News> GetNewsList(String categoryid,String classid,String key) {
        List<News> list = new ArrayList<News>();
        if (!StringUtils.isNotBlank(categoryid)) {
            return list;
        }
        String sql = " newstype=" + TypeConverter.strToInt(categoryid) + " and title like '%" + key + "%'";
        if (TypeConverter.strToInt(categoryid) == 0)
            sql = " (isschool = 1 OR newstype IN (SELECT pkid FROM ecb_newstype WHERE classid= "+ classid +"))  AND title LIKE '%"+ key +"%'";
        list = newsservice.getNewsListForPage(sql, " sortnum asc");
        return list;
    }



    /**
     * @Description: 获取新闻
     * @param: [categoryid：新闻编号]
     *
     * @return: java.util.List<com.easybasic.eclassbrand.model.News>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/getnews", method = RequestMethod.POST)
    @ResponseBody
    public News GetNews(String pkid) {
        News news = newsservice.getByPkId(TypeConverter.strToInt(pkid));
        return news;
    }

    /**
     * @Description: 获取今日考试信息
     * @param: [classid]
     * @return: java.util.List<com.easybasic.eclassbrand.model.ExamPlan>
     * @auther: tangy
     * @date: 2019/6/25 0025 14:14
     */
    @RequestMapping(value = "/getexamplanlist", method = RequestMethod.POST)
    @ResponseBody
    public List<ExamPlan> GetExamPlanList(String spaceid)
    {
        List<ExamPlan> list=new ArrayList<ExamPlan>();
        if(!StringUtils.isNotBlank(spaceid))
        {
            return  list ;
        }
        String sql=" TO_DAYS(starttime) = TO_DAYS(NOW()) AND  examroomid IN (SELECT pkid FROM `exam_examroom` WHERE spaceid ="+TypeConverter.strToInt(spaceid)+")";
        list=examplanservice.getListBySearch(sql," starttime asc");
        for (ExamPlan examplan:list)
        {
            ExamRoom examroom=examroomservice.getByPkId(examplan.getExamroomid());
            if(examroom!=null)
            {
                examplan.setExamRoomName(examroom.getName());
            }
            List<ExamPlanMinee> examplanmineeList=examPlanMineeService.getListBySearch(" examplanid="+examplan.getPkid()," pkid desc");
            examplan.setUsercount(examplanmineeList.size());
        }
        return  list;
    }

    /**
     * @Description: 获取考场中的学生
     * @param: [examplanid]
     * @return: java.util.List<com.easybasic.eclassbrand.model.Examinee>
     * @auther: tangy
     * @date: 2019/6/25 0025 14:22
     */
    @RequestMapping(value = "/getexamineelist", method = RequestMethod.POST)
    @ResponseBody
    public List<Examinee> GetExamineeList(String examplanid)
    {
        List<Examinee> list=new ArrayList<Examinee>();
        if(!StringUtils.isNotBlank(examplanid))
        {
            return  list ;
        }
        String sql=" pkid IN (SELECT examineeid FROM `exam_examplanminee` WHERE examplanid="+TypeConverter.strToInt(examplanid)+")";
        list= examineeService.getListBySearch(sql,"pkid desc");
        return  list;
    }


    /**
     * @Description: 获取活动列表
     * @param: [classid:班级编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.HonorCategory>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/getactivitylist", method = RequestMethod.POST)
    @ResponseBody
    public List<Activity> GetActivityList(String classid,Integer type) {
        List<Activity> list = new ArrayList<Activity>();
        if (!StringUtils.isNotBlank(classid)) {
            return list;
        }
        String sql = "";
        //本班活动
        if (type == 0)
            sql = " pkid in(select activityid from ecb_classactivity where classid = " + classid + ")";
            //全校活动
        else
            sql = " activitytype = 1";
        list = activityService.getActivityListForPage(sql, " pkid asc");
        return list;
    }


    /**
     * @Description: 获取活动详情列表
     * @param: [categoryid：活动编号]
     * @return: java.util.List<com.easybasic.eclassbrand.model.News>
     * @auther: tangy
     * @date: 2019/6/21 0021 10:20
     */
    @RequestMapping(value = "/getactivitydetaillist", method = RequestMethod.POST)
    @ResponseBody
    public List<ActivityDetail> GetActivityDetailList(String activityid,String key) {
        List<ActivityDetail> list = new ArrayList<ActivityDetail>();
        if (!StringUtils.isNotBlank(activityid)) {
            return list;
        }
        String sql = " `describe` like '%" + key + "%' and activityid = " + activityid;
        list = activitydetailService.getActivityDetailListForPage(sql, " createtime asc");
        return list;
    }
    @RequestMapping(value = "/getclassorspaceinfo", method = RequestMethod.POST)
    @ResponseBody
    public String getClassOrSpaceInfo(String terminalname,String code)
    {
        try
        {
            String sql= " name='"+terminalname.trim()+"'";
            List<Space> list=spaceService.getListBySearch(sql," pkid desc");
            if(list.size()>0)
            {
                //场地信息
                Space space=list.get(0);
                //班级信息
                SchoolClass  schoolclass=schoolClassService.getByPkId(space.getClassid());
                //学校信息
                Unit  unit=unitService.getByPkId(schoolclass.getUnitid());

                //班级人数
                int studentcount= schoolClassUserService.getStudentCountByClassId(schoolclass.getPkid());

                if(unit==null)
                {
                    unit=new Unit();
                }
                if(schoolclass==null)
                {
                    schoolclass=new SchoolClass();
                }
                String schoolclassname= schoolclass.getGradeName() + schoolclass.getName();
                return "{schoolId:"+unit.getPkid()+",schoolName:\""+unit.getName()+"\",schoolLogo:\"\",classId:"+schoolclass.getPkid()+",className:\""+schoolclassname+"\",studentcount:"+ studentcount +",stageId:"+schoolclass.getStageid()+",spaceId:"+space.getPkid()+",spaceName:\""+space.getName()+"\"}";
            }
        }
        catch (Exception ex)
        {
            return  "";
        }
        return  "";
    }

    @RequestMapping(value = "/getSchoolScheduleList", method = RequestMethod.POST)
    @ResponseBody
    public List<SchoolSchedule> getSchoolScheduleList(int schoolId, int stageId)
    {
        List<SchoolSchedule> list = schoolScheduleService.getListBySchoolAndStage(schoolId, stageId);
        for (SchoolSchedule schoolSchedule : list) {
            List<SchoolScheduleWeekTime> weekTimeList = schoolScheduleWeekTimeService.getListByScheduleId(schoolSchedule.getPkid());
            schoolSchedule.setWeekTimeList(weekTimeList);
        }
        return list;
    }

    @RequestMapping(value = "/getWebsiteListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Website> getWebsiteListForPage(JqGridPageRequest pageRequest, Integer unitId, String searchKey)
    {
        String searchStr = "unitid=" + unitId;
        if(!com.alibaba.druid.util.StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Website> list = websiteservice.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<Website> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Website> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    @RequestMapping(value = "/getexamplanlistForEasyDs", method = RequestMethod.POST)
    @ResponseBody
    public List<ExamPlan> getexamplanlistForEasy(String terminalname) {
        String sql1 = " name='" + terminalname + "'";
        List<Space> spacelist = spaceService.getListBySearch(sql1, " pkid desc");
        if (spacelist.size() > 0) {
            //场地信息
            Space space = spacelist.get(0);
            List<ExamPlan> list = new ArrayList<ExamPlan>();
            String sql = " TO_DAYS(starttime) = TO_DAYS(NOW()) AND  examroomid IN (SELECT pkid FROM `exam_examroom` WHERE spaceid =" + space.getPkid() + ")";
            list = examplanservice.getListBySearch(sql, " starttime asc");
            for (ExamPlan examplan : list) {
                ExamRoom examroom = examroomservice.getByPkId(examplan.getExamroomid());
                if (examroom != null) {
                    examplan.setExamRoomName(examroom.getName());
                }
                examplan.setExamDate(TypeConverter.dateToString(examplan.getStarttime(),"MM月dd日"));
                examplan.setExamTime(TypeConverter.dateToString(examplan.getStarttime(),"HH:mm")+"--"+TypeConverter.dateToString(examplan.getEndtime(),"HH:mm"));
                List<ExamPlanMinee> examplanmineeList = examPlanMineeService.getListBySearch(" examplanid=" + examplan.getPkid(), " pkid desc");
                examplan.setUsercount(examplanmineeList.size());
            }
            return list;
        }
        return null;

    }

    @RequestMapping(value = "/getweekplanlistForEasyDs", method = RequestMethod.POST)
    @ResponseBody
    public List<PlanWeek> getplanweekForEasy(String terminalname) throws ParseException {
        String sql1 = " name='" + terminalname.trim() + "'";
        List<Space> spacelist = spaceService.getListBySearch(sql1, " pkid desc");
        if (spacelist.size() > 0) {
            //场地信息
            Space space = spacelist.get(0);

            List<PlanWeek> list = planWeekService.getListBySearch("SpaceID =" + space.getPkid(), "pkid");
            Integer[] weekDays = {7, 1, 2, 3, 4, 5, 6};
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;

            String weekno = weekDays[w].toString();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<PlanWeek> newlist=new ArrayList<>();
            for (PlanWeek item : list) {
                if (weekno.equals(item.getWeekno())) {
                    String begin = formatter1.format(new Date()) + " " + formatter.format(item.getBegintime());
                    item.setBegintime(format1.parse(begin));
                    String end = formatter1.format(new Date()) + " " + formatter.format(item.getEndtime());
                    item.setEndtime(format1.parse(end));
                    newlist.add(item);
                }
            }
            return newlist;
        }
        return null;

    }


    public  class  UserInfo implements Serializable {
        public  int pkid;
        public  String name;
        public  String photo;
        public  String subject;
        public  String motto;
    }

    public  class SubjectClass implements Serializable {
        public  int pkid;
        public  String name;
        public  String photo;
        public  String subject;
        public  String motto;
    }
}
