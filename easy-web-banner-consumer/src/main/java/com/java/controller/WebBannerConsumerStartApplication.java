package com.java.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Description:	   <br/>
 * Date:     2021/01/06 21:15 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@SpringBootApplication(scanBasePackages = "com.java.controller")
//启动Eureka客户端
@EnableEurekaClient
//启用负载均衡客户端
@EnableDiscoveryClient
//开启过滤器包扫描
@ServletComponentScan(basePackages = "com.java.filters")
public class WebBannerConsumerStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebBannerConsumerStartApplication.class);


    }
}
