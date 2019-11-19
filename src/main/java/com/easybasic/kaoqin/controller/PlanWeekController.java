package com.easybasic.kaoqin.controller;

import com.easybasic.component.Utils.EncodeUtils;
import com.easybasic.component.Utils.JsonUtils;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.kaoqin.model.*;
import com.easybasic.kaoqin.service.*;
import com.easybasic.basic.model.*;
import com.easybasic.basic.model.SpaceType;
import com.easybasic.basic.service.*;
import com.easybasic.basic.service.SpaceTypeService;
import com.easybasic.component.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.Calendar;
import java.text.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.alibaba.druid.util.StringUtils;

@Controller
@RequestMapping("/manage/kaoqin/planweek")
public class PlanWeekController  extends BaseController {

    @Resource
    private SpaceService spaceService;
    @Resource
    private SpaceTypeService spaceTypeService;
    @Resource
    private PlanWeekService planWeekService;
    @Resource
    private  UserGroupService userGroupService;
    @Resource
    private  UserService userService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String planweekList(Model model)
    {
        String re = setPageCommonModel(model, "kaoqin","/manage/kaoqin/planweek", false);
        if(!StringUtils.isEmpty(re))
        {
           // List<SpaceInfo>  spaceInfoList=GetSpaceInfoList();
            List<Space> spacelist=spaceService.getAllList();
            model.addAttribute("spacelist",spacelist);
            return "manage/kaoqin/planweek/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getPlanWeekScheduleList", method = RequestMethod.POST)
    @ResponseBody
    public  String getPlanWeekScheduleList(String spaceId, String weekTypeId)
    {
        String  sql=" spaceId="+  Integer.parseInt(spaceId) +"  and WeekOrder = '" +  Integer.parseInt(weekTypeId)  + "' ";
        List<PlanWeek> list = planWeekService.getListBySearch(sql+"  group by begintime","begintime desc");
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        SimpleDateFormat yearformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> timelist=  new ArrayList<String>();
        String  data="";
       for (PlanWeek item : list)
        {
            String  time=formatter.format(item.getBegintime())+"-"+formatter.format(item.getEndtime());
            if (timelist.contains(time))//重复
            {
            }
            else{
                String sqlitem=sql+"  and  BeginTime='"+yearformatter.format(item.getBegintime())+"' and  EndTime='"+yearformatter.format(item.getEndtime())+"'" ;
                List<PlanWeek> weekTimeList=  planWeekService.getListBySearch(sqlitem,"pkid desc");
                String itemdata="";
                //
                for (PlanWeek planweek : weekTimeList)
                {
                    String groupnames = "";
                    if (!StringUtils.isEmpty(planweek.getUsergroupidstr())&&planweek.getUsergroupidstr()!=null)
                    {
                        groupnames=GetUserGroupNames(planweek.getUsergroupidstr());
                    }
                    String teachername="";
                    if (planweek.getTeacherid()!=0)
                    {
                         teachername =userService.getByUserId(planweek.getTeacherid()).getName();
                    }
                    if (StringUtils.isEmpty(itemdata))
                    {

                        itemdata = "{\"pkid\":\"" + planweek.getPkid() + "\",\"subject\":\""+planweek.getSubject()+"\",\"week\":\"" +
                                planweek.getWeekno() + "\",\"groupnames\":\""+groupnames+"\",\"teachername\":\"" + teachername+ "\"}";
                    }
                    else
                    {
                        itemdata +=",{\"pkid\":\"" + planweek.getPkid() + "\",\"subject\":\""+planweek.getSubject()+"\",\"week\":\"" +
                                planweek.getWeekno() + "\",\"groupnames\":\""+groupnames+"\",\"teachername\":\"" +teachername+ "\"}";
                    }
                }

                timelist.add(time);
                if (StringUtils.isEmpty(data))
                {
                    data="{\"time\":\""+time+"\",\"weekTimeList\":["+itemdata+"]}";
                }
                else {

                    data="{\"time\":\""+time+"\",\"weekTimeList\":["+itemdata+"]},"+data;
                }
            }
        }
       if(data=="" )
       {
           return "{\"success\":false,\"msg\":\"该场地无课表\",\"data\":{}}";
       }
       else{
           return "{\"success\":true,\"msg\":\"正确\",\"data\":["+data+"]}";
       }
    }
    /**
     * @Description: 新增周期课表节次页面
     * @param: [spaceId, weekTypeId, model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/23 0023 14:01
     */
    @RequestMapping(value = "/addtime", method = RequestMethod.GET)
    public String planWeekTimeAdd(@RequestParam(value = "spaceId", required = false, defaultValue = "0")String spaceId,
                               @RequestParam(value = "weekTypeId", required = false, defaultValue = "1")String weekTypeId,
                               Model model)
    {

        Space space = spaceService.getByPkId(TypeConverter.strToInt(spaceId));
        if(space==null)
        {
            model.addAttribute("msg","该场地不存在");
            return "/error/error";
        }
        model.addAttribute("spaceId", spaceId);
        model.addAttribute("weekTypeId", weekTypeId);
        return "manage/kaoqin/planweek/addtime";
    }
    /**
     * @Description: 新增时间节点
     * @param: [spaceId, weekTypeId, starttime, endtime]
     * @return: int
     * @auther: tangy
     * @date: 2019/5/23 0023 16:25
     */
    @RequestMapping(value = "/doAddTime", method = RequestMethod.POST)
    @ResponseBody
    public int doAddTime(int spaceId,int weekTypeId,String starttime, String  endtime)
    {
        Space space = spaceService.getByPkId(spaceId);
        if(space!=null)
        {
          String startdate="1901-01-01 "+starttime+":00";
          String enddate="1901-01-01 "+endtime+":00";
          Date _startdate=TypeConverter.stringToDate(startdate,"yyyy-MM-dd HH:mm:ss" );
          Date _enddate=TypeConverter.stringToDate(enddate,"yyyy-MM-dd HH:mm:ss" );
           if ((_enddate.getTime() < _startdate.getTime())||(_enddate.getTime()==  _startdate.getTime()))
            {
                return -1;
            }
          //是否相同
          String    sql="spaceId="+spaceId+" and WeekOrder="+weekTypeId+" and BeginTime='"+startdate+"' and EndTime='"+enddate+"'";
          List<PlanWeek> weekTimeList=  planWeekService.getListBySearch(sql,"pkid desc");
          if(weekTimeList.size()>0)
          {
              return  -2;
          }
          //是否在时间范围内
            sql="spaceId="+spaceId+" and WeekOrder="+weekTypeId+" and BeginTime>'"+startdate+"' and EndTime<'"+enddate+"'";
          List<PlanWeek> weekTimeList2=  planWeekService.getListBySearch(sql,"pkid desc");
            if(weekTimeList2.size()>0)
          {
                return  -2;
          }
         for(int i = 1; i <= 7;i++){//遍历7天
             PlanWeek  planweek=new PlanWeek();
             planweek.setSpaceid(spaceId);
             planweek.setWeekorder(weekTypeId);
             planweek.setWeekno(String.valueOf(i));
             planweek.setSchoolid(space.getUnitid());
             planweek.setTeacherid(0);
             planweek.setSubject("");
             planweek.setUsergroupidstr("0");
             planweek.setBegintime(TypeConverter.stringToDate(startdate,"yyyy-MM-dd HH:mm:ss" ));
             planweek.setEndtime(TypeConverter.stringToDate(enddate,"yyyy-MM-dd HH:mm:ss" ));
             planWeekService.insert(planweek);
             setOpLog("kaoqin","周期计划", "新增周期计划：" + String.valueOf(i));
             SetKaoQinOpLog("kq_planweek",planweek.getPkid(),1);
         }
       }
        return 1;
    }


    /**
     * @Description: 新增周期课表节次页面
     * @param: [spaceId, weekTypeId, model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/23 0023 14:01
     */
    @RequestMapping(value = "/edittime", method = RequestMethod.GET)
    public String planWeekTimeEdit(@RequestParam(value = "spaceId", required = false, defaultValue = "0")String spaceId,
                                    @RequestParam(value = "weekTypeId", required = false, defaultValue = "1")String weekTypeId,
                                    @RequestParam(value = "timeStr", required = false, defaultValue = "")String timeStr,
                                    Model model)
     {
        Space space = spaceService.getByPkId(TypeConverter.strToInt(spaceId));
        if(space==null)
        {
            model.addAttribute("msg","该场地不存在");
            return "/error/error";
        }
        model.addAttribute("spaceId", spaceId);
        model.addAttribute("weekTypeId", weekTypeId);
        if(timeStr!="")
        {
            String [] timearr=timeStr.split("-");
            model.addAttribute("startTime", timearr[0]);
            model.addAttribute("endTime", timearr[1]);
        }
        return "manage/kaoqin/planweek/edittime";
    }

    /**
     * @Description: 修改时间节点
     * @param: [spaceId, weekTypeId, starttime, endtime]
     * @return: int
     * @auther: tangy
     * @date: 2019/5/23 0023 16:25
     */
    @RequestMapping(value = "/doEditTime", method = RequestMethod.POST)
    @ResponseBody
    public int doEditTime(int spaceId,int weekTypeId,String oldstarttime, String  oldendtime,String starttime, String  endtime)
    {
        Space space = spaceService.getByPkId(spaceId);
        if(space!=null)
        {
            String startdate="1901-01-01 "+oldstarttime+":00";
            String enddate="1901-01-01 "+oldendtime+":00";
            Date _startdate=TypeConverter.stringToDate("1901-01-01 "+starttime+":00","yyyy-MM-dd HH:mm:ss" );
            Date _enddate=TypeConverter.stringToDate("1901-01-01 "+endtime+":00","yyyy-MM-dd HH:mm:ss" );
            if ((_enddate.getTime() < _startdate.getTime())||(_enddate.getTime()==  _startdate.getTime()))
            {
                return -1;
            }
            String    sql="spaceId="+spaceId+" and WeekOrder="+weekTypeId+" and BeginTime='"+startdate+"' and EndTime='"+enddate+"'";
            List<PlanWeek> weekTimeList=  planWeekService.getListBySearch(sql,"pkid desc");
            if(weekTimeList.size()>0)
            {
              for ( PlanWeek  planweek:weekTimeList)
              {
                  planweek.setBegintime(_startdate);
                  planweek.setEndtime(_enddate);
                  planWeekService.update(planweek);
                  setOpLog("kaoqin","周期计划", "修改周期计划：" +planweek.getPkid() );
                  SetKaoQinOpLog("kq_planweek",planweek.getPkid(),2);
              }
            }
        }
        return 1;
    }

    /**
     * @Description: 删除时间节点
     * @param: [spaceId, weekTypeId, starttime, endtime]
     * @return: int
     * @auther: tangy
     * @date: 2019/5/23 0023 16:25
     */
    @RequestMapping(value = "/doDelTime", method = RequestMethod.POST)
    @ResponseBody
    public int doDelTime(int spaceId,int weekTypeId,String starttime, String  endtime)
    {   String startdate="1901-01-01 "+starttime+":00";
        String enddate="1901-01-01 "+endtime+":00";
        String    sql="spaceId="+spaceId+" and WeekOrder="+weekTypeId+" and BeginTime='"+startdate+"' and EndTime='"+enddate+"'";
        List<PlanWeek> weekTimeList=  planWeekService.getListBySearch(sql,"pkid desc");
        if(weekTimeList.size()>0)
        {
            for ( PlanWeek  planweek:weekTimeList)
            {
                planWeekService.delete(planweek.getPkid());
                setOpLog("kaoqin","周期计划", "删除周期计划：" +planweek.getPkid() );
                SetKaoQinOpLog("kq_planweek",planweek.getPkid(),3);
            }
        }
        return  1;
    }

    /**
     * @Description:编辑页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String planweekEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        PlanWeek planweek = planWeekService.getByPkId(TypeConverter.strToInt(id));
        if(planweek==null)
        {
            model.addAttribute("msg","要编辑的考勤计划不存在");
            return "/error/error";
        }
        if(planweek.getUsergroupidstr()==""||planweek.getUsergroupidstr()==null)
        {
            planweek.setUsergroupidstr("0");
        }
        model.addAttribute("planweek", planweek);
        String teachername="";
        if (planweek.getTeacherid()!=0)
        {
            teachername =userService.getByUserId(planweek.getTeacherid()).getName();
        }
        model.addAttribute("teachername", teachername);
        return "manage/kaoqin/planweek/edit";
    }
    /**
     * @Description: 修改周期计划
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int  planweekDoEdit(int pkid,String subject,String  usergroupidstr,int teacherid )
    {
        PlanWeek planweek = planWeekService.getByPkId(pkid);
        if(planweek!=null) {
            planweek.setSubject(subject);
            planweek.setTeacherid(teacherid);
            planweek.setUsergroupidstr(usergroupidstr);
            planWeekService.update(planweek);
            setOpLog("kaoqin","周期计划", "编辑周期计划：" + planweek.getPkid());
            SetKaoQinOpLog("kq_planweek",planweek.getPkid(),2);
        }
        return 1;
    }

    /**
     * @Description: 新增周期课表
     * @param: [spaceId：场地, weekTypeId：单双周, week：星期几, time：上下课时间, model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/24 0024 9:51
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String planWeekAdd(Model model)
    {
        return "manage/kaoqin/planweek/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int planweekDoAdd(PlanWeek planweek)
    {
        Space space = spaceService.getByPkId(planweek.getSpaceid());
        if(space==null)
        {
            return 0;
        }
        planweek.setSchoolid(space.getUnitid());
        planWeekService.insert(planweek);
        setOpLog("kaoqin","周期计划", "新增周期计划：" + planweek.getPkid());
        SetKaoQinOpLog("kq_planweek",planweek.getPkid(),1);
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int planweekDoDel(int pkid)
    {
        PlanWeek planweek = planWeekService.getByPkId(pkid);
        if(planweek!=null) {
            planWeekService.delete(planweek.getPkid());
            setOpLog("kaoqin","周期计划", "删除周期计划：" + planweek.getPkid());
            SetKaoQinOpLog("kq_planweek",planweek.getPkid(),3);
        }
        return 1;
    }

     private  String GetUserGroupNames(String userGroupIdstr)
    {
        String groupnames = "";
        String[] group = userGroupIdstr.split(",");
            for (String gid : group)
            {
                UserGroup ug = userGroupService.getByPkId(Integer.parseInt(gid));
                if (ug != null)
                {
                    if (groupnames == "")
                    {
                        groupnames = ug.getGroupname();
                    }
                    else
                    {
                        groupnames = groupnames + ','+  ug.getGroupname();
                    }
                }
            }

        return groupnames;
    }

    public   List<SpaceInfo> GetSpaceInfoList()
    { List<SpaceInfo> SpaceInfolist=new ArrayList<SpaceInfo>();
        List<SpaceType> spaceTypeList = spaceTypeService.getAllList();
        for (SpaceType spacetype:spaceTypeList)
        {   List<Space> spacelist=spaceService.getListBySearch("typeid=" + spacetype.getPkid(),"pkid desc");
            SpaceInfo spaceinfo=new SpaceInfo();
            spaceinfo.subcount=spacelist.size();
            spaceinfo.pkid=spacetype.getPkid();
            spaceinfo.name="[类]"+spacetype.getName();
            spaceinfo.parentid=0;
            SpaceInfolist.add(spaceinfo);
            for (Space space:spacelist)
            {  SpaceInfo spaceinfos=new SpaceInfo();
                spaceinfos.pkid=space.getPkid();
                spaceinfos.name="[场]┗"+space.getName();
                spaceinfos.parentid=spacetype.getPkid();
                spaceinfos.subcount=0;
                SpaceInfolist.add(spaceinfos);
            }
        }
        return SpaceInfolist;
    }
    public  class  SpaceInfo{
        public int pkid;
        public String name;
        public int parentid;
        public int subcount;
    }
    public  class PlanTime  implements Serializable
    {
        public String time ;
        public List<PlanWeekData> weekTimeList;
    }

    public  class PlanWeekData  implements Serializable
    {
        public int pkid ;
        public String week ;
        public String subject ;
        public String teachername;
        public String  groupnames;
    }
}
