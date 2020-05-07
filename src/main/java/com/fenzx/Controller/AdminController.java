package com.fenzx.Controller;

import com.fenzx.ServiceImpls.AdminService;
import com.fenzx.ServiceImpls.ProblemService;
import com.fenzx.ServiceImpls.StudentService;
import com.fenzx.ServiceImpls.TeacherService;
import com.fenzx.entity.Problem;
import com.fenzx.entity.Student;
import com.fenzx.entity.Teacher;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    TeacherService teacherService;
    @Autowired
    AdminService adminService;
    @Autowired
    ProblemService problemService;
    @Autowired
    StudentService studentService;

    @RequestMapping("autoAssign")
    public String autoAssign(HttpSession session, ModelMap modelMap) {
        List<Problem> unsignedProblem = problemService.findAllProblemByResolved(0);
        List<Teacher> allTeacher = teacherService.findAllTeacher();
        for (Problem problem : unsignedProblem) {
            //每一个问题都需要一个size变量存放老师负责的最少问题数,以及对应老师的tid
            int size = 99999;
            String minTid = "0";
            for (Teacher teacher : allTeacher) {
                //1、学生问的问题类型要和老师的擅长类型匹配
                String problemType = problem.getProblemType();
                String teacherExpertise = teacher.getExpertise();
                if (teacherExpertise.contains(problemType)) {
                    //类型匹配成功，判断老师负责的问题数量
                    //2、如果有多个擅长同一个问题的老师，则分配给负责问题最少的一个（均匀分配）
                    String tid = teacher.getTid();//当前问题的tid
                    int currentSize = problemService.findAllProblemByTid(tid).size();//当前老师负责的问题数量
                    if (currentSize < size) {
                        size = currentSize;
                        minTid = tid;
                    }
                }

                //3、若问题类型匹配不上，则分配给负责问题最少的一个
            }
            problem.setTid(minTid);
            problem.setResolved(1);
            problemService.saveProblem(problem);
        }
//要把未分配问题置为0
        session.setAttribute("unsignedProblemsSize", problemService.findAllProblemByResolved(0).size());//理论上应该是0
        modelMap.put("message", "成功分配所有问题！");
        return "admin/admin";
    }

    @RequestMapping("teacherList")
    public String showTeacherList(ModelMap modelMap) {
        List<Teacher> allTeacher = teacherService.findAllTeacher();
        modelMap.put("allTeacher", allTeacher);
        return "admin/adminShowTeacherList";
    }

    @RequestMapping("loginAsTeacher")
    public String loginAsTeacher(String tid, HttpSession session) {
        Teacher teacher = teacherService.findByTid(tid);
        session.setAttribute("teacher", teacher);
        return "teacher/teacher";
    }

    //    列出待分配问题名单
    @RequestMapping("adminAssignTeacher.html")
    public String adminAssignTeacher(ModelMap modelMap, HttpSession session) {
//        这里查询出来的是tid和sid，没有关联学生老师的姓名
//        List<Problem> unsignedProblem = adminService.findUnsignedProblem();
//        modelMap.put("unsignedProblem", unsignedProblem);

        List<Object> unsignedProblem = problemService.findUnsignedProblemDetail(0);
        modelMap.put("unsignedProblem", unsignedProblem);


        List<Teacher> teacherList = teacherService.findAllTeacher();
        modelMap.put("teacherList", teacherList);

        session.setAttribute("unsignedProblemsSize", unsignedProblem.size());
        return "admin/adminAssignTeacher";
    }

    //    给问题分配确定老师执行的方法
    @RequestMapping("adminSubmitAssignTeacher")
    public String adminSubmitAssignTeacher(String modelPid, String modelTid, HttpSession session) {
        Problem problem = problemService.findById(Integer.parseInt(modelPid));
        problem.setResolved(1);
        problem.setTid(modelTid);
        problemService.saveProblem(problem);
//        指派老师后，未指定的问题数量减1，要更新
        session.setAttribute("unsignedProblemsSize", problemService.findAllProblemByResolved(0).size());
        return "forward:/adminAssignTeacher.html";
    }


    @RequestMapping("adminShowAllproblem")
    public String adminShowAllproblem(ModelMap modelMap) {
        List<Object> problemDetailListObj = problemService.findAllProblemDetail();
        modelMap.put("problemDetailListObj", problemDetailListObj);

//        List<ProblemDetail> problemDetails = new ArrayList<>();
//        for (Object problemObj : problemDetailListObj) {
//            Object[] problem = (Object[]) problemObj;
//            for (Object col : problem) {
//                System.out.println(col);
//            }
//        }

        return "admin/adminShowAllProblem";
    }

    @RequestMapping("adminShowResolvedProblem")
    public String adminShowResolvedProblem(ModelMap modelMap) {
        List<Object> problemDetailListObj = problemService.findAllProblemDetailByResolved("2");
        modelMap.put("problemDetailListObj", problemDetailListObj);
        return "adminShowResolvedProblem";
    }

    @RequestMapping("adminShowResolvingProblem")
    public String adminShowResolvingProblem(ModelMap modelMap) {
        List<Object> problemDetailListObj = problemService.findAllProblemDetailByResolved("1");
        modelMap.put("problemDetailListObj", problemDetailListObj);
        return "admin/adminShowResolvingProblem";
    }

    @RequestMapping("adminExportAllProblem")
    @ResponseBody
    public OutputStream adminExportAllProblem(ModelMap modelMap, HttpServletResponse response) throws IOException {
        //开始写入excel内容
        List<Object> allProblemDetail = problemService.findAllProblemDetail();
        Workbook wb = new HSSFWorkbook();//对应一个excel文件
        Sheet sheet = wb.createSheet();
        //给excel表头赋值，占第一行
        String[] th = {"pid", "detail", "freeTime", "problemType", "resolved", "time", "title", "teacherName", "tid", "teacherTel", "expertise", "studentName", "sid", "department", "major", "studentTel"};
        Row titleRow = sheet.createRow(0);
        for (int i = 0; i < th.length; i++) {
            titleRow.createCell(i).setCellValue(th[i]);
        }
        //给excel表头赋值end
        for (int i = 0; i < allProblemDetail.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Object[] problem = (Object[]) allProblemDetail.get(i);
            for (int j = 0; j < problem.length; j++) {
                if (problem[j] != null) {
                    row.createCell(j).setCellValue(problem[j].toString());
                }
            }
        }
        //excel内容写入完毕

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String date = dateFormat.format(new Date());
        String fileName = date + "管理员导出问题.xls";
        //不再需要生成excel文件，直接返回输出流给客户端进行下载

        OutputStream responseOutputStream = response.getOutputStream();
        response.reset();

        response.setHeader("Content-disposition",
                "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        wb.write(responseOutputStream);
        wb.close();

        responseOutputStream.flush();

        return responseOutputStream;
    }

    @RequestMapping("studentManagement")
    public String studentManagement(ModelMap modelMap, @RequestParam(required = false, defaultValue = "1") int startPage) {
        Page<Student> studentPage = adminService.findAllStudent(startPage);
        modelMap.put("studentPage", studentPage);
        return "admin/studentManagementPage";
    }

    @RequestMapping("addstudent")
    public String addStudent(String sid, String name, String tel, String gender, String department, String major) {
        Student s = new Student();
        s.setSid(sid);
        s.setName(name);
        s.setTel(tel);
        s.setGender(gender);
        s.setDepartment(department);
        s.setMajor(major);
        s.setPasswd(sid);
        studentService.saveStudent(s);
        return "forward:/studentManagement";
    }

    //    https://my.oschina.net/u/3677987/blog/2988429
    @RequestMapping("addStudentFromExcel")
    public String addStudentFromExcel(MultipartFile inputFile) throws IOException {
        Workbook workbook = null;
        InputStream inputStream = inputFile.getInputStream();
        List<String[]> list = new ArrayList<String[]>();
        workbook = WorkbookFactory.create(inputStream);
        int numberOfSheets = workbook.getNumberOfSheets();
//        Student student = new Student();

         if (numberOfSheets > 0) {
            Sheet sheet = workbook.getSheetAt(0);
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
//            第0行和第1行时title和表头
            for (int rowNum = 2; rowNum <= lastRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);

//                Student student=new Student(row.getCell(0).toString(),row.getCell(1).toString(),row.getCell(2).toString(),row.getCell(3).toString(),row.getCell(4).toString(),row.getCell(5).toString(),row.getCell(0).toString());

//
//                student.setSid(row.getCell(0).toString());
//                student.setName(row.getCell(1).toString());
//                student.setTel(row.getCell(2).toString());
//                student.setGender(row.getCell(3).toString());
//                student.setDepartment(row.getCell(4).toString());
//                student.setMajor(row.getCell(5).toString());
////                密码和学号一样
//                student.setPasswd(row.getCell(0).toString());
                studentService.saveStudent(new Student(row.getCell(0).toString(),row.getCell(1).toString(),row.getCell(2).toString(),row.getCell(3).toString(),row.getCell(4).toString(),row.getCell(5).toString(),row.getCell(0).toString()));

//                for (int i = 0; i < 6; i++) {
//                    System.out.print(row.getCell(i));
//                }
                System.out.println(rowNum + "行结束");
            }
        }

        return "forward:/studentManagement";
    }

}
