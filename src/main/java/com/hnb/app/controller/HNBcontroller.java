package com.hnb.app.controller;

import com.hnb.app.service.HNBservice;
import com.hnb.app.query.Queries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class HNBcontroller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    double prosjek;

    @Autowired
    Queries queries;

    @Autowired
    HNBservice service;

    @GetMapping("/valute")
    public List<String> getValute(){
        logger.debug("Dohvacanje svih valuta");
        return queries.getValute();
    }

    @GetMapping("/srednjitecaj/{valuta}/{start}/{end}")
    public double getSrednjiTecaj(@PathVariable("valuta") String valuta, @PathVariable("start") String start, @PathVariable("end") String end){
        logger.debug("racunanje srednjeg tecaja za valutu "+valuta+" u periodu od "+start+ " do "+end);
        prosjek=service.prosjecnaSrednjaVrijednost(queries.getProsjecniTecajeviRaspon(valuta, LocalDate.parse(start).minusDays(1), LocalDate.parse(end)));
        return prosjek;
    }

    @GetMapping("/praznine/provjera")
    public String provjeraPraznina(){
        logger.debug("Provjera praznina u tablici koja sadrzi podatke o tecajevima");
        return service.provjeravanjePraznina();
    }

    @GetMapping("/praznine/popunjavanje")
    public void popunjavanjePraznina(){
        logger.debug("Popunjavanje praznina i balici koja sadzri podatke o tecajevima");
        service.popunjavanjePraznina();
    }


}
