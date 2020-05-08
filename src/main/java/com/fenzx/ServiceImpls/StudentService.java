package com.fenzx.ServiceImpls;

import com.fenzx.Repos.ProblemRepo;
import com.fenzx.Repos.StudentRepo;
import com.fenzx.entity.Problem;
import com.fenzx.entity.Student;
import com.fenzx.ldy.RecruitmentInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    ProblemRepo problemRepo;
    @Autowired
    RecruitmentInfoRepo recruitmentInfoRepo;

    public Student findBySidAndPasswd(String sid,String passwd){
        return studentRepo.findBySidAndPasswd(sid,passwd);
    }
    public List<Problem> findAllProblemBySid(String sid){
        return problemRepo.findAllBySid(sid);
    }
    public Student findBySid(String sid){
        return studentRepo.findBySid(sid);
    }

    public Student saveStudent(Student student){
        studentRepo.save(student);
        return student;
    }

    public List<Object> findAllRecruitmentWithSalary(){
        return recruitmentInfoRepo.findAllRecruitmentWithSalary();
    }
    public List<Object> findAllRecruitmentWithSalaryLimit(int start,int size){
        return recruitmentInfoRepo.findAllRecruitmentWithSalaryLimit(start,size);
    }
    public List<Object> findAllRecruitmentWithSalaryLimitWithMajor(String major,int size){
        return recruitmentInfoRepo.findALlRecruitmentWithSalaryLimitWithMajor(major,size);
    }
}
