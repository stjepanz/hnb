package com.hnb.service;

import com.hnb.models.Tecajevi;
import com.hnb.repository.HNBcrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Service
public class HNBservice {

    @Autowired
    HNBcrudRepository repository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public void upadateajBazu(LocalDate start) throws ParseException {
        LocalDate pocetak = start;
        LocalDate danas = LocalDate.now();
        boolean zadnji = false;
        while (!zadnji) {
            if (!pocetak.isEqual(danas.plusDays(1))) {
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
                zadnji = true;
            }
        }
    }

    public void napuniBazu() throws ParseException {
        LocalDate pocetak = LocalDate.parse("1994-05-30");
        LocalDate danas = LocalDate.now();
        boolean zadnji = false;
        while (!zadnji) {
            if (!pocetak.isEqual(danas.plusDays(1))) {
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
                zadnji = true;
            }
        }
    }

    public double prosjecnaSrednjaVrijednost(List<String> lista){
        double prosjek = 0;
        for (int i = 0; i< lista.size(); i++){
            prosjek+=Double.parseDouble(lista.get(i).replace(",", "."));
        }
        prosjek=prosjek/lista.size();
        return prosjek;
    }
}
