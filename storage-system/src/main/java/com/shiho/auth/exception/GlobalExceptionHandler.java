package com.shiho.auth.exception;


import com.shiho.auth.util.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 全局异常处理器
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public BaseResponse baseExceptionHandler(RuntimeException e) {
        return BaseResponse.fail(e.getMessage());
    }
}
