package com.java.service.impl;

import com.java.exceptions.SeckillException;
import com.java.mapper.WebSeckillProvider1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/03/26 21:28 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Service
public class WebSeckillProvider1ServiceImpl implements com.java.service.WebSeckillProvider1Service {

    @Autowired
    private WebSeckillProvider1Mapper webSeckillProvider1Mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void doSeckillStep(Long seckillId, Long userId) throws SeckillException {
        //秒杀主键id为空
        if (seckillId == null) {
            throw new SeckillException("秒杀的商品不存在!");
        }
        //1.判断商品是否已经处于秒杀状态
        Map<String, Object> SeckillGoodsMap = webSeckillProvider1Mapper.selectSeckillGoodsBySeckillId(seckillId);
        String seckillStatus = (String) SeckillGoodsMap.get("seckill_status");

        if ("0".equals(seckillStatus)) {
            //秒杀未开始
            throw new SeckillException("秒杀未开始!");
        }

        if ("2".equals(seckillStatus)) {
            //秒杀已开始
            throw new SeckillException("秒杀已开始!");
        }

        //正在秒杀
        ListOperations listOperations = redisTemplate.opsForList();
        //获取正在秒杀商品的商品主键id
        Long goodsId = (Long) listOperations.leftPop("seckill_goods_" + seckillId);
        //判断商品是否已经秒杀完成
        if (goodsId != null) {
            //可以秒杀该商品
            SetOperations setOperations = redisTemplate.opsForSet();
            //判断当前用户是否已经秒杀过该商品
            Boolean member = setOperations.isMember("seckill_users_" + seckillId, userId);
            if (member) {
                //当前用户已经秒杀过该商品
                //重新将秒杀信息存放到Redis数据库
                listOperations.rightPush("seckill_goods_" + seckillId, goodsId);
                throw new SeckillException("不能重复秒杀该商品！");
            } else {
                //当前用户未秒杀过该商品，秒杀成功
                System.out.println("WebSeckillProvider1ServiceImpl-----user:" + userId + ",秒杀成功！");
                //将当前成功秒杀商品的用户主键id存放到Redis数据库
                setOperations.add("seckill_users_" + seckillId, userId);
            }
        } else {
            //商品已经被秒杀完
            
            throw new SeckillException("商品已经被秒杀完！");
        }
    }

}
