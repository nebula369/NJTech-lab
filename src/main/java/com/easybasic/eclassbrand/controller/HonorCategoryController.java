package com.easybasic.eclassbrand.controller;


import com.alibaba.druid.util.StringUtils;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manage/eclassbrand/honorcategory")
public class HonorCategoryController extends BaseController {
    @Resource
    private HonorCategoryService honorcategoryservice;
    @Resource
    private SchoolClassService schoolClassService;
    @Resource
    private HonorService honorservice;
    /**
     * @Description: 网址列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String honorcategoryList(Model model)
    {
        String re = setPageCommonModel(model, "eclassbrand","/manage/eclassbrand/honorcategory", false);
        if(!StringUtils.isEmpty(re))
        {

            return "manage/eclassbrand/honorcategory/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getHonorCategoryListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<HonorCategory> getHonorCategoryListForPage(JqGridPageRequest pageRequest, Integer unitId, String searchKey)
    {
        // String searchStr = " unitid=" + unitId;
        String searchStr = "  pkid>0";
        if(LoginUtil.getCurrentLoginUserProperty().isUnitManager())//单位管理员
        {
            searchStr+=" and  classid" +
                    " IN (SELECT pkid FROM `basic_schoolclass` WHERE   " +
                    "unitid="+LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId()+")";
        }
        else{//普通老师,所管理的班级
            searchStr+=" and   classid" +
                    "  IN (SELECT pkid FROM basic_schoolclass WHERE  pkid" +
                    " IN (SELECT  classid FROM  `edu_schoolclasscurriculum`  WHERE teacherid="+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid()+"))" ;
        }
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<HonorCategory> list = honorcategoryservice.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for(HonorCategory honorcategory: list)
        {
            SchoolClass  schoolclass=schoolClassService.getByPkId(honorcategory.getClassid());
            honorcategory.setClassName(schoolclass.getGradeName() + schoolclass.getName());
        }
        PageInfo<HonorCategory> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<HonorCategory> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

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
    public String honorcategoryAdd(Model model)
    {
        model.addAttribute("classList",  getSchoolClass());
        return "manage/eclassbrand/honorcategory/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int honorcategoryDoAdd(HonorCategory honorcategory)
    {
        honorcategory.setCreatetime(new Date());
        honorcategory.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        honorcategoryservice.insert(honorcategory);
        setOpLog("eclassbrand","荣誉分类", "增加荣誉分类：" + honorcategory.getName());
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
    public String honorcategoryEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        HonorCategory honorcategory = honorcategoryservice.getByPkId(TypeConverter.strToInt(id));
        if(honorcategory==null)
        {
            model.addAttribute("msg","要编辑的荣誉分类不存在");
            return "/error/error";
        }
        model.addAttribute("honorcategory", honorcategory);
        String searchStr=" pkid ="+honorcategory.getClassid();
        List<SchoolClass> classlist =  schoolClassService.getListBySearch(searchStr,"pkid desc");
        for (SchoolClass schoolclass:classlist)
        {
            schoolclass.setName(schoolclass.getGradeName() + schoolclass.getName());
        }
        model.addAttribute("classList",classlist);
        return "manage/eclassbrand/honorcategory/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int honorcategorytDoEdit(HonorCategory honorcategoryData)
    {
        HonorCategory honorcategory = honorcategoryservice.getByPkId(honorcategoryData.getPkid());
        if(honorcategory!=null) {
            honorcategory.setName(honorcategoryData.getName());
            honorcategory.setSortnum(honorcategoryData.getSortnum());
            honorcategoryservice.update(honorcategory);
            //新增荣誉分类与班级关联
            setOpLog("eclassbrand","荣誉分类", "编辑荣誉分类：" + honorcategory.getPkid());
        }
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int honorcategoryDoDel(int id)
    {
        HonorCategory honorcategory = honorcategoryservice.getByPkId(id);
        if(honorcategory!=null) {
            honorcategoryservice.delete(id);
            setOpLog("eclassbrand","荣誉分类", "删除荣誉分类：" + honorcategory.getPkid());
        }
        return 1;
    }


    /**
     * @Description: 网址列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/honorlist", method = RequestMethod.GET)
    public String honorList(@RequestParam(value = "id", required = false, defaultValue = "0") String id,Model model)
    {
        model.addAttribute("categoryid", id);
        return "manage/eclassbrand/honorcategory/honorlist";
    }

    @RequestMapping(value = "/getHonorListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Honor> getHonorListForPage(JqGridPageRequest pageRequest, Integer categoryid, String searchKey)
    {
        String searchStr = "  categoryid= "+categoryid;
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Honor> list = honorservice.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<Honor> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Honor> response = new JqGridPageResponse<>();
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
    @RequestMapping(value = "/honoradd", method = RequestMethod.GET)
    public String honorAdd(@RequestParam(value = "id", required = false, defaultValue = "0") String id,Model model)
    {
        model.addAttribute("categoryid", id);
        return "manage/eclassbrand/honorcategory/honoradd";
    }

    @RequestMapping(value = "/honordoAdd", method = RequestMethod.POST)
    @ResponseBody
    public int honorDoAdd(Honor honor)
    {
        honor.setCreatetime(new Date());
        honor.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        honorservice.insert(honor);
        setOpLog("eclassbrand","荣誉", "增加荣誉：" + honor.getName());
        return 1;
    }

    /**
     * @Description: 编辑页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/honoredit", method = RequestMethod.GET)
    public String honorEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        Honor honor = honorservice.getByPkId(TypeConverter.strToInt(id));
        if(honor==null)
        {
            model.addAttribute("msg","要编辑的荣誉不存在");
            return "/error/error";
        }
        model.addAttribute("honor", honor);
        return "manage/eclassbrand/honorcategory/honoredit";
    }

    @RequestMapping(value = "/honordoEdit", method = RequestMethod.POST)
    @ResponseBody
    public int honorDoEdit(Honor honorData)
    {
        Honor honor = honorservice.getByPkId(honorData.getPkid());
        if(honor!=null) {
            honor.setName(honorData.getName());
            honor.setPicture(honorData.getPicture());
            honor.setSortnum(honorData.getSortnum());
            honorservice.update(honor);
            //新增荣誉与班级关联
            setOpLog("eclassbrand","荣誉", "编辑荣誉：" + honor.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/honordoDel", method = RequestMethod.POST)
    @ResponseBody
    public int honorDoDel(String ids)
    {
        if(ids==""||ids==null)
        {
            return 1;
        }
        String[] idstr=ids.split(",");
        for (String id : idstr)
        {
            Honor honor = honorservice.getByPkId(TypeConverter.strToInt(id));
            if(honor!=null) {
                honorservice.delete(honor.getPkid());
                setOpLog("eclassbrand","荣誉", "删除荣誉：" + honor.getName());
            }
        }
        return 1;
    }

}
