package com.fenzx.Repos;

import com.fenzx.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Integer> {
    Student findBySidAndPasswd(String sid,String passwd);
}
