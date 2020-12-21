package com.java.service.impl;

import com.java.mapper.FrontWebMenuMapper;
import com.java.service.FrontWebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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

}
