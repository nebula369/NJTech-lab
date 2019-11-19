package com.easybasic.eclassbrand.controller;


import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.UnitService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.JsonUtils;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.eclassbrand.model.*;
import com.easybasic.eclassbrand.service.*;
import com.easybasic.kaoqin.controller.FaceAttendanceServiceController;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/manage/eclassbrand/recommend")
public class RecommendController extends BaseController {
    @Resource
    private RecommendService recommendservice;
    @Resource
    private SchoolClassService schoolClassService;
    @Resource
    private ClassRecommendService classRecommendService;
    @Resource
    private DataDicValDetailService dataDicValDetailService;
    /**
     * @Description: 网址列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String recommendList(Model model)
    {
        String re = setPageCommonModel(model, "eclassbrand","/manage/eclassbrand/recommend", false);
        if(!StringUtils.isEmpty(re))
        {

            return "manage/eclassbrand/recommend/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getRecommendListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Recommend> getRecommendListForPage(JqGridPageRequest pageRequest, Integer unitId, String searchKey)
    {
        // String searchStr = " unitid=" + unitId;
        String searchStr = "  pkid>0";
        if(LoginUtil.getCurrentLoginUserProperty().isUnitManager())//单位管理员
        {
            searchStr+=" and   pkid IN (SELECT recommendid FROM `ecb_classrecommend` WHERE classid" +
                    " IN (SELECT pkid FROM `basic_schoolclass` WHERE   " +
                    "unitid="+LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId()+"))";
        }
        else{//普通老师,所管理的班级
            searchStr+=" and  pkid " +
                    "  IN (SELECT recommendid FROM    `ecb_classrecommend` WHERE classid" +
                    "  IN (SELECT pkid FROM basic_schoolclass WHERE  pkid" +
                    " IN (SELECT  classid FROM  `edu_schoolclasscurriculum`  WHERE teacherid="+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid()+")))" ;
        }

        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Recommend> list = recommendservice.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for(Recommend recommend: list)
        {
            String  classNams=  getClassName(recommend.getPkid(),0);
            recommend.setClassName(classNams);
        }
        PageInfo<Recommend> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Recommend> response = new JqGridPageResponse<>();
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
        String searchStr=" pkid IN (SELECT  classid FROM ecb_classrecommend WHERE recommendid="+pkid+")";
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
    public String recommendAdd(Model model)
    {
        model.addAttribute("classList",  getSchoolClass());
        return "manage/eclassbrand/recommend/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int recommendDoAdd(Recommend recommend,String classidstr)
    {
         recommend.setCreatetime(new Date());
        recommend.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        recommendservice.insert(recommend);
        for (String id:classidstr.split(","))
        {
            ClassRecommend classrecommend=new ClassRecommend();
            classrecommend.setClassid(TypeConverter.strToInt(id));
            classrecommend.setRecommendid(recommend.getPkid());
            classRecommendService.insert(classrecommend);
        }
        setOpLog("eclassbrand","推荐", "增加推荐：" + recommend.getName());
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
    public String recommendEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        Recommend recommend = recommendservice.getByPkId(TypeConverter.strToInt(id));
        if(recommend==null)
        {
            model.addAttribute("msg","要编辑的推荐不存在");
            return "/error/error";
        }
        model.addAttribute("recommend", recommend);
        String searchStr=" pkid IN (SELECT  classid FROM ecb_classrecommend WHERE recommendid="+recommend.getPkid()+")";
        List<SchoolClass> classlist =  schoolClassService.getListBySearch(searchStr,"pkid desc");
        for (SchoolClass schoolclass:classlist)
        {
            List<DataDicValDetail> xdnj = dataDicValDetailService.getDataDicValDetailListForCache("XD_NJ");
            DataDicValDetail detail = xdnj.stream().filter(x->x.getValcode().equalsIgnoreCase(schoolclass.getStageid().toString()) && x.getCode().equalsIgnoreCase(schoolclass.getGradeid().toString())).findFirst().orElse(null);
            schoolclass.setName(detail.getName()+"["+schoolclass.getName()+"]");
        }
        model.addAttribute("classList", classlist);
        String classid="";
        List<ClassRecommend> classRecommendList=classRecommendService.getListBySearch(" recommendid="+recommend.getPkid()+" "," pkid desc");
        for (ClassRecommend classrecommend:classRecommendList)
        {
            if(classid=="")
            {
                classid=classrecommend.getClassid().toString();
            }
            else{
                classid=classid+","+classrecommend.getClassid().toString();
            }
        }
        model.addAttribute("classid",  classid);
        return "manage/eclassbrand/recommend/edit";
    }
    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int recommendtDoEdit(Recommend recommendData,String classidstr)
    {
        Recommend recommend = recommendservice.getByPkId(recommendData.getPkid());
        if(recommend!=null) {
            recommend.setName(recommendData.getName());
            recommend.setBuyurl(recommendData.getBuyurl());
            recommend.setDetail(recommendData.getDetail());
            recommend.setPicture(recommendData.getPicture());
            recommend.setSortnum(recommendData.getSortnum());
            recommend.setReason(recommendData.getReason());
            recommendservice.update(recommend);
            //新增推荐与班级关联
            for (String id:classidstr.split(","))
            {
                ClassRecommend classrecommend=classRecommendService.getByClassIdAndRecommendId(TypeConverter.strToInt(id),recommend.getPkid());
                if(classrecommend==null)
                {
                    classrecommend=new  ClassRecommend();
                    classrecommend.setClassid(TypeConverter.strToInt(id));
                    classrecommend.setRecommendid(recommend.getPkid());
                    classRecommendService.insert(classrecommend);
                }
            }
            //删除推荐与班级关联
            List<ClassRecommend> classRecommendList=classRecommendService.getListBySearch("classid not in ("+classidstr+") and recommendid="+recommend.getPkid()+"","pkid desc");
            for (ClassRecommend classrecommend:classRecommendList)
            {
                classRecommendService.delete(classrecommend.getPkid());
            }
            setOpLog("eclassbrand","推荐", "编辑推荐：" + recommend.getPkid());
        }
        return 1;
    }
    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int recommendDoDel(int id)
    {
        Recommend recommend = recommendservice.getByPkId(id);
        if(recommend!=null) {
            recommendservice.delete(id);
            setOpLog("eclassbrand","推荐", "删除推荐：" + recommend.getPkid());
        }
        return 1;
    }


}
