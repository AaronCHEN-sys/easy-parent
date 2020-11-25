package com.java.service;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/11/16 20:29 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public interface UserService {

    /**
     * 根据用户名和密码查询登录信息
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    boolean findCountUser(String username, String password);

    /**
     * 根据用户名获取对应的权限
     *
     * @param username 用户名
     * @param id
     * @return
     */
    List<Map<String, Object>> findAuthorityByUsername(String username, Long id);

}
