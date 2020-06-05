package com.fenzx.ldy.dal.repo;

import com.fenzx.ldy.dal.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project,Integer> {
    @Query(value = "SELECT * FROM project WHERE p_name LIKE CONCAT('%', ?1, '%') order by publish_time DESC LIMIT ?2,?3", nativeQuery = true)
    List<Project> pagePrjByKey(String key, Integer begin, Integer pageSize);
    @Query(value = "SELECT count(*) FROM project WHERE p_name LIKE CONCAT('%', ?1, '%') order by publish_time DESC LIMIT ?2,?3", nativeQuery = true)
    int countpagePrjByKey(String key, Integer begin, Integer pageSize);
    @Query(value = "SELECT * FROM project WHERE p_name LIKE CONCAT('%', ?1, '%') and id=?2", nativeQuery = true)
    Project pageByIdAndPName(String key, int id);
    Optional<Project> findById(Integer id);
    List<Project> findByStuNo(String sid);
}
