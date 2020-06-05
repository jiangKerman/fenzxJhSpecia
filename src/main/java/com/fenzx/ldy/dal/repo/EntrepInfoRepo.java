package com.fenzx.ldy.dal.repo;

import com.fenzx.ldy.dal.entity.EntrepInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrepInfoRepo extends JpaRepository<EntrepInfo,Integer> {
    @Query(value = "select * from entrep_info order by publish_time desc limit ?1,?2", nativeQuery = true)
    List<EntrepInfo> entrepInfoPage(Integer begin, Integer pageSize);
    @Query(value = "select count(*) from entrep_info order by publish_time desc ", nativeQuery = true)
    int countEntrepInfo();
}
