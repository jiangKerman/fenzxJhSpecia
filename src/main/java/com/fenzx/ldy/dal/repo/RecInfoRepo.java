package com.fenzx.ldy.dal.repo;


import com.fenzx.ldy.dal.entity.RecruitmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RecInfoRepo extends JpaRepository<RecruitmentInfo,Integer>{
    @Query(value = "select * from recruitment_info where quality='招聘' order by publish_time desc limit 10", nativeQuery = true)
    List<RecruitmentInfo> findRecToHome();
    @Query(value = "select * from recruitment_info where quality='实习' order by publish_time desc limit 10", nativeQuery = true)
    List<RecruitmentInfo> findInterToHome();
    @Query(value = "select * from recruitment_info where quality='宣讲' order by publish_time desc limit 10", nativeQuery = true)
    List<RecruitmentInfo> findPreachToHome();
    RecruitmentInfo findById(int id);
    @Query(value = "SELECT rec.id,rec.title,rec.employers,rec.publish_time FROM (SELECT * FROM recruitment_info WHERE quality LIKE CONCAT('%', ?1, '%') AND employers LIKE CONCAT('%', ?2, '%') AND work_site LIKE CONCAT('%', ?3, '%')) AS rec  ORDER BY rec.publish_time DESC LIMIT ?4,?5", nativeQuery = true)
    List<Map<String,Object>> pageRec(String quality, String employer, String workSite, Integer begin, Integer pageSize);

    @Query(value = "SELECT rec.id,rec.title,rec.employers,rec.publish_time FROM (SELECT * FROM recruitment_info WHERE quality LIKE CONCAT('%', ?1, '%') AND employers LIKE CONCAT('%', ?3, '%') AND work_site LIKE CONCAT('%', ?5, '%')) AS rec INNER JOIN (SELECT * FROM hiring WHERE post_name LIKE CONCAT('%', ?2, '%') AND profes_require LIKE CONCAT('%', ?4, '%')) AS hir ON rec.`id`=hir.recid ORDER BY rec.publish_time DESC LIMIT ?6,?7", nativeQuery = true)
    List<Map<String,Object>> pageInner(String quality, String postName, String employer, String profess, String workSite, Integer begin, Integer pageSize);

    @Query(value = "SELECT count(*) FROM (SELECT * FROM recruitment_info WHERE quality LIKE CONCAT('%', ?1, '%') AND employers LIKE CONCAT('%', ?2, '%') AND work_site LIKE CONCAT('%', ?3, '%')) AS rec  ORDER BY rec.publish_time DESC", nativeQuery = true)
    int countRec(String quality, String employer, String workSite);

    @Query(value = "SELECT count(*) FROM (SELECT * FROM recruitment_info WHERE quality LIKE CONCAT('%', ?1, '%') AND employers LIKE CONCAT('%', ?3, '%') AND work_site LIKE CONCAT('%', ?5, '%')) AS rec INNER JOIN (SELECT * FROM hiring WHERE post_name LIKE CONCAT('%', ?2, '%') AND profes_require LIKE CONCAT('%', ?4, '%')) AS hir ON rec.`id`=hir.recid ORDER BY rec.publish_time DESC", nativeQuery = true)
    int countInner(String quality, String postName, String employer, String profess, String workSite);

    @Query(value = "select * from recruitment_info order by publish_time desc limit 10", nativeQuery = true)
    List<RecruitmentInfo> findRecommendToHome();
}
