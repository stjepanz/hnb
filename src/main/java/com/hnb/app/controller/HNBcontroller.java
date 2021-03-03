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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class HNBcontroller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoggerService loggerService;

    @Autowired
    Queries queries;

    @Autowired
    HNBservice service;

    @GetMapping("/valute")
    public List<String> getValute(@CurrentSecurityContext(expression="authentication?.name")
                                              String loggedUser){
        logger.debug("Dohvacanje svih valuta");
        loggerService.spremiLog("Dohvacanje svih valutaD", "/valute", loggedUser);
        return service.getValute();
    }

    @GetMapping("/srednjitecaj/{valuta}/{start}/{end}")
    public double getSrednjiTecaj(@CurrentSecurityContext(expression="authentication?.name")
                                              String loggedUser, @PathVariable("valuta") String valuta, @PathVariable("start") String start, @PathVariable("end") String end){
        logger.debug("Racunanje srednjeg tecaja za valutu "+valuta+" u periodu od "+start+ " do "+end);
        loggerService.spremiLog("Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+start+ " do "+end, "/srednjitecaj/"+valuta+"/"+start+"/"+end, loggedUser);
        return service.prosjecnaSrednjaVrijednost(valuta, start, end);
    }

    @GetMapping("/praznine/provjera")
    public String provjeraPraznina(@CurrentSecurityContext(expression="authentication?.name")
                                               String loggedUser){
        logger.debug("Provjera praznina u tablici koja sadrzi podatke o tecajevima");
        loggerService.spremiLog("Provjera praznina u tablici koja sadrzi podatke o tecajevima", "/praznine/provjera", loggedUser);
        return service.provjeravanjePraznina();
    }

    @GetMapping("/praznine/popunjavanje")
    public void popunjavanjePraznina(@CurrentSecurityContext(expression="authentication?.name")
                                                 String loggedUser){
        logger.debug("Popunjavanje praznina u talici koja sadrzi podatke o tecajevima");
        loggerService.spremiLog("Popunjavanje praznina u talici koja sadrzi podatke o tecajevima", "/praznine/popunjavanje", loggedUser);
        service.popunjavanjePraznina();
    }

    @GetMapping("/logovi/{od}/{do}")
    public List<String> logoviOdDo(@PathVariable("od") String start, @PathVariable("do") String end){
        return loggerService.getLogovi(start, end);
    }

    @GetMapping("/proba")
    public LocalDate proba(){
        return LocalDate.parse(queries.getPrviDatumLogger().toString().substring(0,10));
    }
}
