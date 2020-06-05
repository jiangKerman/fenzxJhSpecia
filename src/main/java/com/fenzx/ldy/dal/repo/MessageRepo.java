package com.fenzx.ldy.dal.repo;

import com.fenzx.ldy.dal.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface MessageRepo extends JpaRepository<Message,Integer> {
    void deleteById(Integer id);
    @Query(value = "select student.name,message.content,message.time,message.id,message.isread from student,message where message.reciverid=?1 and recivecite=1 and student.sid=message.senderid order by message.time desc ",nativeQuery = true)
    List<Map<String,Object>> selectall(String stuid);
    @Query(value = "select student.name,message.content,message.time,message.id,message.isread from student,message where message.senderid=?1 and sendcite=1 and student.sid=message.reciverid order by message.time desc ",nativeQuery = true)
    List<Map<String,Object>> selectallsend(String stuid);
    @Query(value = "select student.name,message.content,message.time,message.id from student,message where message.id=?1 and message.senderid=student.sid",nativeQuery = true)
    Map<String,Object> selectById(Integer id);
    @Override
    Optional<Message> findById(Integer id);
}
