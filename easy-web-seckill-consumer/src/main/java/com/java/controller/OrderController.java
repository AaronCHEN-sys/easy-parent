package com.java.controller;

import com.java.service.OrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Description:	   订单结算模块, 自动从消息队列中获取数据, 然后将订单信息存放到数据库中<br/>
 * Date:     2021/04/13 21:28 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Component
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "queue_order"),
                    exchange = @Exchange(value = "exchange_order", type = "fanout")
            )
    )

    @RabbitHandler
    public void handleRabbitMQMessage(@Payload Map<String, Object> dataMap, Channel channel, @Headers Map<String, Object> headers) {

        try {
            //1、对接支付宝/微信
            Thread.sleep(5000);
            //2、从消息队列("queue-order")取出dataMap数据
//            String orderNo = (String) dataMap.get("orderNo");
//            Long userId = (Long) dataMap.get("userId");
            //3、将dataMap等数据保存到数据库的订单表中--->状态已经创建
            orderService.saveOrder(dataMap);
            //4、手动确认正确的从消息队列中取出数据，并且处理完毕
            Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(tag, false);//消息队列确认
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
