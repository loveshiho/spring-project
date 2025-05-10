package com.shiho.auth.controller;

import com.shiho.auth.service.LoginService;
import com.shiho.auth.entity.vo.LoginUserVo;
import com.shiho.auth.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhong
 * @Date: 16/04/2025
 */
@Controller
@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private TokenMemoryCache tokenMemoryCache;

    @RequestMapping("/login")
    public BaseResponse login(@RequestBody LoginUserVo userVo) {
        log.info("【管理模块-登录校验】user = {}", userVo);
        String token = loginService.login(userVo.getKeyword(), userVo.getPassword(), userVo.getCode(), userVo.getUuid());
        log.info("【管理模块-登录校验】登录成功 token = {}", token);
        return BaseResponse.success(ChainedMap.create().set("token", token));
    }
    @RequestMapping("/logout")
    public BaseResponse logout(@RequestHeader("Token") String token) {
        /*登出*/
        // 解析出 userId
        String userId;
        try {
            userId = JwtUtils.parseJwt(token).getSubject();
        } catch (Exception e) {
            return BaseResponse.fail("401", "非法 token");
        }
        // 从内存移除 token
        tokenMemoryCache.remove(Constants.JWT_TOKEN_KEY + userId);
        log.info("【管理模块-退出登录】成功");
        return BaseResponse.success(null);
    }
}
