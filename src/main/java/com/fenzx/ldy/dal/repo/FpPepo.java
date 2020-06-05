package com.fenzx.ldy.dal.repo;

import com.fenzx.ldy.dal.entity.FieldforPj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FpPepo extends JpaRepository<FieldforPj,Integer> {
    List<FieldforPj> findByFid(Integer fid);
    List<FieldforPj> findAllByPid(Integer pid);
    @Transactional
    @Modifying
    @Query(value = "delete from fieldfor_pj where fid=?1",nativeQuery = true)
    void deleteByFid(Integer fid);
}
