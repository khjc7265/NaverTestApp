package com.naver.hyeonjung.navertestapp.network.code;


public enum ResponseCodes {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    CODE_UNKNOWN(10001, "Unknown Response Code"),
    PAGE_IS_MAX(10002,"Invalid start value"),
    SIZE_IS_MAX(10003,"Invalid display value");


    private int code;
    private String desc;

    ResponseCodes(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String description) {
        this.desc = description;
    }
}
