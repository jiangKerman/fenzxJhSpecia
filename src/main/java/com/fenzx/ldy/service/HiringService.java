package com.fenzx.ldy.service;


import com.fenzx.ldy.dal.entity.Hiring;

import java.util.List;

public interface HiringService {
    List<Hiring> findByRecid(Integer recid);
    void insert(Hiring hiring);
}
