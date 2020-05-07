package com.fenzx.ServiceImpls;

import com.fenzx.Repos.AdminRepo;
import com.fenzx.Repos.ProblemRepo;
import com.fenzx.Repos.StudentRepo;
import com.fenzx.entity.Admin;
import com.fenzx.entity.Problem;
import com.fenzx.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    ProblemRepo problemRepo;
    @Autowired
    StudentRepo studentRepo;

    public Admin findByUsernameAndPasswd(String username, String password) {
        return adminRepo.findByUsernameAndPasswd(username, password);
    }

    public Page<Student> findAllStudent(int startPage) {
        PageRequest pageRequest=PageRequest.of(startPage-1,10,new Sort(Sort.Direction.ASC,"sid"));
        Page<Student> studentPage = studentRepo.findAll(pageRequest);
        return studentPage;
    }

}
