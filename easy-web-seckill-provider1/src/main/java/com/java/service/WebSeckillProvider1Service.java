package com.java.service;

import com.java.exceptions.SeckillException;

/**
 * Description:	   <br/>
 * Date:     2021/03/26 22:06 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public interface WebSeckillProvider1Service {

    /**
     * 秒杀步骤二
     *
     * @param seckillId 秒杀主键id
     * @param userId    用户主键id
     * @throws SeckillException
     */
    void doSeckillStep(Long seckillId, Long userId) throws SeckillException;
}
