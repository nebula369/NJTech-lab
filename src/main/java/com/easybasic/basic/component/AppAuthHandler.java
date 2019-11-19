package com.easybasic.basic.component;

import com.easybasic.basic.model.*;
import com.easybasic.basic.service.*;
import com.easybasic.component.Utils.RedisCache;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.component.menu.dto.Menu;
import com.easybasic.component.menu.dto.MenuConfig;
import com.easybasic.component.menu.dto.SubMenu;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppAuthHandler
{
    @Resource
    private AppModuleService appModuleService;
    @Resource
    private AppMenuService appMenuService;
    @Resource
    private PurviewUserService purviewUserService;
    @Resource
    private PurviewRoleService purviewRoleService;
    @Resource
    private PurviewRoleUserService purviewRoleUserService;
    @Resource
    private ThirdAppService thirdAppService;

    private static AppAuthHandler handler;

    @PostConstruct
    public void init()
    {
        handler = this;
        handler.appModuleService = this.appModuleService;
        handler.appMenuService = this.appMenuService;
        handler.purviewRoleService = this.purviewRoleService;
        handler.purviewRoleUserService = this.purviewRoleUserService;
        handler.purviewUserService = this.purviewUserService;
        handler.thirdAppService = this.thirdAppService;
    }

    /**
     * 初始化用户应用菜单项
     * @param menuConfig
     */
    public static MenuConfig initUserMenuData(MenuConfig menuConfig)
    {
        if(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid() == 1|| LoginUtil.getCurrentLoginUserProperty().isUnitManager())
        {
            return menuConfig;
        }
        int userId = LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid();
        MenuConfig result = new MenuConfig();
        result.setName(menuConfig.getName());
        result.setIcon(menuConfig.getIcon());
        result.setAppId(menuConfig.getAppId());
        List<AppMenu> authMenuList = getUserAppAuthForCache(userId);
        List<Menu> menuList = new ArrayList<>();
        for(Menu menu : menuConfig.getMenuList())
        {
            Menu menuResult = new Menu();
            menuResult.setIcon(menu.getIcon());
            menuResult.setName(menu.getName());
            menuResult.setMenuId(menu.getMenuId());
            List<SubMenu> subMenuList = new ArrayList<>();
            for(SubMenu subMenu :menu.getSubMenuList())
            {
                if(subMenu.getUserType() == 2 || authMenuList.stream().anyMatch(x->x.getPkid().intValue() == subMenu.getMenuId()))
                {
                    subMenuList.add(subMenu);
                }
            }
            if(subMenuList.size()>0) {
                menuResult.setSubMenuList(subMenuList);
                menuList.add(menuResult);
            }
        }
        result.setMenuList(menuList);
        return result;
    }

    public static boolean isCurrentUrlInAuth(String currentUrl)
    {
        if(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid() == 1)
        {
            return true;
        }
        if(LoginUtil.getCurrentLoginUserProperty().isUnitManager())
        {
            //单位管理员
            List<AppMenu> menuList = handler.appMenuService.getAppMenuListForCache();
            menuList = menuList.stream().filter(x->x.getUsetype() == 1).collect(Collectors.toList());
            if(menuList.stream().anyMatch(x->x.getLink().equalsIgnoreCase(currentUrl)))
            {
                return true;
            }
            return false;
        }
        int userId = LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid();
        List<AppMenu> authMenuList = getUserAppAuthForCache(userId);
        if(authMenuList.stream().anyMatch(x->x.getLink().equalsIgnoreCase(currentUrl)))
        {
            return true;
        }
        return false;
    }

    public static boolean isHaveThirdAppAuth(String appKey, int userId)
    {
        List<ThirdApp> thirdAppList = AppAuthHandler.getUserThirdAppAuthForCache(userId);
        if(userId==1) {
            return true;
        }
        if(thirdAppList.stream().anyMatch(x->x.getAppkey().equalsIgnoreCase(appKey)))
        {
            return true;
        }
        return false;
    }

    /**
     * 获取用户App权限,合并角色权限最终权限
     * @param userId
     * @return
     */
    public static List<AppMenu> getUserAppAuthForCache(int userId)
    {
        String key = RedisCache.CachePrex + "_getUserAppAuth_" + userId;
        List<AppMenu> data = (List<AppMenu>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getUserAppMenuList(userId);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public static List<ThirdApp> getUserThirdAppAuthForCache(int userId)
    {
        String key = RedisCache.CachePrex + "_getUserThirdAppAuthForCache_" + userId;
        List<ThirdApp> data = (List<ThirdApp>) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getUserThirdAppList(userId);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }
        }
        return data;
    }

    public static void clearUserAuthCache(int userId)
    {
        String key = RedisCache.CachePrex + "_getUserAppAuth_" + userId;
        RedisCache.getInstance().removeObject(key);

        key = RedisCache.CachePrex + "_getUserThirdAppAuthForCache_" + userId;
        RedisCache.getInstance().removeObject(key);
    }

    /**
     * 计算用户应用授权，需要合并角色权限
     * @param userId
     * @return
     */
    private static List<AppMenu> getUserAppMenuList(int userId)
    {
        List<Integer> itemAuthIds = new ArrayList<>();
        PurviewUser purviewUser = handler.purviewUserService.getByUserId(userId);
        if(purviewUser!=null)
        {
            itemAuthIds = Arrays.stream(purviewUser.getItempurviews().split(",")).map(x->TypeConverter.strToInt(x)).collect(Collectors.toList());
        }
        List<PurviewRoleUser> list = handler.purviewRoleUserService.getListByUserId(userId);
        for(PurviewRoleUser roleUser: list)
        {
            PurviewRole role = handler.purviewRoleService.getByPkId(roleUser.getRoleid());
            if(role!=null && role.getStatus() == 1)
            {
                itemAuthIds = mergeAuthMenuId(itemAuthIds, role.getItempurviews());
            }
        }
        List<AppMenu> result = new ArrayList<>();
        for(Integer id : itemAuthIds)
        {
            AppMenu appMenu = handler.appMenuService.getByPkId(id);
            if(appMenu!=null)
            {
                result.add(appMenu);
            }
        }
        return result;
    }

    /**
     * 计算用户应用授权，需要合并角色权限
     * @param userId
     * @return
     */
    private static List<ThirdApp> getUserThirdAppList(int userId)
    {
        List<String> appAuthIds = new ArrayList<>();
        PurviewUser purviewUser = handler.purviewUserService.getByUserId(userId);
        if(purviewUser!=null)
        {
            appAuthIds = Arrays.stream(purviewUser.getModulepurviews().split(",")).collect(Collectors.toList());
        }
        List<PurviewRoleUser> list = handler.purviewRoleUserService.getListByUserId(userId);
        for(PurviewRoleUser roleUser: list)
        {
            PurviewRole role = handler.purviewRoleService.getByPkId(roleUser.getRoleid());
            if(role!=null && role.getStatus() == 1)
            {
                appAuthIds = mergeAuthAppKey(appAuthIds, role.getModulepurviews());
            }
        }
        List<ThirdApp> result = new ArrayList<>();
        for(String id : appAuthIds)
        {
            ThirdApp thirdApp = handler.thirdAppService.getThirdAppForCache(id);
            if(thirdApp!=null)
            {
                result.add(thirdApp);
            }
        }
        return result;
    }

    /**
     * 合并权限
     * @param oriList
     * @param itemAuthIds
     * @return
     */
    private static List<Integer> mergeAuthMenuId(List<Integer> oriList, String itemAuthIds)
    {
        List<Integer> list = Arrays.stream(itemAuthIds.split(",")).map(x->TypeConverter.strToInt(x)).collect(Collectors.toList());
        for(Integer i : list)
        {
            if(!oriList.contains(i))
            {
                oriList.add(i);
            }
        }
        return oriList;
    }

    private static List<String> mergeAuthAppKey(List<String> oriList, String appAuthIds)
    {
        List<String> list = Arrays.stream(appAuthIds.split(",")).collect(Collectors.toList());
        for(String i : list)
        {
            if(!oriList.contains(i))
            {
                oriList.add(i);
            }
        }
        return oriList;
    }
}
