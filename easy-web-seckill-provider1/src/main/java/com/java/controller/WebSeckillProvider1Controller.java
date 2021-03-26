package com.java.controller;

import com.java.exceptions.SeckillException;
import com.java.service.WebSeckillProvider1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/03/26 21:20 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Controller
public class WebSeckillProvider1Controller {

    @Autowired
    private WebSeckillProvider1Service webSeckillProvider1Service;

    /**
     * 秒杀步骤二
     *
     * @param seckillId 秒杀主键id
     * @param userId    用户主键id
     * @return
     */
    @RequestMapping("/processSeckill.do")
    @ResponseBody
    public Map<String, Object> processSeckill(Long seckillId, Long userId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            webSeckillProvider1Service.doSeckillStep(seckillId, userId);
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
