package com.java.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/03/26 21:26 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Repository
public interface WebSeckillProvider1Mapper {

    /**
     * 根据秒杀主键id查询秒杀商品信息
     *
     * @param seckillId 秒杀主键id
     * @return
     */
    Map<String, Object> selectSeckillGoodsBySeckillId(@Param("seckillId") Long seckillId);
}
