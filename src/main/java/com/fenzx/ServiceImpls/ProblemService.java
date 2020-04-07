package com.fenzx.ServiceImpls;

import com.fenzx.Repos.ChatRepo;
import com.fenzx.Repos.ProblemRepo;
import com.fenzx.entity.Chat;
import com.fenzx.entity.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ProblemService {
    @Autowired
    ProblemRepo problemRepo;

   public Problem saveProblem(Problem problem){
       problemRepo.save(problem);
       return problem;
   }
    public List<Problem> findAllProblemByTid(String tid) {
        List<Problem> problemList = problemRepo.findAllByTid(tid);
        return problemList;
    }
    public Page<Problem> findALlProblemByTidSortByProblemId(String tid, Integer startPage, Integer size) {
        PageRequest pageRequest = PageRequest.of(startPage - 1, size, new Sort(Sort.Direction.DESC, "id"));
        Page<Problem> problemPage = problemRepo.findAllByTid(tid, pageRequest);

        return problemPage;
    }
//    三表联查询
    public List<Object> findAllProblemDetail(){
       return problemRepo.findAllProblemDetail();
    }

//    student  problem连接查找未分配指导老师的问题
    public List<Object> findUnsignedProblem(Integer resolved){
       return problemRepo.findUnsignedProblem(resolved);
    }

    public List<Object> findProblemDetailsByTid(String tid){
      return  problemRepo.findProblemDetailsByTid(tid);
    }

    public Problem findById(int id){
       return  problemRepo.findById(id);
    }

   public List<Problem> findAllProblemByResolved(int resolved){
       return  problemRepo.findAllByResolved(resolved);
   }

   public List<Object> findProblemDetailsBySid(String sid){
       return problemRepo.findProblemDetailsBySid(sid);
   }

}
