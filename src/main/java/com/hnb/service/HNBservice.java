package com.hnb.service;

import com.hnb.models.Tecajevi;
import com.hnb.query.Queries;
import com.hnb.repository.HNBcrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class HNBservice {

    @Autowired
    HNBcrudRepository repository;

    @Autowired
    Queries queries;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public void upadateajBazu(LocalDate start){
        LocalDate pocetak = start;
        LocalDate danas = LocalDate.now();
        boolean zadnji = false;
        while (!zadnji) {
            if (!pocetak.isEqual(danas.plusDays(1))) {
                Tecajevi[] sviTecajevi = webClientBuilder.build()
                        .get()
                        .uri("http://api.hnb.hr/tecajn/v2?datum-primjene=" + pocetak.toString())
                        .retrieve()
                        .bodyToMono(Tecajevi[].class)
                        .block();

                for (int i = 0; i < sviTecajevi.length; i++) {
                    Tecajevi var = sviTecajevi[i];
                    repository.save(var);
                }
                pocetak = pocetak.plusDays(1);
            } else {
                zadnji = true;
            }
        }
    }

    public void napuniBazu(){
        LocalDate pocetak = LocalDate.parse("1994-05-30");
        LocalDate danas = LocalDate.now();
        boolean zadnji = false;
        while (!zadnji) {
            if (!pocetak.isEqual(danas.plusDays(1))) {
                Tecajevi[] sviTecajevi = webClientBuilder.build()
                        .get()
                        .uri("http://api.hnb.hr/tecajn/v2?datum-primjene=" + pocetak.toString())
                        .retrieve()
                        .bodyToMono(Tecajevi[].class)
                        .block();

                for (int i = 0; i < sviTecajevi.length; i++) {
                    Tecajevi var = sviTecajevi[i];
                    repository.save(var);
                }
                pocetak = pocetak.plusDays(1);
            } else {
                zadnji = true;
            }
        }
//        for (int i = 0; i < 3; i++) {
//            popunjavanjePraznina();
//        }
    }

    public void popunjavanjePraznina() {
        LocalDate pocetak = LocalDate.parse("1994-05-30");
        LocalDate danas = LocalDate.now();
        boolean zadnji = false;
        while (!zadnji) {
            if (!pocetak.isEqual(danas.plusDays(1))) {
                if (queries.provjeriDatum(pocetak) == null) {
                    Tecajevi[] sviTecajevi = webClientBuilder.build()
                            .get()
                            .uri("http://api.hnb.hr/tecajn/v2?datum-primjene=" + pocetak.toString())
                            .retrieve()
                            .bodyToMono(Tecajevi[].class)
                            .block();

                    for (int i = 0; i < sviTecajevi.length; i++) {
                        Tecajevi var = sviTecajevi[i];
                        repository.save(var);
                    }
                }
                pocetak = pocetak.plusDays(1);
            } else {
                zadnji = true;
            }
        }
    }

    public String provjeravanjePraznina() {
        LocalDate pocetak = LocalDate.parse("1994-05-30");
        LocalDate danas = LocalDate.now();
        int brojac = 0;
        boolean zadnji = false;
        while (!zadnji) {
            if (!pocetak.isEqual(danas.plusDays(1))) {
                if (queries.provjeriDatum(pocetak) == null) {
                    brojac++;
                }
                pocetak = pocetak.plusDays(1);
            } else {
                zadnji = true;
            }
        }
        return "Broj dana koji nedostaju: " + brojac;
    }

    public double prosjecnaSrednjaVrijednost(List<String> lista) {
        double prosjek = 0;
        for (int i = 0; i < lista.size(); i++) {
            prosjek += Double.parseDouble(lista.get(i).replace(",", "."));
        }
        prosjek = prosjek / lista.size();
        return prosjek;
    }
}