package com.hnb.service;

import com.hnb.models.Tecajevi;
import com.hnb.repository.HNBcrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
