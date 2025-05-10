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
public class TokenMemoryCache {
    private static final long EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L; // 30 d
    private static class TokenItem {
        String code;
        long expireAt;

        TokenItem(String code, long expireAt) {
            this.code = code;
            this.expireAt = expireAt;
        }
    }
    private final ConcurrentHashMap<String, TokenItem> cache = new ConcurrentHashMap<>();
    public void put(String key, String code) {
        long expireAt = System.currentTimeMillis() + EXPIRE_TIME;
        cache.put(key, new TokenItem(code, expireAt));
    }
    public String get(String key) {
        TokenItem item = cache.get(key);
        if (item == null || System.currentTimeMillis() > item.expireAt) {
            cache.remove(key);
            return null;
        }
        return item.code;
    }
    @Scheduled(fixedDelay = 30 * 24 * 60 * 60 * 1000L) // 每 30 d 清理一次
    public void cleanUp() {
        long now = System.currentTimeMillis();
        for (Map.Entry<String, TokenItem> entry : cache.entrySet()) {
            if (entry.getValue().expireAt < now) {
                cache.remove(entry.getKey());
            }
        }
    }
    public void remove(String key) {
        cache.remove(key);
    }
}
