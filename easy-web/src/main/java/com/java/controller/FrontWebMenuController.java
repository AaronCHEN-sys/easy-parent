package com.java.controller;

import com.java.service.FrontWebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/12/01 16:06 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Controller
@RequestMapping("/frontWebMenu")
public class FrontWebMenuController {

    @Autowired
    private FrontWebMenuService frontWebMenuService;

    /**
     * 查询前8条前台横向菜单
     *
     * @return
     */
    @RequestMapping("/getHorizontalWebMenu.do")
    @ResponseBody
    public List<Map<String, Object>> getHorizontalWebMenu() {
        return frontWebMenuService.findHorizontalWebMenu();
    }



}
