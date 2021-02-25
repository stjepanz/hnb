package com.hnb.app.controller;

import com.hnb.app.service.HNBservice;
import com.hnb.app.query.Queries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class HNBcontroller {

    double prosjek;

    @Autowired
    Queries queries;

    @Autowired
    HNBservice service;

    @GetMapping("/valute")
    public List<String> getValute(){
        return queries.getValute();
    }

    @GetMapping("/srednjitecaj/{valuta}/{start}/{end}")
    public double getSrednjiTecaj(@PathVariable("valuta") String valuta, @PathVariable("start") String start, @PathVariable("end") String end){
        prosjek=service.prosjecnaSrednjaVrijednost(queries.getProsjecniTecajeviRaspon(valuta, LocalDate.parse(start).minusDays(1), LocalDate.parse(end)));
        return prosjek;
    }

    @GetMapping("/praznine/provjera")
    public String provjeraPraznina(){
        return service.provjeravanjePraznina();
    }

    @GetMapping("/praznine/popunjavanje")
    public void popunjavanjePraznina(){
        service.popunjavanjePraznina();
    }
}
