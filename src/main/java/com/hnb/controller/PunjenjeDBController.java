package com.hnb.controller;

import com.hnb.models.Tecajevi;
import com.hnb.service.HNBDohvacanjePodataka;
import com.hnb.service.HNBtoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@RestController
public class PunjenjeDBController {

    @Autowired
    HNBtoDB service;

    @GetMapping("/punjenje")
    public List<Tecajevi> getAllTecajevi() {
        List<Tecajevi> allTecajevi = Arrays.asList(service.getAllTecajevi());
        return allTecajevi;
    }

//    @GetMapping("/punjenje/{valuta}")
//    public Flux<Tecajevi> getPostById(@PathVariable("valuta") String valuta) {
//        Flux<Tecajevi> tecaj = service.getTecaj(valuta);
//        return tecaj;
//    }

    @GetMapping("/punjenje/{valuta}")
    public Tecajevi getPostById(@PathVariable("valuta") String valuta) {
        Tecajevi tecaj = service.getTecaj(valuta);
        return tecaj;
    }
}
