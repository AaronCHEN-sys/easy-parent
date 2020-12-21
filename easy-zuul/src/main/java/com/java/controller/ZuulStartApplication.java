package com.java.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Description:	   <br/>
 * Date:     2020/12/11 20:55 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@SpringBootApplication
//开启注册中心Eureka客户端
@EnableEurekaClient
//开启Zuul网关
@EnableZuulProxy
@EnableDiscoveryClient
public class ZuulStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulStartApplication.class);
    }
}
