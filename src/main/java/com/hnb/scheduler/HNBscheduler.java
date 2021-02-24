package com.hnb.scheduler;

import com.hnb.query.Queries;
import com.hnb.service.HNBservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;

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
            logger.debug("Provjera - sve je updateano");
        }
        else if(lastDate==null){
            service.napuniBazu();
            System.out.println("Baza je napunjena od nule");

        }
        else {
            service.upadateajBazu(lastDate);
            System.out.println("Updateanje baze");
        }
    }
}
