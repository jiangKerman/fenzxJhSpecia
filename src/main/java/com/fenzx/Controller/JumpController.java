package com.fenzx.Controller;


import com.fenzx.ServiceImpls.ProblemService;
import com.fenzx.ServiceImpls.StudentService;
import com.fenzx.entity.Admin;
import com.fenzx.entity.Problem;
import com.fenzx.entity.Student;
import com.fenzx.entity.Teacher;
import com.fenzx.ServiceImpls.AdminService;
import com.fenzx.ServiceImpls.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class JumpController {

    @Autowired
    TeacherService teacherService;
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    ProblemService problemService;

    @RequestMapping("login")
    public String login(String tid, String password, HttpSession session) {
//这里的tid可能是学号，教师号或者管理员账号

//先判断是否为老师
        Teacher teacher = teacherService.findByTidAndPassword(tid, password);
        session.setAttribute("teacher", teacher);
        if (teacher != null) {
            return "teacher.html";
        }
//再判断是否为admin
        Admin admin = adminService.findByUsernameAndPasswd(tid, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            List<Problem> unsignedProblems = problemService.findAllProblemByResolved(0);
            session.setAttribute("unsignedProblemsSize", unsignedProblems.size());

            return "admin.html";
        }
//        最后判断是否为学生
        Student student = studentService.findBySidAndPasswd(tid, password);
        if (student != null) {
            session.setAttribute("student", student);
            return "student.html";
        }

//否则用户名或密码错误
        else return "login.html";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("teacher");
        session.removeAttribute("admin");
        session.removeAttribute("student");
        return "login";
    }

    @RequestMapping("admin.html")
    public String admin() {
        return "admin";
    }


    @RequestMapping("login.html")
    public String login() {
        return "login";
    }

    @RequestMapping("teacher.html")
    public String teacher() {
        return "teacher";
    }

//    @RequestMapping("teacherAllProblem.html")
//    public String teacherAllProblem() {
//        return "teacherAllProblem";
//    }

    @RequestMapping("student.html")
    public String student() {
        return "student";
    }


    @RequestMapping("teacherExportData.html")
    public String teacherExportData() {
        return "teacherExportData";
    }

    @RequestMapping("teacherProblemDetail.html")
    public String teacherProblemDetail() {
        return "teacherProblemDetail";
    }


    @RequestMapping("teacherSolvedProblem.html")
    public String teacherSolvedProblem() {
        return "teacherSolvedProblem";
    }

    @RequestMapping("teacherUnsolvedProblem.html")
    public String teacherUnsolvedProblem() {
        return "teacherUnsolvedProblem";
    }


}
