package com.java.controller;

import com.java.service.UserAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/12/13 11:11 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Controller
@RequestMapping("/userAuthority")
public class UserAuthorityController {

    @Autowired
    private UserAuthorityService userAuthorityService;

    /**
     * 查询所有系统用户信息
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/getSystemUser.do")
    @ResponseBody
    public Map<String, Object> getSystemUser(Integer page, Integer rows) {
        int startIndex = (page - 1) * rows;
        return userAuthorityService.findSystemUser(startIndex, rows);
    }

    /**
     * 根据父节点查询一级权限
     *
     * @param parentId
     * @return
     */
    @RequestMapping("/getFirstAuthority.do")
    @ResponseBody
    public List<Map<String, Object>> getFirstAuthority(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
        return userAuthorityService.findFirstAuthority(parentId);
    }


    /**
     * @param username
     * @param password
     * @param menuIds
     * @return
     */
    @RequestMapping("/addSystemUser.do")
    @ResponseBody
    public boolean addSystemUser(String username, String password, String menuIds) {
        System.out.println("username=" + username);
        System.out.println("password=" + password);
        System.out.println("menuIds=" + menuIds);
        return false;
    }

}
