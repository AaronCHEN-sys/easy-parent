package com.java.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/12/13 11:13 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
public interface UserAuthorityService {

    /**
     * 查询所有系统用户信息
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = false)
    Map<String, Object> findSystemUser(Integer startIndex, Integer pageSize);

    /**
     * 根据父节点查询一级权限
     *
     * @param parentId
     * @return
     */
    List<Map<String, Object>> findFirstAuthority(Long parentId);

    /**
     * 添加系统用户及权限
     *
     * @param username 用户名
     * @param password 密码
     * @param menuIds
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    boolean insertSystemUser(String username, String password, String menuIds) throws Exception;
}
