package com.fenzx.ldy.webController;


import com.fenzx.entity.Student;
import com.fenzx.ldy.dal.entity.Comment;
import com.fenzx.ldy.dal.entity.Message;
import com.fenzx.ldy.dal.entity.Project;
import com.fenzx.ldy.dal.repo.CommentRepo;
import com.fenzx.ldy.dal.repo.MessageRepo;
import com.fenzx.ldy.dal.repo.ProjectRepo;
import com.fenzx.ldy.webController.DataStruct.MessageStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class MessageController {
    @Autowired
    CommentRepo commentRepo;
    @RequestMapping("/comment")
    @ResponseBody
    public String comment(String id, String comment, HttpSession session){
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请先登录";
        }
        Comment comment1=new Comment();
        comment1.setCotent(comment);
        comment1.setPid(Integer.parseInt(id));
        comment1.setComid(stu.getSid());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(new Date());
        comment1.setTime(time);
        commentRepo.save(comment1);
        String msg="200";
        return msg;
    }

    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    MessageRepo messageRepo;
    @RequestMapping("privateletter")
    @ResponseBody
    public String privateletter(String pid, String mess,  HttpSession session){
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请先登录";
        }
        Project project=projectRepo.findById(Integer.parseInt(pid)).get();
        String reciverid=project.getStuNo();
        Message message=new Message();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(new Date());
        message.setContent(mess);
        message.setTime(time);
        message.setIsread(0);
        message.setContent(mess);
        message.setReciverid(reciverid);
        message.setRecivecite(1);
        message.setSendcite(1);
        message.setSenderid(stu.getSid());
        messageRepo.save(message);
        String msg="200";
        return msg;
    }

    @RequestMapping("deleterecivemess")
    @ResponseBody
    public String deletemess(HttpServletRequest request, HttpSession session){
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请先登录";
        }
        String messid=request.getParameter("messid");
        Message mess=messageRepo.findById(Integer.parseInt(messid)).get();
        if(!mess.getReciverid().equals(stu.getSid())){
            System.out.println("不具有操作权限");
            return "对不起，您没有操作权限";
        }
        mess.setRecivecite(0);
        if(mess.getSendcite()==1){
            messageRepo.save(mess);
        }else{
            messageRepo.deleteById(Integer.parseInt(messid));
        }
        String msg="200";
        return msg;
    }
    @RequestMapping("deletesendmess")
    @ResponseBody
    public String deletesendmess(HttpServletRequest request, HttpSession session){
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请先登录";
        }
        String messid=request.getParameter("messid");
        Message mess=messageRepo.findById(Integer.parseInt(messid)).get();
        if(!mess.getSenderid().equals(stu.getSid())){
            System.out.println("不具有操作权限");
            return "对不起，您没有操作权限";
        }
        mess.setSendcite(0);
        if(mess.getRecivecite()==1){
            messageRepo.save(mess);
        }else{
            messageRepo.deleteById(Integer.parseInt(messid));
        }
        String msg="200";
        return msg;
    }
    @RequestMapping("messInfo")
    public String messInfo(HttpSession session, String messid, ModelMap modelMap){
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请先登录";
        }
        Message message=messageRepo.findById(Integer.parseInt(messid)).get();
        if(!message.getReciverid().equals(stu.getSid())){
            return "limit";
        }
        Map<String,Object> messmap=messageRepo.selectById(Integer.parseInt(messid));
        MessageStruct mess=new MessageStruct();
        mess.setTime(messmap.get("time").toString());
        mess.setContent(messmap.get("content").toString());
        mess.setSenderName(messmap.get("name").toString());
        mess.setId(messmap.get("id").toString());
        message.setIsread(1);
        messageRepo.save(message);
        modelMap.put("mess",mess);
        return "messInfo";
    }
    @RequestMapping("messInfosender")
    public String messInfosender(HttpSession session, String messid, ModelMap modelMap){
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请先登录";
        }
        Message message=messageRepo.findById(Integer.parseInt(messid)).get();
        if(!message.getSenderid().equals(stu.getSid())){
            return "limit";
        }
        Map<String,Object> messmap=messageRepo.selectById(Integer.parseInt(messid));
        MessageStruct mess=new MessageStruct();
        mess.setTime(messmap.get("time").toString());
        mess.setContent(messmap.get("content").toString());
        mess.setSenderName(messmap.get("name").toString());
        mess.setId(messmap.get("id").toString());
        messageRepo.save(message);
        modelMap.put("mess",mess);
        return "messInfo";
    }

    @RequestMapping("reply")
    public String reply(HttpSession session,String replytext, String messid, ModelMap modelMap){
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请先登录";
        }
        Message message=new Message();
        message.setContent(replytext);
        SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        message.setTime(df.format(new Date()));
        message.setReciverid(messageRepo.findById(Integer.parseInt(messid)).get().getSenderid());
        message.setSenderid(stu.getSid());
        message.setRecivecite(1);
        message.setSendcite(1);
        message.setIsread(0);
        messageRepo.save(message);
        return "redirect:stuhome";
    }

}
