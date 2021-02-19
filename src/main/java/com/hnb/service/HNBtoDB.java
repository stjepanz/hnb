package com.hnb.service;

import com.hnb.models.Tecajevi;
import com.hnb.repository.HNBcrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class HNBtoDB {

    @Autowired
    HNBcrudRepository repository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public void getAllTecajevi() {
        Tecajevi[] sviTecajevi = webClientBuilder.build()
                .get()
                .uri("http://api.hnb.hr/tecajn/v2?datum-od=1994-05-30&datum-do=2021-02-19")
                .retrieve()
                .bodyToMono(Tecajevi[].class)
                .block();

        for (int i=0; i<sviTecajevi.length; i++){
            Tecajevi var = sviTecajevi[i];
            repository.save(var);
        }
        System.out.println("Gotovo");
    }

    public void getOdredeniTecajevi() throws ParseException {
        LocalDate pocetak = LocalDate.parse("2021-02-15");
        LocalDate danas = LocalDate.now();
        boolean zadnji = false;

        while (!zadnji) {
            if (!pocetak.isEqual(danas.plusDays(1))) {

                System.out.println(pocetak.toString());
                Tecajevi[] sviTecajevi = webClientBuilder.build()
                        .get()
                        .uri("http://api.hnb.hr/tecajn/v2?datum-primjene="+pocetak.toString())
                        .retrieve()
                        .bodyToMono(Tecajevi[].class)
                        .block();

                for (int i=0; i<sviTecajevi.length; i++) {
                    Tecajevi var = sviTecajevi[i];
                    repository.save(var);
                }
                pocetak = pocetak.plusDays(1);
            } else {
                System.out.println("Danasnji dan");
                zadnji = true;
            }
        }





//        System.out.println("Gotovo");
    }

}
