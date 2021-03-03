package com.hnb.app.controller;

import com.hnb.app.models.Users;
import com.hnb.app.service.AdminService;
import com.hnb.app.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoggerService loggerService;

    @Autowired
    AdminService service;

    @GetMapping("")
    public List<Users> getAllUsers(@CurrentSecurityContext(expression="authentication?.name")
                                               String loggedUser){
        logger.debug("Dohvacanje svih usera");
        loggerService.spremiLog("Dohvacanje svih usera", "/users", loggedUser);
        List<Users> list = service.getAllUsers();
        return list;
    }

    @GetMapping("/{id}")
    public Users getUserById(@CurrentSecurityContext(expression="authentication?.name")
                                         String loggedUser, @PathVariable("id") int id){
        logger.debug("Dohvacanje usera koji ima id: "+id);
        loggerService.spremiLog("Dohvacanje usera koji ima id: "+id, "/users/"+id, loggedUser);
        Users user= service.getUsersById(id);
        return user;
    }

    @PostMapping("")
    public void createUser (@CurrentSecurityContext(expression="authentication?.name")
                                        String loggedUser, @RequestBody Users user){
        logger.debug("Dodavanje novog usera");
        loggerService.spremiLog("Dodavanje novog usera", "/users", loggedUser);
        service.createUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@CurrentSecurityContext(expression="authentication?.name")
                                       String loggedUser,@PathVariable("id") int id, @RequestBody Users user){
        logger.debug("Updateanje usera koji ima id: "+id);
        loggerService.spremiLog("Updateanje usera koji ima id: "+id, "/users/"+id, loggedUser);
        service.updateUserById(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@CurrentSecurityContext(expression="authentication?.name")
                                           String loggedUser,@PathVariable("id") int id){
        logger.debug("Brisanje usera koji ima id: "+id);
        loggerService.spremiLog("Brisanje usera koji ima id: "+id, "/users/"+id, loggedUser);
        service.deleteUserById(id);
    }
}
