package com.hnb.app.controller;

import com.hnb.app.models.Users;
import com.hnb.app.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AdminService service;

    @GetMapping("")
    public List<Users> getAllUsers(){
        logger.debug("Dohvacanje svih usera");
        List<Users> list = service.getAllUsers();
        return list;
    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable("id") int id){
        logger.debug("Dohvacanje usera koji ima id: "+id);
        Users user= service.getUsersById(id);
        return user;
    }

    @PostMapping("")
    public void createUser (@RequestBody Users user){
        logger.debug("Dodavanje novog usera");
        service.createUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id") int id, @RequestBody Users user){
        logger.debug("Updateanje usera koji ima id: "+id);
        service.updateUserById(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") int id){
        logger.debug("Brisanje usera koji ima id: "+id);
        service.deleteUserById(id);
    }
}
