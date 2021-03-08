package com.hnb.app.service;

import com.hnb.app.models.Logger;
import com.hnb.app.query.Queries;
import com.hnb.app.repository.LoggerRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LoggerService {

    @Autowired
    LoggerService loggerService;


    @Autowired
    Queries queries;

    @Autowired
    LoggerRepository repository;

    public void spremiLog(String log,
                          String endpoint,
                          String user,
                          int code) {
        Logger logovi = new Logger(log, LocalDateTime.now(), endpoint, user, code);
        repository.save(logovi);
    }

    public List<String> getLogovi(String user,
                                  String datumOd,
                                  String datumDo,
                                  String loggedUser,
                                  HttpServletResponse response) {
        LocalDate start;
        LocalDate end;
        if (datumOd == null && datumDo == null) {
            if (user == null) {
                loggerService.spremiLog("Dohvacanje logova za danasnji datum", "/log", loggedUser, response.getStatus());
                return queries.getLogoviPoDatumu(LocalDate.now(), LocalDate.now().plusDays(1));
            } else {
                loggerService.spremiLog("Dohvacanje logova za danasnji datum za usera: " + user, "/log", loggedUser, response.getStatus());
                return queries.getLogoviPoDatumuIUseru(LocalDate.now(), LocalDate.now().plusDays(1), user);
            }
        } else if (datumOd != null && datumDo != null) {
            try {
                start = LocalDate.parse(datumOd);
            } catch (Exception e) {
                response.setStatus(400);
                loggerService.spremiLog("Error - Dohvacanje logova - Datum nije ispravan", "/log", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum pocetka koji ste unijeli nije ispravan", e);
            }
            try {
                end = LocalDate.parse(datumDo);
            } catch (Exception e) {
                response.setStatus(400);
                loggerService.spremiLog("Error - Dohvacanje logova - Datum nije ispravan", "/log", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum kraja koji ste unijeli nije ispravan", e);
            }
            if (start.isBefore((LocalDate.parse(queries.getPrviDatumLogger().toString().substring(0, 10))))) {
                response.setStatus(400);
                loggerService.spremiLog("Error - Dohvacanje logova - Uneseni datum je prije datuma prvog loga u bazi", "/log", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum prvog loga je: " + queries.getPrviDatumLogger().toString().substring(0, 10));
            }
            if (end.isAfter((LocalDate.parse(queries.getZadnjiDatumLogger().toString().substring(0, 10))))) {
                response.setStatus(400);
                loggerService.spremiLog("Error - Dohvacanje logova - Uneseni datum jos nije dosao", "/log", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum zadnjeg loga je: " + queries.getZadnjiDatumLogger().toString().substring(0, 10));
            }
            if (user == null) {
                loggerService.spremiLog("Dohvacanje logova za datume od: " + datumOd + " do: " + datumDo, "/log", loggedUser, response.getStatus());
                return queries.getLogoviPoDatumu(start, end.plusDays(1));
            } else {
                loggerService.spremiLog("Dohvacanje logova za datume od: " + datumOd + " do: " + datumDo + " za usera: " + user, "/log", loggedUser, response.getStatus());
                return queries.getLogoviPoDatumuIUseru(start, end.plusDays(1), user);
            }
        }
        else {
            if (datumDo == null) {
                try {
                    start = LocalDate.parse(datumOd);
                } catch (Exception e) {
                    response.setStatus(400);
                    loggerService.spremiLog("Error - Dohvacanje logova - Datum nije ispravan", "/log", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum koji ste unijeli nije ispravan (format: GGGG-MM-DD)", e);
                }
                if (start.isBefore((LocalDate.parse(queries.getPrviDatumLogger().toString().substring(0, 10))))) {
                    response.setStatus(400);
                    loggerService.spremiLog("Error - Dohvacanje logova - Uneseni datum je prije datuma prvog loga u bazi", "/log", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum prvog loga je: " + queries.getPrviDatumLogger().toString().substring(0, 10));
                }
                if (start.isAfter((LocalDate.parse(queries.getZadnjiDatumLogger().toString().substring(0, 10))))) {
                    response.setStatus(400);
                    loggerService.spremiLog("Error - Dohvacanje logova - Uneseni datum jos nije dosao", "/log", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum zadnjeg loga je: " + queries.getZadnjiDatumLogger().toString().substring(0, 10));
                }
                if (user == null) {
                    loggerService.spremiLog("Dohvacanje logova za datum: " + start, "/log", loggedUser, response.getStatus());
                    return queries.getLogoviPoDatumu(start, start.plusDays(1));
                } else {
                    loggerService.spremiLog("Dohvacanje logova za datum: " + start +" za usera: "+user, "/log", loggedUser, response.getStatus());
                    return queries.getLogoviPoDatumuIUseru(start, start.plusDays(1), user);
                }
            }
            else {
                try {
                    start = LocalDate.parse(datumDo);
                } catch (Exception e) {
                    response.setStatus(400);
                    loggerService.spremiLog("Error - Dohvacanje logova - Datum nije ispravan", "/log", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum koji ste unijeli nije ispravan (format: GGGG-MM-DD)", e);
                }
                if (start.isBefore((LocalDate.parse(queries.getPrviDatumLogger().toString().substring(0, 10))))) {
                    response.setStatus(400);
                    loggerService.spremiLog("Error - Dohvacanje logova - Uneseni datum je prije datuma prvog loga u bazi", "/log", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum prvog loga je: " + queries.getPrviDatumLogger().toString().substring(0, 10));
                }
                if (start.isAfter((LocalDate.parse(queries.getZadnjiDatumLogger().toString().substring(0, 10))))) {
                    response.setStatus(400);
                    loggerService.spremiLog("Error - Dohvacanje logova - Uneseni datum jos nije dosao", "/log", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum zadnjeg loga je: " + queries.getZadnjiDatumLogger().toString().substring(0, 10));
                }
                if (user == null) {
                    loggerService.spremiLog("Dohvacanje logova za datum: " + start, "/log", loggedUser, response.getStatus());
                    return queries.getLogoviPoDatumu(start, start.plusDays(1));
                } else {
                    loggerService.spremiLog("Dohvacanje logova za datum: " + start +" za usera: "+user, "/log", loggedUser, response.getStatus());
                    return queries.getLogoviPoDatumuIUseru(start, start.plusDays(1), user);
                }
            }
        }
    }
}

