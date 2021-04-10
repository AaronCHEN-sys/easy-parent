package com.java.controller;

import com.java.service.UserService;
import com.java.utils.MD5Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/11/13 17:52 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名和密码查询登录信息
     *
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCountUser.do")
    public String getCountUser(String username, String password, HttpSession session) throws Exception {
        if (username == null || password == null) {
            return "/pages/index.jsp";
        }
        boolean flag1 = username.matches(".{3,12}");
        boolean flag2 = password.matches(".{3,12}");
        if (!flag1 || !flag2) {
            return "/pages/index.jsp";
        }
        boolean flag = userService.findCountUser(username, MD5Tool.finalMD5(password));
        if (flag) {
            session.setAttribute("username", username);
            return "/pages/index.jsp";
        } else {
            return "/pages/login.jsp";
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(MD5Tool.finalMD5("12345678"));
    }

    /**
     * 根据用户名获取对应的权限
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/getAuthorityByUsername.do")
    @ResponseBody
    public List<Map<String, Object>> getAuthorityByUsername(@RequestParam(name = "id", defaultValue = "0") Long id, HttpSession session) {
        return userService.findAuthorityByUsername(session.getAttribute("username").toString(), id);
    }
}
