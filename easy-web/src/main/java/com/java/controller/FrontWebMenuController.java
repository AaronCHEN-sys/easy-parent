package com.java.controller;

import com.alibaba.fastjson.JSON;
import com.java.pojo.web.GoodsItem;
import com.java.pojo.web.ShoppingCart;
import com.java.service.FrontWebMenuService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    private RedisTemplate redisTemplate;


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
        //查询即将开始秒杀和正在秒杀的商品信息, 包含图片地址信息
        List<Map<String, Object>> seckillGoodsDetailList = frontWebMenuService.findSeckillGoodsDetail();
        model.addAttribute("seckillGoodsDetailList", seckillGoodsDetailList);
        return "/pages/Index.jsp";
    }

    /**
     * 添加商品至购物车
     *
     * @param goodsId
     * @param count
     * @param request
     * @param response
     * @param session
     */
    @RequestMapping("/addGoodsToShoppingCart.do")
    public void addGoodsToShoppingCart(Long goodsId, Integer count, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        ShoppingCart shoppingCart = null;

        //获取cookies
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            //Cookie存在
            for (int i = 0; i < cookies.length; i++) {
                String cookieName = cookies[i].getName();
                if ("easyBuyShoppingCart".equals(cookieName)) {
                    String cookieValue = cookies[i].getValue();
                    //将cookieValue字符串还原成ShoppingCart对象
                    shoppingCart = JSON.parseObject(cookieValue, ShoppingCart.class);

                    List<GoodsItem> goodsItemList = shoppingCart.getGoodsItemList();
                    for (int j = 0; j < goodsItemList.size(); j++) {
                        Long goodsIdTemp = goodsItemList.get(j).getGoodsId();
                        //购物车中存在相同的商品
                        if (goodsIdTemp == goodsId) {
                            goodsItemList.get(j).setCount(goodsItemList.get(j).getCount() + count);
                        } else {
                            //购物车中不存在相同的商品
                            GoodsItem goodsItem = new GoodsItem(goodsId, count);
                            shoppingCart.getGoodsItemList().add(goodsItem);
                        }
                    }
                } else {
                    GoodsItem goodsItem = new GoodsItem(goodsId, count);
                    shoppingCart = new ShoppingCart();
                    shoppingCart.setGoodsItemList(Arrays.asList(goodsItem));
                }
            }
        } else {
            //Cookie不存在
            GoodsItem goodsItem = new GoodsItem(goodsId, count);
            shoppingCart = new ShoppingCart();
            shoppingCart.setGoodsItemList(Arrays.asList(goodsItem));
        }

        String shoppingCartStringJSON = JSON.toJSONString(shoppingCart);

        //判断用户是否已经登录
        Object username = session.getAttribute("username");
        if (username == null) {
            //没有登录
            Cookie cookie = new Cookie("easyBuyShoppingCart", shoppingCartStringJSON);
            cookie.setMaxAge(24 * 60 * 60 * 7 + 8 * 60 * 60);
            response.addCookie(cookie);
        } else {
            //已登录
            //将数据添加到Redis数据库中
            SetOperations setOperations = redisTemplate.opsForSet();
            setOperations.add("easyBuyShoppingCart_" + username, shoppingCart);
            //清空Cookie
            Cookie cookie = new Cookie("easyBuyShoppingCart", "");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
        }
    }

}
