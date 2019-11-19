package com.easybasic.edu.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.DataDicVal;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.DataDicValService;
import com.easybasic.basic.service.UnitService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ExcelUtil;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.edu.model.SchoolSchedule;
import com.easybasic.edu.model.SchoolScheduleWeekTime;
import com.easybasic.edu.service.SchoolScheduleService;
import com.easybasic.edu.service.SchoolScheduleWeekTimeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/edu/schedule")
public class ScheduleController extends BaseController {

    @Resource
    private SchoolScheduleService schoolScheduleService;
    @Resource
    private SchoolScheduleWeekTimeService schoolScheduleWeekTimeService;
    @Resource
    private UnitService unitService;
    @Resource
    private DataDicValService dataDicValService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String scheduleList(Model model)
    {
        String re = setPageCommonModel(model, "edu","/manage/edu/schedule", false);
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
            return "manage/edu/set/schedule/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String scheduleAdd(@RequestParam(value = "schoolId", required = false, defaultValue = "0")String schoolId,
                              @RequestParam(value = "stageId",required = false, defaultValue = "0")String stageId,
                              Model model)
    {
        Unit unit = unitService.getByPkId(TypeConverter.strToInt(schoolId));
        if(unit == null)
        {
            model.addAttribute("msg","要设置的学校信息为空");
            return "/error/error";
        }
        if(TypeConverter.strToInt(stageId) == 0)
        {
            model.addAttribute("msg","必须选择对应的学段");
            return "/error/error";
        }
        if(!ToolsUtil.InArray(stageId, unit.getStageids()))
        {
            model.addAttribute("msg","选择的学段与学校不匹配");
            return "/error/error";
        }
        List<DataDicVal> list = dataDicValService.getListByDataDicCode("SKSJD");
        model.addAttribute("list", list);
        List<DataDicVal> sksxList = dataDicValService.getDataDicValListForCache("SKSX");
        model.addAttribute("sksxList", sksxList);
        List<DataDicVal> sksdList = dataDicValService.getDataDicValListForCache("SKSD");
        model.addAttribute("sksdList", sksdList);
        List<DataDicVal> xqList = dataDicValService.getDataDicValListForCache("XQ");
        model.addAttribute("xqList", xqList);
        model.addAttribute("schoolId",TypeConverter.strToInt(schoolId));
        model.addAttribute("stageId", TypeConverter.strToInt(stageId));
        return "/manage/edu/set/schedule/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int scheduleDoAdd(SchoolSchedule schedule, String starttime, String endtime, String weeks)
    {
        if(StringUtils.isEmpty(schedule.getName()))
        {
            return 0;
        }
        Unit unit = unitService.getByPkId(schedule.getSchoolid());
        if(unit == null)
        {
            return -1;
        }
        if(schedule.getStageid() == 0)
        {
            return -1;
        }
        if(!ToolsUtil.InArray(schedule.getStageid().toString(), unit.getStageids()))
        {
            return -1;
        }
        SchoolSchedule temp = schoolScheduleService.getBySchoolAndStageAndSesson(schedule.getSchoolid(), schedule.getStageid(), schedule.getSession());
        if(temp != null)
        {
            return -2;
        }
        schedule.setCreatetime(new Date());
        schoolScheduleService.insert(schedule);
        String[] weekStrList = weeks.split(",");
        for (String s : weekStrList) {
            int week = TypeConverter.strToInt(s);
            SchoolScheduleWeekTime weekTime = new SchoolScheduleWeekTime();
            weekTime.setSchoolid(schedule.getSchoolid());
            weekTime.setScheduleid(schedule.getPkid());
            weekTime.setWeek(week);
            weekTime.setStarttime(starttime);
            weekTime.setEndtime(endtime);
            weekTime.setIsrest(0);
            weekTime.setCreatetime(new Date());
            schoolScheduleWeekTimeService.insert(weekTime);
        }
        schoolScheduleService.clearSchoolScheduleCache(schedule.getSchoolid(), schedule.getStageid());
        setOpLog("edu","上课时间段", "学校“"+unit.getName()+"”增加上课时间段：" + schedule.getName());
        return 1;
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

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String scheduleEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                               @RequestParam(value = "weeks", required = false, defaultValue = "1,2,3,4,5")String weeks,
                               Model model)
    {
        SchoolSchedule schoolSchedule = schoolScheduleService.getByPkId(TypeConverter.strToInt(id));
        if(schoolSchedule==null)
        {
            model.addAttribute("msg","要修改的上课时间段信息不存在");
            return "/error/error";
        }
        List<SchoolScheduleWeekTime> weekTimeList = schoolScheduleWeekTimeService.getListByScheduleId(schoolSchedule.getPkid());
        if(weekTimeList.size()>0)
        {
            List<SchoolScheduleWeekTime> temp = weekTimeList.stream().filter(x->ToolsUtil.InArray(x.getWeek().toString(), weeks)).collect(Collectors.toList());
            if(temp.size()>0)
            {
                schoolSchedule.setStartTime(temp.get(0).getStarttime());
                schoolSchedule.setEndTime(temp.get(0).getEndtime());
            }
            else {
                schoolSchedule.setStartTime(weekTimeList.get(0).getStarttime());
                schoolSchedule.setEndTime(weekTimeList.get(0).getEndtime());
            }
        }
        model.addAttribute("schedule", schoolSchedule);
        List<DataDicVal> list = dataDicValService.getListByDataDicCode("SKSJD");
        model.addAttribute("list", list);
        List<DataDicVal> sksxList = dataDicValService.getDataDicValListForCache("SKSX");
        model.addAttribute("sksxList", sksxList);
        List<DataDicVal> sksdList = dataDicValService.getDataDicValListForCache("SKSD");
        model.addAttribute("sksdList", sksdList);
        List<DataDicVal> xqList = dataDicValService.getDataDicValListForCache("XQ");
        model.addAttribute("xqList", xqList);
        model.addAttribute("weeks", weeks);
        return "/manage/edu/set/schedule/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int scheduleDoEdit(SchoolSchedule editData, String starttime, String endtime, String weeks)
    {
        SchoolSchedule schoolSchedule = schoolScheduleService.getByPkId(editData.getPkid());
        if(schoolSchedule == null)
        {
            return 0;
        }
        schoolSchedule.setType(editData.getType());
        schoolSchedule.setTime(editData.getTime());
        schoolSchedule.setSortnum(editData.getSortnum());
        schoolScheduleService.update(schoolSchedule);
        String[] weekStrList = weeks.split(",");
        List<SchoolScheduleWeekTime> list = schoolScheduleWeekTimeService.getListByScheduleId(schoolSchedule.getPkid());
        for (String s : weekStrList) {
            int week = TypeConverter.strToInt(s);
            int index = -1;
            for(int i=0;i<list.size(); i++)
            {
                SchoolScheduleWeekTime temp = list.get(i);
                if(temp.getWeek().intValue() == week)
                {
                    index = i;
                    break;
                }
            }
            if(index !=-1)
            {
                SchoolScheduleWeekTime weekTime = list.get(index);
                list.remove(index);
                weekTime.setStarttime(starttime);
                weekTime.setEndtime(endtime);
                schoolScheduleWeekTimeService.update(weekTime);
            }
            else
            {
                SchoolScheduleWeekTime weekTime = new SchoolScheduleWeekTime();
                weekTime.setSchoolid(schoolSchedule.getSchoolid());
                weekTime.setScheduleid(schoolSchedule.getPkid());
                weekTime.setWeek(week);
                weekTime.setStarttime(starttime);
                weekTime.setEndtime(endtime);
                weekTime.setIsrest(0);
                weekTime.setCreatetime(new Date());
                schoolScheduleWeekTimeService.insert(weekTime);
            }
        }
        schoolScheduleService.clearSchoolScheduleCache(schoolSchedule.getSchoolid(), schoolSchedule.getStageid());
        setOpLog("edu","上课时间段", "修改上课时间段：" + schoolSchedule.getName());
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int doDel(int id)
    {
        SchoolSchedule schoolSchedule = schoolScheduleService.getByPkId(id);
        if(schoolSchedule!=null)
        {
            schoolScheduleService.delete(id);
            setOpLog("edu","上课时间段", "删除上课时间段：" + schoolSchedule.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/restTimeInport", method = RequestMethod.GET)
    public String restTimeInport(@RequestParam(value = "schoolId", required = false, defaultValue = "0")String schoolIdStr,
                                 @RequestParam(value = "stageId", required = false, defaultValue = "0")String stageIdStr,
                                 Model model)
    {
        int schoolId = TypeConverter.strToInt(schoolIdStr);
        int stageId = TypeConverter.strToInt(stageIdStr);
        if(schoolId == 0 || stageId == 0)
        {
            model.addAttribute("msg","传入的参数不能为空");
            return "/error/error";
        }
        model.addAttribute("schoolId", schoolId);
        model.addAttribute("stageId", stageId);
        return "/manage/edu/set/schedule/inport";
    }

    @RequestMapping(value = "/doInportTemplate", method = RequestMethod.POST)
    @ResponseBody
    public int doInportTemplate(String fileName,int schoolId, int stageId)
    {
        Unit unit = unitService.getByPkId(schoolId);
        if(unit == null)
        {
            return -1;
        }
        if(stageId == 0)
        {
            return -1;
        }
        if(!ToolsUtil.InArray(String.valueOf(stageId), unit.getStageids()))
        {
            return -1;
        }
        String filePath = ToolsUtil.getUploadPath("/upload/temp/" + fileName);
        try {
            List<String[]> result = ExcelUtil.readExcel(filePath, 0);
            schoolScheduleService.deleteBySchoolIdAndStageId(schoolId, stageId);
            int session = 1;
            for (String[] strings : result) {
                SchoolSchedule schoolSchedule = schoolScheduleService.getBySchoolAndStageAndSesson(schoolId, stageId, session);
                if(schoolSchedule == null)
                {
                    schoolSchedule = new SchoolSchedule();
                    schoolSchedule.setSchoolid(schoolId);
                    schoolSchedule.setStageid(stageId);
                    schoolSchedule.setName(strings[1]);
                    schoolSchedule.setType(0);
                    schoolSchedule.setSession(session);
                    schoolSchedule.setTime(2);
                    schoolSchedule.setSortnum(session);
                    schoolSchedule.setCreatetime(new Date());
                    schoolScheduleService.insert(schoolSchedule);
                }
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
                    String[] st = s.trim().split("-");
                    if(st.length!=2)
                    {
                        count++;
                        continue;
                    }
                    SchoolScheduleWeekTime weekTime = new SchoolScheduleWeekTime();
                    weekTime.setSchoolid(schoolId);
                    weekTime.setScheduleid(schoolSchedule.getPkid());
                    weekTime.setWeek(week);
                    weekTime.setStarttime(st[0]);
                    weekTime.setEndtime(st[1]);
                    weekTime.setIsrest(0);
                    weekTime.setCreatetime(new Date());
                    schoolScheduleWeekTimeService.insert(weekTime);
                    count++;
                }
                schoolScheduleService.clearSchoolScheduleCache(schoolId, stageId);
                session ++;
            }
            setOpLog("edu","上课时间段", "学校“"+unit.getName()+"”导入作息时间数据");
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
