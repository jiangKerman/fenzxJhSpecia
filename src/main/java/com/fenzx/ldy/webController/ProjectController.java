package com.fenzx.ldy.webController;

import com.fenzx.entity.Student;
import com.fenzx.ldy.dal.entity.*;
import com.fenzx.ldy.dal.repo.*;
import com.fenzx.ldy.webController.DataStruct.PageProjectParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
public class ProjectController {
    @Autowired
    FieldRepo fieldRepo;
    @RequestMapping("/publishProject.html")
    public String projectsub(ModelMap modelMap,HttpSession session){
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "redirect:login.html";
        }
        List<Field> fields=fieldRepo.findAll();
        for(Field f:fields){
            if(f.getFName().equals("其他")){
                fields.remove(f);
                fields.add(f);
                break;
            }
        }
        modelMap.put("fields",fields);
        return "publishProject";
    }
    @RequestMapping("/addField")
    @ResponseBody
    public String addField(String fieldName){
        String msg="";
        System.out.println(fieldRepo.findByFName(fieldName));
        if(fieldName.equals("其他")||fieldRepo.findByFName(fieldName)!=null){
            msg="201";
            return msg;
        }
        Field field=new Field();
        field.setFName(fieldName);
        msg=fieldRepo.save(field).getId().toString();
        return msg;
    }
    @Autowired
    FpPepo fpPepo;
    @Autowired
    ProjectRepo projectRepo;
    @RequestMapping(value = "/submitProject",method = RequestMethod.POST)
    public String subProject(String projectName, String[] field, String evolve, String survey,
                             ModelMap modelMap, HttpSession session,String remark,
                             @RequestParam(required = false, defaultValue = "")String projectid){
        System.out.println(projectName);
        System.out.println(field);
        System.out.println(evolve);
        System.out.println(survey);
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "redirect:login.html";
        }
        String msg="";
        if((projectName==null||projectName.equals("")||projectName.length()==0)||
                (evolve==null||evolve.equals("")||evolve.length()==0)||
                (survey==null||survey.equals("")||survey.length()==0)||field.length==0){
            //字段未填完
            msg="201";
            modelMap.put("msg",msg);
            List<Field> fields=fieldRepo.findAll();
            for(Field f:fields){
                if(f.getFName().equals("其他")){
                    fields.remove(f);
                    fields.add(f);
                    break;
                }
            }
            modelMap.put("fields",fields);
            return "publishProject";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(new Date());
        Project project=new Project();
        project.setPublishTime(time);
        if(!projectid.equals("")){
            project.setId(Integer.parseInt(projectid));
            for(String f:field){
                int fid=Integer.parseInt(f);
                fpPepo.deleteByFid(fid);
            }
        }
        project.setEvolve(evolve);
        project.setStuNo(stu.getSid());
        project.setSurvey(survey);
        project.setPName(projectName);
        project.setRemark(remark);
        int pid=projectRepo.save(project).getId();
        for(String f:field){
            FieldforPj fieldforPj=new FieldforPj();
            int fid=Integer.parseInt(f);
            fieldforPj.setFid(fid);
            fieldforPj.setPid(pid);
            fpPepo.save(fieldforPj);
        }
        msg="200";
        modelMap.put("msg",msg);
        List<Field> fields=fieldRepo.findAll();
        for(Field f:fields){
            if(f.getFName().equals("其他")){
                fields.remove(f);
                fields.add(f);
                break;
            }
        }
        modelMap.put("fields",fields);
        return "publishProject";
    }
    @RequestMapping("projectlist")
    public String projectPage(ModelMap modelMap, @RequestParam(required = false, defaultValue = "1") String pageIdx,
                       @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false, defaultValue = "0")String field,
                       @RequestParam(required = false, defaultValue = "")String key ){
        int pageIdxint=Integer.parseInt(pageIdx);
        int begin=(pageIdxint-1)*pageSize;
        int count=0;
        int fid=Integer.parseInt(field);
        System.out.println("fid:"+fid);
        List<Project> projects=new ArrayList<>();
        if(fid==0){
            count=projectRepo.countpagePrjByKey(key,begin,pageSize);
            projects=projectRepo.pagePrjByKey(key,begin,pageSize);
        }else {
            List<FieldforPj> flist=fpPepo.findByFid(fid);
            if(flist.size()!=0){
                for(FieldforPj fp:flist){
                    System.out.println(fp);
                    Project project=projectRepo.pageByIdAndPName(key,fp.getPid());
                    if(project!=null){
                        projects.add(project);
                    }
                }
                count=flist.size();
            }
        }
        PageProjectParam page=new PageProjectParam();
        page.setCount(count);
        page.setCurr(pageIdxint);
        page.setField(fid);
        page.setKey(key);
        List<Field> fields=fieldRepo.findAll();
        modelMap.put("fields",fields);
        modelMap.put("pageParam",page);
        modelMap.put("projects",projects);
        return "projectPage";
    }
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    ClctProjectRepo clctProjectRepo;
    @RequestMapping("/projectInfo")
    public String projectInfo(String projectid, HttpSession session, ModelMap modelMap){
        Student stu=(Student) session.getAttribute("student");
        if(session.getAttribute("teacher")==null&&session.getAttribute("teacher")==null&&stu==null){
            return "redirect:login.html";
        }
        String isCollect="0";
        if(stu!=null){
            CollectProject collectProject=clctProjectRepo.findBySidAndPid(stu.getSid(),Integer.parseInt(projectid));
            if(collectProject!=null){
                isCollect="1";
            }
        }
        int pid=Integer.parseInt(projectid);
        Optional<Project> projectOptional=projectRepo.findById(pid);
        if(!projectOptional.isPresent()){
            return "notexit";
        }
        Project project=projectOptional.get();
        List<Comment> comments=commentRepo.findByPid(pid);
        modelMap.put("project",project);
        modelMap.put("comments",comments);
        modelMap.put("isCollect",isCollect);
        return "projectInfo";
    }
    @RequestMapping("deleteproject")
    @ResponseBody
    public String deleteproject(HttpServletRequest request, HttpSession session){
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "请先登录";
        }
        String projectid=request.getParameter("projectid");
        System.out.println(projectid);
        Optional<Project> projectOptional=projectRepo.findById(Integer.parseInt(projectid));
        if(!projectOptional.isPresent()){
            return "notexit";
        }
        Project project=projectOptional.get();
        if(!project.getStuNo().equals(stu.getSid())){
            System.out.println("不具有操作权限");
            return "对不起，您没有操作权限";
        }
        projectRepo.deleteById(Integer.parseInt(projectid));
        String msg="200";
        return msg;
    }

    @RequestMapping("/alterProject")
    public String alterProject(String projectid, HttpSession session, ModelMap modelMap){
        Student stu=(Student) session.getAttribute("student");
        if(stu==null){
            return "redirect:login.html";
        }
        Optional<Project> projectOptional=projectRepo.findById(Integer.parseInt(projectid));
        if(!projectOptional.isPresent()){
            return "notexit";
        }
        Project project=projectOptional.get();
        System.out.println(project);
        if(!project.getStuNo().equals(stu.getSid())){
            System.out.println("不具有操作权限");
            return "limit";
        }
        List<Field> fields=fieldRepo.findAll();
        modelMap.put("project",project);
        List<FieldforPj> fps=fpPepo.findAllByPid(project.getId());
        List<Integer> fids=new ArrayList<>();
        for(FieldforPj fp:fps){
            fids.add(fp.getFid());
        }
        modelMap.put("fids",fids);
        modelMap.put("fields",fields);
        return "alterProject";
    }
}
