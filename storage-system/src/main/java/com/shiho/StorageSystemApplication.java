package com.shiho;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: zhong
 * @Date: 16/04/2025
 */
@SpringBootApplication
@MapperScan("com.shiho.**.mapper")
public class StorageSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageSystemApplication.class, args);
    }
}