package com.hnb.app.service;

import com.hnb.app.models.Users;
import com.hnb.app.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AdminRepository repository;

    public List<Users> getAllUsers() {
        try {
            List<Users> userList = (List<Users>) repository.findAll();
            return userList;
        }
        catch (Exception e){
            logger.debug("Baza je prazna");
            return null;
        }
    }

    public Users getUsersById (int id){
        try {
            Optional<Users> user = repository.findById(id);
            return user.get();
        }
        catch (Exception e){
            logger.debug("Usera koji ima id: " + id +" ne postoji");
            return null;
        }
    }

    public void createUser(Users user) {
        if (user.getUsername()!=null && user.getPassword()!= null && user.getRoles()!=null){
            repository.save(user);
        }

    }

    public void updateUserById (int id, Users user) {
        Optional<Users> odlUser = repository.findById(id);
        Users newUser = new Users(user.getUsername(), user.getPassword(), user.getRoles());
        if (user.getUsername()==null){newUser.setUsername(odlUser.get().getUsername());}
        if (user.getPassword()==null){newUser.setPassword(odlUser.get().getPassword());}
        if (user.getRoles()==null){newUser.setRoles(odlUser.get().getRoles());}
        newUser.setId(id);
        repository.save(newUser);
    }


    public void deleteUserById(int id){
        try {
            Optional<Users> user = repository.findById(id);
            repository.deleteById(id);
        }
        catch (Exception e){
            logger.debug("User koji ima id: " + id +" ne postoji");
        }
    }
}
