package com.easybasic.kaoqin.controller;

import com.alibaba.fastjson.JSON;
import com.easybasic.basic.model.Space;
import com.easybasic.component.ServiceController;
import com.easybasic.component.jwt.LoginUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.easybasic.basic.service.SpaceService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Serializable;
import javax.annotation.Resource;
import com.easybasic.component.Utils.JsonUtils;
import com.easybasic.kaoqin.model.*;
import com.easybasic.kaoqin.service.*;
import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.File;

@Controller
public class FaceAttendanceServiceController  extends ServiceController {
    @Resource
    private SpaceService spaceService;
    @Resource
    private PlanWeekService planWeekService;
    @Resource
    private UserGroupService userGroupService;
    @Resource
    private UserService userService;
    @Resource
    private UserGroupAndUserService userGroupAndUserService;
    @Resource
    private OperatLogService operatLogService;
    @Resource
    private UserPhotoService userPhotoService;
    @Resource
    private AppointmentService appointmentService;
    @Resource
    private UserCheckInfoService userCheckInfoService;

    /**
     * @Description: 获取场地
     * @param: [unitid:单位编号, classid：教室编号]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/15 0015 13:49
     */
    @RequestMapping(value = "/service/faceattendance/GetSpace", method = RequestMethod.POST)
    @ResponseBody
    public String GetSpace(int unitid, int classid) {
        try {
            String sqlStr = " pkid>0";
            if (unitid != 0) {
                sqlStr += "  and unitid=" + unitid;
            }
            if (classid != 0) {
                sqlStr += "   and classid=" + classid;
            }
            List<Space> spaceList = spaceService.getListBySearch(sqlStr, "sortnum asc");
            List<SpaceInfo> resultlist = new ArrayList<SpaceInfo>();
            for (Space space : spaceList) {
                SpaceInfo spaceinfo = new SpaceInfo();
                spaceinfo.pkid = space.getPkid();
                spaceinfo.name = space.getName();
                resultlist.add(spaceinfo);
            }
            if (resultlist != null) {

                return JsonUtils.toJson(resultlist);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public class SpaceInfo implements Serializable {
        public Integer pkid;
        public String name;
    }

    /**
     * @Description: 根据场地获取人脸考勤所有周课表
     * @param: [spaceId：场地编号]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/15 0015 13:57
     */
    @RequestMapping(value = "/service/faceattendance/GetPlanWeekList", method = RequestMethod.POST)
    @ResponseBody
    public List<PlanWeek> GetPlanWeekList(int spaceId, String lastRequestTime) {
        List<PlanWeek> planweeklist = new ArrayList<PlanWeek>();
        if (spaceId == 0) {
            return planweeklist;
        }
        String sql = " spaceId=" + spaceId;
        if (lastRequestTime != null && lastRequestTime != "") {
            sql = "  and  pkid IN (SELECT  `code`  FROM  kq_operatlog WHERE `TableNames`='kq_planweek' AND  OperatTime >='" + lastRequestTime + "' )";
        }
        planweeklist = planWeekService.getListBySearch(sql, "pkid asc");
        return planweeklist;
    }

    /**
     * @Description: 根据场地获取所有考勤用户组
     * @param: [spaceId：场地编号]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/15 0015 13:57
     */
    @RequestMapping(value = "/service/faceattendance/GetUserGroupList", method = RequestMethod.POST)
    @ResponseBody
    public String GetUserGroupList(int spaceId, String lastRequestTime) {
        try {
            if (spaceId == 0) {
                return "";
            }
            List<UserGroup> usergrouplist = new ArrayList<UserGroup>();
            /*1、获取周课表*/
            List<PlanWeek> planweeklist = planWeekService.getListBySearch(" spaceId=" + spaceId, "pkid asc");
            /*2、根据周课表中获取考勤分组*/
            String ids = "";
            for (PlanWeek planweek : planweeklist) {
                String sourceStr = planweek.getUsergroupidstr();
                String[] sourceStrArray = sourceStr.split(",");
                for (int i = 0; i < sourceStrArray.length; i++) {
                    int id = Integer.parseInt(sourceStrArray[i]);
                    if (id != 0) {
                        UserGroup usergroup = userGroupService.getByPkId(id);
                        if (usergrouplist.size() > 0) {
                            if (!usergrouplist.contains(usergroup)) {
                                usergrouplist.add(usergroup);
                                ids = ids + "," + usergroup.getPkid().toString();
                            }
                        } else {
                            usergrouplist.add(usergroup);
                            ids = usergroup.getPkid().toString();
                        }
                    }
                }
            }
            if (ids != "") {
                String sql = "  pkid IN(" + ids + ")";
                if ((lastRequestTime != null && lastRequestTime != ""))//获取有效数据
                {
                    sql += "  and pkid IN (SELECT  `code`  FROM  kq_operatlog WHERE `TableNames`='kq_usergroup' AND  OperatTime >='" + lastRequestTime + "' )";
                }
                usergrouplist = userGroupService.getListBySearch(sql, "pkid asc");
                if (usergrouplist != null) {
                    return JsonUtils.toJson(usergrouplist);
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @Description: 获取管理员数据
     * @param: [spaceId]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/16 0016 9:20
     */
    @RequestMapping(value = "/service/faceattendance/GetAdminList", method = RequestMethod.POST)
    @ResponseBody
    public List<Admin> GetAdminList(int spaceId, String lastRequestTime) {
        List<Admin> admininfolist = new ArrayList<Admin>();
        try {
            if (spaceId == 0) {
                return admininfolist;
            }
            String sql = " pkid  IN (SELECT   teacherID  FROM   kq_planweek WHERE  spaceId=" + spaceId + ")";
            if (lastRequestTime != null && lastRequestTime != "")//获取有效数据
            {
                sql += " and  pkid IN (SELECT  `code`  FROM  `kq_operatlog` WHERE `TableNames`='basic_user' AND  OperatTime >='" + lastRequestTime + "' ) ";
            }
            List<User> userlist = userService.getListBySearch(sql, "pkid asc");
            for (User tempUser : userlist) {
                if (tempUser.getPkid() != 0) {
                    Admin admininfo = new Admin();
                    admininfo.id = tempUser.getPkid();
                    admininfo.username = tempUser.getLoginname();
                    admininfo.realname = tempUser.getName();
                    admininfo.pwd = tempUser.getPassword().toUpperCase();//转成大写
                    admininfo.createtime = tempUser.getCreatetime();
                    admininfo.tel = tempUser.getMobile();
                    admininfolist.add(admininfo);
                }
            }
            return admininfolist;
        } catch (Exception e) {
            return admininfolist;
        }
    }

    public class Admin implements Serializable {
        public int id;
        public String username;
        public String pwd;
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        public Date createtime;
        public String realname;
        public String tel;
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        public Date lastlogintime;
        public String logincount;
    }

    /**
     * @Description: 获取用户
     * @param: [spaceId]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/16 0016 9:53
     */
    @RequestMapping(value = "/service/faceattendance/GetUserInfoList", method = RequestMethod.POST)
    @ResponseBody
    public String GetUserInfoList(int spaceId, String lastRequestTime) {
        try {
            if (spaceId == 0) {
                return "";
            }
            String Ids = GetUserGroupIds(spaceId);
            List<UserInf> UserInfList = GetUserInfListByGroupID(Ids, lastRequestTime);
            if (UserInfList != null) {
                return JsonUtils.toJson(UserInfList);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    private List<UserInf> GetUserInfListByGroupID(String usergroupIDs, String lastRequestTime) {
        List<UserInf> userinflist = new ArrayList<UserInf>();
        String sql = "usergroupID in (" + usergroupIDs + ")";
        if (lastRequestTime != null && lastRequestTime != "")//获取有效数据
        {
            sql += " and  pkid IN (SELECT  `code`  FROM  `kq_operatlog` WHERE `TableNames`='basic_user' AND  OperatTime >='" + lastRequestTime + "' ) ";
        }
        List<UserGroupAndUser> usergroupanduserlist = userGroupAndUserService.getListBySearch(sql, "pkid asc");
        for (UserGroupAndUser usergroupanduser : usergroupanduserlist) {
            User tempUser = userService.getByUserId(usergroupanduser.getUserid());
            //用户表
            UserInf userinf = new UserInf();
            userinf.ID = tempUser.getPkid();
            userinf.Sex = tempUser.getSex();
            //考勤图片 add tangy  20190521
            UserPhoto userPhoto = userPhotoService.getByUserId(tempUser.getPkid());
            if (userPhoto != null) {
                userinf.Photo = userPhoto.getPhoto();
            }
            userinf.ReaName = tempUser.getName();
            userinflist.add(userinf);
        }
        return userinflist;
    }

    //UserInf
    public class UserInf implements Serializable {
        public int ID;
        public String ReaName;
        public String Photo;
        public int Sex;
    }

    /**
     * @Description: 获取用户和考勤组关联表
     * @param: [spaceId]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/16 0016 9:53
     */
    @RequestMapping(value = "/service/faceattendance/GetUserAndUserGroupList", method = RequestMethod.POST)
    @ResponseBody
    public String GetUserAndUserGroupList(int spaceId, String lastRequestTime) {
        try {
            if (spaceId == 0) {
                return "";
            }
            String Ids = GetUserGroupIds(spaceId);
            List<Rl_UserAndUserGroup> rl_userandusergrouplist = GetUserAndUserGroupListByID(Ids, lastRequestTime);
            if (rl_userandusergrouplist != null) {
                return JsonUtils.toJson(rl_userandusergrouplist);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    private List<Rl_UserAndUserGroup> GetUserAndUserGroupListByID(String userGroupIDs, String lastRequestTime) {
        List<Rl_UserAndUserGroup> rl_userandusergrouplist = new ArrayList<Rl_UserAndUserGroup>();
        String sql = " usergroupID in (" + userGroupIDs + ") ";
        if (lastRequestTime != null && lastRequestTime != "")//获取有效数据
        {
            sql += " and  pkid IN (SELECT  `code`  FROM  `kq_operatlog` WHERE `TableNames`='kq_usergroupanduser' AND  OperatTime >='" + lastRequestTime + "' ) ";
        }
        List<UserGroupAndUser> usergroupanduserlist = userGroupAndUserService.getListBySearch(sql, "pkid asc");
        for (UserGroupAndUser usergroupanduser : usergroupanduserlist) {
            //关联表
            Rl_UserAndUserGroup rl_userandusergroup = new Rl_UserAndUserGroup();
            rl_userandusergroup.ID = usergroupanduser.getPkid();
            rl_userandusergroup.UserGroupID = usergroupanduser.getUsergroupid();
            rl_userandusergroup.UserID = usergroupanduser.getUserid();
            rl_userandusergrouplist.add(rl_userandusergroup);
        }
        return rl_userandusergrouplist;
    }

    //Rl_UserAndUserGroup
    public class Rl_UserAndUserGroup implements Serializable {
        public int ID;
        public int UserID;
        public int UserGroupID;
    }

    /**
     * @Description: 根据表名称获取编辑和新增日志数据
     * @param: [lastRequestTime]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/16 0016 16:17
     */
    @RequestMapping(value = "/service/faceattendance/GetOperatLogEditList", method = RequestMethod.POST)
    @ResponseBody
    public List<OperatLog> GetOperatLogEditList(int spaceId, String lastRequestTime, int tableNamesType) {
        List<OperatLog> operatloglist = new ArrayList<OperatLog>();
        if (tableNamesType == 0) {
            return operatloglist;
        }
        String sql = "  pkid>0";
        String ids = GetUserGroupIds(spaceId);
        if (lastRequestTime != null && lastRequestTime != "")//获取有效时间范围内数据
        {
            sql += " and OperatTime >='" + lastRequestTime + "'";
        }
        if (tableNamesType == 1)//周期考勤表记录
        {
            sql += "  and (`TableNames`='kq_planweek' AND  `code` IN(SELECT pkid FROM kq_planweek  WHERE  spaceId=" + spaceId + "  ))";
            return GetOperatLogList(sql);
        } else if (tableNamesType == 2)//用户分组表
        {
            if (ids != "") {
                sql += " and (`TableNames`='kq_usergroup'  and code in (" + ids + "))";
                return GetOperatLogList(sql);
            }
        } else if (tableNamesType == 3)//用户分组与用户关联表
        {
            if (ids != "") {
                sql += " and (`TableNames`='kq_usergroupanduser'   AND  `code`  IN (SELECT  pkid  FROM  kq_usergroupanduser   WHERE  usergroupID IN(" + ids + ")))";
                return GetOperatLogList(sql);
            }
        } else if (tableNamesType == 4)//教师管理员
        {
            sql += "  and (`TableNames`='basic_user'   AND  `code`  IN (SELECT teacherID FROM kq_planweek WHERE  spaceId=" + spaceId + "  ))";
            return GetOperatLogList(sql);
        } else if (tableNamesType == 5)//用户表
        {
            if (ids != "") {
                sql += " and  (`TableNames`='basic_user'   AND  `code` IN (SELECT  pkid  FROM  basic_user   WHERE   usertype=1 AND pkid IN  (SELECT  UserID  FROM  kq_usergroupanduser   WHERE  usergroupID IN(" + ids + "))))";
                return GetOperatLogList(sql);
            }
        }
        return operatloglist;
    }


    public String GetUserGroupIds(int spaceId) {
        List<PlanWeek> planweeklist = planWeekService.getListBySearch(" spaceId=" + spaceId, "pkid asc");
        String ids = "";
        List<String> list = new ArrayList<String>();
        for (PlanWeek planweek : planweeklist) {
            String sourceStr = planweek.getUsergroupidstr();
            if(!StringUtils.isNotBlank(sourceStr)) continue;
            String[] sourceStrArray = sourceStr.split(",");
            for (int i = 0; i < sourceStrArray.length; i++) {
                int id = Integer.parseInt(sourceStrArray[i]);
                if (id != 0) {
                    UserGroup usergroup = userGroupService.getByPkId(id);
                    if(usergroup==null) continue;
                    if (list.size() > 0) {
                        if (!list.contains(usergroup.getPkid().toString())) {
                            list.add(usergroup.getPkid().toString());
                            ids = ids + "," + usergroup.getPkid().toString();
                        }
                    } else {
                        list.add(usergroup.getPkid().toString());
                        ids = usergroup.getPkid().toString();
                    }
                }
            }
        }
        return ids;
    }

    /**
     * @Description: 获取所有删除日志数据
     * @param: [lastRequestTime]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/16 0016 16:17
     */
    @RequestMapping(value = "/service/faceattendance/GetOperatLogDelList", method = RequestMethod.POST)
    @ResponseBody
    public List<OperatLog> GetOperatLogDelList(String lastRequestTime) {
        String sql = " type=3";//删除
        if (lastRequestTime != null && lastRequestTime != "")//获取有效数据
        {
            sql += " and OperatTime >='" + lastRequestTime + "'";
        }
        List<OperatLog> operatloglist = operatLogService.getListBySearch(sql, "pkid asc");
        return operatloglist;
        //   return   GetOperatLogList(sql);
    }

    public List<OperatLog> GetOperatLogList(String sql) {
        List<OperatLog> operatloglist = operatLogService.getListBySearch(sql, "pkid asc");
        return operatloglist;
       /* if (operatloglist != null) {
            return JsonUtils.toJson(operatloglist);
        } else {
            return "";
        }*/
    }

    /**
     * @Description: 同步客户端考勤数据
     * @param: [lastRequestTime]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/16 0016 16:17
     */
    @RequestMapping(value = "/service/faceattendance/SyncCheckInfo", method = RequestMethod.POST)
    @ResponseBody
    public Integer SyncCheckInfo(String data) {
        if(data.equals("[]")) return 1;
        List<UserCheckInfo> list = JSON.parseArray(data, UserCheckInfo.class);
        for (UserCheckInfo checkinfo : list) {
            userCheckInfoService.insert(checkinfo);
        }
        return 1;
    }


/*    @RequestMapping(value = "/service/faceattendance/GetWeinUser", method = RequestMethod.GET)
    @ResponseBody
    public void GetWeinUser(String token) {
        //return "http://www.inposs.cn/Integration/Weixin.aspx?code=weixinuser&userid=" + LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid() + "&name="+ LoginUtil.getCurrentLoginUserProperty().CurrentUser.getLoginname() +"&token=" + token;
    }*/

    @RequestMapping(value = "/service/faceattendance/GetWeinUser", method = RequestMethod.GET)
    public void GetWeinUser(String token, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //String contNo =req.getParameter("contNo"); //保单号
        resp.sendRedirect("http://www.inposs.cn/Integration/Weixin.aspx?code=weixinuser&userid=" + LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid() + "&name="+ LoginUtil.getCurrentLoginUserProperty().CurrentUser.getLoginname() +"&token=" + token);
    }

    /**
     * @Description: 获取教室预约数据
     * @param: [lastRequestTime]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/16 0016 16:17
     */
    @RequestMapping(value = "/service/faceattendance/GetAppointmentList", method = RequestMethod.POST)
    @ResponseBody
    public List<Appointment> GetAppointmentList(Integer placeID) {
        String sql = " placeID=" + placeID + " and isSync = 0 ";//删除
        List<Appointment> operatloglist = appointmentService.getListBySearch(sql, "pkid asc");
        for (Appointment item : operatloglist) {
            item.setIssync(true);
            appointmentService.update(item);
        }
        return operatloglist;
    }

    /**
     * @Description: 教室预约登记
     * @param: [lastRequestTime]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/16 0016 16:17
     */
    @RequestMapping(value = "/manage/kaoqin/appointment", method = RequestMethod.GET)
    public String Appointment(Model model) {
        User user=userService.getByUserId(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        model.addAttribute("userid",user.getPkid());
        model.addAttribute("photo",user.getPhoto());
        model.addAttribute("username",user.getName());
        return "manage/kaoqin/appointment/appointment";
    }

    @RequestMapping(value = "/manage/kaoqin/appointment/add", method = RequestMethod.POST)
    @ResponseBody
    public int addAppointment(String subject,String place,String teacher,String begintime,String endtime) throws ParseException {
        UserPhoto user = userPhotoService.getByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        begintime = begintime.replace("T", " ") + ":00";
        endtime = endtime.replace("T", " ") + ":00";
        Appointment appointment = new Appointment();
        appointment.setIssync(false);
        appointment.setPhoto(user.getPhoto());
        appointment.setPlaceid(2);
        appointment.setReaname(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getName());
        appointment.setSubject(subject);
        appointment.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        appointment.setBegintime(format.parse(begintime));
        appointment.setEndtime(format.parse(endtime));
        appointmentService.insert(appointment);
        return 1;
    }
}