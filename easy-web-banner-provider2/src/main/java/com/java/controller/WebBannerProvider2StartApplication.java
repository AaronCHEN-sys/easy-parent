package com.java.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Description:	   <br/>
 * Date:     2021/01/06 20:15 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@SpringBootApplication(scanBasePackages = {"com.java.controller", "com.java.service.impl"})
//启动Eureka客户端
@EnableEurekaClient
//启用负载均衡客户端
@EnableDiscoveryClient
@MapperScan(basePackages = "com.java.mapper")
public class WebBannerProvider2StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebBannerProvider2StartApplication.class);
    }
}
