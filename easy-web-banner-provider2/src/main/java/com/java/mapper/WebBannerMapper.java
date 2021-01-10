package com.java.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/01/07 20:12 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Repository
public interface WebBannerMapper {
    /**
     * 查询前台轮播图
     *
     * @return
     */
    List<Map<String, Object>> selectWebBanner();
}
