package com.hnb.controller;

import com.hnb.service.HNBtoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class PunjenjeDBController {

    @Autowired
    HNBtoDB service;

    @GetMapping("/datum")
    public void getDate() {
        LocalDateTime datum = LocalDateTime.now();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    @GetMapping("/datum1")
    public void getOdredeniTecajevi() throws ParseException {
        service.napuniBazu();
    }
}
