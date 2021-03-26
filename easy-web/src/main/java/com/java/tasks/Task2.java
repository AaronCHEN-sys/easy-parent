package com.java.tasks;

import com.java.service.FrontWebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description:	   <br/>
 * Date:     2021/03/24 21:26 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Component
public class Task2 {
    @Autowired
    private FrontWebMenuService frontWebMenuService;

    /**
     * 秒杀步骤一
     * 1.查询出即将开始秒杀的商品信息
     * 2.将秒杀商品信息存放在Redis数据库中
     * 3.修改秒杀商品的状态
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void doSeckillStep1() {
        frontWebMenuService.processSeckill();
        System.out.println("Task2-----秒杀步骤一");
    }

    /**
     * 秒杀结束
     * 1.删除Redis中空的秒杀商品集合
     * 2.修改产品状态
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void completeSeckill() {
        frontWebMenuService.processSeckillwhenCompleted();
        System.out.println("Task2-----秒杀结束");
    }

}
