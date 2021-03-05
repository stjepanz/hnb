package com.hnb.app.controller;

import com.hnb.app.models.Users;
import com.hnb.app.service.AdminService;
import com.hnb.app.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AdminController {

    @Autowired
    AdminService service;

    @GetMapping("")
    public List<Users> getAllUsers(@CurrentSecurityContext(expression="authentication?.name") String loggedUser,
                                   HttpServletResponse response){
        List<Users> list = service.getAllUsers(loggedUser,response);
        return list;
    }

    @GetMapping("/{id}")
    public Users getUserById(@CurrentSecurityContext(expression="authentication?.name") String loggedUser,
                             @PathVariable("id") int id,
                             HttpServletResponse response){
        Users user= service.getUsersById(id, loggedUser, response);
        return user;
    }

    @PostMapping("")
    public void createUser (@CurrentSecurityContext(expression="authentication?.name") String loggedUser,
                            @RequestBody Users user,
                            HttpServletResponse response){
        service.createUser(user,loggedUser,response);
    }

    @PutMapping("/{id}")
    public void updateUser(@CurrentSecurityContext(expression="authentication?.name") String loggedUser,
                           @PathVariable("id") int id,
                           @RequestBody Users user,
                           HttpServletResponse response){
        service.updateUserById(id, user, loggedUser, response);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@CurrentSecurityContext(expression="authentication?.name") String loggedUser,
                               @PathVariable("id") int id,
                               HttpServletResponse response){
        service.deleteUserById(id, loggedUser, response);
    }
}
