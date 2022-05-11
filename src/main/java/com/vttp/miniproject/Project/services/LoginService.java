package com.vttp.miniproject.Project.services;

import java.util.logging.Logger;

import com.vttp.miniproject.Project.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    Logger logger = Logger.getLogger(LoginService.class.getName());

    @Autowired
    UserRepository userRepo;

    public boolean authenticate(String username) {
        return userRepo.authenticate(username);
    }
}
