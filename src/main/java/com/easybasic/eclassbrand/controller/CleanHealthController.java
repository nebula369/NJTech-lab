package com.easybasic.eclassbrand.controller;


import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.Unit;
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
import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import javax.annotation.Resource;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manage/eclassbrand/cleanhealth")
public class CleanHealthController extends BaseController {
    @Resource
    private CleanHealthService cleanhealthservice;
    @Resource
    private SchoolClassService schoolClassService;
    /**cleanhealth
     * @Description: 网址列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String cleanhealthList(Model model)
    {
        String re = setPageCommonModel(model, "eclassbrand","/manage/eclassbrand/cleanhealth", false);
        if(!StringUtils.isEmpty(re))
        {
           /* List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
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
          */
            String searchStr="pkid IN (SELECT  classid FROM  `edu_schoolclasscurriculum`  WHERE teacherid="+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid()+")";
            List<SchoolClass> classlist =  schoolClassService.getListBySearch(searchStr,"pkid desc");
            for (SchoolClass schoolclass:classlist)
            {
                schoolclass.setName(schoolclass.getGradeName() + schoolclass.getName());
            }
            model.addAttribute("classList",  classlist);
            return "manage/eclassbrand/cleanhealth/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getCleanHealthList", method = RequestMethod.POST)
    @ResponseBody
    public  List<CleanHealth>  getCleanHealthList(int classid)
    {
        String searchStr = "  classid="+classid;
        List<CleanHealth> list = cleanhealthservice.getListBySearch(searchStr," day asc ");
        return list;
    }

    /**
     * @Description: 新增页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String cleanhealthAdd(@RequestParam(value = "classid", required = false, defaultValue = "0")String classid,
                                 @RequestParam(value = "day", required = false, defaultValue = "1")String day,
                                 Model model)
    {
        model.addAttribute("classid", classid);
        model.addAttribute("day", day);
        return "manage/eclassbrand/cleanhealth/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int cleanhealthDoAdd(CleanHealth cleanhealth)
    {
        cleanhealth.setCreatetime(new Date());
        cleanhealth.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        cleanhealthservice.insert(cleanhealth);
        setOpLog("eclassbrand","值日", "增加值日：" + cleanhealth.getContent());
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
    public String cleanhealthEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        CleanHealth cleanhealth = cleanhealthservice.getByPkId(TypeConverter.strToInt(id));
        if(cleanhealth==null)
        {
            model.addAttribute("msg","要编辑的值日不存在");
            return "/error/error";
        }
        model.addAttribute("cleanhealth", cleanhealth);
        return "manage/eclassbrand/cleanhealth/edit";
    }
    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int cleanhealthtDoEdit(CleanHealth cleanhealthData)
    {
        CleanHealth cleanhealth = cleanhealthservice.getByPkId(cleanhealthData.getPkid());
        if(cleanhealth!=null) {
            cleanhealth.setContent(cleanhealthData.getContent());
            cleanhealthservice.update(cleanhealth);
            setOpLog("eclassbrand","值日", "编辑值日：" + cleanhealth.getPkid());
        }
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int cleanhealthDoDel(int id)
    {
        CleanHealth cleanhealth = cleanhealthservice.getByPkId(id);
        if(cleanhealth!=null) {
            cleanhealthservice.delete(id);
            setOpLog("eclassbrand","值日", "删除值日：" + cleanhealth.getPkid());
        }
        return 1;
    }
}
