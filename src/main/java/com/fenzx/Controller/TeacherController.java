package com.fenzx.Controller;

import com.fenzx.ServiceImpls.ProblemService;
import com.fenzx.entity.Chat;
import com.fenzx.entity.Problem;
import com.fenzx.entity.Teacher;
import com.fenzx.ServiceImpls.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    ChatService chatService;
    @Autowired
    ProblemService problemService;

    @RequestMapping("teacherAllProblem.html")
    public String teacherAllProblem(
            String tid,
            Integer startPage,
            Integer size, ModelMap modelMap) {

//        List<Problem> allProblemByTid = teacherService.findAllProblemByTid(tid);
//        modelMap.put("allProblemByTid",allProblemByTid);

        Page<Problem> problemPage = problemService.findALlProblemByTidSortByProblemId(tid, startPage, size);
        modelMap.put("problemPage", problemPage);

        return "teacherAllProblem.html";
    }

    @RequestMapping("defaultTeacherAllProblem.html")
    public String defaultTeacherAllProblem(
            @RequestParam(defaultValue = "438") String tid,
            @RequestParam(defaultValue = "1") Integer startPage,
            @RequestParam(defaultValue = "10") Integer size, ModelMap modelMap, HttpSession session) {

        Teacher teacher = (Teacher) session.getAttribute("teacher");
//        tid = teacher.getTid();
//        Page<Problem> problemPage = problemService.findALlProblemByTidSortByProblemId(tid, startPage, size);
//        modelMap.put("problemPage", problemPage);

        List<Object> problemDetails = problemService.findProblemDetailsByTid(teacher.getTid());
        modelMap.put("problemDetails",problemDetails);

        return "teacherAllProblem.html";
    }

    @RequestMapping("teacherViewDetail")
    public String teacherViewDetail(Integer problemId ,ModelMap modelMap) {
        List<Chat> chats = chatService.findAllChatByProblemId(problemId);
        modelMap.put("chats",chats);
        modelMap.put("problemId",problemId);
        return "teacherProblemDetail";
    }

//    老师回复消息保存后跳转到查看详情
    @RequestMapping("teacherReply")
    public String teacherReply(Integer problemId,String type,String content){
        chatService.saveChatByPidAndType(problemId,type,content);

        return "forward:/teacherViewDetail?param1="+problemId;
    }

}
