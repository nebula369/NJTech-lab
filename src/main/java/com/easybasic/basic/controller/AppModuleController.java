package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.AppMenu;
import com.easybasic.basic.model.AppModule;
import com.easybasic.basic.service.AppMenuService;
import com.easybasic.basic.service.AppModuleService;
import com.easybasic.component.BaseController;
import com.easybasic.component.MyDictionary;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.menu.MenuConfigHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/basic/app")
public class AppModuleController extends BaseController {

    @Resource
    private AppModuleService appModuleService;
    @Resource
    private AppMenuService appMenuService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String appList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/app", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/basic/set/app/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getAppList", method = RequestMethod.POST)
    @ResponseBody
    public List<AppModule> getAppList()
    {
        List<AppModule> list = appModuleService.getAllList();
        list = list.stream().sorted(Comparator.comparing(AppModule::getSortnum)).collect(Collectors.toList());
        return list;
    }

    @RequestMapping(value = "/saveAppOrder", method = RequestMethod.POST)
    @ResponseBody
    public int saveAppOrder(String orderParam)
    {
        MyDictionary param = MyDictionary.Init(orderParam);
        List<AppModule> list = appModuleService.getAllList();
        for(AppModule app : list)
        {
            app.setSortnum(TypeConverter.strToInt(param.get(app.getPkid().toString())));
            appModuleService.update(app);
        }
        setOpLog("basic","应用配置", "修改应用列表排序");
        return 1;
    }

    @RequestMapping(value = "/setAppStatus", method = RequestMethod.POST)
    @ResponseBody
    public int setAppStatus(int id, int status)
    {
        AppModule appModule = appModuleService.getByPkId(id);
        if(appModule!=null)
        {
            appModule.setStatus(status);
            appModuleService.update(appModule);
            MenuConfigHandler.resetMenuConfigCache(appModule);
            setOpLog("basic","应用配置","修改应用“"+appModule.getName()+"”状态为：" + status);
        }
        return 1;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String appEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                          Model model)
    {
        AppModule appModule = appModuleService.getByPkId(TypeConverter.strToInt(id));
        if(appModule==null)
        {
            model.addAttribute("msg","要修改的应用信息不存在");
            return "/error/error";
        }
        model.addAttribute("app", appModule);
        return "/manage/basic/set/app/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int appDoEdit(AppModule editData)
    {
        AppModule appModule = appModuleService.getByPkId(editData.getPkid());
        if(appModule!=null)
        {
            appModule.setName(editData.getName());
            appModule.setLink(editData.getLink());
            appModule.setLinktarget(editData.getLinktarget());
            appModule.setSmallicon(editData.getSmallicon());
            appModule.setStatus(editData.getStatus());
            appModule.setUsetype(editData.getUsetype());
            appModuleService.update(appModule);
            MenuConfigHandler.resetMenuConfigCache(appModule);
            setOpLog("basic","应用配置","修改应用("+appModule.getName()+")参数");
        }
        return 1;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String appAdd(Model model)
    {
        return "/manage/basic/set/app/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int appDoAdd(AppModule appModule)
    {
        if(StringUtils.isEmpty(appModule.getCode()))
        {
            return 0;
        }
        AppModule temp = appModuleService.getByCode(appModule.getCode());
        if(temp != null)
        {
            return -1;
        }
        appModule.setApptype(0);
        appModule.setBigicon("");
        appModule.setSortnum(0);
        appModule.setCreatetime(new Date());
        appModuleService.insert(appModule);
        MenuConfigHandler.resetMenuConfigCache(appModule);
        setOpLog("basic","应用配置","增加应用模块（"+appModule.getName()+"）");
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int appDoDel(int id)
    {
        AppModule appModule = appModuleService.getByPkId(id);
        if(appModule!=null) {
            List<AppMenu> list = appMenuService.getListByAppIdForMenu(id);
            if (list.size() != 0) {
                return 0;
            }
            appModuleService.delete(id);
            MenuConfigHandler.resetMenuConfigCache(appModule);
            setOpLog("basic","应用配置", "删除应用模块("+appModule.getName()+")");
        }
        return 1;
    }

    @RequestMapping(value = "/getAppMenuList", method = RequestMethod.POST)
    @ResponseBody
    public List<AppMenu> getAppMenuList(int appId)
    {
        List<AppMenu> list = appMenuService.getListByAppIdForMenu(appId);
        list = list.stream().sorted(Comparator.comparing(AppMenu::getSortnum)).collect(Collectors.toList());
        return list;
    }

    @RequestMapping(value = "/saveAppMenuOrder", method = RequestMethod.POST)
    @ResponseBody
    public int saveAppMenuOrder(int appId, String orderParam)
    {
        AppModule appModule = appModuleService.getByPkId(appId);
        if(appModule!=null) {
            MyDictionary param = MyDictionary.Init(orderParam);
            List<AppMenu> list = appMenuService.getListByAppIdForMenu(appId);
            for (AppMenu app : list) {
                app.setSortnum(TypeConverter.strToInt(param.get(app.getPkid().toString())));
                appMenuService.update(app);
            }
            setOpLog("basic","应用配置", "修改应用菜单列表排序");
            MenuConfigHandler.resetMenuConfigCache(appModule);
        }
        return 1;
    }

    @RequestMapping(value = "/setAppMenuStatus", method = RequestMethod.POST)
    @ResponseBody
    public int setAppMenuStatus(int id, int status)
    {
        AppMenu appMenu = appMenuService.getByPkId(id);
        if(appMenu!=null)
        {
            appMenu.setStatus(status);
            appMenuService.update(appMenu);
            MenuConfigHandler.resetMenuConfigCache(appMenu.getAppid());
            setOpLog("basic","应用配置","修改应用菜单“"+appMenu.getName()+"”状态为：" + status);
        }
        return 1;
    }

    @RequestMapping(value = "/doMenuDel", method = RequestMethod.POST)
    @ResponseBody
    public int appDoMenuDel(int id)
    {
        AppMenu appMenu = appMenuService.getByPkId(id);
        if(appMenu!=null) {
            List<AppMenu> list = appMenuService.getListByParentId(id);
            if (list.size() != 0) {
                return 0;
            }
            appMenuService.delete(id);
            MenuConfigHandler.resetMenuConfigCache(appMenu.getAppid());
            setOpLog("basic","应用配置", "删除应用菜单（"+appMenu.getName()+"）");
        }
        return 1;
    }

    @RequestMapping(value = "/menuAdd", method = RequestMethod.GET)
    public String appMenuAdd(@RequestParam(value = "id", required = false, defaultValue = "0")String id
            ,Model model)
    {
        AppModule appModule = appModuleService.getByPkId(TypeConverter.strToInt(id));
        if(appModule==null)
        {
            model.addAttribute("msg","当前应用模块不存在");
            return "/error/error";
        }
        model.addAttribute("app", appModule);
        return "/manage/basic/set/app/menuadd";
    }

    @RequestMapping(value = "/doMenuAdd", method = RequestMethod.POST)
    @ResponseBody
    public int appMenuDoAdd(AppMenu appMenu)
    {
        AppModule appModule = appModuleService.getByPkId(appMenu.getAppid());
        if(appModule == null)
        {
            return 0;
        }
        appMenu.setParentid(0);
        appMenu.setLink("");
        appMenu.setCode("");
        appMenu.setLinktarget(0);
        appMenu.setSortnum(0);
        appMenu.setCreatetime(new Date());
        appMenuService.insert(appMenu);
        MenuConfigHandler.resetMenuConfigCache(appModule);
        setOpLog("basic","应用配置","增加应用“"+appModule.getName()+"”下面的菜单“"+appMenu.getName()+"”");
        return 1;
    }

    @RequestMapping(value = "/menuEdit", method = RequestMethod.GET)
    public String appMenuEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                          Model model)
    {
        AppMenu appMenu = appMenuService.getByPkId(TypeConverter.strToInt(id));
        if(appMenu==null)
        {
            model.addAttribute("msg","要修改的应用菜单信息不存在");
            return "/error/error";
        }
        AppModule appModule = appModuleService.getByPkId(appMenu.getAppid());
        model.addAttribute("app", appModule);
        model.addAttribute("menu", appMenu);
        return "/manage/basic/set/app/menuedit";
    }

    @RequestMapping(value = "/doMenuEdit", method = RequestMethod.POST)
    @ResponseBody
    public int appMenuDoEdit(AppMenu editData)
    {
        AppMenu appMenu = appMenuService.getByPkId(editData.getPkid());
        if(appMenu!=null)
        {
            appMenu.setName(editData.getName());
            appMenu.setIcon(editData.getIcon());
            appMenu.setStatus(editData.getStatus());
            appMenu.setUsetype(editData.getUsetype());
            appMenuService.update(appMenu);
            MenuConfigHandler.resetMenuConfigCache(appMenu.getAppid());
            setOpLog("basic","应用配置","修改应用菜单“"+appMenu.getName()+"”参数");
        }
        return 1;
    }

    @RequestMapping(value = "/getAppSubMenuList", method = RequestMethod.POST)
    @ResponseBody
    public List<AppMenu> getAppSubMenuList(int menuId)
    {
        List<AppMenu> list = appMenuService.getListByParentId(menuId);
        list = list.stream().sorted(Comparator.comparing(AppMenu::getSortnum)).collect(Collectors.toList());
        return list;
    }

    @RequestMapping(value = "/saveAppSubMenuOrder", method = RequestMethod.POST)
    @ResponseBody
    public int saveAppSubMenuOrder(int menuId, String orderParam)
    {
        AppMenu appMenu = appMenuService.getByPkId(menuId);
        if(appMenu!=null) {
            MyDictionary param = MyDictionary.Init(orderParam);
            List<AppMenu> list = appMenuService.getListByParentId(menuId);
            for (AppMenu app : list) {
                app.setSortnum(TypeConverter.strToInt(param.get(app.getPkid().toString())));
                appMenuService.update(app);
            }
            MenuConfigHandler.resetMenuConfigCache(appMenu.getAppid());
            setOpLog("basic","应用配置", "修改应用子菜单列表排序");
        }
        return 1;
    }

    @RequestMapping(value = "/setAppSubMenuStatus", method = RequestMethod.POST)
    @ResponseBody
    public int setAppSubMenuStatus(int id, int status)
    {
        AppMenu appMenu = appMenuService.getByPkId(id);
        if(appMenu!=null)
        {
            appMenu.setStatus(status);
            appMenuService.update(appMenu);
            MenuConfigHandler.resetMenuConfigCache(appMenu.getAppid());
            setOpLog("basic","应用配置","修改应用子菜单“"+appMenu.getName()+"”状态为：" + status);
        }
        return 1;
    }

    @RequestMapping(value = "/doSubMenuDel", method = RequestMethod.POST)
    @ResponseBody
    public int appDoSubMenuDel(int id)
    {
        AppMenu appMenu = appMenuService.getByPkId(id);
        if(appMenu!=null) {
            appMenuService.delete(id);
            MenuConfigHandler.resetMenuConfigCache(appMenu.getAppid());
            setOpLog("basic","应用配置", "删除应用子菜单“"+appMenu.getName()+"”");
        }
        return 1;
    }

    @RequestMapping(value = "/subMenuAdd", method = RequestMethod.GET)
    public String subMenuAdd(@RequestParam(value = "id", required = false, defaultValue = "0")String id
            ,Model model)
    {
        AppMenu appMenu = appMenuService.getByPkId(TypeConverter.strToInt(id));
        if(appMenu==null)
        {
            model.addAttribute("msg","当前应用菜单不存在");
            return "/error/error";
        }
        AppModule appModule = appModuleService.getByPkId(appMenu.getAppid());
        model.addAttribute("app", appModule);
        model.addAttribute("menu", appMenu);
        return "/manage/basic/set/app/submenuadd";
    }

    @RequestMapping(value = "/doSubMenuAdd", method = RequestMethod.POST)
    @ResponseBody
    public int doSubMenuAdd(AppMenu appMenu)
    {
        AppMenu parentMenu = appMenuService.getByPkId(appMenu.getParentid());
        if(parentMenu == null)
        {
            return 0;
        }
        appMenu.setCode("");
        appMenu.setIcon("fa-caret-right");
        appMenu.setSortnum(0);
        appMenu.setCreatetime(new Date());
        appMenuService.insert(appMenu);
        MenuConfigHandler.resetMenuConfigCache(appMenu.getAppid());
        setOpLog("basic","应用配置","增加菜单“"+parentMenu.getName()+"”下面的子菜单“"+appMenu.getName()+"”");
        return 1;
    }

    @RequestMapping(value = "/subMenuEdit", method = RequestMethod.GET)
    public String subMenuEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                              Model model)
    {
        AppMenu appMenu = appMenuService.getByPkId(TypeConverter.strToInt(id));
        if(appMenu==null)
        {
            model.addAttribute("msg","要修改的子菜单信息不存在");
            return "/error/error";
        }
        AppModule appModule = appModuleService.getByPkId(appMenu.getAppid());
        AppMenu parentMenu = appMenuService.getByPkId(appMenu.getParentid());
        model.addAttribute("app", appModule);
        model.addAttribute("parent", parentMenu);
        model.addAttribute("menu", appMenu);
        return "/manage/basic/set/app/submenuedit";
    }

    @RequestMapping(value = "/doSubMenuEdit", method = RequestMethod.POST)
    @ResponseBody
    public int doSubMenuEdit(AppMenu editData)
    {
        AppMenu appMenu = appMenuService.getByPkId(editData.getPkid());
        if(appMenu!=null)
        {
            appMenu.setName(editData.getName());
            appMenu.setLink(editData.getLink());
            appMenu.setLinktarget(editData.getLinktarget());
            appMenu.setStatus(editData.getStatus());
            appMenu.setUsetype(editData.getUsetype());
            appMenuService.update(appMenu);
            MenuConfigHandler.resetMenuConfigCache(appMenu.getAppid());
            setOpLog("basic","应用配置","修改子菜单“"+appMenu.getName()+"”参数");
        }
        return 1;
    }
}


