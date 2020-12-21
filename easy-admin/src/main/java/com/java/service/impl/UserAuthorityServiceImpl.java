package com.java.service.impl;

import com.java.mapper.UserAuthorityMapper;
import com.java.service.UserAuthorityService;
import com.java.utils.MD5Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/12/13 11:10 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Service
public class UserAuthorityServiceImpl implements UserAuthorityService {

    @Autowired
    private UserAuthorityMapper userAuthorityMapper;

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> findSystemUser(Integer startIndex, Integer pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", userAuthorityMapper.selectSystemUser(startIndex, pageSize));
        resultMap.put("total", userAuthorityMapper.selectCountSystemUser());
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> findFirstAuthority(Long parentId) {
        return userAuthorityMapper.selectFirstAuthority(parentId);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveSystemUser(String username, String password, String menuIds) throws Exception {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("username", username);
        parameterMap.put("password", MD5Tool.finalMD5(password));
        //添加系统用户
        int i = userAuthorityMapper.insertSystemUser(parameterMap);
        if (i >= 1) {
            Long userId = (Long) parameterMap.get("userId");
            String[] menuIdAttr = menuIds.split("\\,");
            for (String menuId : menuIdAttr) {
                int j = userAuthorityMapper.insertUserAndAuthorityRelationship(userId, Long.parseLong(menuId));
                if (j <= 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
