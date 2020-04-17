package com.fenzx.Controller;

import com.fenzx.ServiceImpls.ProblemService;
import com.fenzx.ServiceImpls.StudentService;
import com.fenzx.entity.Chat;
import com.fenzx.entity.Problem;
import com.fenzx.entity.Teacher;
import com.fenzx.ServiceImpls.ChatService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    ChatService chatService;
    @Autowired
    ProblemService problemService;
    @Autowired
    StudentService studentService;

    @RequestMapping("teacherAllProblem.html")
    public String teacherAllProblem(
            String tid,
            Integer startPage,
            Integer size, ModelMap modelMap) {

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
        modelMap.put("problemDetails", problemDetails);

        return "teacherAllProblem.html";
    }

    @RequestMapping("teacherViewDetail")
    public String teacherViewDetail(Integer problemId,String sid, ModelMap modelMap) {
        List<Chat> chats = chatService.findAllChatByProblemId(problemId);
        modelMap.put("chats", chats);
        modelMap.put("problemId", problemId);
        modelMap.put("student",studentService.findBySid(sid));
        return "teacherProblemDetail";
    }


    //    老师回复消息保存后跳转到查看详情
    @ResponseBody
    @RequestMapping("teacherReply")
    public String teacherReply(Integer pid, String type, String content) {
        Chat chat = chatService.saveChatByPidAndType(pid, type, content);
        Gson gson = new Gson();
        return gson.toJson(chat);
    }

}
