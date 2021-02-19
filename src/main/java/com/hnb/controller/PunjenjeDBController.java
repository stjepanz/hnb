package com.hnb.controller;

import com.hnb.models.Tecajevi;
import com.hnb.service.HNBDohvacanjePodataka;
import com.hnb.service.HNBtoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
public class PunjenjeDBController {

    @Autowired
    HNBtoDB service;

    @GetMapping("/punjenje")
    public void getAllTecajevi() {
        service.getAllTecajevi();
    }

    @GetMapping("/datum")
    public void getDate() {
        LocalDateTime datum = LocalDateTime.now();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(datum.format(myFormat));
    }
    @GetMapping("/datum1")
    public void getOdredeniTecajevi() throws ParseException {
        service.getOdredeniTecajevi();
    }
}
