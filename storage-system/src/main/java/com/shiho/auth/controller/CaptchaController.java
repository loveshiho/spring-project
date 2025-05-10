package com.shiho.auth.controller;

import com.shiho.auth.util.BaseResponse;
import com.shiho.auth.util.CaptchaMemoryCache;
import com.shiho.auth.util.Constants;
import com.wf.captcha.SpecCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

/**
 * @Author: zhong
 * @Date: 10/05/2025
 */
@RestController
@Slf4j
public class CaptchaController {
    @Autowired
    private CaptchaMemoryCache captchaMemoryCache;
    @RequestMapping("/captchaImage")
    public BaseResponse getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String key = Constants.CAPTCHA_CODE_KEY + uuid;
        String code = specCaptcha.text().toLowerCase();

        // 存到内存中
        captchaMemoryCache.put(key, code);

        HashMap<String, Object> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("img", specCaptcha.toBase64());
        log.info("【管理模块-验证码获取】获取成功 code = {}", code);
        return BaseResponse.success(result);
    }
}
