package com.java.mapper;

import com.java.pojo.admin.FirstMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/12/13 11:09 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Repository
public interface UserAuthorityMapper {


    /**
     * 查询所有系统用户信息
     *
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> selectSystemUser(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询所有系统用户信息的数据量
     *
     * @return
     */
    int selectCountSystemUser();

    /**
     * 根据父节点查询一级权限
     *
     * @return
     */
    List<Map<String, Object>> selectFirstAuthority(@Param("parentId") Long parentId);

    /**
     * 添加系统用户
     *
     * @param parameterMap username 用户名
     *                     password 密码
     * @return
     */
    int insertSystemUser(Map<String, Object> parameterMap);

    /**
     * 添加系统用户和权限关系
     *
     * @param userId 用户主键id
     * @param menuId 菜单主键id
     * @return
     */
    int insertUserAndAuthorityRelationship(@Param("userId") Long userId, @Param("menuId") Long menuId);

    /**
     * 查询一级和二级权限
     *
     * @return
     */
    List<FirstMenu> selectFirstAndSecondAuthorityRelation();
}
