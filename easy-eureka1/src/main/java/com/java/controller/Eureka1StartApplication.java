package com.java.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Description:	   <br/>
 * Date:     2020/12/09 21:14 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@SpringBootApplication
//开启注册中心Eureka服务器
@EnableEurekaServer
public class Eureka1StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(Eureka1StartApplication.class);
    }
}
