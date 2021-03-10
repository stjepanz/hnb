package com.hnb.app.service;

import com.hnb.app.models.Users;
import com.hnb.app.models.UsersGet;
import com.hnb.app.query.Queries;
import com.hnb.app.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Queries queries;

    @Autowired
    LoggerService loggerService;

    @Autowired
    AdminRepository repository;

    public List<UsersGet> getAllUsers(String loggedUser,
                                      HttpServletResponse response) {
        try {
            List<Users> userList = (List<Users>) repository.findAll();
            List<UsersGet> userGet = new ArrayList<>();
            for (int i=0; i<userList.size();i++){
                userGet.add(new UsersGet(userList.get(i).getUsername(), userList.get(i).getRoles()));
            }
            logger.debug("Dohvacanje liste svih usera i njihovih passworda i ovlasti");
            loggerService.spremiLog("Dohvacanje svih usera i njihovih passworda i ovlasti", "/users", loggedUser, response.getStatus());
            return userGet;
        }
        catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Dohvacanje svih usera i njihovih passworda i ovlasti - Baza sa userima je prazna");
            loggerService.spremiLog("Error - Dohvacanje svih usera i njihovih passworda i ovlasti - Baza sa userima je prazna", "/users", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Baza sa userima je prazna", e);
        }
    }

    public UsersGet getUsersByUsername(String username,
                                       String loggedUser,
                                       HttpServletResponse response){
        try {
            List<Users> userList = (List<Users>) repository.findAll();
            for (int i=0; i<userList.size(); i++){
                if (userList.get(i).getUsername().equals(username)){
                    logger.debug("Dohvacanje usera koji ima username: "+username);
                    loggerService.spremiLog("Dohvacanje usera koji ima username: "+username, "/users"+username, loggedUser, response.getStatus());
                    return new UsersGet(username, queries.getRolesByUsername(username));
                }
            }response.setStatus(400);
            logger.debug("Error - Dohvacanje usera koji ima username: "+username);
            loggerService.spremiLog("Error - Dohvacanje usera koji ima username: "+username, "/users"+username+" - User koji ima username: "+ username + " ne postoji u bazi", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima username: "+ username + " ne postoji u bazi");
        }
        catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Dohvacanje usera koji ima username: "+username);
            loggerService.spremiLog("Error - Dohvacanje usera koji ima username: "+username, "/users"+username+" - User koji ima username: "+ username + " ne postoji u bazi", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima username: "+ username + " ne postoji u bazi", e);
        }
    }

    public void createUser(Users user,
                           String loggedUser,
                           HttpServletResponse response) {
        List<Users> userList = (List<Users>) repository.findAll();
        for (int i =0; i<userList.size(); i++){
            if (userList.get(i).getUsername().equals(user.getUsername())){
                response.setStatus(400);
                logger.debug("Error - Unos novog korisnika u bazu - Korisnik vec postoji u bazi");
                loggerService.spremiLog("Error - Unos novog korisnika u bazu - Korisnik "+user.getUsername() +" vec postoji u bazi", "/users/", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Korisnik "+user.getUsername() +" vec postoji u bazi");
            }
        }
        if (user.getUsername()!=null && user.getPassword()!= null && user.getRoles()!=null){
            repository.save(user);
            logger.debug("Novi korisnik je dodan u bazu");
            loggerService.spremiLog("Unos novog korisnika u bazu", "/users/", loggedUser, response.getStatus());
        }
        else if(user.getUsername()!=null && user.getPassword()!= null && user.getRoles()==null){
            Users tempUser = new Users(user.getUsername(), user.getPassword(), "USER");
            repository.save(tempUser);
            logger.debug("Novi korisnik je dodan u bazu");
            loggerService.spremiLog("Unos novog korisnika u bazu", "/users/", loggedUser, response.getStatus());
        }
        else{
            response.setStatus(400);
            logger.debug("Error - Unos novog korisnika u bazu - Unesite sve podatke potrebne za kreiranje usera");
            loggerService.spremiLog("Error - Unos novog korisnika u bazu - Unesite sve podatke potrebne za kreiranje usera", "/users/", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unesite sve podatke potrebne za kreiranje usera");
        }
    }

    public void updateUserByUsername (String username,
                                      Users user,
                                      String loggedUser,
                                      HttpServletResponse response) {
        int flag = 0;
        List<Users> userList = (List<Users>) repository.findAll();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(user.getUsername())) {
                response.setStatus(400);
                logger.debug("Error - Unos novog korisnika u bazu - Korisnik vec postoji u bazi");
                loggerService.spremiLog("Error - Unos novog korisnika u bazu - Korisnik " + user.getUsername() + " vec postoji u bazi", "/users/", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Korisnik " + user.getUsername() + " vec postoji u bazi");
            }
        }
//        try {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                flag=1;
                Users oldUser = repository.findById(userList.get(i).getId()).get();
                if (user.getUsername()!=null){
                    oldUser.setUsername(user.getUsername());
                }
                if (user.getPassword()!=null){
                    oldUser.setPassword(user.getPassword());
                }
                if (user.getRoles()!=null){
                    oldUser.setRoles(user.getRoles());
                }
                repository.save(oldUser);
                logger.debug("Updateanje usera koji ima username: " + username);
                loggerService.spremiLog("Updateanje usera koji ima username: " + username, "/users/" + username, loggedUser, response.getStatus());
            }
        }
//        } catch (Exception e) {
        if(flag==0) {
            response.setStatus(400);
            logger.debug("Error - Updateanje korisnika u baz1 - Korisnik kojeg zelite updateati ne postoji u bazi");
            loggerService.spremiLog("Error - Updateanje korisnika u baz1 - Korisnik kojeg zelite updateati ne postoji u bazi", "/users/", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Korisnik kojeg zelite updateati ne postoji u bazi");
        }
    }
//    }

    public void deleteUserUsername(String username,
                                   String loggedUser,
                                   HttpServletResponse response){
        int flag = 0;
        List<Users> userList = (List<Users>) repository.findAll();

//        try {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                flag=1;
                repository.deleteById(userList.get(i).getId());
                logger.debug("Brisanje usera koji ima username: " + username);
                loggerService.spremiLog("Brisanje usera koji ima username: " + username, "/users/" + username, loggedUser, response.getStatus());
            }
        }
//        } catch (Exception e) {
        if(flag==0) {
            response.setStatus(400);
            logger.debug("Error - Brisanje korisnika u bazi - Korisnik kojeg zelite izbrisati ne postoji u bazi");
            loggerService.spremiLog("Error - Brisanje korisnika u bazi - Korisnik kojeg zelite izbrisati ne postoji u bazi", "/users/", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Korisnik kojeg zelite izbrisati ne postoji u bazi");
        }
    }
}
