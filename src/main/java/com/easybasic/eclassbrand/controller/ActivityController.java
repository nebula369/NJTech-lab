package com.easybasic.eclassbrand.controller;


import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.SchoolClass;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.SchoolClassService;
import com.easybasic.basic.service.UnitService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manage/eclassbrand/activity")
public class ActivityController extends BaseController {
    @Resource
    private UnitService unitService;
    @Resource
    private ActivityService activityservice;
    @Resource
    private ActivityDetailService activitydetailservice;
    @Resource
    private ClassActivityService classactivityservice;
    @Resource
    private SchoolClassService schoolClassService;
    /**
     * @Description: 网址列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String ActivityList(Model model)
    {
        String re = setPageCommonModel(model, "eclassbrand","/manage/eclassbrand/activity", false);
        if(!StringUtils.isEmpty(re))
        {
            List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
            initUnitList(list);
            model.addAttribute("unitList", list);
            return "manage/eclassbrand/activity/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getActivityListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Activity> getActivityListForPage(JqGridPageRequest pageRequest, String searchKey)
    {
        String searchStr = " pkid >0 ";
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and title like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Activity> list = activityservice.getActivityListForPage(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for(Activity activity: list)
        {
            String  classNams=  getClassName(activity.getPkid(),0);
            activity.setClassName(classNams);
        }
        PageInfo<Activity> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Activity> response = new JqGridPageResponse<>();
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
        String searchStr=" pkid IN (SELECT  classid FROM ecb_classactivity WHERE activityid="+pkid+")";
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
    @RequestMapping(value = "/getActivityDetailListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<ActivityDetail> getActivityDetailListForPage(JqGridPageRequest pageRequest,Integer activityid, String searchKey) {
        String searchStr = " pkid >0 and activityid = " + activityid;
        if (!StringUtils.isEmpty(searchKey)) {
            searchStr += " and title like '%" + searchKey + "%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<ActivityDetail> list = activitydetailservice.getActivityDetailListForPage(searchStr, pageRequest.getSidx() + " " + pageRequest.getSord());
        PageInfo<ActivityDetail> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<ActivityDetail> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    /**
     * @Description: 新增页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String activityAdd(Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("classList",  getSchoolClass());
        return "manage/eclassbrand/activity/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int activitytDoAdd(Integer activityType,String placeId,String intro,String title,String author,String starttime,String endtime,Integer sortnum,String content,String photo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startdate= sdf.parse(starttime);
        Date endDate= sdf.parse(endtime);
        Activity activity=new Activity();
        activity.setContent(content);
        activity.setTitle(title);
        activity.setAuthor(author);
        activity.setCreatetime(new Date());
        activity.setActivitytype(activityType);
        activity.setEndtime(startdate);
        activity.setStarttime(endDate);
        activity.setIntro(intro);
        activity.setSortnum(sortnum);
        activity.setPhoto(photo);

        activityservice.insert(activity);
        if (placeId!=null&&placeId!="") {
            for (String id : placeId.split(",")) {
                ClassActivity classtask = new ClassActivity();
                classtask.setClassid(TypeConverter.strToInt(id));
                classtask.setActivityid(activity.getPkid());
                classactivityservice.insert(classtask);
            }
        }
        setOpLog("eclassbrand","活动名称", "增加活动名称：" + activity.getTitle());
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
    public String activityEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        Activity activity = activityservice.getByPkId(TypeConverter.strToInt(id));
        if(activity==null)
        {
            model.addAttribute("msg","要编辑的活动不存在");
            return "/error/error";
        }

        String classid="";
        List<ClassActivity> classTaskList=classactivityservice.getListBySearch(" activityid="+activity.getPkid()+" "," pkid desc");
        for (ClassActivity classtask:classTaskList)
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("activity", activity);
        model.addAttribute("classList",  getSchoolClass());
        model.addAttribute("starttime",sdf.format(activity.getStarttime()));
        model.addAttribute("endtime",sdf.format(activity.getEndtime()));
        return "manage/eclassbrand/activity/edit";
    }
    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int activitytDoEdit(Integer pkid,Integer activityType,String classid,String intro,String title,String author,String starttime,String endtime,Integer sortnum,String content,String photo) throws ParseException {
        Activity website = activityservice.getByPkId(pkid);
        if (website != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startdate = sdf.parse(starttime);
            Date endDate = sdf.parse(endtime);
            website.setAuthor(author);
            website.setTitle(title);
            website.setContent(content);
            website.setStarttime(startdate);
            website.setSortnum(sortnum);
            website.setIntro(intro);
            website.setActivitytype(activityType);
            website.setEndtime(endDate);
            website.setPhoto(photo);
            activityservice.update(website);
            //新增活动与班级关联
            if (classid != null && classid != "") {
                for (String id : classid.split(",")) {
                    ClassActivity classtask = classactivityservice.getByClassIdAndTaskId(TypeConverter.strToInt(id), website.getPkid());
                    if (classtask == null) {
                        classtask = new ClassActivity();
                        classtask.setClassid(TypeConverter.strToInt(id));
                        classtask.setActivityid(website.getPkid());
                        classactivityservice.insert(classtask);
                    }
                }
            }
            //删除活动与班级关联
            String str = " activityid=" + website.getPkid();
            if (classid != null && classid != "") {
                str = str + " and classid not in (" + classid + ")";
            }
            List<ClassActivity> classTaskList = classactivityservice.getListBySearch(str, "pkid desc");
            for (ClassActivity classtask : classTaskList) {
                classactivityservice.delete(classtask.getPkid());
            }
            setOpLog("eclassbrand", "活动内容", "编辑活动内容：" + website.getTitle());
        }
        return 1;
    }
    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int activityDoDel(int id)
    {
        Activity website = activityservice.getByPkId(id);
        if(website!=null) {
            activityservice.delete(id);
            setOpLog("eclassbrand","网站地址", "删除网站地址：" + website.getTitle());
        }
        return 1;
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
        return classlist;
    }

    /**
     * @Description: 活动详情列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/detaillist", method = RequestMethod.GET)
    public String NewsList(@RequestParam(value = "activityid", required = false, defaultValue = "0")Integer activityid, Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("activityid", activityid);
        return "manage/eclassbrand/activity/detaillist";
    }

    /**
     * @Description: 新增活动详情页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/detailadd", method = RequestMethod.GET)
    public String newsAdd(Model model,@RequestParam(value = "activityid", required = false, defaultValue = "0")String activityid)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("activityid",  activityid);
        return "manage/eclassbrand/activity/detailadd";
    }

    @RequestMapping(value = "/doDetailAdd", method = RequestMethod.POST)
    @ResponseBody
    public int activityDetailDoAdd(Integer activityid,String describe,String photo) throws ParseException {
        ActivityDetail activity=new ActivityDetail();
        activity.setActivityid(activityid);
        activity.setDescribe(describe);
        activity.setCreatetime(new Date());
        activity.setPhoto(photo);
        activitydetailservice.insert(activity);

        setOpLog("eclassbrand","活动详情", "增加活动详情：" + activity.getActivityid());
        return 1;
    }


    /**
     * @Description: 编辑活动详情页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/detailedit", method = RequestMethod.GET)
    public String Detailedit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        ActivityDetail news = activitydetailservice.getByPkId(TypeConverter.strToInt(id));
        if(news==null)
        {
            model.addAttribute("msg","要编辑的活动详情不存在");
            return "/error/error";
        }
        model.addAttribute("activitydetail", news);
        return "manage/eclassbrand/activity/detailedit";
    }

    @RequestMapping(value = "/DetaildoEdit", method = RequestMethod.POST)
    @ResponseBody
    public int DetailDoEdit(Integer pkid,String describe,String photo) throws ParseException {
        ActivityDetail website = activitydetailservice.getByPkId(pkid);
        if (website != null) {
            website.setDescribe(describe);
            website.setPhoto(photo);

            activitydetailservice.update(website);

            setOpLog("eclassbrand", "活动详情", "编辑活动详情：" + website.getActivityid());
        }
        return 1;
    }

    @RequestMapping(value = "/DetaildoDel", method = RequestMethod.POST)
    @ResponseBody
    public int DetaildoDel(String ids) {
        if (ids == "" || ids == null) {
            return 1;
        }
        String[] idstr = ids.split(",");
        for (String id : idstr) {
            ActivityDetail website = activitydetailservice.getByPkId(Integer.parseInt(id));
            if (website != null) {
                activitydetailservice.delete(Integer.parseInt(id));
                setOpLog("eclassbrand", "活动详情", "删除活动详情：" + website.getPkid());
            }
        }
        return 1;
    }
}
