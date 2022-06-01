package com.example.webapp.service;


import com.example.webapp.entity.UserEntity;
import com.example.webapp.model.User;
import com.example.webapp.model.enums.RolesEnum;
import com.example.webapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    public String registerNewAccount(User user) {
        if (userRepo.findByUsername(user.getUsername()) != null){
            return "Користувач з логіном " + user.getUsername() + " вже існує";
        }

        if (user.getPassword().length() < 6){
            return "Має бути не менше 6 символів в паролі";
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setActive(true);
        userEntity.setRoles(Collections.singleton(RolesEnum.ROLE_USER));
        userRepo.save(userEntity);
        return null;
    }
}
