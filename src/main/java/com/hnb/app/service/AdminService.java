package com.hnb.app.service;

import com.hnb.app.models.Users;
import com.hnb.app.models.UsersPosrednik;
import com.hnb.app.query.Queries;
import com.hnb.app.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Queries queries;

    @Autowired
    LoggerService loggerService;

    @Autowired
    AdminRepository repository;

    public List<UsersPosrednik> getAllUsers(String loggedUser,
                                            HttpServletResponse response) {
        try {
            List<UsersPosrednik> userList= new ArrayList<>();

            List<Users> userListOriginal = (List<Users>) repository.findAll();
            for (int i=0; i<userListOriginal.size();i++) {
                userList.add(new UsersPosrednik(userListOriginal.get(i).getUsername(), userListOriginal.get(i).getPassword(), userListOriginal.get(i).getPassword()));
            }
            logger.debug("Dohvacanje liste svih usera i njihovih passworda i ovlasti");
            loggerService.spremiLog("Dohvacanje svih usera i njihovih passworda i ovlasti", "/users", loggedUser, response.getStatus());
            return userList;
        }
        catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Dohvacanje svih usera i njihovih passworda i ovlasti - Baza sa userima je prazna");
            loggerService.spremiLog("Error - Dohvacanje svih usera i njihovih passworda i ovlasti - Baza sa userima je prazna", "/users", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Baza sa userima je prazna", e);

        }
    }

    public UsersPosrednik getUsersByUsername (String username,
                               String loggedUser,
                               HttpServletResponse response){
        try {
            UsersPosrednik user = queries.getUserByUsername(username);
            logger.debug("Dohvacanje usera koji ima username: "+username);
            loggerService.spremiLog("Dohvacanje usera koji ima username: "+username, "/users/"+username, loggedUser, response.getStatus());
            return user;
        }
        catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Dohvacanje usera koji ima username: "+username);
            loggerService.spremiLog("Error - Dohvacanje usera koji ima username: "+username, "/users/"+username+" - User koji ima username: "+ username + "ne postoji u bazi", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima username: "+ username + "ne postoji u bazi", e);

        }
    }

    public void createUser(UsersPosrednik user,
                           String loggedUser,
                           HttpServletResponse response) {
        List<Users> userList = (List<Users>) repository.findAll();
        for(int i=0; i<userList.size();i++){
            if (userList.get(i).getUsername().equals(user.getUsername())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username vec postoji u bazi");
            }
        }
        if (user.getUsername()!=null && user.getPassword()!= null && user.getRoles()!=null){
            repository.save(new Users(user.getUsername(), user.getPassword(), user.getRoles()));
            logger.debug("Novi korisnik je dodan u bazu");
            loggerService.spremiLog("Unos novog korisnika u bazu", "/users/", loggedUser, response.getStatus());
        }
        else if(user.getUsername()!=null && user.getPassword()!= null && user.getRoles()==null){
            repository.save(new Users(user.getUsername(), user.getPassword(), "USER"));
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

    public void updateUserById (String username,
                                UsersPosrednik user,
                                String loggedUser,
                                HttpServletResponse response) {
        List<Users> userList = (List<Users>) repository.findAll();
        for(int i=0; i<userList.size();i++){
            if (userList.get(i).getUsername().equals(user.getUsername())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username vec postoji u bazi");
            }
        }
        try {
            UsersPosrednik usersPosrednik = queries.getUserByUsername(username);
            if (user.getUsername()!=null){
                usersPosrednik.setUsername(user.getUsername());
            }
            if (user.getPassword()!=null){
                usersPosrednik.setPassword(user.getPassword());
            }
            if (user.getRoles()!=null){
                usersPosrednik.setRoles(user.getRoles());
            }
            repository.save(new Users(user.getUsername(), user.getPassword(), user.getRoles()));
            logger.debug("Updateanje usera koji ima username: "+username);
            loggerService.spremiLog("Updateanje usera koji ima username: "+username, "/users/"+username, loggedUser, response.getStatus());
        }
        catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Updateanje usera koji ima username: "+username +" - User koji ima id: "+ username + "ne postoji u bazi");
            loggerService.spremiLog("Error - Updateanje usera koji ima username: "+username +" - User koji ima username: "+ username + "ne postoji u bazi", "/users/"+username, loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima username: "+ username + "ne postoji u bazi", e);

        }
    }

    public void deleteUserById(String username,
                               String loggedUser,
                               HttpServletResponse response){
        try {
            queries.deleteUserByUsername(username);
            logger.debug("Brisanje usera koji ima username: "+username);
            loggerService.spremiLog("Brisanje usera koji ima username: "+username, "/users/"+username, loggedUser, response.getStatus());
        }
        catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Brisanje usera koji ima username: "+username);
            loggerService.spremiLog("Error - Brisanje usera koji ima username: "+username, "/users/"+username+" - User koji ima username: "+ username + " ne postoji u bazi", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima username: "+ username + " ne postoji u bazi", e);

        }
    }
}
