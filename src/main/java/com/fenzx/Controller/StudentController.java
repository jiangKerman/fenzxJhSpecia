package com.fenzx.Controller;

import com.fenzx.ServiceImpls.ChatService;
import com.fenzx.ServiceImpls.ProblemService;
import com.fenzx.ServiceImpls.StudentService;
import com.fenzx.ServiceImpls.TeacherService;
import com.fenzx.entity.Chat;
import com.fenzx.entity.Problem;
import com.fenzx.entity.Student;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    TeacherService teacherService;

    //    学生跳转到提问界面执行
    @RequestMapping("studentPutQuesionts")
    public String studentPutQuestions() {
        return "student/studentPutQuestions";
    }

    //    学生提交问题,点击提交执行此方法
    @RequestMapping("StudentSubmitQuestions")
    public String StudentSubmitQuestions(
            String sid, String title, String time,
            String freeTime, String problemType, String detail) {

        Problem problem = new Problem();
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
    public String StudentViewQuestions(ModelMap modelMap, HttpSession session) {
        Student student = (Student) session.getAttribute("student");
        String sid = student.getSid();

//        List<Problem> problemList = studentService.findAllProblemBySid(sid);
//        modelMap.put("problemList",problemList);
        List<Object> problemList = problemService.findProblemDetailsBySid(sid);
        modelMap.put("problemList", problemList);
        return "student/studentAllProblem";
    }

    @RequestMapping("StudentViewQuestionDetail")
    public String StudentViewQuestionDetail(String pid,String tid, ModelMap modelMap) {
        List<Chat> chats = chatService.findAllChatByProblemId(Integer.parseInt(pid));
        modelMap.put("chats", chats);
        Problem problem = problemService.findById(Integer.parseInt(pid));
        modelMap.put("problem", problem);
        modelMap.put("teacher",teacherService.findByTid(tid));//前端需要老师的姓名

         return "student/studentProblemDetails";
    }

    //    学生回复消息保存后跳转到查看详情,ajax的做法
    @RequestMapping("studentReply")
    @ResponseBody
    public String  studentReply(int pid, String type, String content) {
        Chat chat = chatService.saveChatByPidAndType(pid, type, content);
        Gson gson=new Gson();
        return gson.toJson(chat);
    }

    @RequestMapping("studentConfirmingProblem")
    public String studentConfirmingProblem(String pid){
        Problem problem = problemService.findById(Integer.parseInt(pid));
        problem.setResolved(2);
        problemService.saveProblem(problem);
        System.out.println("我确认我的问题解决了");
        return "forward:/StudentViewQuestions";
    }

    @RequestMapping("recommendContent")
    public String recommendContent(ModelMap modelMap,HttpSession session){
        Student student = (Student) session.getAttribute("student");
        String major="计算机";
        if(student.getMajor().contains("计算机")){
            major="计算机";
        }
        if(student.getMajor().contains("电子")){
            major="电子";
        }

        System.out.println(major);


        List<Object> recruitmentList = studentService.findAllRecruitmentWithSalaryLimitWithMajor(major,10);
//        List<Object> recruitmentList = studentService.findAllRecruitmentWithSalaryLimit(0,10);
        modelMap.put("recruitmentList",recruitmentList);
        return "student/studentRecommendContent";
    }

}
