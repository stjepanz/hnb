package com.hnb.app.controller;

import com.hnb.app.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@RestController
public class LoggerController {

    @Autowired
    LoggerService loggerService;

    @GetMapping("/log")
    public List<String> getLogoviUserDatumi(@CurrentSecurityContext(expression="authentication?.name") String loggedUser,
                                            @RequestParam (required = false) String user,
                                            @RequestParam (required = false) String datumOd,
                                            @RequestParam (required = false) String datumDo,
                                            HttpServletResponse response){
        return loggerService.getLogovi(user, datumOd, datumDo, loggedUser, response);
    }
}