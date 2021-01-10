package com.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/01/06 21:20 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@RequestMapping("/webBannerConsumer")
@Controller
public class WebBannerConsumerController {


    //注入负载均衡的工具类
    @Bean
    //开启负载均衡
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询前台轮播信息
     *
     * @return
     */
    @RequestMapping("/getBannerByConsumer.do")
    @ResponseBody
    public List<Map<String, Object>> getBannerByConsumer() {
        return restTemplate.getForObject("http://easy-web-banner-provider/getWebBanner.do", List.class);
    }

}
