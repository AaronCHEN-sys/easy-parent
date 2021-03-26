package com.java.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/12/01 15:59 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Repository
public interface FrontWebMenuMapper {

    /**
     * 查询前8条前台横向菜单
     *
     * @param menuType 菜单类型
     *                 1 横向菜单
     *                 2 纵向菜单
     * @return
     */
    List<Map<String, Object>> selectHorizontalWebMenu(@Param("menuType") String menuType);

    /**
     * 查询所有商品详细信息, 不包含图片地址信息
     *
     * @return
     */
    List<Map<String, Object>> selectAllProductDetail();


    /**
     * 根据商品主键id获取对应图片地址信息
     *
     * @param goodsId 商品主键id
     * @return
     */
    List<String> selectImageUrlByGoodsId(@Param("goodsId") Long goodsId);

    /**
     * 查询五分钟之内被修改过的商品详情, 不包含图片地址信息
     *
     * @return
     */
    List<Map<String, Object>> selectAllProductDetailUpdated();

    /**
     * 查询即将开始秒杀的商品的秒杀信息
     *
     * @return
     */
    List<Map<String, Object>> selectSeckillGoodsInformation();

    /**
     * @param seckillStatus 秒杀商品状态
     *                      0未开始
     *                      1以开始
     *                      2已结束
     * @param seckillId     秒杀商品主键
     * @return
     */
    int updateSeckillGoodsStatusById(@Param("seckillStatus") String seckillStatus, @Param("seckillId") Long seckillId);

    /**
     * 查询寂静结束秒杀的商品信息
     *
     * @return
     */
    List<Map<String, Object>> selectSeckilledGoods();

}
