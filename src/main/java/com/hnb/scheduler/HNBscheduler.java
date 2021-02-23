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

@Configuration
@EnableScheduling
public class HNBscheduler {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    HNBservice service;

    private final Queries queries;

    public HNBscheduler(Queries queries) {
        this.queries = queries;
    }


    @Scheduled(fixedRate = 3600000)
    public void scheduleFixedRateTask() throws ParseException {
        try {
            LocalDate localDate = LocalDate.now();

            if(localDate.equals(LocalDate.parse(queries.getLastDate()))){
                logger.debug("Sve je updateano");
            }
            else{
                logger.debug("Updateanje");
                service.upadateajBazu(LocalDate.parse(queries.getLastDate()));
                logger.debug("Updateano");
            }
        }
        catch (Exception e){
            logger.debug("Punjenje");
            service.napuniBazu();
            logger.debug("Napunjeno");
        }
    }
}
