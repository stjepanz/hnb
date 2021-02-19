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
    private WebClient webClient = WebClient.create("http://api.hnb.hr/tecajn");

    HNBcrudRepository repository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Tecajevi[] getAllTecajevi() {
        final String POST_API = "http://api.hnb.hr/tecajn/v2";
        Tecajevi[] sviTecajevi = webClientBuilder.build()
                .get()
                .uri(POST_API)
                .retrieve()
                .bodyToMono(Tecajevi[].class)
                .block();
        return sviTecajevi;
    }

//    public Flux<Tecajevi> getTecaj(String valuta){
//        Flux<Tecajevi> a = webClient.get()
//                .uri("/v2?valuta="+valuta)
//                .retrieve()
//                .bodyToFlux(Tecajevi.class);
//
//
//        return a;
//    }






public Tecajevi getTecaj(String valuta){

        Tecajevi[] tecajLista = webClientBuilder.build()
                .get()
                .uri("http://api.hnb.hr/tecajn/v2?valuta="+valuta, valuta)
                .retrieve()
                .bodyToMono(Tecajevi[].class)
                .block();

        Tecajevi a = tecajLista[0];
        Tecajevi b = new Tecajevi(a.getBroj_tecajnice(), a.getDatum_primjene(), a.getDrzava(), a.getDrzava_iso(), a.getSifra_valute(), a.getValuta(), a.getJedinica(), a.getKupovni_tecaj(), a.getSrednji_tecaj(), a.getProdajni_tecaj());
        System.out.println(a);
        System.out.println(a.toString());
        System.out.println(b);
//        repository.save(new Tecajevi(a.getBroj_tecajnice(), a.getDatum_primjene(), a.getDrzava(), a.getDrzava_iso(), a.getSifra_valute(), a.getValuta(), a.getJedinica(), a.getKupovni_tecaj(), a.getSrednji_tecaj(), a.getProdajni_tecaj()));
        return a;
    }
}
