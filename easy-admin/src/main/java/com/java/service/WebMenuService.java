package com.java.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/11/23 22:21 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public interface WebMenuService {

    /**
     * 查询所有横向菜单
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = false)
    Map<String, Object> findWebMenu(Integer startIndex, Integer pageSize);

    /**
     * 添加前台横向菜单
     *
     * @param title 菜单名称
     * @param url   跳转链接
     * @return
     */
    boolean saveWebMenu(String title, String url);

    /**
     * 更新前台菜单的菜单名称和链接地址
     *
     * @param title 菜单名称
     * @param url   跳转链接
     * @param id    主键id
     * @return
     */
    boolean modifyWebMenu(String title, String url, Long id);

    /**
     * 删除前台横向菜单
     *
     * @param idStr 主键id字符串
     * @return
     */
    boolean removeWebMenu(String idStr);
}
