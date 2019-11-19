package com.easybasic.component.jwt;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.component.Utils.CookieUtil;
import com.easybasic.component.Utils.RedisCache;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("utf-8");
        String token = CookieUtil.getCookie(request, "easy_basic_token");
        String uid = CookieUtil.getCookie(request, "easy_basic_uid");
        //token不存在
        if(StringUtils.isEmpty(token)) {
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.LOGIN_TOKEN_NOT_NULL, false);
            responseMessage(response, request, writer, responseVO);
            return false;
        }
        if(StringUtils.isEmpty(uid)){
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.USERID_NOT_NULL, false);
            responseMessage(response, request, writer, responseVO);
            return false;
        }

        Login login = JWTUtil.unsign(token, Login.class);
        //解密token后的loginId与用户传来的loginId判断是否一致
        if(null == login || !StringUtils.equals(login.getUid(), uid)){
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.USERID_NOT_UNAUTHORIZED, false);
            responseMessage(response, request, writer, responseVO);
            return false;
        }

        //验证登录时间
        String key = RedisCache.CachePrex + "_easy_basic_uid_" + uid;
        RedisLogin redisLogin = (RedisLogin)RedisCache.getInstance().getObject(key);
        if(null == redisLogin){
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.RESPONSE_CODE_UNLOGIN_ERROR, false);
            responseMessage(response, request, writer, responseVO);
            return false;
        }

        if(!StringUtils.equals(token, redisLogin.getToken())){
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.USERID_NOT_UNAUTHORIZED, false);
            responseMessage(response, request, writer, responseVO);
            return false;
        }
        //系统时间>有效期（说明已经超过有效期）
        /*
        if (System.currentTimeMillis() > redisLogin.getRefTime()) {
            writer = response.getWriter();
            ResponseVO responseVO = LoginResponseCode.buildEnumResponseVO(LoginResponseCode.LOGIN_TIME_EXP, false);
            responseMessage(response, request, writer, responseVO);
            return false;
        }
        */
        //重新刷新有效期
        redisLogin = new RedisLogin(uid, token, System.currentTimeMillis() + 60L* 1000L* 30L * 1000L, redisLogin.getUserLoginProperty());
        RedisCache.getInstance().putObject(key , redisLogin);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {


    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    private void responseMessage(HttpServletResponse response, HttpServletRequest request, PrintWriter out, ResponseVO responseVO) {
        //response.setContentType("application/json; charset=utf-8");
        //JSONObject result = new JSONObject();
        //result.put("result", responseVO);
        //out.print(result);
        //out.flush();
        //out.close();
        try {
            //request.getRequestDispatcher("/manage/login").forward(request, response);
            response.sendRedirect("/login");
        }
        catch (Exception ex)
        {

        }
    }
}
