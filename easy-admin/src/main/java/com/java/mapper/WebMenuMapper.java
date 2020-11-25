package com.java.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/11/23 22:14 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Repository
public interface WebMenuMapper {

    /**
     * 查询所有横向菜单
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> selectWebMenu(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询所有横向菜单的总数目
     *
     * @return
     */
    int selectCountWebMenu();

    /**
     * 添加前台横向菜单
     *
     * @param parameterMap
     * @return
     */
    int insertWebMenu(Map<String, Object> parameterMap);

    /**
     * 更新前台菜单的菜单名称和链接地址
     *
     * @param parameterMap
     * @return
     */
    int updateWebMenu(Map<String, Object> parameterMap);

    /**
     * 删除前台横向菜单
     *
     * @param idStr 主键id字符串
     * @return
     */
    int deleteWebMenu(@Param("idStr") String idStr);

}
