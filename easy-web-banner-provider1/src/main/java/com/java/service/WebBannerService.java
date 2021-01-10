package com.java.service;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/01/07 20:17 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public interface WebBannerService {

    /**
     * 查询前台轮播图
     *
     * @return
     */
    List<Map<String, Object>> findWebBanner();
}
