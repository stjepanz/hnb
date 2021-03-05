package com.hnb.app.controller;

import com.hnb.app.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

@RestController
public class LoggerController {

    @Autowired
    LoggerService loggerService;

    @GetMapping(value = {"/logovi","/logovi/{od}", "/logovi/{od}/{do}"})
    public List<String> logoviOdDo(@PathVariable(value = "od", required = false) String start,
                                   @PathVariable(value = "do", required = false) String end){
        if (start!=null && end!=null){
            return loggerService.getLogovi(start, end);
        }
        else if(start==null && end==null){
            return loggerService.getLogovi(LocalDate.now().toString(),LocalDate.now().toString() );
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upišite oba datuma za neki period ili ostavite prazno za sve logove od danas");
        }
    }
}
