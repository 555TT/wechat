package com.whr.chat.ws;

import com.alibaba.fastjson.JSON;
import com.whr.chat.config.GetHttpSessionConfig;
import com.whr.chat.pojo.User;
import com.whr.chat.utils.MessageUtils;
import com.whr.chat.ws.pojo.Message;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wanghaoran1
 * @create: 2025-03-27
 */
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
@Service
public class ChatEndPoint {

    /**
     * 在线的用户
     */
    private final static ConcurrentHashMap<String, Session> onlineUsers = new ConcurrentHashMap<>();
    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        User user = (User) httpSession.getAttribute("user");
        onlineUsers.put(user.getUsername(), session);
        String message = MessageUtils.getMessage(true, null, onlineUsers.keySet());
        Set<Map.Entry<String, Session>> entries = onlineUsers.entrySet();
        for (Map.Entry<String, Session> entry : entries) {
            Session value = entry.getValue();
            value.getBasicRemote().sendText(message);
        }
    }

    /**
     * 浏览器主动发送消息到服务端，也就是用户私发消息
     *
     * @param message 发送者发送的消息
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        Message messageObject = JSON.parseObject(message, Message.class);
        //要发送的消息
        String msg = messageObject.getMessage();
        //发送给谁
        String toName = messageObject.getToName();
        //接受消息者的websocket的session
        Session session = onlineUsers.get(toName);
        //从发送消息者的httpSession中获取发送者的user信息
        User user = (User) this.httpSession.getAttribute("user");
        String sendMessage = MessageUtils.getMessage(false, user.getUsername(), msg);
        //用接受者的websocket session发送消息给接受者
        session.getBasicRemote().sendText(sendMessage);
    }

    @OnClose
    public void onClose() throws IOException {
        User user = (User) this.httpSession.getAttribute("user");
        onlineUsers.remove(user.getUsername());
        //通知其它用户当前用户下线了
        String message = MessageUtils.getMessage(true, null, onlineUsers.keySet());
        Set<Map.Entry<String, Session>> entries = onlineUsers.entrySet();
        for (Map.Entry<String, Session> entry : entries) {
            Session value = entry.getValue();
            value.getBasicRemote().sendText(message);
        }
    }
}
