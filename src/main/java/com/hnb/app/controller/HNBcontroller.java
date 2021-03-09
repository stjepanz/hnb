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

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    Queries queries;

    @Autowired
    HNBservice service;

    @GetMapping("/valute")
    public List<String> getValute(HttpServletResponse response,
                                  HttpServletRequest request){
        return service.getValute(request.getUserPrincipal().getName(), response);
    }

    @GetMapping("/srednjitecaj/{valuta}/{start}/{end}")
    public double getSrednjiTecaj(@PathVariable("valuta") String valuta,
                                  @PathVariable("start") String start,
                                  @PathVariable("end") String end,
                                  HttpServletResponse response,
                                  HttpServletRequest request){
        return service.prosjecnaSrednjaVrijednost(valuta, start, end, request.getUserPrincipal().getName(), response);
    }

    @GetMapping("/praznine/provjera")
    public String provjeraPraznina(HttpServletResponse response,
                                   HttpServletRequest request){
        return service.provjeravanjePraznina(request.getUserPrincipal().getName(), response);
    }

    @GetMapping("/praznine/popunjavanje")
    public void popunjavanjePraznina(HttpServletResponse response,
                                     HttpServletRequest request){
        service.popunjavanjePraznina(request.getUserPrincipal().getName(), response);
    }
}
