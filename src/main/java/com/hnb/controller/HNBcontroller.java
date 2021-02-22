package com.hnb.controller;

import com.hnb.query.Queries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HNBcontroller {

    @Autowired
    Queries queries;

    @GetMapping("/valute")
    public List<String> getValute(){
        return queries.getValute();
    }
}
