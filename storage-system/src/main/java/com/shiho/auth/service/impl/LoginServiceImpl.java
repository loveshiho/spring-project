package com.shiho.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shiho.auth.entity.User;
import com.shiho.auth.mapper.UserMapper;
import com.shiho.auth.service.LoginService;
import com.shiho.auth.util.CaptchaMemoryCache;
import com.shiho.auth.util.Constants;
import com.shiho.auth.util.JwtUtils;
import com.shiho.auth.util.TokenMemoryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhong
 * @Date: 16/04/2025
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private CaptchaMemoryCache captchaMemoryCache;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenMemoryCache tokenMemoryCache;
    @Override
    public String login(String keyword, String password, String code, String uuid) {
        String mkey = Constants.CAPTCHA_CODE_KEY + uuid;
        String mcode = captchaMemoryCache.get(mkey);
        if (mcode == null) throw new RuntimeException("验证码已过期或不存在");
        if (!mcode.equalsIgnoreCase(code)) throw new RuntimeException("验证码错误");
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", keyword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.error("【管理模块-登录校验】用户名不存在 username = {}", keyword);
            throw new RuntimeException("用户名不存在");
        }
        log.info("【管理模块-登录校验】查询成功 user = {}", user);
        if (!user.getPassword().equals(password)) {
            log.error("【管理模块-登录校验】密码错误 password = {}", password);
            throw new RuntimeException("密码错误");
        }
        String jwt = JwtUtils.createJwt(user.getId().toString());
        tokenMemoryCache.put(Constants.JWT_TOKEN_KEY + user.getId(), "ok");
        log.info("【管理模块-登录校验】登录成功 userId = {}", user.getId());
        return jwt;
    }
}
