package com.fenzx.ldy.service.imp;

import com.fenzx.ldy.dal.entity.RecruitmentInfo;
import com.fenzx.ldy.dal.repo.RecInfoRepo;
import com.fenzx.ldy.service.RecInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RecInfoImp implements RecInfoService {
    @Autowired
    RecInfoRepo recInfoRepo;
    private static RecInfoImp recInfoImp ;
    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        recInfoImp = this;
        recInfoImp.recInfoRepo=this.recInfoRepo;
        // 初使化时将已静态化的testService实例化
    }
    @Override
    public List<RecruitmentInfo> findRecToHome() {
        return recInfoImp.recInfoRepo.findRecToHome();
    }
    @Override
    public List<RecruitmentInfo> findInterToHome() {
        return recInfoImp.recInfoRepo.findInterToHome();
    }

    @Override
    public List<RecruitmentInfo> findPreachToHome() {
        return recInfoImp.recInfoRepo.findPreachToHome();
    }
    @Override
    public List<RecruitmentInfo> findRecommnedHome() {
        return recInfoImp.recInfoRepo.findRecommendToHome();
    }

    @Override
    public RecruitmentInfo insert(RecruitmentInfo recruitmentInfo) {
        System.out.println("存入rec");
        System.out.println(recruitmentInfo);
        return recInfoImp.recInfoRepo.save(recruitmentInfo);
    }

    @Override
    public RecruitmentInfo findbyid(int id) {
        return recInfoImp.recInfoRepo.findById(id);
    }

    @Override
    public List<RecruitmentInfo> findPage(String pageIdx, Integer pageSize, String quality, String postName, String employer, String profess, String workUnitsite){
        return null;
    }


}
