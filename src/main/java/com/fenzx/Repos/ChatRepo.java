package com.fenzx.Repos;

import com.fenzx.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepo extends JpaRepository<Chat,Integer> {
    List<Chat> findAllByProblemId(Integer problemId);
}
