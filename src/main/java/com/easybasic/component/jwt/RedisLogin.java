package com.easybasic.component.jwt;

import java.io.Serializable;

public class RedisLogin implements Serializable {
    /**
     * 用户id
     */
    private String uid;

    /**
     * jwt生成的token信息
     */
    private String token;

    /**
     * 登录或刷新应用的时间
     */
    private long refTime;

    private UserLoginProperty userLoginProperty;

    public RedisLogin(){

    }

    public RedisLogin(String uid, String token, long refTime, UserLoginProperty userLoginProperty){
        this.uid = uid;
        this.token = token;
        this.refTime = refTime;
        this.userLoginProperty = userLoginProperty;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public long getRefTime() {
        return refTime;
    }
    public void setRefTime(long refTime) {
        this.refTime = refTime;
    }

    public UserLoginProperty getUserLoginProperty(){return userLoginProperty;}
    public void setUserLoginProperty(UserLoginProperty userLoginProperty){this.userLoginProperty = userLoginProperty;}

}
