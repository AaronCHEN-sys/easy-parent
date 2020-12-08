package com.java.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/12/01 15:59 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Repository
public interface FrontWebMenuMapper {

    /**
     * 查询前8条前台横向菜单
     *
     * @param menuType 菜单类型
     *                 1 横向菜单
     *                 2 纵向菜单
     * @return
     */
    List<Map<String, Object>> selectHorizontalWebMenu(@Param("menuType") String menuType);
}
