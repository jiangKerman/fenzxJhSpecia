package com.fenzx.Controller;

import com.fenzx.ServiceImpls.ChatService;
import com.fenzx.ServiceImpls.ProblemService;
import com.fenzx.ServiceImpls.StudentService;
import com.fenzx.entity.Chat;
import com.fenzx.entity.Problem;
import com.fenzx.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    ProblemService problemService;
    @Autowired
    StudentService studentService;
    @Autowired
    ChatService chatService;

//    学生跳转到提问界面执行
    @RequestMapping("studentPutQuesionts")
    public String studentPutQuestions() {
        return "studentPutQuestions";
    }

//    学生提交问题,点击提交执行此方法
    @RequestMapping("StudentSubmitQuestions")
    public String StudentSubmitQuestions(
            String sid,String title,String  time,
            String freeTime,String problemType,String detail){

        Problem problem=new Problem();
        problem.setSid(sid);
        problem.setTitle(title);
        problem.setTime(time);
        problem.setFreeTime(freeTime);
        problem.setProblemType(problemType);
        problem.setDetail(detail);
        problem.setResolved(0);
        problemService.saveProblem(problem);
        return "forward:/StudentViewQuestions";
    }


    @RequestMapping("StudentViewQuestions")
    public String StudentViewQuestions(ModelMap modelMap,HttpSession session){
        Student student= (Student) session.getAttribute("student");
        String sid=student.getSid();

//        List<Problem> problemList = studentService.findAllProblemBySid(sid);
//        modelMap.put("problemList",problemList);
        List<Object> problemList = problemService.findProblemDetailsBySid(sid);
        modelMap.put("problemList",problemList);
        return "studentAllProblem";
    }

    @RequestMapping("StudentViewQuestionDetail")
    public String StudentViewQuestionDetail(String  pid,ModelMap modelMap){
        List<Chat> chats = chatService.findAllChatByProblemId(Integer.parseInt(pid));
        modelMap.put("chats",chats);
        Problem problem =problemService.findById(Integer.parseInt(pid));
        modelMap.put("problem",problem);

        return "studentProblemDetails";
    }

//    学生回复消息保存后跳转到查看详情
    @RequestMapping("studentReply")
    public String studentReply(int pid,String type,String content){
        chatService.saveChatByPidAndType(pid,type,content);
        return "forward:/StudentViewQuestionDetail?param1="+pid;
    }

}
