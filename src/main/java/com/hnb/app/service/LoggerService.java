package com.hnb.app.service;

import com.hnb.app.models.Logger;
import com.hnb.app.query.Queries;
import com.hnb.app.repository.LoggerRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LoggerService {

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

    public List<String> getLogovi(String user,String datumOd,String datumDo) {
        LocalDate start;
        LocalDate end;
        if (datumOd == null && datumDo == null) {
            if (user == null) {
                return queries.getLogoviPoDatumu(LocalDate.now(), LocalDate.now().plusDays(1));
            } else {
                return queries.getLogoviPoDatumuIUseru(LocalDate.now(), LocalDate.now().plusDays(1), user);
            }
        }
        else if (datumOd != null && datumDo != null) {
            try {
                start = LocalDate.parse(datumOd);
            }
            catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum pocetka koji ste unijeli nije ispravan", e);
            }
            try{
                end = LocalDate.parse(datumDo);
            }
            catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum kraja koji ste unijeli nije ispravan", e);
            }
            if(start.isBefore((LocalDate.parse(queries.getPrviDatumLogger().toString().substring(0, 10))))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum prvog loga je: " + queries.getPrviDatumLogger().toString().substring(0, 10));
            }
            if(end.isAfter((LocalDate.parse(queries.getZadnjiDatumLogger().toString().substring(0, 10))))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum zadnjeg loga je: " + queries.getZadnjiDatumLogger().toString().substring(0, 10));
            }
            if(user == null) {
                return queries.getLogoviPoDatumu(start, end.plusDays(1));
            }
            else{
                return queries.getLogoviPoDatumuIUseru(start, end.plusDays(1), user);
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trebaju biti unesena oba datuma ili ne treba unijeti ni jedan datum");
        }
    }
}

