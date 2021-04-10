package com.java.service;

import com.java.exceptions.SeckillException;

/**
 * Description:	   <br/>
 * Date:     2021/03/28 20:53 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public interface WebSeckillProvider2Service {

    /**
     * 秒杀步骤二
     *
     * @param seckillId 秒杀主键id
     * @param userId    用户主键id
     * @throws SeckillException
     */
    void doSeckillStep(Long seckillId, Long userId) throws SeckillException;
}
