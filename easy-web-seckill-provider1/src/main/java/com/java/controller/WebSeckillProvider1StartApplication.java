package com.java.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description:	   <br/>
 * Date:     2021/03/22 20:56 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@SpringBootApplication(scanBasePackages = {"com.java.controller", "com.java.service.impl"})
@MapperScan(basePackages = "com.java.mapper")
//启用负载均衡客户端
@EnableDiscoveryClient
public class WebSeckillProvider1StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSeckillProvider1StartApplication.class);
    }
}
