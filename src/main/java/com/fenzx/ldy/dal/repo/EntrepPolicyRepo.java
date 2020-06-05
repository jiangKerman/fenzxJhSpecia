package com.fenzx.ldy.dal.repo;

import com.fenzx.ldy.dal.entity.EntrepPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntrepPolicyRepo extends JpaRepository<EntrepPolicy,Integer> {
    @Query(value = "select * from entrep_policy order by publish_time desc limit ?1,?2", nativeQuery = true)
    List<EntrepPolicy> entrepPolicyPage(Integer begin, Integer pageSize);
    @Query(value = "select count(*) from entrep_policy order by publish_time desc ", nativeQuery = true)
    int countEntrepPolicy();
    List<EntrepPolicy> findAllById(Integer integer);
}
