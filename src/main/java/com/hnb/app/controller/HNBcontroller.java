package com.hnb.app.controller;

import com.hnb.app.service.HNBservice;
import com.hnb.app.query.Queries;
import com.hnb.app.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class HNBcontroller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Queries queries;

    @Autowired
    HNBservice service;

    @GetMapping("/valute")
    public List<String> getValute(@CurrentSecurityContext(expression="authentication?.name") String loggedUser,
                                  HttpServletResponse response){
        return service.getValute(loggedUser, response);
    }

    @GetMapping("/srednjitecaj/{valuta}/{start}/{end}")
    public double getSrednjiTecaj(@CurrentSecurityContext(expression="authentication?.name") String loggedUser,
                                  @PathVariable("valuta") String valuta,
                                  @PathVariable("start") String start,
                                  @PathVariable("end") String end,
                                  HttpServletResponse response){
        return service.prosjecnaSrednjaVrijednost(valuta, start, end, loggedUser, response);
    }

    @GetMapping("/praznine/provjera")
    public String provjeraPraznina(@CurrentSecurityContext(expression="authentication?.name") String loggedUser,
                                   HttpServletResponse response){
        return service.provjeravanjePraznina(loggedUser, response);
    }

    @GetMapping("/praznine/popunjavanje")
    public void popunjavanjePraznina(@CurrentSecurityContext(expression="authentication?.name") String loggedUser,
                                     HttpServletResponse response){
        service.popunjavanjePraznina(loggedUser, response);
    }



}
