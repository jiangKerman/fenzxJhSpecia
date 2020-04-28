package com.fenzx.Controller;

import com.fenzx.ServiceImpls.ProblemService;
import com.fenzx.ServiceImpls.StudentService;
import com.fenzx.entity.Chat;
import com.fenzx.entity.Problem;
import com.fenzx.entity.Teacher;
import com.fenzx.ServiceImpls.ChatService;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        ;
        modelMap.put("chats", chats);
        modelMap.put("problemId", problemId);
        modelMap.put("student",studentService.findBySid(sid));
        modelMap.put("problem",problemService.findById(problemId));
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

    @RequestMapping("teacherExportAllProblem")
    @ResponseBody
    public OutputStream  teacherExportAllProblem( HttpServletResponse response,HttpSession session) throws IOException {
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        //开始写入excel内容

        List<Object> allProblemDetail = problemService.findProblemDetailsByTid(teacher.getTid());

        Workbook wb = new HSSFWorkbook();//对应一个excel文件
        Sheet sheet = wb.createSheet();
        //给excel表头赋值，占第一行
        String[] th = {"pid", "detail", "freeTime", "problemType", "resolved", "sid", "time", "title", "department", "major", "name", "tel"};
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
        String fileName = date + teacher.getName()+"导出问题.xls";
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

}
