package com.shiho.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhong
 * @Date: 10/05/2025
 */
@RestController
@Slf4j
public class HiController {
    @RequestMapping("/ok")
    public String ok() {
        log.info("【管理模块-请求测试】成功");
        return "hello shiho~";
    }
}
