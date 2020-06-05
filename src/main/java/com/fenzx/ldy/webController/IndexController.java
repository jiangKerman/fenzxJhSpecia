package com.fenzx.ldy.webController;

import com.fenzx.entity.Student;
import com.fenzx.ldy.dal.entity.Project;
import com.fenzx.ldy.dal.entity.RecruitmentInfo;
import com.fenzx.ldy.dal.repo.ClctProjectRepo;
import com.fenzx.ldy.dal.repo.ClctRecRepo;
import com.fenzx.ldy.dal.repo.MessageRepo;
import com.fenzx.ldy.dal.repo.ProjectRepo;
import com.fenzx.ldy.service.imp.RecInfoImp;
import com.fenzx.ldy.webController.DataStruct.ClctPrjParam;
import com.fenzx.ldy.webController.DataStruct.ClctRecParam;
import com.fenzx.ldy.webController.DataStruct.MessageStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    RecInfoImp recInfoImp=new RecInfoImp();
    @RequestMapping("/index")
    public String index(ModelMap modelMap ,HttpSession session){
        List<RecruitmentInfo> recInfoHome=recInfoImp.findRecToHome();
        List<RecruitmentInfo> interInfoHome=recInfoImp.findInterToHome();
        List<RecruitmentInfo> preachInfoHome=recInfoImp.findPreachToHome();
        modelMap.addAttribute("recInfoHome",recInfoHome);
        modelMap.addAttribute("interInfoHome",interInfoHome);
        modelMap.addAttribute("preachInfoHome",preachInfoHome);
        Calendar date = Calendar.getInstance();
        int year= date.get(Calendar.YEAR);
        int month=date.get(Calendar.MONTH)+1;
        int day=date.get(Calendar.DAY_OF_MONTH);
        modelMap.addAttribute("year",year);
        modelMap.addAttribute("month",month);
        modelMap.addAttribute("date",day);
        List<RecruitmentInfo> recommend;//
        recommend=recInfoImp.findRecommnedHome();
        modelMap.addAttribute("recommend",recommend);
        return "index";
    }
    @RequestMapping("/entrep")
    public String entrep(ModelMap modelMap){
        return "entrepHome";
    }

    @Autowired
    MessageRepo messageRepo;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    ClctRecRepo clctRecRepo;
    @Autowired
    ClctProjectRepo clctProjectRepo;
    @RequestMapping(value = "/stuhome")
    public String stuHome(ModelMap modelMap,HttpSession session){
        Student student=(Student)session.getAttribute("student");
        if(student==null){
            return "redirect:login.html";
        }
        String stuid=student.getSid();
        List<Map<String,Object>> messagemapList=messageRepo.selectall(stuid);
        List<MessageStruct> allmessage=new ArrayList<>();
        List<MessageStruct> notreadmessage=new ArrayList<>();
        if(messagemapList!=null&&messagemapList.size()!=0){
            for(Map map:messagemapList){
                MessageStruct messageStruct=new MessageStruct();
                messageStruct.setContent(map.get("content").toString());
                messageStruct.setId(map.get("id").toString());
                messageStruct.setSenderName(map.get("name").toString());
                messageStruct.setTime(map.get("time").toString());
                messageStruct.setIsread(map.get("isread").toString());
                System.out.println("message"+messageStruct);
                allmessage.add(messageStruct);
                if(messageStruct.getIsread().equals("0")){
                    notreadmessage.add(messageStruct);
                }
            }
        }
        List<Map<String,Object>> sendmessagemapList=messageRepo.selectallsend(stuid);
        List<MessageStruct> sendmessage=new ArrayList<>();
        if(sendmessagemapList!=null&&sendmessagemapList.size()!=0){
            for(Map map:sendmessagemapList){
                MessageStruct messageStruct=new MessageStruct();
                messageStruct.setContent(map.get("content").toString());
                messageStruct.setId(map.get("id").toString());
                messageStruct.setSenderName(map.get("name").toString());
                messageStruct.setTime(map.get("time").toString());
                System.out.println("sendmessage"+messageStruct);
                sendmessage.add(messageStruct);
            }
        }
        modelMap.put("sendmessage",sendmessage);
        modelMap.put("allmessage",allmessage);
        modelMap.put("notreadmessage",notreadmessage);
        modelMap.put("notreadnum",notreadmessage.size());
        List<Project> projects=projectRepo.findByStuNo(student.getSid());
        modelMap.put("projects",projects);
        List<Map<String,Object>> clctprojectlist=clctProjectRepo.selectClctProject(student.getSid());
        List<ClctPrjParam> cplist=new ArrayList<>();
        for(Map<String,Object> map:clctprojectlist){
            ClctPrjParam cp=new ClctPrjParam();
            cp.setCpid(map.get("cpid").toString());
            cp.setPid(map.get("pid").toString());
            cp.setPName(map.get("p_name").toString());
            cp.setPtime(map.get("ptime").toString());
            cplist.add(cp);
        }
        List<Map<String,Object>> clctreclist=clctRecRepo.selectClctRec(student.getSid());
        List<ClctRecParam> creclist=new ArrayList<>();//招聘
        List<ClctRecParam> cinterlist=new ArrayList<>();//实习
        List<ClctRecParam> cpreachlist=new ArrayList<>();//宣讲
        for(Map<String,Object> map:clctreclist){
            ClctRecParam cr=new ClctRecParam();
            cr.setCrid(map.get("crid").toString());
            cr.setRecid(map.get("recid").toString());
            cr.setTitle(map.get("title").toString());
            cr.setRectime(map.get("publish_time").toString());
            if(map.get("quality").equals("招聘")){
                creclist.add(cr);
            }else if(map.get("quality").equals("实习")){
                cinterlist.add(cr);
            }else if(map.get("quality").equals("宣讲")){
                cpreachlist.add(cr);
            }
        }
        modelMap.put("cplist",cplist);
        modelMap.put("creclist",creclist);
        modelMap.put("cinterlist",cinterlist);
        modelMap.put("cpreachlist",cpreachlist);
        return "stuHome";
    }

}
