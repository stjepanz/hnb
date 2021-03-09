package com.hnb.app.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hnb.app.models.Users;
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
    public List<Users> getAllUsers(HttpServletResponse response,
                                   HttpServletRequest request){
        List<Users> list = service.getAllUsers(request.getUserPrincipal().getName(),response);
        return list;
    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable("id") int id,
                             HttpServletResponse response,
                             HttpServletRequest request){
        Users user= service.getUsersById(id, request.getUserPrincipal().getName(), response);
        return user;
    }

    @PostMapping("")
    public void createUser (@RequestBody Users user,
                            HttpServletResponse response,
                            HttpServletRequest request){
        service.createUser(user,request.getUserPrincipal().getName(),response);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id") int id,
                           @RequestBody Users user,
                           HttpServletResponse response,
                           HttpServletRequest request){
//        String loggedUser="ABC";
        service.updateUserById(id, user, request.getUserPrincipal().getName(), response);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") int id,
                               HttpServletResponse response,
                               HttpServletRequest request){
        service.deleteUserById(id, request.getUserPrincipal().getName(), response);
    }
}
