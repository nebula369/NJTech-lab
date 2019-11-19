package com.easybasic.service.dto;

public enum ResponseCode {

    RESPONSE_CODE_SUCCESS(1000, "成功"),
    TICKET_NOT_NULL(1001,"验证ticket不能为空"),
    AUTH_NOT_UNAUTHORIZED(1002,"auth验证错误"),
    THIRDAPP_NOT_REGISTER(1003, "第三方应用未注册"),
    THIRDAPP_NOT_ACTIVE(1004, "第三方应用未激活"),
    USERID_NOT_NULL(1005,"用户帐号验证不通过."),
    FORBIDDEN_USER_LOGIN(1006, "用户被禁止登录"),
    USER_NOT_LOGIN(1007, "用户未登录"),
    USER_NOT_EXIST(1008, "用户不存在"),
    USER_APP_NOT_AUTH(1009, "用户未授权"),
    EXCEPTION(1010, "服务调用发生异常"),
    MSGSEND_FROMUSER_NOT_EXIST(1011, "发送消息时，发件人用户不存在"),
    MSGSEND_TOUSER_NOT_EXIST(1012, "发送消息时，收件人用户不存在");

    // 成员变量
    private int code; //状态码
    private String message; //返回消息

    // 构造方法
    private ResponseCode(int code,String message) {
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

    public static ResponseDTO buildResponseData(ResponseCode responseCode, Object data) {
        return new ResponseDTO(responseCode.getCode(),responseCode.getMessage(),data);
    }

}
