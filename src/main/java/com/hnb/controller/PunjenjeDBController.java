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
    public void getAllTecajevi() {
        service.getAllTecajevi();
    }
}
