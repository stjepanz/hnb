package com.hnb.app.scheduler;

import com.hnb.app.service.HNBservice;
import com.hnb.app.query.Queries;
import com.hnb.app.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.time.LocalDate;

@Configuration
@EnableScheduling
public class HNBscheduler {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    HNBservice service;

    private final Queries queries;

    public HNBscheduler(Queries queries) {
        this.queries = queries;
    }


    @Scheduled(fixedRate = 3600000)
    public void scheduleFixedRateTask() throws ParseException {
        LocalDate localDate = LocalDate.now();
        LocalDate lastDate =  queries.getLastDate();

        if(localDate.equals(lastDate)){
            logger.debug("Provjera - sve je updateano (scheduler)");
        }
        else if(lastDate==null){
            service.napuniBazu();
            logger.debug("Baza je napunjena od nule (scheduler)");

        }
        else {
            service.upadateajBazu(lastDate);
            logger.debug("Baza je updateana sa podacima koji su nedostajali (scheduler)");
        }
    }
}
