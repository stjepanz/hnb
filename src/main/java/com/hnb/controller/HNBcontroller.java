package com.hnb.controller;

import com.hnb.query.Queries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
public class HNBcontroller {

    @Autowired
    Queries queries;

    @GetMapping("/valute")
    public List<String> getValute(){
        return queries.getValute();
    }

    @GetMapping("/srednjitecaj/{valuta}/{start}/{end}")
    public List<String> getSrednjiTecaj(@PathVariable("valuta") String valuta, @PathVariable("start") String start, @PathVariable("end") String end){
//        return queries.getProsjecniTecajeviRaspon("EUR", LocalDate.parse("2021-02-10"), LocalDate.parse("2021-02-22"));

        return queries.getProsjecniTecajeviRaspon(valuta, LocalDate.parse(start), LocalDate.parse(end));
    }
}
