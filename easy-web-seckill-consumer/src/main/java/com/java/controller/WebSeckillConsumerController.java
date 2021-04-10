package com.java.controller;

/**
 * Description:	   <br/>
 * Date:     2021/03/28 20:59 <br/>
 *
 * @author Aaron CHEN
 * @see
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/webSeckillConsumer")
@Controller
public class WebSeckillConsumerController {

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
     * 开始秒杀
     *
     * @param seckillId 秒杀主键id
     * @param userId    用户主键id
     * @return
     */
    @RequestMapping("/getSeckillByConsumer.do")
    @ResponseBody
    public Map<String, Object> getBannerByConsumer(Long seckillId, Long userId) {
        Map<String, Object> resultMap = restTemplate.getForObject("http://easy-web-seckill-provider/processSeckill.do/" + seckillId + "/" + userId, Map.class);
        return resultMap;
    }

}
