package com.fenzx.ldy.dal.repo;


import com.fenzx.ldy.dal.entity.Hiring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HiringRepo extends JpaRepository<Hiring,Integer> {
    List<Hiring> findByRecid(int recid);
}
