package com.hnb.app.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hnb.app.models.Users;
import com.hnb.app.models.UsersPosrednik;
import com.hnb.app.service.AdminService;
import com.hnb.app.service.LoggerService;
import org.apache.catalina.connector.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AdminController {

    @Autowired
    AdminService service;

    @GetMapping("")
    public List<UsersPosrednik> getAllUsers(HttpServletResponse response,
                                   HttpServletRequest request){
        List<UsersPosrednik> list = service.getAllUsers(request.getUserPrincipal().getName(),response);
        return list;
    }

    @GetMapping("/{id}")
    public UsersPosrednik getUserById(@PathVariable("username") String username,
                             HttpServletResponse response,
                             HttpServletRequest request){
        UsersPosrednik user= service.getUsersByUsername(username, request.getUserPrincipal().getName(), response);
        return user;
    }

    @PostMapping("")
    public void createUser (@RequestBody UsersPosrednik user,
                            HttpServletResponse response,
                            HttpServletRequest request){
        service.createUser(user,request.getUserPrincipal().getName(),response);
    }

    @PutMapping("/{username}")
    public void updateUser(@PathVariable("username") String username,
                           @RequestBody UsersPosrednik user,
                           HttpServletResponse response,
                           HttpServletRequest request){
        service.updateUserById(username, user, request.getUserPrincipal().getName(), response);
    }

    @DeleteMapping("/{username}")
    public void deleteUserById(@PathVariable("username") String username,
                               HttpServletResponse response,
                               HttpServletRequest request){
        service.deleteUserById(username, request.getUserPrincipal().getName(), response);
    }
}
