package com.hnb.app.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hnb.app.models.Users;
import com.hnb.app.models.UsersGet;
import com.hnb.app.models.UsersPostPut;
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
    public List<UsersGet> getAllUsers(HttpServletResponse response,
                                      HttpServletRequest request){
        return service.getAllUsers(request.getUserPrincipal().getName(),response);
    }

    @GetMapping("/{username}")
    public UsersGet getUserByUsername(@PathVariable("username") String username,
                                      HttpServletResponse response,
                                      HttpServletRequest request){
        return service.getUsersByUsername(username, request.getUserPrincipal().getName(), response);
    }

    @PostMapping("")
    public void createUser (@RequestBody UsersPostPut user,
                            HttpServletResponse response,
                            HttpServletRequest request){
        service.createUser(user,request.getUserPrincipal().getName(),response);
    }

    @PutMapping("/{username}")
    public void updatePrekoUsernamea(@PathVariable("username") String username,
                                     @RequestBody UsersPostPut user,
                                     HttpServletResponse response,
                                     HttpServletRequest request){
        service.updateUserByUsername(username, user, request.getUserPrincipal().getName(), response);
    }

    @DeleteMapping("/{username}")
    public void deleteUserUsername(@PathVariable("username") String username,
                                   HttpServletResponse response,
                                   HttpServletRequest request){
        service.deleteUserUsername(username, request.getUserPrincipal().getName(), response);
    }
}
