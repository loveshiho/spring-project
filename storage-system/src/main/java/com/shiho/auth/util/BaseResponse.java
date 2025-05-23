package com.shiho.auth.util;

import java.io.Serializable;

/**
 * @Author: zhong
 * @Date: 16/04/2025
 */
public class BaseResponse<T> implements Serializable {
    /**
     * 响应状态码
     */
    private String code;
    /**
     * 响应结果描述
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;

    private boolean success;

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMsg(ResultCode.SUCCESS.getMessage());
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    /**
     * 失败返回：只有一个参数message
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> fail(String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.ERROR.getCode());
        response.setMsg(message);
        return response;
    }

    /**
     * 失败返回：响应码从外部获取
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> fail(String code, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(code);
        response.setMsg(message);
        return response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}