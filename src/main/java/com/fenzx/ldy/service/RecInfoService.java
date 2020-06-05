package com.fenzx.ldy.service;


import com.fenzx.ldy.dal.entity.RecruitmentInfo;

import java.util.List;

public interface RecInfoService {
    List<RecruitmentInfo> findRecToHome();
    List<RecruitmentInfo> findInterToHome();
    List<RecruitmentInfo> findPreachToHome();
    List<RecruitmentInfo> findRecommnedHome();
    RecruitmentInfo insert(RecruitmentInfo recruitmentInfo);
    RecruitmentInfo findbyid(int id);
    List<RecruitmentInfo> findPage(String pageIdx, Integer pageSize, String quality, String postName, String employer,
                                   String profess, String workUnitsite);
}
