package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.component.AppAuthHandler;
import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ToolsUtil;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/basic/userAuth")
public class UserAuthController extends BaseController {

    @Resource
    private UnitService unitService;
    @Resource
    private PurviewUserService purviewUserService;
    @Resource
    private UserService userService;
    @Resource
    private AppModuleService appModuleService;
    @Resource
    private AppMenuService appMenuService;
    @Resource
    private PurviewRoleService purviewRoleService;
    @Resource
    private PurviewRoleUserService purviewRoleUserService;
    @Resource
    private ThirdAppService thirdAppService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String userAuthList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/userAuth", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/basic/auth/user/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getUserAuthListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<PurviewUser> getUserAuthListForPage(JqGridPageRequest pageRequest, String searchKey)
    {
        String searchStr = "pkid>0";
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and userid in (select pkid from basic_user where name like '%"+searchKey+"%' or loginname like '%"+searchKey+"%')";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<PurviewUser> list = purviewUserService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for(PurviewUser purviewUser: list)
        {
            User user = userService.getUserForCache(purviewUser.getUserid());
            purviewUser.setUser(user);
            List<PurviewRole> roles = purviewRoleService.getListBySearch("pkid in (select roleid from basic_purviewroleuser where userid="+purviewUser.getUserid()+")","pkid asc");
            String roleStrs = roles.stream().map(x->x.getName()).collect(Collectors.joining("，"));
            purviewUser.setRoleStrs(roleStrs);
        }
        PageInfo<PurviewUser> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<PurviewUser> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    @RequestMapping(value = "/authUserList", method = RequestMethod.GET)
    public String authUserList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/userAuth", false);
        if(!StringUtils.isEmpty(re))
        {
            Unit unit = unitService.getUnitForCache(1);
            if(unit!=null)
            {
                model.addAttribute("topUnitName", unit.getName());
            }
            else
            {
                model.addAttribute("topUnitName","未知");
            }
            return "/manage/basic/auth/user/authuserlist";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/setAuthModule", method = RequestMethod.GET)
    public String setAuthModule(int userId, int roleId, int type ,Model model)
    {
        if(type==0) {
            User user = userService.getUserForCache(userId);
            if (user == null) {
                model.addAttribute("msg", "要授权的用户不存在");
                return "/error/error";
            }
        }
        else
        {
            PurviewRole purviewRole = purviewRoleService.getByPkId(roleId);
            if(purviewRole==null)
            {
                model.addAttribute("msg", "要授权的角色不存在");
                return "/error/error";
            }
        }
        model.addAttribute("userId", userId);
        model.addAttribute("roleId", roleId);
        model.addAttribute("type", type);
        return "manage/basic/auth/user/setauthmodule";
    }

    @RequestMapping(value = "/viewAuthModule", method = RequestMethod.GET)
    public String viewAuthModule(int userId, Model model)
    {
        User user = userService.getUserForCache(userId);
        if (user == null) {
            model.addAttribute("msg", "查看授权的用户不存在");
            return "/error/error";
        }
        model.addAttribute("userId", userId);
        return "manage/basic/auth/user/viewauthmodule";
    }

    @RequestMapping(value = "/getAuthModuleList")
    @ResponseBody
    public String getAuthModuleList(@RequestParam("id") String id, @RequestParam(value = "level", required = false, defaultValue = "0") String level,
                                    int userId, int roleId, int type)
    {
        String data = "";
        int parentId = TypeConverter.strToInt(id);
        int levelId = TypeConverter.strToInt(level);
        if(levelId ==0)
        {
            List<AppModule> list = appModuleService.getAppModuleListForCache();
            for (AppModule module : list)
            {
                List<AppMenu> subList = appMenuService.getAppMenuListForCache(module.getPkid(), 0);
                String title = module.getName();
                if (StringUtils.isEmpty(data))
                {
                    data = "{\"key\":\"" + module.getPkid() + "\",\"title\":\"" +
                            title + "\",\"code\":\"0\",\"name\":\"" +
                            module.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":" + subList.size() + ","+(subList.size() == 0 ? "" : ("\"children\": " + getSubNodeHtml(module.getPkid(), 0, (levelId + 1), userId, roleId, type) + ","))+"\"level\":" + (levelId + 1) +
                            ",\"isFolder\":\"true\",\"isLazy\":" + (subList.size() > 0 ? "true" : "false") + "}";
                }
                else
                {
                    data += ",{\"key\":\"" + module.getPkid() + "\",\"title\":\"" +
                            title + "\",\"code\":\"0\",\"name\":\"" +
                            module.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":" + subList.size() + ","+(subList.size() == 0 ? "" : ("\"children\": " + getSubNodeHtml(module.getPkid(), 0, (levelId + 1), userId, roleId, type) + ","))+"\"level\":" + (levelId + 1) +
                            ",\"isFolder\":\"true\",\"isLazy\":" + (subList.size() > 0 ? "true" : "false") + "}";
                }
            }
            List<ThirdApp> thirdApps = thirdAppService.getThirdAppListForCache();
            String authApps = "";
            if(type==0)
            {
                PurviewUser purviewUser = purviewUserService.getByUserId(userId);
                if(purviewUser !=null)
                {
                    authApps = purviewUser.getModulepurviews();
                }
            }
            else
            {
                PurviewRole purviewRole = purviewRoleService.getByPkId(roleId);
                if(purviewRole!=null)
                {
                    authApps = purviewRole.getModulepurviews();
                }
            }
            for(ThirdApp thirdApp : thirdApps)
            {
                String title = thirdApp.getName();
                String select = ",\"select\":false";
                if(ToolsUtil.InArray(thirdApp.getAppkey().toString(), authApps))
                {
                    select = ",\"select\":true";
                }
                if (StringUtils.isEmpty(data))
                {
                    data = "{\"key\":\"" + thirdApp.getAppkey() + "\""+select+",\"title\":\"" +
                            title + "\",\"code\":\"-1\",\"name\":\"" +
                            thirdApp.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":0,\"level\":" + (levelId + 1) +
                            ",\"isFolder\":\"false\",\"isLazy\":false}";
                }
                else
                {
                    data += ",{\"key\":\"" + thirdApp.getAppkey() + "\""+select+",\"title\":\"" +
                            title + "\",\"code\":\"-1\",\"name\":\"" +
                            thirdApp.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":0,\"level\":" + (levelId + 1) +
                            ",\"isFolder\":\"false\",\"isLazy\":false}";
                }
            }
        }
        return "{\"success\":true,\"data\":["+data+"]}";
    }

    private String getSubNodeHtml(int appId, int parentId,int level, int userId, int roleId, int type)
    {
        String data = "";
        String authItemIds = "";
        if(level == 2)
        {
            if(type==0)
            {
                PurviewUser purviewUser = purviewUserService.getByUserId(userId);
                if(purviewUser !=null)
                {
                    authItemIds = purviewUser.getItempurviews();
                }
            }
            else
            {
                PurviewRole purviewRole = purviewRoleService.getByPkId(roleId);
                if(purviewRole!=null)
                {
                    authItemIds = purviewRole.getItempurviews();
                }
            }
        }
        List<AppMenu> list = appMenuService.getAppMenuListForCache(appId, parentId);
        for (AppMenu menu : list)
        {
            List<AppMenu> subList = appMenuService.getAppMenuListForCache(0, menu.getPkid());;
            String select = ",\"select\":false";
            if(ToolsUtil.InArray(menu.getPkid().toString(), authItemIds))
            {
                select = ",\"select\":true";
            }
            String title = menu.getName();
            if (StringUtils.isEmpty(data))
            {
                data = "{\"key\":\"" + menu.getPkid() + "\""+select+",\"title\":\"" +
                        title + "\",\"code\":\""+level+"\",\"name\":\"" +
                        menu.getName() + "\",\"parent\":\"" + parentId +
                        "\",\"path\":null,\"childCount\":" + subList.size() + ","+(subList.size() == 0 ? "" : ("\"children\": " + getSubNodeHtml(appId, menu.getPkid(), (level + 1), userId, roleId, type) + ",")) +"\"level\":" + level +
                        ",\"isFolder\":"+(level == 1 ?"true":"false")+",\"isLazy\":" + (subList.size() > 0 ? "true" : "false") + "}";
            }
            else
            {
                data += ",{\"key\":\"" + menu.getPkid() + "\""+select+",\"title\":\"" +
                        title + "\",\"code\":\""+level+"\",\"name\":\"" +
                        menu.getName() + "\",\"parent\":\"" + parentId +
                        "\",\"path\":null,\"childCount\":" + subList.size() + ","+(subList.size() == 0 ? "" : ("\"children\": " + getSubNodeHtml(appId, menu.getPkid(), (level + 1), userId, roleId, type) + ","))+"\"level\":" + level +
                        ",\"isFolder\":"+(level == 1?"true":"false")+",\"isLazy\":" + (subList.size() > 0 ? "true" : "false") + "}";
            }
        }
        return "[" + data + "]";
    }

    @RequestMapping(value = "/getAuthModuleListForView")
    @ResponseBody
    public String getAuthModuleListForView(@RequestParam("id") String id, @RequestParam(value = "level", required = false, defaultValue = "0") String level,
                                    int userId)
    {
        String data = "";
        int parentId = TypeConverter.strToInt(id);
        int levelId = TypeConverter.strToInt(level);
        List<AppMenu> authMenuList = AppAuthHandler.getUserAppAuthForCache(userId);
        if(levelId ==0)
        {
            List<AppModule> list = appModuleService.getAppModuleListForCache();
            list = list.stream().filter(x->authMenuList.stream().anyMatch(y->y.getAppid().intValue() == x.getPkid().intValue())).collect(Collectors.toList());
            for (AppModule module : list)
            {
                List<AppMenu> subList = appMenuService.getAppMenuListForCache(module.getPkid(), 0);
                subList = subList.stream().filter(x->authMenuList.stream().anyMatch(y->y.getParentid().intValue() == x.getPkid().intValue())).collect(Collectors.toList());
                String title = module.getName();
                if (StringUtils.isEmpty(data))
                {
                    data = "{\"key\":\"" + module.getPkid() + "\",\"title\":\"" +
                            title + "\",\"code\":\"0\",\"name\":\"" +
                            module.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":" + subList.size() + ",\"level\":" + (levelId + 1) +
                            ",\"isFolder\":\"true\",\"isLazy\":" + (subList.size() > 0 ? "true" : "false") + "}";
                }
                else
                {
                    data += ",{\"key\":\"" + module.getPkid() + "\",\"title\":\"" +
                            title + "\",\"code\":\"0\",\"name\":\"" +
                            module.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":" + subList.size() + ",\"level\":" + (levelId + 1) +
                            ",\"isFolder\":\"true\",\"isLazy\":" + (subList.size() > 0 ? "true" : "false") + "}";
                }
            }
            List<ThirdApp> thirdApps  = AppAuthHandler.getUserThirdAppAuthForCache(userId);
            for(ThirdApp thirdApp : thirdApps)
            {
                String title = thirdApp.getName();
                if (StringUtils.isEmpty(data))
                {
                    data = "{\"key\":\"" + thirdApp.getAppkey() + "\",\"title\":\"" +
                            title + "\",\"code\":\"-1\",\"name\":\"" +
                            thirdApp.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":0,\"level\":" + (levelId + 1) +
                            ",\"isFolder\":\"false\",\"isLazy\":false}";
                }
                else
                {
                    data += ",{\"key\":\"" + thirdApp.getAppkey() + "\",\"title\":\"" +
                            title + "\",\"code\":\"-1\",\"name\":\"" +
                            thirdApp.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":0,\"level\":" + (levelId + 1) +
                            ",\"isFolder\":\"false\",\"isLazy\":false}";
                }
            }
        }
        else if(levelId ==1)
        {
            List<AppMenu> list = appMenuService.getAppMenuListForCache(parentId, 0);
            list = list.stream().filter(x->authMenuList.stream().anyMatch(y->y.getParentid().intValue() == x.getPkid().intValue())).collect(Collectors.toList());
            for (AppMenu menu : list)
            {
                List<AppMenu> subList = authMenuList.stream().filter(x->x.getParentid().intValue() == menu.getPkid().intValue()).collect(Collectors.toList());
                String title = menu.getName();
                if (StringUtils.isEmpty(data))
                {
                    data = "{\"key\":\"" + menu.getPkid() + "\",\"title\":\"" +
                            title + "\",\"code\":\"0\",\"name\":\"" +
                            menu.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":" + subList.size() + ",\"level\":" + (levelId + 1) +
                            ",\"isFolder\":\"true\",\"isLazy\":" + (subList.size() > 0 ? "true" : "false") + "}";
                }
                else
                {
                    data += ",{\"key\":\"" + menu.getPkid() + "\",\"title\":\"" +
                            title + "\",\"code\":\"0\",\"name\":\"" +
                            menu.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":" + subList.size() + ",\"level\":" + (levelId + 1) +
                            ",\"isFolder\":\"true\",\"isLazy\":" + (subList.size() > 0 ? "true" : "false") + "}";
                }
            }
        }
        else if(levelId ==2)
        {
            List<AppMenu> list = authMenuList.stream().filter(x->x.getParentid().intValue() == parentId).collect(Collectors.toList());
            for (AppMenu menu : list)
            {
                String title = menu.getName();
                if (StringUtils.isEmpty(data))
                {
                    data = "{\"key\":\"" + menu.getPkid() + "\",\"title\":\"" +
                            title + "\",\"code\":\"0\",\"name\":\"" +
                            menu.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":0,\"level\":" + (levelId + 1) +
                            ",\"isFolder\":false,\"isLazy\":false}";
                }
                else
                {
                    data += ",{\"key\":\"" + menu.getPkid() + "\",\"title\":\"" +
                            title + "\",\"code\":\"0\",\"name\":\"" +
                            menu.getName() + "\",\"parent\":\"" + parentId +
                            "\",\"path\":null,\"childCount\":0,\"level\":" + (levelId + 1) +
                            ",\"isFolder\":false,\"isLazy\":false}";
                }
            }
        }
        return "{\"success\":true,\"data\":["+data+"]}";
    }

    @RequestMapping(value = "/saveUserAuth", method = RequestMethod.POST)
    @ResponseBody
    public int saveUserAuth(int userId, String authModuleIds, String authMenuIds,String authItemIds)
    {
        User user = userService.getUserForCache(userId);
        if(user == null) return 0;
        PurviewUser purviewUser = purviewUserService.getByUserId(userId);
        if(purviewUser == null)
        {
            purviewUser = new PurviewUser();
            purviewUser.setUserid(userId);
            purviewUser.setModulepurviews(authModuleIds);
            purviewUser.setMenupurviews(authMenuIds);
            purviewUser.setItempurviews(authItemIds);
            purviewUser.setCreatetime(new Date());
            purviewUserService.insert(purviewUser);
        }
        else
        {
            purviewUser.setModulepurviews(authModuleIds);
            purviewUser.setMenupurviews(authMenuIds);
            purviewUser.setItempurviews(authItemIds);
            purviewUserService.update(purviewUser);
        }
        AppAuthHandler.clearUserAuthCache(userId);
        setOpLog("basic","用户授权", "设置用户权限：" + user.getName());
        return 1;
    }
}
