package com.java.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/04/13 20:59 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public interface OrderService {


    /**
     * 将秒杀信息保存到RabbitMQ中
     *
     * @param userId    用户主键id
     * @param seckillId 秒杀主键id
     * @return
     */
    String sendDataToRabbitMQ(Long userId, Long seckillId);

    @Transactional(readOnly = false)
    void saveOrder(Map<String, Object> parameterMap);
}
