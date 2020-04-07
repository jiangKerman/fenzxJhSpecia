package com.fenzx.ServiceImpls;

import com.fenzx.Repos.TeacherRepo;
import com.fenzx.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherRepo teacherRepo;
    public Teacher findByTidAndPassword(String tid,String password){

        return teacherRepo.findByTidAndPasswd(tid, password);
    }

    public List<Teacher> findAllTeacher(){
        return teacherRepo.findAll();
    }
    public Teacher findByTid(String tid){
        return teacherRepo.findByTid(tid);
    }

}
