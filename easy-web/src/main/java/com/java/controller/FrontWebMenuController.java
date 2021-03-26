package com.java.controller;

import com.java.service.FrontWebMenuService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.soap.Detail;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    //注入负载均衡的工具类
    @Bean
    //开启负载均衡
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private FrontWebMenuService frontWebMenuService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private RestTemplate restTemplate;


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


    /**
     * 通过FreeMaker动态生成HTML文件
     *
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @RequestMapping("/toStaticProductDetail.do")
    @ResponseBody
    public void toStaticProductDetail() throws IOException, TemplateException {
        List<Map<String, Object>> goodsDetailList = frontWebMenuService.findAllGoodsDetail();
        for (Map<String, Object> goodsDetailMap : goodsDetailList) {
            //FreeMaker取出数据后, 生成静态的HTML页面(productId.html)
            // 1.获取指定的FreeMaker模板对象
            Template template = configuration.getTemplate("Product.ftl");
            File file = new File("D:\\ProgramFiles\\freemaker\\easy_buy\\details\\" + goodsDetailMap.get("goodsId") + ".html");
            FileWriter fileWriter = new FileWriter(file);
            template.process(goodsDetailMap, fileWriter);
            fileWriter.close();
        }
    }

    /**
     * @param model
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex(Model model) {
        //注意实现负载均衡
        List<Map<String, Object>> goodsDetailList = frontWebMenuService.findAllGoodsDetail();
        //封装商品列表
        model.addAttribute("goodsDetailList", goodsDetailList);
        //封装轮播图片
        List<Map<String, Object>> bannerList = restTemplate.getForObject("http://easy-web-banner-provider/getWebBanner.do", List.class);
        model.addAttribute("bannerList", bannerList);
        return "/pages/Index.jsp";
    }
}
