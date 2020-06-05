package com.fenzx.ldy.webController;

import com.fenzx.entity.Admin;
import com.fenzx.entity.Student;
import com.fenzx.entity.Teacher;

import com.fenzx.ldy.dal.entity.CollectRec;
import com.fenzx.ldy.dal.entity.Hiring;
import com.fenzx.ldy.dal.entity.RecruitmentInfo;
import com.fenzx.ldy.dal.repo.ClctRecRepo;
import com.fenzx.ldy.service.imp.HiringImp;
import com.fenzx.ldy.service.imp.RecInfoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class RecInfoController {
    RecInfoImp recInfoImp=new RecInfoImp();
    HiringImp hiringImp=new HiringImp();
    @Autowired
    ClctRecRepo clctRecRepo;
    @RequestMapping("recInfo")
    public String recinfo(String recid, ModelMap modelMap,HttpSession session){
//        System.out.println(id);
        int id= Integer.parseInt(recid);
        Student stu=(Student) session.getAttribute("student");
        RecruitmentInfo rec=recInfoImp.findbyid(id);
        if(rec==null){
            return "notexit";
        }
        List<Hiring> hiringList=hiringImp.findByRecid(id);
        String isCollect="0";
        if(stu!=null){
            CollectRec cr=clctRecRepo.findBySidAndRecid(stu.getSid(),Integer.parseInt(recid));
            if(cr!=null){
                isCollect="1";
            }
        }
        modelMap.put("rec",rec);
        modelMap.put("hiringList",hiringList);
        modelMap.put("isCollect",isCollect);
        return "recDetail";
    }

    @RequestMapping("publishRec.html")
    public String publish( HttpSession session){
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
        return "publishRec";
    }
    @RequestMapping("/submitRec")
    public String submit(ModelMap modelMap, HttpSession session,String title, String employer, String tel, String email, String preachTime
            , String unisite, String worksite, String quality, String survey, String preachSite
            , String[] postname, String[] education, String[] profess, String[] numberpost, String[] salary){
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
        String msg="";
        if((title==null||title.equals("")||title.length()==0)||
                (employer==null||employer.equals("")||employer.length()==0)||
                (quality==null||quality.equals("")||quality.length()==0)){
            msg="201";
            modelMap.put("msg",msg);
            return "publishRec";
        }
        RecruitmentInfo rec=new RecruitmentInfo();
        rec.setTitle(title);
        rec.setQuality(quality);
        rec.setEmployers(employer);
        rec.setTel(tel);
        rec.setEmail(email);
        rec.setUnitSite(unisite);
        rec.setWorkSite(worksite);
        rec.setDetail(survey);
        rec.setPreachTime(preachTime);
        rec.setPreachSite(preachSite);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(new Date());
        rec.setPublishTime(time);
        RecInfoImp recInfoImp=new RecInfoImp();
        int recid=recInfoImp.insert(rec).getId();
        for(int i=0;i<postname.length;i++){
            if(postname[i].length()!=0&&postname[i]!=null&&postname[i]!=""){
                Hiring hiring=new Hiring();
                hiring.setPostName(postname[i]);
                hiring.setEducation(education[i]);
                hiring.setSalary(salary[i]);
                hiring.setRecid(recid);
                hiring.setNumberPost(numberpost[i]);
                hiring.setProfesRequire(profess[i]);
                HiringImp hiringImp=new HiringImp();
                hiringImp.insert(hiring);
            }
        }
        msg="200";
        modelMap.put("msg",msg);
        return "publishRec";
    }

}
