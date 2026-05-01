package com.journalApp.demo.controller;


import com.journalApp.demo.entity.User;
import com.journalApp.demo.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }



    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        //find user by username in database
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
       User userInDb= userService.findByUserName(userName);
       userInDb.setUserName(user.getUserName());
       userInDb.setPassword(user.getPassword());
       userService.saveNewUser(userInDb);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        userService.deleteUserByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
