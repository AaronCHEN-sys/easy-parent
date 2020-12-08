package com.java.service;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/12/01 16:06 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public interface FrontWebMenuService {

    /**
     * 查询前8条前台横向菜单
     *
     * @return
     */
    List<Map<String, Object>> findHorizontalWebMenu();
}
