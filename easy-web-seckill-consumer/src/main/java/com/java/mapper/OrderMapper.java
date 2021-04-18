package com.java.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/04/13 22:19 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Repository
public interface OrderMapper {

    /**
     * 添加订单数据
     *
     * @param parameterMap
     * @return
     */
    int insertOrder(Map<String, Object> parameterMap);

    /**
     * 根据秒杀主键id查询秒杀价格
     *
     * @param seckillId 秒杀主键id
     * @return
     */
    Float selectSeckillId(@Param("seckillId") Long seckillId);

    /**
     * 结算时, 根据订单编号查询是否存在订单
     *
     * @param orderNo 订单编号
     * @return
     */
    int selectCountOrder(@Param("orderNo") String orderNo);
}
