package com.fenzx.ldy.service.imp;

import com.fenzx.ldy.dal.entity.Hiring;
import com.fenzx.ldy.dal.repo.HiringRepo;
import com.fenzx.ldy.service.HiringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class HiringImp implements HiringService {
    @Autowired
    HiringRepo hiringRepo;
    private static HiringImp hiringImp ;
    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        hiringImp = this;
        hiringImp.hiringRepo=this.hiringRepo;
        // 初使化时将已静态化的testService实例化
    }
    @Override
    public List<Hiring> findByRecid(Integer recid) {
        return hiringImp.hiringRepo.findByRecid(recid);
    }

    @Override
    public void insert(Hiring hiring) {
        hiringImp.hiringRepo.save(hiring);
    }
}
