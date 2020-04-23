package com.fenzx.Repos;

import com.fenzx.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemRepo extends JpaRepository<Problem, Integer> {

    Problem findById(int pid);

    //  查询tid老师下的所有问题
    List<Problem> findAllByTid(String tid);

    //  查询tid老师下的所有问题，并且可排序分页
    Page<Problem> findAllByTid(String tid, Pageable pageable);

    List<Problem> findAllByResolved(int resolved);

    List<Problem> findAllBySid(String sid);


    //    teacher  student   problem  一起查询所有问题
    @Query(value = "select problem.id as problem_id,problem.detail,problem.free_time,problem.problem_type,problem.resolved,problem.time,problem.title,\n" +
            "teacher.name as teacher_name,teacher.tid,teacher.tel as teacher_tel,teacher.expertise,\n" +
            "student.name as student_name,student.sid,student.department,student. major,student.tel as student_tel\n" +
            "from problem,teacher,student where problem.tid=teacher.tid && problem.sid=student.sid", nativeQuery = true)
    List<Object> findAllProblemDetail();

    //teacher student problem 查询解决状态为0，1，2 的问题
    @Query(value = "select problem.id as problem_id,problem.detail,problem.free_time,problem.problem_type,problem.resolved,problem.time,problem.title,\n" +
            "teacher.name as teacher_name,teacher.tid,teacher.tel as teacher_tel,teacher.expertise,\n" +
            "student.name as student_name,student.sid,student.department,student. major,student.tel as student_tel\n" +
            "from problem,teacher,student where problem.tid=teacher.tid && problem.sid=student.sid && problem.resolved=?1", nativeQuery = true)
    List<Object> findAllProblemDetailByResolved(String resolved);


    @Query(value = "select p.id as pid, p.detail,p.free_time,p.problem_type,p.resolved,p.sid,p.time,p.title,\n" +
            "s.department,s.major,s.name,s.tel\n" +
            " from problem p,student s where p.sid=s.sid && p.resolved= ?1 ", nativeQuery = true)
    List<Object> findUnsignedProblemDetail(Integer resolved);


    @Query(value = "select p.id as pid, p.detail,p.free_time,p.problem_type,p.resolved,p.sid,p.time,p.title,\n" +
            "s.department,s.major,s.name,s.tel\n" +
            " from problem p,student s where p.sid=s.sid && p.tid=?1", nativeQuery = true)
    List<Object> findProblemDetailsByTid(String tid);




    // # 0 pid
    //# 1 detail
    //# 2 freeTime
    //# 3 problemType
    //# 4 resolved
    //# 5 sid
    //# 6 time
    //# 7 title
    //# 8 teacherEmail
    //# 9 teacherName
    //# 10 teacherTel
    //# 11 tid
    //# 12 expertise
    //# 学生查询自己的问题时，要用到外连接，因为可能问题还未分配老师，如果直接多表查询会丢失数据
    @Query(value = "select * from\n" +
            "    ( select p.id as pid, p.detail,p.free_time,p.problem_type,p.resolved,p.sid,p.time,p.title,\n" +
            "             t.email as teacherEmail, t.name as teacherName, t.tel as teacherTel,t.tid,t.expertise\n" +
            "      from problem p left join teacher t on  p.tid=t.tid ) as t where t.sid=?1", nativeQuery = true)
    List<Object> findProblemDetailsBySid(String sid);


}
