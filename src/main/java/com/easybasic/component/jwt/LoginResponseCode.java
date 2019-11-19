package com.easybasic.component.jwt;

import java.util.HashMap;
import java.util.Map;

public enum LoginResponseCode {
    USERID_NOT_NULL(3001,"用户帐号错误."),
    LOGIN_TOKEN_NOT_NULL(3002,"登录token不能为空."),
    USERID_NOT_UNAUTHORIZED(3003, "第三方应用token或ID验证不通过"),
    RESPONSE_CODE_UNLOGIN_ERROR(421, "未登录异常"),
    LOGIN_TIME_EXP(3004, "登录时间超长，请重新登录"),
    FORBIDDEN_USER_LOGIN(3005, "用户被禁止登录"),
    VERIFYCODE_NOT_UNAUTHORIZED(3007,"验证码验证不通过"),
    RESPONSE_CODE_LOGIN_SUCCESS(3000, "登录验证成功"),
    REQUEST_URL_NOT_SERVICE(3006,"访问路径异常");

    // 成员变量
    private int code; //状态码
    private String message; //返回消息

    // 构造方法
    private LoginResponseCode(int code,String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public static ResponseVO buildEnumResponseVO(LoginResponseCode responseCode, Object data) {
        return new ResponseVO(responseCode.getCode(),responseCode.getMessage(),data);
    }

    public static Map<String, Object> buildReturnMap(LoginResponseCode responseCode, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", responseCode.getCode());
        map.put("message", responseCode.getMessage());
        map.put("data", data);
        return map;
    }
}
