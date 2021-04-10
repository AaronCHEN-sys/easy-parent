package com.java.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Description:	   <br/>
 * Date:     2021/03/28 20:57 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@SpringBootApplication(scanBasePackages = "com.java.controller")
//启动Eureka客户端
@EnableEurekaClient
//启用负载均衡客户端
@EnableDiscoveryClient
public class WebSeckillConsumerStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSeckillConsumerStartApplication.class);


    }
}
