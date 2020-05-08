package com.fenzx.ldy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// 0 rid
// 5 r.preachSIte
// 10 title
// 15 h.postname
// 17 salary
public interface RecruitmentInfoRepo extends JpaRepository<RecruitmentInfo, Integer> {
    @Query(value = "select r.id, r.data_source,r.delivery_link,r.email,r.employers,r.preach_site,r.preach_time,r.publish_time,r.quality,r.tel,r.title,r.unit_site,r.work_site,\n" +
            "h.education,h.number_post,h.post_name,h.profes_require,h.salary \n" +
            "from recruitment_info as r,hiring  as h where r.id=h.recid order by r.publish_time desc", nativeQuery = true)
    List<Object> findAllRecruitmentWithSalary();

    @Query(value = "select r.id, r.data_source,r.delivery_link,r.email,r.employers,r.preach_site,r.preach_time,r.publish_time,r.quality,r.tel,r.title,r.unit_site,r.work_site,\n" +
            "h.education,h.number_post,h.post_name,h.profes_require,h.salary \n" +
            "from recruitment_info as r,hiring  as h where r.id=h.recid order by r.publish_time desc limit ?1,?2", nativeQuery = true)
    List<Object> findAllRecruitmentWithSalaryLimit(int start,int size);

    @Query(value = "select r.id, r.data_source,r.delivery_link,r.email,r.employers,r.preach_site,r.preach_time,r.publish_time,r.quality,r.tel,r.title,r.unit_site,r.work_site,\n" +
            "h.education,h.number_post,h.post_name,h.profes_require,h.salary \n" +
            "from recruitment_info as r,hiring  as h where (r.id=h.recid  and (h.profes_require like concat('%',?1,'%') or h.profes_require like '%不限%'))    order by r.publish_time desc limit 0,?2", nativeQuery = true)
    List<Object> findALlRecruitmentWithSalaryLimitWithMajor(String major, int size);
}

