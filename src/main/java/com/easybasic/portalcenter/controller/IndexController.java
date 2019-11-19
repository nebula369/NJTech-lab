package com.easybasic.portalcenter.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.component.AppAuthHandler;
import com.easybasic.basic.model.*;
import com.easybasic.basic.service.AppModuleService;
import com.easybasic.basic.service.OnlineUserService;
import com.easybasic.basic.service.ThirdAppService;
import com.easybasic.basic.service.UserService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.profile.service.InboxService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private AppModuleService appModuleService;
    @Resource
    private ThirdAppService thirdAppService;
    @Resource
    private OnlineUserService onlineUserService;

    @RequestMapping(value = "/manage/home", method = RequestMethod.GET)
    public String portalCenterHome(Model model)
    {
        model.addAttribute("title", "个人桌面");
        model.addAttribute("user", LoginUtil.getCurrentLoginUserProperty().CurrentUser);
        List<AppMenu> appMenuList = AppAuthHandler.getUserAppAuthForCache(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        List<ThirdApp> thirdAppList = AppAuthHandler.getUserThirdAppAuthForCache(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        List<AppModule> appModuleList = appModuleService.getAppModuleListForCache();
        if(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid()!=1) {
            if(LoginUtil.getCurrentLoginUserProperty().isUnitManager())
            {
                appModuleList = appModuleList.stream().filter(x -> x.getUsetype()==1 || x.getUsetype()==2).collect(Collectors.toList());
            }
            else {
                appModuleList = appModuleList.stream().filter(x -> x.getUsetype()==2 || appMenuList.stream().anyMatch(y -> y.getAppid().intValue() == x.getPkid().intValue())).collect(Collectors.toList());
            }
        }
        else
        {
            thirdAppList = thirdAppService.getThirdAppListForCache();
            appModuleList = appModuleList.stream().filter(x->x.getStatus() == 1).collect(Collectors.toList());
        }
        thirdAppList = thirdAppList.stream().filter(x->x.getStatus()==1).collect(Collectors.toList());
        model.addAttribute("appList", appModuleList);
        model.addAttribute("thirdAppList", thirdAppList);
        return "/manage/home";
    }

    @RequestMapping(value = "/manage/platform", method = RequestMethod.GET)
    public String platform(Model model)
    {
        model.addAttribute("title", "平台信息");
        model.addAttribute("user", LoginUtil.getCurrentLoginUserProperty().CurrentUser);
        return "/manage/platform";
    }

    @RequestMapping(value = "/manage/editmypwd", method = RequestMethod.GET)
    public String editMyPwd(Model model)
    {
        model.addAttribute("user", LoginUtil.getCurrentLoginUserProperty().CurrentUser);
        return "/manage/editmypwd";
    }

    @RequestMapping(value = "/manage/doeditmypwd", method = RequestMethod.POST)
    @ResponseBody
    public String doEditMyPwd(String oldPwd, String newPwd)
    {
        User user = userService.getByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        user.setPassword(ToolsUtil.getMd5(newPwd));
        userService.update(user);
        return "1";
    }

    @RequestMapping(value = "/manage/ssoLogin", method = RequestMethod.GET)
    public String ssoLogin(String appKey, String auth, Model model)
    {
        if(StringUtils.isEmpty(appKey))
        {
            model.addAttribute("msg","第三方应用标识为空");
            return "/error/error";
        }
        ThirdApp thirdApp = thirdAppService.getThirdAppForCache(appKey);
        if(thirdApp == null)
        {
            model.addAttribute("msg","第三方应用未注册");
            return "/error/error";
        }
        if(thirdApp.getStatus() == 0)
        {
            model.addAttribute("msg","第三方应用未激活");
            return "/error/error";
        }
        String validateAuth = ToolsUtil.getMd5(thirdApp.getAppsecret() + thirdApp.getAppkey());
        if (!auth.equalsIgnoreCase(validateAuth)) {
            model.addAttribute("msg","auth验证失败");
            return "/error/error";
        }
        if(!AppAuthHandler.isHaveThirdAppAuth(appKey, LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid()))
        {
            model.addAttribute("msg","当前用户没有权限访问该应用");
            return "/error/error";
        }
        OnlineUser onlineUser = onlineUserService.getByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        if(onlineUser!=null)
        {
            String url = thirdApp.getAppurl() + "?ticket=" + onlineUser.getCode();
            return "redirect:" + url;
        }
        else
        {
            model.addAttribute("msg","获取当前登录用户失败");
            return "/error/error";
        }
    }

}
