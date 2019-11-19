package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.component.AppAuthHandler;
import com.easybasic.basic.model.PurviewRole;
import com.easybasic.basic.model.PurviewRoleUser;
import com.easybasic.basic.model.User;
import com.easybasic.basic.service.PurviewRoleService;
import com.easybasic.basic.service.PurviewRoleUserService;
import com.easybasic.basic.service.UserService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manage/basic/roleAuth")
public class RoleAuthController extends BaseController {

    @Resource
    private PurviewRoleService purviewRoleService;
    @Resource
    private PurviewRoleUserService purviewRoleUserService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String userAuthList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/roleAuth", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/basic/auth/role/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getRoleAuthListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<PurviewRole> getRoleAuthListForPage(JqGridPageRequest pageRequest, String searchKey)
    {
        String searchStr = "pkid>0";
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        List<PurviewRole> list = purviewRoleService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        JqGridPageResponse<PurviewRole> response = new JqGridPageResponse<>();
        response.setRows(list);
        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String roleAdd()
    {
        return "/manage/basic/auth/role/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int roleDoAdd(PurviewRole purviewRole, String roleUserIds)
    {
        purviewRole.setCreatetime(new Date());
        purviewRoleService.insert(purviewRole);
        String[] roleUserStrList = roleUserIds.split(",");
        for(String userIdStr : roleUserStrList)
        {
            int userId = TypeConverter.strToInt(userIdStr);
            if(userId==0)continue;
            if(!purviewRoleUserService.isRoleUserExist(purviewRole.getPkid(), userId))
            {
                PurviewRoleUser purviewRoleUser = new PurviewRoleUser();
                purviewRoleUser.setRoleid(purviewRole.getPkid());
                purviewRoleUser.setUserid(userId);
                purviewRoleUserService.insert(purviewRoleUser);
                AppAuthHandler.clearUserAuthCache(userId);
            }
        }
        setOpLog("basic","角色授权", "新建角色：" + purviewRole.getName());
        return 1;
    }

    @RequestMapping(value = "/doSetStatus", method = RequestMethod.POST)
    @ResponseBody
    public int roleDoSetStatus(int id, int status)
    {
        PurviewRole role = purviewRoleService.getByPkId(id);
        if(role!=null)
        {
            List<PurviewRoleUser> list = purviewRoleUserService.getListByRoleId(role.getPkid());
            for(PurviewRoleUser roleUser : list)
            {
                AppAuthHandler.clearUserAuthCache(roleUser.getUserid());
            }
            role.setStatus(status);
            purviewRoleService.update(role);
            setOpLog("basic","角色授权", "修改角色状态：" + role.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int roleDoDel(int id)
    {
        PurviewRole role = purviewRoleService.getByPkId(id);
        if(role!=null)
        {
            List<PurviewRoleUser> list = purviewRoleUserService.getListByRoleId(role.getPkid());
            for(PurviewRoleUser roleUser : list)
            {
                AppAuthHandler.clearUserAuthCache(roleUser.getUserid());
            }
            purviewRoleService.delete(id);
            setOpLog("basic","角色授权", "删除角色：" + role.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/saveRoleAuth", method = RequestMethod.POST)
    @ResponseBody
    public int saveRoleAuth(int roleId, String authModuleIds, String authMenuIds,String authItemIds)
    {
        PurviewRole purviewRole = purviewRoleService.getByPkId(roleId);
        if(purviewRole != null)
        {
            purviewRole.setModulepurviews(authModuleIds);
            purviewRole.setMenupurviews(authMenuIds);
            purviewRole.setItempurviews(authItemIds);
            purviewRoleService.update(purviewRole);

            List<PurviewRoleUser> list = purviewRoleUserService.getListByRoleId(purviewRole.getPkid());
            for(PurviewRoleUser roleUser : list)
            {
                AppAuthHandler.clearUserAuthCache(roleUser.getUserid());
            }
        }
        setOpLog("basic","角色授权", "设置角色权限：" + purviewRole.getName());
        return 1;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String roleEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        PurviewRole role = purviewRoleService.getByPkId(TypeConverter.strToInt(id));
        if(role == null)
        {
            model.addAttribute("msg","要编辑的角色不存在");
            return "/error/error";
        }
        model.addAttribute("role", role);
        return "/manage/basic/auth/role/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int roleDoEdit(PurviewRole editDate)
    {
        PurviewRole role = purviewRoleService.getByPkId(editDate.getPkid());
        if(role==null)
        {
            return 0;
        }
        role.setName(editDate.getName());
        role.setRemark(editDate.getRemark());
        role.setSortnum(editDate.getSortnum());
        role.setStatus(editDate.getStatus());
        purviewRoleService.update(role);
        setOpLog("basic","角色授权", "编辑角色：" + role.getName());
        return 1;
    }

    @RequestMapping(value = "/setRoleUser", method = RequestMethod.GET)
    public String setRoleUser(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        PurviewRole purviewRole = purviewRoleService.getByPkId(TypeConverter.strToInt(id));
        if(purviewRole==null)
        {
            model.addAttribute("msg","角色数据不存在");
            return "/error/error";
        }
        model.addAttribute("roleId", purviewRole.getPkid());
        return "/manage/basic/auth/role/roleuser";
    }

    @RequestMapping(value = "/getRoleUserListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<User> getRoleUserListForPage(JqGridPageRequest pageRequest, int roleId, String searchKey)
    {
        String searchStr = "pkid in (select userid from basic_purviewroleuser where roleid="+roleId+")";
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

    @RequestMapping(value = "/doRoleUserDel", method = RequestMethod.POST)
    @ResponseBody
    public int doRoleUserDel(int id, int roleId)
    {
        PurviewRoleUser purviewRoleUser = purviewRoleUserService.getByRoleIdAndUserId(roleId, id);
        if(purviewRoleUser!=null)
        {
            purviewRoleUserService.delete(purviewRoleUser.getPkid());
            AppAuthHandler.clearUserAuthCache(purviewRoleUser.getUserid());
            setOpLog("basic","角色授权", "移除角色用户");
        }
        return 1;
    }

    @RequestMapping(value = "/doBatchDel", method = RequestMethod.POST)
    @ResponseBody
    public int doBatchDel(String ids, int roleId)
    {
        String[] idStrList = ids.split(",");
        for(String idStr : idStrList)
        {
            int id = TypeConverter.strToInt(idStr);
            if(id == 0)continue;
            doRoleUserDel(id, roleId);
        }
        return 1;
    }

    @RequestMapping(value = "/roleUserDoAdd", method = RequestMethod.POST)
    @ResponseBody
    public int roleUserDoAdd(String ids, int roleId)
    {
        String[] idStrList = ids.split(",");
        for(String idStr : idStrList)
        {
            int id = TypeConverter.strToInt(idStr);
            if(id == 0)continue;
            if(!purviewRoleUserService.isRoleUserExist(roleId, id))
            {
                PurviewRoleUser roleUser = new PurviewRoleUser();
                roleUser.setRoleid(roleId);
                roleUser.setUserid(id);
                purviewRoleUserService.insert(roleUser);
                AppAuthHandler.clearUserAuthCache(roleUser.getUserid());
            }
        }
        setOpLog("basic","角色授权", "设置角色用户新增");
        return 1;
    }
}
