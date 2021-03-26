package com.java.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Description:	   <br/>
 * Date:     2020/12/01 15:33 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@SpringBootApplication(scanBasePackages = {"com.java.controller", "com.java.service.impl", "com.java.tasks"})
@MapperScan(basePackages = "com.java.mapper")
//开启缓存
@EnableCaching
//开启注册中心Eureka客户端
@EnableEurekaClient
//开启任务调度
@EnableScheduling
public class WebStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebStartApplication.class);
    }
}
