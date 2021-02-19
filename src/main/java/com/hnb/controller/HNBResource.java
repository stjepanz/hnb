package com.hnb.controller;

import com.hnb.models.Tecajevi;
import com.hnb.service.HNBDohvacanjePodataka;
import com.hnb.service.HNBtoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HNBResource {

    @Autowired
    HNBDohvacanjePodataka service;

    @GetMapping("/tecajevi")
    public List<Tecajevi> getAllTecajevi() {
        List<Tecajevi> allTecajevi = Arrays.asList(service.getAllTecajevi());
        return allTecajevi;
    }

    @GetMapping("/tecajevi/{valuta}")
    public Tecajevi getPostById(@PathVariable("valuta") String valuta) {
        Tecajevi tecaj = service.getTecaj(valuta);
        return tecaj;
    }


}
