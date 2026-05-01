package com.journalApp.demo.service;

import com.journalApp.demo.entity.User;
import com.journalApp.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void saveEntry(User user){
        userRepository.save(user);
    }

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);

    }
    public User findByUserName(String userName){
        System.out.println("3333333333333333333"+ userName);
       return userRepository.findByUserName(userName);
    }
    public void deleteUserByUserName(String userName){
        userRepository.deleteByUserName(userName);
    }
    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
}
