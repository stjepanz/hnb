package com.hnb.app.service;

import com.hnb.app.models.Users;
import com.hnb.app.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            logger.debug("Dohvacanje liste svih usera i njihovih passworda i ovlasti");
            return userList;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Baza sa userima je prazna", e);

        }
    }

    public Users getUsersById (int id){
        try {
            Optional<Users> user = repository.findById(id);
            logger.debug("Dohvacanje usera, njegovog passworda i ovlasti preko id-a");
            return user.get();
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima id: "+ id + "ne postoji u bazi", e);

        }
    }

    public void createUser(Users user) {
        if (user.getUsername()!=null && user.getPassword()!= null && user.getRoles()!=null){
            repository.save(user);
            logger.debug("Novi korisnik je dodan u bazu");
        }

    }

    public void updateUserById (int id, Users user) {
        try {
            Optional<Users> odlUser = repository.findById(id);
            Users newUser = new Users(user.getUsername(), user.getPassword(), user.getRoles());
            if (user.getUsername()==null){
                newUser.setUsername(odlUser.get().getUsername());
            }
            if (user.getPassword()==null){
                newUser.setPassword(odlUser.get().getPassword());
            }
            if (user.getRoles()==null){
                newUser.setRoles(odlUser.get().getRoles());
            }
            newUser.setId(id);
            repository.save(newUser);
            logger.debug("User sa id-em "+id+" je updatean");
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima id: "+ id + "ne postoji u bazi", e);

        }
    }


    public void deleteUserById(int id){
        try {
            Optional<Users> user = repository.findById(id);
            repository.deleteById(id);
            logger.debug("User sa id-em "+id+" je obrisan");
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima id: "+ id + "ne postoji u bazi", e);

        }
    }
}
