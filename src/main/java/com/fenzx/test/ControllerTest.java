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


        return output;
    }


}
