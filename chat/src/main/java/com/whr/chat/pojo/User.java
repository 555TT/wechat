package com.whr.chat.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: wanghaoran1
 * @create: 2025-03-27
 */
@Data
@TableName("chat_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long userId;
    private String username;
    private String password;
}
