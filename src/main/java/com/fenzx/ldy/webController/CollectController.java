package com.fenzx.ldy.webController;

import com.fenzx.entity.Student;
import com.fenzx.ldy.dal.entity.CollectProject;
import com.fenzx.ldy.dal.entity.CollectRec;
import com.fenzx.ldy.dal.repo.ClctProjectRepo;
import com.fenzx.ldy.dal.repo.ClctRecRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CollectController {
    @Autowired
    ClctProjectRepo clctProjectRepo;
    @RequestMapping("collectProject")
    @ResponseBody
    public String collectProject(HttpSession session, ModelMap modelMap , String pid){
        System.out.println("收藏pid："+pid);
        String msg;
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请登录后收藏";
        }
        CollectProject cp=clctProjectRepo.findBySidAndPid(stu.getSid(),Integer.parseInt(pid));
        if(cp!=null){
            return "已收藏";
        }
        CollectProject clctProject=new CollectProject();
        clctProject.setPid(Integer.parseInt(pid));
        clctProject.setSid(stu.getSid());
        clctProjectRepo.save(clctProject);
        msg="200";
        return msg;
    }
    @Autowired
    ClctRecRepo clctRecRepo;
    @RequestMapping("collectRec")
    @ResponseBody
    public String collectRec(HttpSession session,ModelMap modelMap ,String recid){
        System.out.println("收藏recid："+recid);
        String msg;
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请登录后收藏";
        }
        CollectRec cr=clctRecRepo.findBySidAndRecid(stu.getSid(),Integer.parseInt(recid));
        if(cr!=null){
            msg="已收藏";
            return msg;
        }
        CollectRec clctRec=new CollectRec();
        clctRec.setRecid(Integer.parseInt(recid));
        clctRec.setSid(stu.getSid());
        clctRecRepo.save(clctRec);
        msg="200";
        return msg;
    }
    @RequestMapping("deleteClctRec")
    @ResponseBody
    public String deleteClctRec(HttpSession session,ModelMap modelMap ,String crid){
        System.out.println("删除recid："+crid);
        String msg;
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请先登录";
        }
        CollectRec collectRec=clctRecRepo.findById(Integer.parseInt(crid)).get();
        System.out.println(collectRec);
        if(!collectRec.getSid().equals(stu.getSid())){
            return "对不起，您无权操作";
        }else{
            System.out.println("删除rec");
            clctRecRepo.deleteRec(Integer.parseInt(crid));
            msg="200";
            return msg;
        }
    }
    @RequestMapping("deleteClctProject")
    @ResponseBody
    public String deleteClctProject(HttpSession session,ModelMap modelMap ,String cpid){
        System.out.println("收藏pid："+cpid);
        String msg;
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请先登录";
        }
        CollectProject collectProject=clctProjectRepo.findById(Integer.parseInt(cpid)).get();
        if(!collectProject.getSid().equals(stu.getSid())){
            return "对不起，您无权操作";
        } else{
            System.out.println("删除cpid：");
            clctProjectRepo.deleteProject(Integer.parseInt(cpid));
            msg="200";
            return msg;
        }
    }
}
