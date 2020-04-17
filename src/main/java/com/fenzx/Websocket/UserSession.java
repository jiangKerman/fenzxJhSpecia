package com.fenzx.Websocket;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;
@Data
@ToString
public class UserSession {
    private String targetId;//存放要对话的目标学生id 或者教师id
    private String userId;//发送这条消息的人的id
    private String type;//判断是student还是teacher
    private String pid;
    private WebSocketSession session;
}
