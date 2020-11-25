package com.java.service.impl;

import com.java.mapper.UserMapper;
import com.java.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/11/16 20:27 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean findCountUser(String username, String password) {
        return userMapper.selectCountUser(username, password) >= 1;
    }

    @Override
    public List<Map<String, Object>> findAuthorityByUsername(String username, Long id) {
        return userMapper.selectAuthorityByUsername(username,id);
    }


}
