package com.easybasic.component.jwt;

import java.io.Serializable;

public class Login implements Serializable {
    /**
     * 用户id
     */
    private String uid;

    /**
     * 登录用户名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String password;

    public Login(){
        super();
    }

    public Login(String uid, String loginName, String password){
        this.uid = uid;
        this.loginName = loginName;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
