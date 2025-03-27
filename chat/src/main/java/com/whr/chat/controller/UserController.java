package com.whr.chat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whr.chat.mapper.UserMapper;
import com.whr.chat.pojo.Result;
import com.whr.chat.pojo.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author: wanghaoran1
 * @create: 2025-03-27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @PostMapping("/login")
    public Result login(@RequestBody User userRequest, HttpSession httpSession) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userRequest.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return Result.builder().flag(false).message("登录失败").build();
        }
        if (userRequest.equals(user.getPassword())) {
            return Result.builder().flag(false).message("密码错误").build();
        }
        httpSession.setAttribute("user", user);
        return Result.builder().flag(true).message("登录成功").build();
    }

    /**
     * 获取用户名
     *
     * @param session
     * @return
     */
    @GetMapping("/getUsername")
    public String getUsername(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user.getUsername();
    }
}
