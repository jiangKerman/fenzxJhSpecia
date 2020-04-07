package com.fenzx.Repos;

import com.fenzx.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,Integer> {
    Admin findByUsernameAndPasswd(String username,String password);
}
