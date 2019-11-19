package com.easybasic.kaoqin.controller;


import com.easybasic.basic.model.DataDicVal;

import com.easybasic.basic.model.User;
import com.easybasic.basic.service.UserService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.kaoqin.dao.IUserGroupAndUserDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.druid.util.StringUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.easybasic.kaoqin.model.*;
import com.easybasic.kaoqin.service.*;
import com.easybasic.basic.service.DataDicValService;

@Controller
@RequestMapping("/manage/kaoqin/usergroup")
public class UserGroupController extends BaseController {

    @Resource
    private UserGroupService userGroupService;
    @Resource
    private UserService userService;
    @Resource
    private UserGroupAndUserService userGroupAndUserService;
    @Resource
    private PlanWeekService planWeekService;
    /**
     * @Description: 用户分组列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String usergroupList(Model model)
    {

        String re = setPageCommonModel(model, "kaoqin","/manage/kaoqin/usergroup", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/kaoqin/usergroup/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }
    /**
     * @Description: 用户分组新增页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String usergroupAdd(Model model)
    {
        return "manage/kaoqin/usergroup/add";
    }
    /**
     * @Description: 用户分组编辑页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String usergroupEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        UserGroup usergroup = userGroupService.getByPkId(TypeConverter.strToInt(id));
        if(usergroup==null)
        {
            model.addAttribute("msg","要编辑的用户分组不存在");
            return "/error/error";
        }
        model.addAttribute("usergroup", usergroup);
        return "manage/kaoqin/usergroup/edit";
    }

    /**
     * @Description: 考勤分组关联用户
     * @param: [id, model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 17:02
     */
    @RequestMapping(value = "/relationuser", method = RequestMethod.GET)
    public String usergroupRelationUser(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        UserGroup usergroup = userGroupService.getByPkId(TypeConverter.strToInt(id));
        if(usergroup==null)
        {
            model.addAttribute("msg","角色数据不存在");
            return "/error/error";
        }
        model.addAttribute("usergroupId", usergroup.getPkid());
        return "manage/kaoqin/usergroup/relationuser";
    }

    /**
     * @Description: 用户分组列表页面分页数据查询
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/getUserGroupListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<UserGroup> getUserGroupListForPage(JqGridPageRequest pageRequest, String searchKey)
    {
        String searchStr = "pkid>0" ;
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<UserGroup> list = userGroupService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<UserGroup> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<UserGroup> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }
    /**
     * @Description: 新增用户分组
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int usergroupDoAdd(String groupname)
    {
        UserGroup usergroup =new UserGroup();
        usergroup.setGroupname(groupname);
        userGroupService.insert(usergroup);
        setOpLog("kaoqin","用户分组信息", "增加用户分组：" + usergroup.getGroupname());
        SetKaoQinOpLog("kq_usergroup",usergroup.getPkid(),1);
        return 1;
    }

    /**
     * @Description: 修改用户分组
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int usergroupDoEdit(int pkid,String groupname)
    {
        UserGroup usergroup = userGroupService.getByPkId(pkid);
        if(usergroup!=null) {
            usergroup.setGroupname(groupname);
            userGroupService.update(usergroup);
            SetKaoQinOpLog("kq_usergroup",usergroup.getPkid(),2);
            setOpLog("kaoqin","用户分组信息", "编辑用户分组：" + usergroup.getGroupname());
        }
        return 1;
    }
    /**
     * @Description: 考勤用户组删除
     * @param: [id]
     * @return: int
     * @auther: tangy
     * @date: 2019/5/21 0021 18:14
     */
    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int usergroupDoDel(int id)
    {
        UserGroup usergroup = userGroupService.getByPkId(id);
        if(usergroup!=null) {//是否关联周课程表
            List<PlanWeek> list = planWeekService.getListBySearch(" UserGroupIDStr LIKE '%"+usergroup.getPkid()+"%'", "pkid asc");
            if(list.size()>0)
            {
                return 0;
            }
            userGroupService.delete(usergroup.getPkid());
            setOpLog("kaoqin","用户分组信息", "删除用户分组：" + usergroup.getGroupname());
            SetKaoQinOpLog("kq_usergroup",usergroup.getPkid(),3);
        }
        return 1;
    }

    /**
     * @Description: 获取用户组与用户关联数据列表
     * @param: [pageRequest, usergroupId, searchKey]
     * @return: com.easybasic.component.jqgrid.JqGridPageResponse<com.easybasic.basic.model.User>
     * @auther: tangy
     * @date: 2019/5/21 0021 18:14
     */
    @RequestMapping(value = "/getUserListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<User> getUserListForPage(JqGridPageRequest pageRequest, int usergroupId, String searchKey)
    {
        String searchStr = "pkid in (select UserID from kq_usergroupanduser where usergroupID="+usergroupId+")";
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and (name like '%"+searchKey+"%' or loginname like '%"+searchKey+"%')";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<User> list = userService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<User> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<User> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }
    /**
     * @Description: 新增用户与用户组关联
     * @param: [ids, usergroupId]
     * @return: int
     * @auther: tangy
     * @date: 2019/5/21 0021 18:22
     */
    @RequestMapping(value = "/DoUsergroupAndUserAdd", method = RequestMethod.POST)
    @ResponseBody
    public int usergroupanduserAdd(String ids, int usergroupId)
    {
        String[] idStrList = ids.split(",");
        for(String idStr : idStrList)
        {
            int id = TypeConverter.strToInt(idStr);
            if(id == 0)continue;
            UserGroupAndUser  usergroupanduser =userGroupAndUserService.getByUserIdAndGroupId(id,usergroupId);
            if(usergroupanduser==null)
            {
                usergroupanduser=new UserGroupAndUser();
                usergroupanduser.setUserid(id);
                usergroupanduser.setUsergroupid(usergroupId);
                userGroupAndUserService.insert(usergroupanduser);
                setOpLog("kaoqin","用户分组信息", "新增用户分组与用户关联：" + usergroupanduser.getPkid());
                SetKaoQinOpLog("kq_usergroupanduser",usergroupanduser.getPkid(),1);
            }
        }
        return 1;
    }
    /**
     * @Description: 删除用户与用户组关联
     * @param: [ids：用户编号, usergroupId:用户组编号]
     * @return: int
     * @auther: tangy
     * @date: 2019/5/21 0021 18:15
     */
    @RequestMapping(value = "/DoUsergroupAndUserDel", method = RequestMethod.POST)
    @ResponseBody
    public int usergroupAndUserDel(String ids, int usergroupId)
    {
        String[] idStrList = ids.split(",");
        for(String idStr : idStrList)
        {
            int id = TypeConverter.strToInt(idStr);
            if(id == 0)continue;
            UserGroupAndUser  usergroupanduser =userGroupAndUserService.getByUserIdAndGroupId(id,usergroupId);
            if(usergroupanduser!=null)
            {
                userGroupAndUserService.delete(usergroupanduser.getPkid());
                setOpLog("kaoqin","用户分组信息", "删除用户分组与用户关联：" + usergroupanduser.getPkid());
                SetKaoQinOpLog("kq_usergroupanduser",usergroupanduser.getPkid(),3);
            }
        }
        return 1;
    }
    @RequestMapping(value = "/getUserGroupList", method = RequestMethod.POST)
    @ResponseBody
    public List<UserGroup> getUserGroupList()
    {
        List<UserGroup> list = userGroupService.getAllList();
        return list;
    }

}
