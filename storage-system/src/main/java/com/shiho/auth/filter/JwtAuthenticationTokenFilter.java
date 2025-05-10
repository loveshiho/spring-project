package com.shiho.auth.filter;

import com.shiho.auth.util.Constants;
import com.shiho.auth.util.JwtUtils;
import com.shiho.auth.util.TokenMemoryCache;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zhong
 * @Date: 10/05/2025
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenMemoryCache tokenMemoryCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*1 获取token*/
        String token = request.getHeader("Token");
        /*2 判断 token 是否为空，为空直接放行*/
        log.info("【管理模块-用户请求】url = {}", request.getRequestURI());
        if ((token == null || token == "") && (request.getRequestURI().equals("/auth/login") || request.getRequestURI().equals("/captchaImage"))) {
            log.info("【管理模块-用户请求】token 为空，直接放行 url = {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            /*return 的作用是返回响应的时候，避免走下面的逻辑*/
            return;
        }
        /*3 解析 jwt*/
        Claims claims = null;
        try {
            claims = JwtUtils.parseJwt(token);
        } catch (Exception e) {
            log.error("【管理模块-用户请求】非法 token = {}", token);
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":\"401\",\"msg\":\"" + "非法 token" + "\", \"success\":false}");
            return;
        }
        /*4 获取userId*/
        String userId = claims.getSubject();
        String code = tokenMemoryCache.get(Constants.JWT_TOKEN_KEY + userId);
        if (code == null) {
            log.error("【管理模块-用户请求】用户未登录");
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":\"401\",\"msg\":\"" + "用户未登录" + "\", \"success\":false}");
            return;
        }
        /*7 放行*/
        log.info("【管理模块-用户请求】请求放行 userId = {}", userId);
        filterChain.doFilter(request, response);
    }
}
