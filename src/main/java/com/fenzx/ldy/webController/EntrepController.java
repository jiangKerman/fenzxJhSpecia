package com.fenzx.ldy.webController;

import com.fenzx.entity.Admin;
import com.fenzx.entity.Student;
import com.fenzx.entity.Teacher;
import com.fenzx.ldy.dal.entity.EntrepInfo;
import com.fenzx.ldy.dal.entity.EntrepPolicy;
import com.fenzx.ldy.dal.repo.EntrepInfoRepo;
import com.fenzx.ldy.dal.repo.EntrepPolicyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class EntrepController {
    @RequestMapping("publishEntrep.html")
    public String publishEntrep(HttpSession session){
        Teacher teacher=(Teacher)session.getAttribute("teacher");
        Admin admin=(Admin) session.getAttribute("admin");
        Student student=(Student) session.getAttribute("student");
        if(admin==null&&teacher==null){
            if(student!=null){
                return "limit";
            }else{
                return "redirect:login.html";
            }
        }
        return "publishEntrep";
    }
    @Autowired
    EntrepInfoRepo entrepInfoRepo;
    @Autowired
    EntrepPolicyRepo entrepPolicyRepo;
    @RequestMapping("/submitEntrep")
    public String submitEntrep(ModelMap modelMap, HttpSession session, String title, String survey, String quality){
        Teacher teacher=(Teacher)session.getAttribute("teacher");
        Admin admin=(Admin) session.getAttribute("admin");
        Student student=(Student) session.getAttribute("student");
        if(student==null&&admin==null&&teacher==null){
            return "redirect:login.html";
        }else if(student!=null&&admin==null&&teacher==null){
            return "limit";
        }
        System.out.println(title+survey+quality);
        String msg="";
        if((title==null||title.equals("")||title.length()==0)||
                (quality==null||quality.equals("")||quality.length()==0)||
                (survey==null||survey.equals("")||survey.length()==0)){
            msg="201";
            modelMap.put("msg",msg);
            return "publishEntrep";
        }
        System.out.println("1");
        SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        if(quality.equals("资讯")){
            System.out.println("资讯");
            EntrepInfo entrepInfo=new EntrepInfo();
            entrepInfo.setDetail(survey);
            entrepInfo.setPublishTime(df.format(new Date()));
            entrepInfo.setTitle(title);
            System.out.println(entrepInfo);
            entrepInfoRepo.save(entrepInfo);
        }else if(quality.equals("政策")){
            System.out.println("政策");
            EntrepPolicy entrepPolicy=new EntrepPolicy();
            entrepPolicy.setDetail(survey);
            entrepPolicy.setTitle(title);
            entrepPolicy.setPublishTime(df.format(new Date()));
            System.out.println(entrepPolicy);
            entrepPolicyRepo.save(entrepPolicy);
        }
        msg="200";
        modelMap.put("msg",msg);
        return "publishEntrep";
    }
    @RequestMapping("/entrepInfo")
    public String entrepInfo(ModelMap modelMap,String id){
        System.out.println(id);
        EntrepInfo entrep=entrepInfoRepo.findById(Integer.parseInt(id)).get();
        modelMap.put("entrep",entrep);
        modelMap.put("msg",200);
        return "entrepDetail";
    }
    @RequestMapping("/entrepPolicy")
    public String entrepPolicy(ModelMap modelMap,String id){
        SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        EntrepPolicy entrep=entrepPolicyRepo.findById(Integer.parseInt(id)).get();
        modelMap.put("entrep",entrep);
        modelMap.put("msg",200);
        return "entrepDetail";
    }
}
