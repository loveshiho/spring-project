package com.shiho.auth.util;

/**
 * @Author: zhong
 * @Date: 16/04/2025
 */
public enum ResultCode {
    SUCCESS("200", "操作成功"),
    ERROR("500", "操作失败");
    // 自定义状态码
    private String code;
    // 自定义描述
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
