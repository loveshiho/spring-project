package com.shiho.auth.util;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhong
 * @Date: 10/05/2025
 */
@Component
@EnableScheduling
public class CaptchaMemoryCache {
    private static final long EXPIRE_TIME = 600 * 1000L; // 600 秒
    private static class CaptchaItem {
        String code;
        long expireAt;

        CaptchaItem(String code, long expireAt) {
            this.code = code;
            this.expireAt = expireAt;
        }
    }
    private final ConcurrentHashMap<String, CaptchaItem> cache = new ConcurrentHashMap<>();
    public void put(String key, String code) {
        long expireAt = System.currentTimeMillis() + EXPIRE_TIME;
        cache.put(key, new CaptchaItem(code, expireAt));
    }
    public String get(String key) {
        CaptchaItem item = cache.get(key);
        if (item == null || System.currentTimeMillis() > item.expireAt) {
            cache.remove(key);
            return null;
        }
        return item.code;
    }
    @Scheduled(fixedDelay = 5 * 60 * 1000) // 每 5 分钟清理一次
    public void cleanUp() {
        long now = System.currentTimeMillis();
        for (Map.Entry<String, CaptchaItem> entry : cache.entrySet()) {
            if (entry.getValue().expireAt < now) {
                cache.remove(entry.getKey());
            }
        }
    }
}