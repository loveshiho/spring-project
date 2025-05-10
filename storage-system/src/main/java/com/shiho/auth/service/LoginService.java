package com.shiho.auth.service;

/**
 * @Author: zhong
 * @Date: 16/04/2025
 */
public interface LoginService {
    String login(String keyword, String password, String code, String uuid);
}
