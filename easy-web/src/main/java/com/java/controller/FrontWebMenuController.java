package com.java.controller;

import com.java.service.FrontWebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.soap.Detail;
import java.util.ArrayList;
import java.util.HashMap;
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


    /**
     * 使用FreeMaker静态化页面
     * 进入商品详情
     *
     * @param productId
     * @return
     */
    @RequestMapping("/toProductDetailByFTL.do/{productId}")
    public ModelAndView toProductDetailByFTL(@PathVariable(name = "productId") Long productId) {

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("title", "联想(Lenovo)小新Pro13 2020英特尔酷睿i5全面屏性能轻薄笔记本电脑(i5 16G 512G");
        dataMap.put("subTitle", "爆款热销,网课办公娱乐");
        dataMap.put("price", 5999.00F);
        dataMap.put("type", "数码");
        dataMap.put("color", "MX350独显2.5K屏 高色域)银");
        dataMap.put("model", "Pro13 2020英特尔酷睿i5全面屏");
        dataMap.put("remaining", 100);

        List<String> imgUrlList = new ArrayList<>();
        imgUrlList.add("http://192.168.25.133/group1/M00/00/04/wKgZhV6a6XyAOP87AAAGj-xhH_0935.jpg");
        imgUrlList.add("http://192.168.25.133/group1/M00/00/04/wKgZhV6a6ZCAdh70AAAEMteVRto714.jpg");
        imgUrlList.add("http://192.168.25.133/group1/M00/00/04/wKgZhV6a6Z6AOD1HAAAGCRp5uyU705.jpg");

        dataMap.put("imgUrlList", imgUrlList);

        //将数据保存到request域中并且跳转到Product--->Product.html
        ModelAndView modelAndView = new ModelAndView("Product");
        modelAndView.addAllObjects(dataMap);
        //modelAndView.addObject("dataMap", dataMap);
        return modelAndView;
    }

    @RequestMapping("/toProductDetailByFTL.do/{productId}")
    public void toProductDetail(@PathVariable(name = "productId") Long productId) {


    }

}
