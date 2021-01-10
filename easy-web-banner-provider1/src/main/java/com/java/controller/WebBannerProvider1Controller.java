package com.java.controller;

import com.java.service.WebBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/01/06 21:26 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Controller
public class WebBannerProvider1Controller {

    @Autowired
    private WebBannerService webBannerService;

    /**
     * 查询前台轮播图
     *
     * @return
     */
    @RequestMapping("/getWebBanner.do")
    @ResponseBody
    public List<Map<String, Object>> getWebBanner() {
        System.out.println("WebBannerProvider1Controller");
        return webBannerService.findWebBanner();
    }
}
