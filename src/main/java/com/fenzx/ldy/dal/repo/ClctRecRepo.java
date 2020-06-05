package com.fenzx.ldy.dal.repo;

import com.fenzx.ldy.dal.entity.CollectRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface ClctRecRepo extends JpaRepository<CollectRec,Integer> {
    @Query(value = "select recruitment_info.id as recid,collect_rec.id as crid,recruitment_info.title,recruitment_info.publish_time,recruitment_info.quality from recruitment_info,collect_rec where collect_rec.sid=?1 and recruitment_info.id=collect_rec.recid order by recruitment_info.publish_time",nativeQuery = true)
    List<Map<String,Object>> selectClctRec(String sid);
    CollectRec findBySidAndRecid(String sid, Integer recid);
    @Transactional
    @Modifying
    @Query(value = "delete from collect_rec where id=?1",nativeQuery = true)
    void deleteRec(Integer recid);
}
