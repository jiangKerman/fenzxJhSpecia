package com.fenzx.ldy.dal.repo;

import com.fenzx.ldy.dal.entity.CollectProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface ClctProjectRepo extends JpaRepository<CollectProject,Integer> {
    @Query(value = "select project.id as pid,collect_project.id as cpid,project.publish_time as ptime,project.p_name from project,collect_project where collect_project.sid=?1 and project.id=collect_project.pid order by project.publish_time",nativeQuery = true)
    List<Map<String,Object>> selectClctProject(String sid);
    CollectProject findBySidAndPid(String sid, Integer projectid);
    @Transactional
    @Modifying
    @Query(value = "delete from collect_project where id=?1",nativeQuery = true)
    void deleteProject(Integer pid);
}
