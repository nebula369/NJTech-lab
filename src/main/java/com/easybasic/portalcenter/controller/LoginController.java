package com.easybasic.portalcenter.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.component.AppAuthHandler;
import com.easybasic.basic.model.OnlineUser;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.model.User;
import com.easybasic.basic.service.OnlineUserService;
import com.easybasic.basic.service.UnitService;
import com.easybasic.basic.service.UserService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.CookieUtil;
import com.easybasic.component.Utils.RedisCache;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.jwt.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class LoginController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private OnlineUserService onlineUserService;
    @Resource
    private UnitService unitService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index()
    {
        return "redirect:/manage/home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String manageLogin(@RequestParam(value = "appKey", required = false, defaultValue = "")String appKey,
                              @RequestParam(value = "auth", required = false, defaultValue = "")String auth,
                              HttpServletRequest request,
                              Model model)
    {
        if(!StringUtils.isEmpty(appKey))
        {
            //第三方应用访问登录
            if(LoginUtil.checkIsLogin(request))
            {
                return "redirect:/manage/ssoLogin?appKey=" + appKey +"&auth=" + auth;
            }
        }
        model.addAttribute("appKey", appKey);
        model.addAttribute("auth", auth);
        return "/login/login";
    }

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkLogin(String loginName, String loginPwd, String appKey, String auth, HttpSession session)
    {
        Boolean u = Pattern.compile("^[0-9a-zA-Z_@]+$").matcher(loginName.trim()).matches();
        Boolean p = Pattern.compile("^[%=]+$").matcher(loginPwd.trim()).matches();
        if (!u || p)
        {
            return LoginResponseCode.buildReturnMap(LoginResponseCode.USERID_NOT_NULL, null);
        }
        if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(loginPwd))
        {
            return LoginResponseCode.buildReturnMap(LoginResponseCode.USERID_NOT_NULL, null);
        }
        User user = userService.getByLoginName(loginName);
        if(null == user){
            return LoginResponseCode.buildReturnMap(LoginResponseCode.USERID_NOT_NULL, false);
        }
        if (user.getStatus() != 1 && user.getPkid() != 1)
        {
            return LoginResponseCode.buildReturnMap(LoginResponseCode.FORBIDDEN_USER_LOGIN, null);
        }
        boolean isValidate = userService.validatePassword(loginPwd, user.getPassword());
        if(!isValidate){
            return LoginResponseCode.buildReturnMap(LoginResponseCode.USERID_NOT_NULL, false);
        }
        if(isValidate){
            Login login = new Login(user.getPkid().toString(), user.getLoginname(), user.getPassword());
            //给用户jwt加密生成token
            String token = JWTUtil.sign(login, 60L* 1000L* 30L);
            Map<String,Object> result =new HashMap<>();
            result.put("loginToken", token);
            result.put("userId", user.getPkid().toString());
            result.put("user", user);
            //重建用户信息
            this.rebuildLoginUser(user, token);
            setLoginLog("认证中心", "用户"+user.getName()+"（"+user.getLoginname()+"）成功登录平台", user.getPkid(), user.getName());
            return LoginResponseCode.buildReturnMap(LoginResponseCode.RESPONSE_CODE_LOGIN_SUCCESS, result);
        }
        return LoginResponseCode.buildReturnMap(LoginResponseCode.USERID_NOT_NULL, false);
    }

    private void rebuildLoginUser(User user, String token)
    {
        AppAuthHandler.clearUserAuthCache(user.getPkid());

        user.setLastloginip(ToolsUtil.getRequestIP());
        user.setLogincount(user.getLogincount()+1);
        user.setLastlogintime(user.getLogintime());
        user.setLogintime(new Date());
        userService.update(user);

        OnlineUser onlineUser = onlineUserService.getByUserId(user.getPkid());
        if(onlineUser==null) {
            onlineUser = new OnlineUser();
            onlineUser.setUserid(user.getPkid());
            onlineUser.setLoginname(user.getLoginname());
            onlineUser.setName(user.getName());
            onlineUser.setActivetime(new Date());
            onlineUser.setLoginip(ToolsUtil.getRequestIP());
            onlineUser.setLogintime(new Date());
            onlineUser.setCode(java.util.UUID.randomUUID().toString());
            onlineUserService.insert(onlineUser);
        }
        else
        {
            onlineUser.setUserid(user.getPkid());
            onlineUser.setLoginname(user.getLoginname());
            onlineUser.setName(user.getName());
            onlineUser.setActivetime(new Date());
            onlineUser.setLoginip(ToolsUtil.getRequestIP());
            onlineUser.setLogintime(new Date());
            onlineUser.setCode(java.util.UUID.randomUUID().toString());
            onlineUserService.update(onlineUser);
        }

        UserLoginProperty userLoginProperty = new UserLoginProperty();
        userLoginProperty.CurrentUser = user;
        userLoginProperty.LoginIP = ToolsUtil.getRequestIP();
        userLoginProperty.LoginTime = new Date();

        Unit unit = unitService.getByManageUser(user.getPkid());
        if(unit!=null)
        {
            //是单位管理员
            userLoginProperty.setUnitManager(true);
            userLoginProperty.setManageUnit(unit);
        }
        else
        {
            userLoginProperty.setUnitManager(false);
        }

        RedisLogin redisLogin =  new RedisLogin(user.getPkid().toString(), token, System.currentTimeMillis() + 60L* 1000L* 30L, userLoginProperty);
        String key = RedisCache.CachePrex + "_easy_basic_uid_" + user.getPkid();
        RedisCache.getInstance().putObject(key , redisLogin);

        CookieUtil.addCookie(getResponse(),"easy_basic_token", token, 0);
        CookieUtil.addCookie(getResponse(),"easy_basic_uid", user.getPkid().toString(), 0);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String mangeLogout(HttpServletResponse response){

        try {
            User user = LoginUtil.getCurrentLoginUserProperty().CurrentUser;
            CookieUtil.removeCookie(response, "easy_basic_token");
            CookieUtil.removeCookie(response, "easy_basic_uid");
            UserLoginProperty userLoginProperty = LoginUtil.getCurrentLoginUserProperty();
            if (userLoginProperty != null) {
                String key = RedisCache.CachePrex + "_easy_basic_uid_" + user.getPkid();
                RedisCache.getInstance().removeObject(key);
            }
            setLoginLog("退出登录", "用户"+user.getName()+"（"+user.getLoginname()+"）注销登录，退出平台", user.getPkid(), user.getName());
        }
        catch (Exception ex)
        {
            logger.error("退出登录异常", ex);
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/manage/validateOnlineUser", method = RequestMethod.POST)
    @ResponseBody
    public int validateOnlineUser()
    {
        OnlineUser onlineUser = onlineUserService.getByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        if(onlineUser==null) {
            onlineUser = new OnlineUser();
            onlineUser.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
            onlineUser.setLoginname(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getLoginname());
            onlineUser.setName(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getName());
            onlineUser.setActivetime(new Date());
            onlineUser.setLoginip(ToolsUtil.getRequestIP());
            onlineUser.setLogintime(new Date());
            onlineUser.setCode(java.util.UUID.randomUUID().toString());
            onlineUserService.insert(onlineUser);
        }
        else
        {
            onlineUser.setActivetime(new Date());
            onlineUserService.update(onlineUser);
        }
        onlineUserService.deleteByValidate();
        return 1;
    }
}
