package com.hnb.controller;

import com.hnb.query.Queries;
import com.hnb.service.HNBservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class HNBcontroller {

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

        double prosjek =service.prosjecnaSrednjaVrijednost(queries.getProsjecniTecajeviRaspon(valuta, LocalDate.parse(start).minusDays(1), LocalDate.parse(end)));
        return prosjek;
    }
}
