package com.easybasic.component.jwt;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.component.Utils.CookieUtil;
import com.easybasic.component.Utils.RedisCache;
import com.easybasic.component.Utils.TypeConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class LoginUtil {

    public static UserLoginProperty getCurrentLoginUserProperty()
    {
        try{
            int userId = TypeConverter.objectToInt(CookieUtil.getCookie(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(), "easy_basic_uid"));
            String key = RedisCache.CachePrex + "_easy_basic_uid_" + userId;
            RedisLogin redisLogin = (RedisLogin)RedisCache.getInstance().getObject(key);
            if(redisLogin!=null)
            {
                return redisLogin.getUserLoginProperty();
            }
        }
        catch (Exception ex)
        {

        }
        return null;
    }

    /**
     * 检查当前是否有帐号登录
     * @param request
     * @return
     */
    public static boolean checkIsLogin(HttpServletRequest request)
    {
        String token = CookieUtil.getCookie(request, "easy_basic_token");
        String uid = CookieUtil.getCookie(request, "easy_basic_uid");
        //token不存在
        if(StringUtils.isEmpty(token)) {
            return false;
        }
        if(StringUtils.isEmpty(uid)){
            return false;
        }
        Login login = JWTUtil.unsign(token, Login.class);
        //解密token后的loginId与用户传来的loginId判断是否一致
        if(null == login || !StringUtils.equals(login.getUid(), uid)){
            return false;
        }
        String key = RedisCache.CachePrex + "_easy_basic_uid_" + uid;
        //验证登录时间
        RedisLogin redisLogin = (RedisLogin)RedisCache.getInstance().getObject(key);
        if(null == redisLogin){
            return false;
        }

        if(!StringUtils.equals(token, redisLogin.getToken())){
            return false;
        }
        //系统时间>有效期（说明已经超过有效期）
        if (System.currentTimeMillis() > redisLogin.getRefTime()) {
            return false;
        }
        //重新刷新有效期
        redisLogin = new RedisLogin(uid, token, System.currentTimeMillis() + 60L* 1000L* 30L, redisLogin.getUserLoginProperty());
        RedisCache.getInstance().putObject(key , redisLogin);
        return true;
    }
}
