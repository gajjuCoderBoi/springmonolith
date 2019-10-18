package com.ga.controller;

import com.ga.entity.JwtResponse;
import com.ga.entity.User;
import com.ga.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;


    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!!";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        return ResponseEntity.ok(new JwtResponse(userService.signup(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        return ResponseEntity.ok(new JwtResponse( userService.login(user)));
    }

    @PutMapping("/update/{id}")
    public User update(@RequestAttribute("id") Long userId, @RequestBody User user){
        return userService.update(user, userId);
    }

    @DeleteMapping("/delete/{id}")
    public User delete(@RequestAttribute("id") Long userId){
        return userService.delete(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public List<User> listUsers() {
        return userService.listUsers();
    }


}