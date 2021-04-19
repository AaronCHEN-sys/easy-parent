package com.java.service.impl;

import com.java.mapper.OrderMapper;
import com.java.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Description:	   <br/>
 * Date:     2021/04/13 20:56 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String sendDataToRabbitMQ(Long userId, Long seckillId) {
        //1、生成订单编号
        String orderNo = UUID.randomUUID().toString();
        //2、将订单编号、用户ID保存到rabbitMQ中去
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("orderNo", orderNo);
        dataMap.put("userId", userId);
        dataMap.put("seckillId", seckillId);
        rabbitTemplate.convertAndSend("exchange_order", null, dataMap);
        return orderNo;
    }

    @Transactional(readOnly = false)
    @Override
    public void saveOrder(Map<String, Object> parameterMap) {
        //1.查询出秒杀价格
        Float seckillPrice = orderMapper.selectSeckillId((Long) parameterMap.get("seckillId"));
        //2.将数据存放到数据库
        parameterMap.put("orderAmount", seckillPrice);
        orderMapper.insertOrder(parameterMap);
    }

    @Override
    public boolean checkOrder(String orderNo) {
        int i = orderMapper.selectCountOrder(orderNo);
        return i == 1;
    }
}
