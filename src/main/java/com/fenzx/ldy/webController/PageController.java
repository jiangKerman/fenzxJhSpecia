package com.fenzx.ldy.webController;

import com.fenzx.ldy.dal.entity.EntrepInfo;
import com.fenzx.ldy.dal.entity.EntrepPolicy;
import com.fenzx.ldy.dal.entity.Rec;
import com.fenzx.ldy.dal.repo.EntrepInfoRepo;
import com.fenzx.ldy.dal.repo.EntrepPolicyRepo;
import com.fenzx.ldy.dal.repo.RecInfoRepo;
import com.fenzx.ldy.webController.DataStruct.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {
    @Autowired
    RecInfoRepo recInfoRepo;
    @RequestMapping("infopage")
    public String page(ModelMap modelMap, @RequestParam(required = false, defaultValue = "1") String pageIdx,
                       @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false, defaultValue = "")String quality,
                       @RequestParam(required = false, defaultValue = "")String postName, @RequestParam(required = false, defaultValue = "")String employer,
                       @RequestParam(required = false, defaultValue = "")String profess, @RequestParam(required = false, defaultValue = "")String workSite){
        int pageIdxint=Integer.parseInt(pageIdx);
        int begin=(pageIdxint-1)*pageSize;
        System.out.println(quality+"  "+postName+"  "+employer+"  "+profess+"  "+workSite+"  "+begin+"  "+pageSize);
        int count=0;
        List<Map<String,Object>> mapList;
        if(profess.equals("")&&postName.equals("")){
            count=recInfoRepo.countRec(quality,employer,workSite);
            System.out.println("count: "+count);
            mapList=recInfoRepo.pageRec(quality,employer,workSite,begin,pageSize);
        }else{
            count=recInfoRepo.countInner(quality,postName,employer,profess,workSite);
            System.out.println("count: "+count);
            mapList=recInfoRepo.pageInner(quality,postName,employer,profess,workSite,begin,pageSize);
        }
//        System.out.println(mapList.size());
        List<Rec> recs=new ArrayList<>();
        for(Map map:mapList){
            Rec rec=new Rec();
            rec.setTitle(map.get("title").toString());
            rec.setEmployers(map.get("employers").toString());
            rec.setId(map.get("id").toString());
            rec.setPublishTime(map.get("publish_time").toString());
            recs.add(rec);
        }
        PageParam pageParam=new PageParam();
        pageParam.setCount(count);
        pageParam.setCurr(pageIdxint);
        pageParam.setEmployer(employer);
        pageParam.setPostName(postName);
        pageParam.setQuality(quality);
        pageParam.setWorkSite(workSite);
        pageParam.setProfess(profess);
        modelMap.put("pageParam",pageParam);
        modelMap.put("recs",recs);
        return "recPage";
    }
    @Autowired
    EntrepInfoRepo entrepInfoRepo;
    @RequestMapping("entrepInfoPage")
    public String entrepInfo(ModelMap modelMap, @RequestParam(required = false, defaultValue = "1") String pageIdx,
                       @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        int pageIdxint=Integer.parseInt(pageIdx);
        int begin=(pageIdxint-1)*pageSize;
        int count=0;
        count=entrepInfoRepo.countEntrepInfo();
        List<EntrepInfo> entreps=entrepInfoRepo.entrepInfoPage(begin,pageSize);
        modelMap.put("count",count);
        modelMap.put("curr",pageIdxint);
        modelMap.put("entreps",entreps);
        return "entrepInfoPage";
    }
    @Autowired
    EntrepPolicyRepo entrepPolicyRepo;
    @RequestMapping("entrepPolicyPage")
    public String entrepPolicy(ModelMap modelMap, @RequestParam(required = false, defaultValue = "1") String pageIdx,
                         @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        int pageIdxint=Integer.parseInt(pageIdx);
        int begin=(pageIdxint-1)*pageSize;
        int count=0;
        count=entrepPolicyRepo.countEntrepPolicy();
        List<EntrepPolicy> entrepPolicies=entrepPolicyRepo.entrepPolicyPage(begin,pageSize);
        modelMap.put("count",count);
        modelMap.put("curr",pageIdxint);
        modelMap.put("entreps",entrepPolicies);
        return "entrepPolicyPage";
    }
}
