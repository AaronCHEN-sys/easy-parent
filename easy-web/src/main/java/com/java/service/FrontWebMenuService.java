package com.java.service;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/12/01 16:06 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public interface FrontWebMenuService {

    /**
     * 查询前8条前台横向菜单
     *
     * @return
     */
    List<Map<String, Object>> findHorizontalWebMenu();

    /**
     * 查询所有商品详细信息
     *
     * @return
     */
    List<Map<String, Object>> findAllGoodsDetail();

    /**
     * 查询五分钟之内被修改过的商品详情, 不包含图片地址信息
     *
     * @return
     */
    List<Map<String, Object>> findAllProductDetailUpdated();

    /**
     * 开始秒杀
     */
    void processSeckill();

    /**
     * 秒杀结束
     */
    void processSeckillwhenCompleted();

    /**
     * 查询即将开始秒杀和正在秒杀的商品信息, 包含图片地址信息
     *
     * @return
     */
    List<Map<String, Object>> findSeckillGoodsDetail();
}


