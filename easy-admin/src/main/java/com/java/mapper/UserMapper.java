package com.java.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/11/16 20:24 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public interface UserMapper {
    /**
     * 根据用户名和密码查询登录信息
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    int selectCountUser(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名获取对应的权限
     *
     * @param username 用户名
     * @param id
     * @param flag     是否是超级管理员 1是
     *                 0不是
     * @return
     */
    List<Map<String, Object>> selectAuthorityByUsername(@Param("username") String username, @Param("id") Long id, @Param("flag") String flag);

    /**
     * 根据用户名查询是否是超级管理员
     *
     * @param username 用户名
     * @return
     */
    String selectAdminUser(@Param("username") String username);
}
