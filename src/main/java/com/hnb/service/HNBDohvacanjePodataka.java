package com.hnb.service;

import com.hnb.models.Tecajevi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class HNBDohvacanjePodataka {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Tecajevi[] getAllTecajevi() {
        final String POST_API = "http://api.hnb.hr/tecajn/v2";
        Tecajevi[] a = webClientBuilder.build()
                .get()
                .uri(POST_API)
                .retrieve()
                .bodyToMono(Tecajevi[].class)
                .block();

        return a;
    }

    public Tecajevi getTecaj(String valuta){
        Tecajevi[] tecajLista = webClientBuilder.build()
                .get()
                .uri("http://api.hnb.hr/tecajn/v2?valuta=" + valuta, valuta)
                .retrieve()
                .bodyToMono(Tecajevi[].class)
                .block();
        return tecajLista[0];
    }
}
