package com.easybasic.component.jwt;

public class ResponseVO {
    private int code;
    private String message;
    private String version = "v1.0";
    private Object data;

    public String getVersion(){
        return  version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }

    public ResponseVO(){
        super();
    }

    public ResponseVO(int code, String message, Object data){
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
