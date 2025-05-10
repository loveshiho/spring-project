package com.shiho.auth.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhong
 * @Date: 16/04/2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo {
    private String keyword;
    private String password;
    private String code;
    private String uuid;
}
