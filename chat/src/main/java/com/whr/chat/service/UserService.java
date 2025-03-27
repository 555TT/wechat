package com.whr.chat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whr.chat.mapper.UserMapper;
import com.whr.chat.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author: wanghaoran1
 * @create: 2025-03-27
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}
