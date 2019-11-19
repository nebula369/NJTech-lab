package com.easybasic.component;

import com.easybasic.basic.component.AppAuthHandler;
import com.easybasic.basic.model.AppModule;
import com.easybasic.basic.model.LoginLog;
import com.easybasic.basic.model.OpLog;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.AppModuleService;
import com.easybasic.basic.service.LoginLogService;
import com.easybasic.basic.service.OpLogService;
import com.easybasic.component.Utils.CookieUtil;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.component.menu.MenuConfigHandler;
import com.easybasic.component.menu.dto.Menu;
import com.easybasic.component.menu.dto.MenuConfig;
import com.easybasic.kaoqin.model.OperatLog;
import com.easybasic.kaoqin.service.OperatLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OpLogService opLogService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private AppModuleService appModuleService;
    @Resource
    private OperatLogService operatlogservice;

    protected String setPageCommonModel(Model model, String appModule, String validateUrl, boolean index)
    {
        MenuConfig menuConfig = MenuConfigHandler.getMenuConfig(appModule);
        menuConfig = AppAuthHandler.initUserMenuData(menuConfig);
        model.addAttribute("menuConfig", menuConfig);
        model.addAttribute("title", menuConfig.getName());
        model.addAttribute("user", LoginUtil.getCurrentLoginUserProperty().CurrentUser);
        String result = "";
        if(index) {
            if (menuConfig != null && menuConfig.getMenuList().size() > 0) {
                Menu menu = menuConfig.getMenuList().get(0);
                if (menu.getSubMenuList().size() > 0) {
                    result = menu.getSubMenuList().get(0).getHref();
                }
            }
        }
        else
        {
            if(AppAuthHandler.isCurrentUrlInAuth(validateUrl))
            {
                result = validateUrl;
            }
        }
        return result;
    }

    protected String setPageCommonModelForNoAuth(Model model, String appModule, String validateUrl, boolean index)
    {
        MenuConfig menuConfig = MenuConfigHandler.getMenuConfig(appModule);
        model.addAttribute("menuConfig", menuConfig);
        model.addAttribute("title", menuConfig.getName());
        model.addAttribute("user", LoginUtil.getCurrentLoginUserProperty().CurrentUser);
        String result = "";
        if(index) {
            if (menuConfig != null && menuConfig.getMenuList().size() > 0) {
                Menu menu = menuConfig.getMenuList().get(0);
                if (menu.getSubMenuList().size() > 0) {
                    result = menu.getSubMenuList().get(0).getHref();
                }
            }
        }
        else
        {
            result = validateUrl;
        }
        return result;
    }

    /**
     * 把浏览器参数转化放到Map集合中
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    protected Map<String, Object> getParam(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        String method = request.getMethod();
        Enumeration<?> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            if(key!=null){
                if (key instanceof String) {
                    String value = request.getParameter(key.toString());
                    if("GET".equals(method)){//前台encodeURIComponent('我们');转码后到后台还是ISO-8859-1，所以还需要转码
                        try {
                            value =new String(value.getBytes("ISO-8859-1"),"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    paramMap.put(key.toString(), value);
                }
            }
        }
        return paramMap;
    }

    //将数据刷新写回web端
    protected void flushResponse(HttpServletResponse response, String responseContent) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("GBK");
            // 针对ajax中页面编码为GBK的情况，一定要加上以下两句
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/html;charset=UTF-8");
            writer = response.getWriter();
            if (responseContent==null || "".equals(responseContent) || "null".equals(responseContent)) {
                writer.write("");
            } else {
                writer.write(responseContent);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public HttpServletResponse getResponse() {
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    protected int getCurrentPageSize()
    {
        String data = CookieUtil.getCookie(getRequest(),"currentPageSize");
        return TypeConverter.strToInt(data, 20);
    }



    protected String getUUID() {
        return UUID.randomUUID().toString();
    }

    protected String getFlag(int level, Boolean isLast)
    {
        String result = "";
        for (int i = 0; i < level - 1; i++)
        {
            result += "┃";
        }
        if (level != 0)
        {
            if (isLast)
            {
                result += "┗";
            }
            else
            {
                result += "┣";
            }
        }
        return result;
    }

    protected void setOpLog(String code, String opmodule, String opremark)
    {
        AppModule appModule = appModuleService.getByCode(code);
        OpLog opLog = new OpLog();
        opLog.setType(0);
        opLog.setCode(code);
        opLog.setOpapp(appModule.getName());
        opLog.setOpmodule(opmodule);
        opLog.setOpremark(opremark);
        opLog.setOpip(ToolsUtil.getRequestIP());
        opLog.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        opLog.setUsername(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getName());
        opLog.setCreatetime(new Date());
        opLog.setStatus(1);
        opLogService.insert(opLog);
    }

    protected void setLoginLog(String opmodule, String opremark, int userId, String userName)
    {
        LoginLog opLog = new LoginLog();
        opLog.setLoginmodule(opmodule);
        opLog.setRemark(opremark);
        opLog.setLoginip(ToolsUtil.getRequestIP());
        opLog.setUserid(userId);
        opLog.setUsername(userName);
        opLog.setCreatetime(new Date());
        opLog.setStatus(1);
        loginLogService.insert(opLog);
    }

    /**
     * @Description: 记录考勤日志相关
     * @param: [tablenames：表名称, code：表所属于id, type：1新增2修改3删除]
     * @return: void
     * @auther: tangy
     * @date: 2019/5/27 0027 9:27
     */
    public void SetKaoQinOpLog(String tablenames,int code, int type)
    {
        OperatLog operatlog  =new OperatLog();
        operatlog.setTablenames(tablenames);
        operatlog.setCode(code);
        operatlog.setType(type);
        operatlog.setOperattime(new Date());
        operatlogservice.insert(operatlog);
    }
    protected void initUnitList(List<Unit> list)
    {
        for (Unit item : list)
        {
            item.setName(getFlag(item.getLevel(), item.getLast()) + item.getName());
            if(item.getType()==1)
            {
                item.setName("[学]" + item.getName());
            }
            else
            {
                item.setName("[单]" + item.getName());
            }
        }
    }

}
