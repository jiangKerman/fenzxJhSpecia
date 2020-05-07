package com.fenzx.Repos;

import com.fenzx.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Integer> {
    Student findBySidAndPasswd(String sid,String passwd);
    Student findBySid(String sid);

    @Override
    Page<Student> findAll(Pageable pageable);
}
