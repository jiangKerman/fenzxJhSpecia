package com.fenzx.test;

import com.fenzx.ServiceImpls.ProblemService;
import com.fenzx.entity.Problem;
import org.apache.http.HttpResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ControllerTest {
    @Autowired
    ProblemService problemService;

    @RequestMapping("test")
    @ResponseBody
    public String test(String p1, ModelMap modelMap) {
        modelMap.put("tt", "tt");
        return "hello" + p1;
    }

    @RequestMapping("toTest")
    public String totest(String p1) {
        return "forward:/test?param1=v1";
    }

    @RequestMapping("file")
    @ResponseBody
    public OutputStream file(HttpServletResponse response) throws IOException {
        Workbook wb=new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        sheet.createRow(1).createCell(1).setCellValue("SB java");
        OutputStream output=response.getOutputStream();

        response.reset();
        response.setHeader("Content-disposition", "attachment; filename="+"SB.xls");
        wb.write(output);
        output.flush();
        output.flush();


//                // 以byte流的方式打开文件 文件
//        FileInputStream hFile = new FileInputStream("工作日志.txt");
//        // 获得到文件
//        File file = new File("工作日志.txt".trim());
//        // 设置响应流为下载  并加上返回文件的名称 增加utf-8设置防止乱码
//        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
//        // 获取文件有多大
//        int i = hFile.available();
//        // 生成对应大小字节
//        byte data[] = new byte[i];
//        //读数据
//        hFile.read(data);
//        //得到向客户端输出二进制数据的对象
//        OutputStream toClient = response.getOutputStream();
//        //输出读出的数据
//        toClient.write(data);
//        // 刷新流
//        toClient.flush();
//        // 关闭流
//        toClient.close();
//        // 关闭流
//        hFile.close();
//        // 返回输出流
        return output;
    }


}
