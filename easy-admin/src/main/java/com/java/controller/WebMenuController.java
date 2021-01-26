package com.java.controller;

import com.java.service.WebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/11/23 22:14 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Controller
@RequestMapping("/web")
public class WebMenuController {

    @Autowired
    private WebMenuService webMenuService;

    /**
     * 查询所有横向菜单
     *
     * @return
     */
    @RequestMapping("/getWebMenu.do")
    @ResponseBody
    public Map<String, Object> getWebMenu(Integer page, Integer rows) {
        int startIndex = (page - 1) * rows;
        return webMenuService.findWebMenu(startIndex, rows);
    }

    /**
     * 添加前台横向菜单
     *
     * @param title 菜单名称
     * @param url   跳转链接
     */
    @RequestMapping("/addWebMenu.do")
    @ResponseBody
    public boolean addWebMenu(String title, String url) {
        if (title == null | url == null) {
            return false;
        }
        boolean flag1 = title.matches(".{1,10}");
        boolean flag2 = url.matches("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
        if (!flag1 | !flag2) {
            return false;
        }
        return webMenuService.saveWebMenu(title, url);
    }

    /**
     * 更新前台菜单的菜单名称和链接地址
     *
     * @param title 菜单名称
     * @param url   跳转链接
     * @param id    主键id
     * @return
     */
    @RequestMapping("/alterWebMenu.do")
    @ResponseBody
    public boolean alterWebMenu(String title, String url, Long id) {
        if (title == null | url == null) {
            return false;
        }
        boolean flag1 = title.matches(".{1,10}");
        boolean flag2 = url.matches("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
        if (!flag1 | !flag2) {
            return false;
        }
        return webMenuService.modifyWebMenu(title, url, id);
    }

    /**
     * 删除前台横向菜单
     *
     * @param idStr 主键id字符串
     * @return
     */
    @RequestMapping("/abandonWebMenu.do")
    @ResponseBody
    public boolean abandonWebMenu(String idStr) {
        idStr = idStr.substring(0, idStr.length() - 1);
        return webMenuService.removeWebMenu(idStr);
    }

    /**
     * 查询前台轮播图
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/getWebBanner.do")
    @ResponseBody
    public Map<String, Object> getWebBanner(Integer page, Integer rows) {
        int startIndex = (page - 1) * rows;
        return webMenuService.findWebBanner(startIndex, rows);
    }

    /**
     * 添加前台轮播图
     *
     * @param imgUrl
     * @param href
     * @param remark
     * @param sort
     * @return
     */
    @RequestMapping("/addWebBanner.do")
    public boolean addWebBanner(String imgUrl, String href, String remark, Integer sort) {
        System.out.println(imgUrl);
        System.out.println(href);
        System.out.println(remark);
        System.out.println(sort);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("imgUrl", imgUrl);
        paramMap.put("href", href);
        paramMap.put("remark", remark);
        paramMap.put("sort", sort);
        return webMenuService.saveWebBanner(paramMap);
    }

}
