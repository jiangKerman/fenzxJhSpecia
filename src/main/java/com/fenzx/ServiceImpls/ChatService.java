package com.fenzx.ServiceImpls;

import com.fenzx.Repos.ChatRepo;
import com.fenzx.entity.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class ChatService {
    @Autowired
    ChatRepo chatRepo;

    public List<Chat> findAllChatByProblemId(Integer problemId) {
        List<Chat> chatList = chatRepo.findAllByProblemId(problemId);
        return chatList;
    }

    public Chat saveChatByPidAndType(Integer problemId,String type,String content){
        Chat chat=new Chat();
        chat.setContent(content);
        chat.setProblemId(problemId);
        chat.setType(type);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        chat.setTime(dateFormat.format(new Date()));
        chatRepo.save(chat);


        return chat;
    }

}
