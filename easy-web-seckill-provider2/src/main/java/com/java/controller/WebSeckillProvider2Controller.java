package com.java.controller;

import com.java.exceptions.SeckillException;
import com.java.service.WebSeckillProvider2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/03/28 20:54 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Controller
public class WebSeckillProvider2Controller {

    @Autowired
    private WebSeckillProvider2Service webSeckillProvider2Service;

    /**
     * 秒杀步骤二
     *
     * @param seckillId 秒杀主键id
     * @param userId    用户主键id
     * @return
     */
    @RequestMapping("/processSeckill.do/{seckillId}/{userId}")
    @ResponseBody
    public Map<String, Object> processSeckill(@PathVariable(name = "seckillId") Long seckillId, @PathVariable(name = "userId") Long userId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            webSeckillProvider2Service.doSeckillStep(seckillId, userId);
            //秒杀成功
            resultMap.put("status", "0");
            return resultMap;
        } catch (SeckillException e) {
            e.printStackTrace();
            //秒杀失败
            resultMap.put("status", "1");
            resultMap.put("errorMsg", e.getMessage());
            return resultMap;
        }
    }

}
