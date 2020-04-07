package com.fenzx.ServiceImpls;

import com.fenzx.Repos.AdminRepo;
import com.fenzx.Repos.ProblemRepo;
import com.fenzx.entity.Admin;
import com.fenzx.entity.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    ProblemRepo problemRepo;

    public Admin findByUsernameAndPasswd(String username, String password) {
        return adminRepo.findByUsernameAndPasswd(username, password);
    }


}
