package com.easybasic.component.menu;

import com.easybasic.basic.model.AppMenu;
import com.easybasic.basic.model.AppModule;
import com.easybasic.basic.service.AppMenuService;
import com.easybasic.basic.service.AppModuleService;
import com.easybasic.component.Utils.RedisCache;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.XmlUtils;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.component.menu.dto.Menu;
import com.easybasic.component.menu.dto.MenuConfig;
import com.easybasic.component.menu.dto.SubMenu;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuConfigHandler {

    @Resource
    private AppModuleService appModuleService;
    @Resource
    private AppMenuService appMenuService;

    private static MenuConfigHandler handler;

    @PostConstruct
    public void init()
    {
        handler = this;
        handler.appModuleService = this.appModuleService;
        handler.appMenuService = this.appMenuService;
    }

    /**
     * 从缓存中读取应用菜单
     * @param appModule
     * @return
     */
    public static MenuConfig getMenuConfig(String appModule)
    {
        if(LoginUtil.getCurrentLoginUserProperty().isUnitManager())
        {
            String key = RedisCache.CachePrex + "getMenuConfig_unitmanager_" + appModule.toLowerCase();
            MenuConfig menuConfig = (MenuConfig) RedisCache.getInstance().getObject(key);
            if (menuConfig == null) {
                menuConfig = initMenuConifgForUnitManager(appModule);
                if (menuConfig != null) {
                    RedisCache.getInstance().putObject(key, menuConfig);
                }
            }
            return menuConfig;
        }
        else {
            String key = RedisCache.CachePrex+"getMenuConfig_" + appModule.toLowerCase();
            MenuConfig menuConfig = (MenuConfig) RedisCache.getInstance().getObject(key);
            if(menuConfig == null)
            {
                menuConfig = initMenuConifg(appModule);
                if(menuConfig!=null)
                {
                    RedisCache.getInstance().putObject(key, menuConfig);
                }
            }
            return menuConfig;
        }
    }

    public static void resetMenuConfigCache(AppModule appModule)
    {
        String key = RedisCache.CachePrex+"getMenuConfig_" + appModule.getCode().toLowerCase();
        RedisCache.getInstance().removeObject(key);
    }

    public static void resetMenuConfigCache(int appId)
    {
        AppModule appModule = handler.appModuleService.getByPkId(appId);
        if(appModule!=null)
        {
            resetMenuConfigCache(appModule);
        }
    }

    /**
     * 解析应用模块对应的左侧菜单数据项
     * @param code 应用模块标识
     * @return
     */
    private static MenuConfig initMenuConifg(String code)
    {
        code = code.toLowerCase();
        AppModule appModule = handler.appModuleService.getByCode(code);
        if(appModule == null) return null;
        MenuConfig menuConfig = new MenuConfig();
        //初始化菜单列表
        List<AppMenu> list = handler.appMenuService.getListByAppId(appModule.getPkid());

        List<Menu> menuList = list.stream().filter(x->x.getStatus()==1 && x.getParentid()==0).sorted(Comparator.comparing(AppMenu::getSortnum))
                .map(x->{
                    Menu menu = new Menu();
                    menu.setName(x.getName());
                    menu.setIcon(x.getIcon());
                    menu.setSubMenuList(initSubMenuList(x.getPkid(), list));
                    menu.setMenuId(x.getPkid());
                    return menu;
                }).collect(Collectors.toList());
        menuConfig.setName(appModule.getName());
        menuConfig.setIcon(appModule.getSmallicon());
        menuConfig.setMenuList(menuList);
        menuConfig.setAppId(appModule.getPkid());
        return menuConfig;
    }

    private static List<SubMenu> initSubMenuList(int parentId, List<AppMenu> list)
    {
        List<AppMenu> subMenuList = list.stream().filter(x->x.getStatus()==1 && x.getParentid().intValue() == parentId)
                .sorted(Comparator.comparing(AppMenu::getSortnum)).collect(Collectors.toList());
        return subMenuList.stream().map(x->{
            SubMenu subMenu = new SubMenu();
            subMenu.setName(x.getName());
            subMenu.setHref(x.getLink());
            subMenu.setIcon(x.getIcon());
            subMenu.setLinkTarget(x.getLinktarget());
            subMenu.setMenuId(x.getPkid());
            return subMenu;
        }).collect(Collectors.toList());
    }

    /**
     * 解析应用模块对应的左侧菜单数据项(针对单位管理员的菜单项)
     * @param code 应用模块标识
     * @return
     */
    private static MenuConfig initMenuConifgForUnitManager(String code)
    {
        code = code.toLowerCase();
        AppModule appModule = handler.appModuleService.getByCode(code);
        if(appModule == null) return null;
        MenuConfig menuConfig = new MenuConfig();
        //初始化菜单列表
        List<AppMenu> list = handler.appMenuService.getListByAppId(appModule.getPkid());

        List<Menu> menuList = list.stream().filter(x->x.getStatus()==1 && x.getParentid()==0 && (x.getUsetype()==1 || x.getUsetype()==2)).sorted(Comparator.comparing(AppMenu::getSortnum))
                .map(x->{
                    Menu menu = new Menu();
                    menu.setName(x.getName());
                    menu.setIcon(x.getIcon());
                    menu.setSubMenuList(initSubMenuListForUnitManager(x.getPkid(), list));
                    menu.setMenuId(x.getPkid());
                    menu.setUserType(x.getUsetype());
                    return menu;
                }).collect(Collectors.toList());
        menuConfig.setName(appModule.getName());
        menuConfig.setIcon(appModule.getSmallicon());
        menuConfig.setMenuList(menuList);
        menuConfig.setAppId(appModule.getPkid());
        return menuConfig;
    }

    private static List<SubMenu> initSubMenuListForUnitManager(int parentId, List<AppMenu> list)
    {
        List<AppMenu> subMenuList = list.stream().filter(x->x.getStatus()==1 && x.getParentid().intValue() == parentId && (x.getUsetype()==1||x.getUsetype()==2))
                .sorted(Comparator.comparing(AppMenu::getSortnum)).collect(Collectors.toList());
        return subMenuList.stream().map(x->{
            SubMenu subMenu = new SubMenu();
            subMenu.setName(x.getName());
            subMenu.setHref(x.getLink());
            subMenu.setIcon(x.getIcon());
            subMenu.setLinkTarget(x.getLinktarget());
            subMenu.setMenuId(x.getPkid());
            subMenu.setUserType(x.getUsetype());
            return subMenu;
        }).collect(Collectors.toList());
    }


}
