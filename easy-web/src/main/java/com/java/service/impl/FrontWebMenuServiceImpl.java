package com.java.service.impl;

import com.java.mapper.FrontWebMenuMapper;
import com.java.service.FrontWebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description:	   <br/>
 * Date:     2020/12/01 16:05 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@CacheConfig(cacheNames = {"FrontWebMenuServiceImpl_"})
@Service
public class FrontWebMenuServiceImpl implements FrontWebMenuService {

    @Autowired
    private FrontWebMenuMapper frontWebMenuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Map<String, Object>> findHorizontalWebMenu() {
        try {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Object horizontalWebMenu = valueOperations.get("horizontalWebMenu");
            if (horizontalWebMenu == null) {
                List<Map<String, Object>> horizontalWebMenuList = frontWebMenuMapper.selectHorizontalWebMenu("1");
                //将查询出来的数据保存到Redis中
                valueOperations.set("horizontalWebMenu", horizontalWebMenuList);
                //设置失效时间
                redisTemplate.expire("horizontalWebMenu", 1, TimeUnit.MINUTES);
                return horizontalWebMenuList;
            } else {
                return (List<Map<String, Object>>) horizontalWebMenu;
            }
        } catch (Exception e) {
            System.out.println("FrontWebMenuServiceImpl----------未开启Redis");
            return frontWebMenuMapper.selectHorizontalWebMenu("1");
        }
    }

    @Override
    public List<Map<String, Object>> findAllGoodsDetail() {
        //1.获取所有商品信息, 不包含图片地址信息
        List<Map<String, Object>> productDetailList = frontWebMenuMapper.selectAllProductDetail();
        //2.根据商品主键id获取每个商品的图片地址信息
        for (int i = 0; i < productDetailList.size(); i++) {
            Long goodsId = (Long) productDetailList.get(i).get("goodsId");
            List<String> imageUrlList = frontWebMenuMapper.selectImageUrlByGoodsId(goodsId);
            productDetailList.get(i).put("imageUrlList", imageUrlList);
        }
        return productDetailList;
    }

    @Override
    public List<Map<String, Object>> findAllProductDetailUpdated() {
        //1.获取所有商品信息, 不包含图片地址信息
        List<Map<String, Object>> productDetailList = frontWebMenuMapper.selectAllProductDetailUpdated();
        //2.根据商品主键id获取每个商品的图片地址信息
        for (int i = 0; i < productDetailList.size(); i++) {
            Long goodsId = (Long) productDetailList.get(i).get("goodsId");
            List<String> imageUrlList = frontWebMenuMapper.selectImageUrlByGoodsId(goodsId);
            productDetailList.get(i).put("imageUrlList", imageUrlList);
        }
        return productDetailList;
    }

    @Override
    public void processSeckill() {
        ListOperations listOperations = redisTemplate.opsForList();

        //1.查询出即将开始秒杀的商品信息
        List<Map<String, Object>> seckillGoodsInformationList = frontWebMenuMapper.selectSeckillGoodsInformation();
        //2.将秒杀商品信息存放在Redis数据库中
        for (Map<String, Object> seckillGoodsInformationMap : seckillGoodsInformationList) {
            //获取秒杀商品的秒杀主键
            Long seckillId = (Long) seckillGoodsInformationMap.get("id");
            //获取参与秒杀商品主键
            Long goodsId = (Long) seckillGoodsInformationMap.get("goods_id");
            //创建key
            String key = "seckill_goods_" + seckillId;
            //将number数量的秒杀名额对应的商品id存放到Redis数据库中
            //获取秒杀数量
            Integer seckillNumber = (Integer) seckillGoodsInformationMap.get("seckill_number");

            for (int i = 0; i < seckillNumber; i++) {
                listOperations.rightPush(key, goodsId);
            }
            //3.修改秒杀商品的状态
            frontWebMenuMapper.updateSeckillGoodsStatusById("1", seckillId);
        }
    }

    @Override
    public void processSeckillwhenCompleted() {
        //查询已经结束秒杀的商品信息
        List<Map<String, Object>> seckilledGoodsList = frontWebMenuMapper.selectSeckilledGoods();
        for (Map<String, Object> seckilledGoodsMap : seckilledGoodsList) {
            //获取秒杀商品的秒杀主键
            Long seckillId = (Long) seckilledGoodsMap.get("id");
            //1.删除Redis中空的秒杀商品集合
            redisTemplate.delete("seckill_goods_" +
                    "" + seckillId);
            //2.修改产品状态
            frontWebMenuMapper.updateSeckillGoodsStatusById("2", seckillId);
        }

    }

    @Override
    public List<Map<String, Object>> findSeckillGoodsDetail() {

        //1.查询即将开始秒杀和正在秒杀的商品信息
        List<Map<String, Object>> seckillGoodsDetailList = frontWebMenuMapper.selectSeckillGoodsDetails();
        //2.根据商品主键id获取每个商品的图片地址信息
        for (int i = 0; i < seckillGoodsDetailList.size(); i++) {
            Long goodsId = (Long) seckillGoodsDetailList.get(i).get("goodsId");
            List<String> imageUrlList = frontWebMenuMapper.selectImageUrlByGoodsId(goodsId);
            seckillGoodsDetailList.get(i).put("imageUrlList", imageUrlList);
        }
        return seckillGoodsDetailList;
    }


}
