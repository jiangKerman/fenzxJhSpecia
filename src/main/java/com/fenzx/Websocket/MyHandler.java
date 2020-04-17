package com.fenzx.Websocket;

import com.fenzx.entity.Chat;
import com.google.gson.Gson;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyHandler extends TextWebSocketHandler {
    private static List<UserSession> users = new ArrayList<>();//存放所有websession


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Gson gson = new Gson();
        Map map = gson.fromJson((message.getPayload().toString()), Map.class);

        UserSession userSession = new UserSession();
        userSession.setSession(session);
        userSession.setTargetId((String) map.get("targetId"));
        userSession.setUserId((String) map.get("userId"));
//
        userSession.setPid(map.get("pid").toString());
//
//        System.out.println(message.getPayload().toString());
//        System.out.println(map);


        int i = 0;
        for (UserSession user : users) {
            //如果不是第一次聊天
            if (user.getUserId().equals(map.get("userId").toString())) {
                break;
            }
            i++;
        }
        if (i == users.size()) {
            users.add(userSession);
        }


        if (map.get("content") != null) {
            //给目标用户发送消息
            for (UserSession user : users) {
                if (user.getUserId().equals(map.get("targetId").toString()) &&
                        user.getTargetId().equals(map.get("userId").toString()) &&
                        user.getPid().equals(map.get("pid").toString())) {
                    user.getSession().sendMessage(new TextMessage(message.getPayload().toString()));
                }
            }
        }


        System.out.println("当前人数" + users.size() + "---" + users);


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)  {
        for (UserSession user : users) {
            if (user.getSession() == session) {
                users.remove(user);
                break;
            }
        }
        System.out.println("一人断开连接，当前在线人数" + users.size());
    }
}
