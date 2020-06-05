package com.fenzx.ldy.dal.repo;

import com.fenzx.ldy.dal.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepo extends JpaRepository<Field,Integer> {
    List<Field> findAll();
    Field findByFName(String fName);
}
