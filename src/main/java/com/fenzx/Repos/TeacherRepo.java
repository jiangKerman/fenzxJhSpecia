package com.fenzx.Repos;


import com.fenzx.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepo extends JpaRepository<Teacher,Integer> {
    Teacher findByTidAndPasswd(String tid,String password);
    List<Teacher> findAll();
    Teacher findByTid(String tid);
}
