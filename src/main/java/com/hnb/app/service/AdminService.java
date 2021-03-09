package com.hnb.app.service;

import com.hnb.app.models.Users;
import com.hnb.app.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoggerService loggerService;

    @Autowired
    AdminRepository repository;

    public List<Users> getAllUsers(String loggedUser,
                                   HttpServletResponse response) {
        try {
            List<Users> userList = (List<Users>) repository.findAll();
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

    public Users getUsersById (int id,
                               String loggedUser,
                               HttpServletResponse response){
        try {
            Optional<Users> user = repository.findById(id);
            logger.debug("Dohvacanje usera koji ima id: "+id);
            loggerService.spremiLog("Dohvacanje usera koji ima id: "+id, "/users/"+id, loggedUser, response.getStatus());
            return user.get();
        }
        catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Dohvacanje usera koji ima id: "+id);
            loggerService.spremiLog("Error - Dohvacanje usera koji ima id: "+id, "/users/"+id+" - User koji ima id: "+ id + "ne postoji u bazi", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima id: "+ id + "ne postoji u bazi", e);

        }
    }

    public void createUser(Users user,
                           String loggedUser,
                           HttpServletResponse response) {
        List<Users> userList = (List<Users>) repository.findAll();
        for(int i=0; i<userList.size();i++){
            if (userList.get(i).getUsername().equals(user.getUsername())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username vec postoji u bazi");
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

    public void updateUserById (int id,
                                Users user,
                                String loggedUser,
                                HttpServletResponse response) {
        List<Users> userList = (List<Users>) repository.findAll();
        for(int i=0; i<userList.size();i++){
            if (userList.get(i).getUsername().equals(user.getUsername())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username vec postoji u bazi");
            }
        }
        try {
            Optional<Users> optionalUser = repository.findById(id);
            Users oldUser=optionalUser.get();
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
            logger.debug("Updateanje usera koji ima id: "+id);
            loggerService.spremiLog("Updateanje usera koji ima id: "+id, "/users/"+id, loggedUser, response.getStatus());
        }
        catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Updateanje usera koji ima id: "+id +" - User koji ima id: "+ id + "ne postoji u bazi");
            loggerService.spremiLog("Error - Updateanje usera koji ima id: "+id +" - User koji ima id: "+ id + "ne postoji u bazi", "/users/"+id, loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima id: "+ id + "ne postoji u bazi", e);

        }
    }


    public void deleteUserById(int id,
                               String loggedUser,
                               HttpServletResponse response){
        try {
            Optional<Users> user = repository.findById(id);
            repository.deleteById(id);
            logger.debug("Brisanje usera koji ima id: "+id);
            loggerService.spremiLog("Brisanje usera koji ima id: "+id, "/users/"+id, loggedUser, response.getStatus());
        }
        catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Brisanje usera koji ima id: "+id);
            loggerService.spremiLog("Error - Brisanje usera koji ima id: "+id, "/users/"+id+" - User koji ima id: "+ id + "ne postoji u bazi", loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User koji ima id: "+ id + "ne postoji u bazi", e);

        }
    }
}
