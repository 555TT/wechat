package com.whr.chat.ws.pojo;

import lombok.Data;

/**
 * 用于封装浏览器发送给服务端的消息数据
 */
@Data
public class Message {
    /**
     * 发送给谁
     */
    private String toName;
    private String message;
}
