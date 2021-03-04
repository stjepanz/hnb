package com.hnb.app.controller;

import com.hnb.app.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoggerController {

    @Autowired
    LoggerService loggerService;

    @GetMapping("/logovi/{od}/{do}")
    public List<String> logoviOdDo(@PathVariable("od") String start,
                                   @PathVariable("do") String end){
        return loggerService.getLogovi(start, end);
    }
}
