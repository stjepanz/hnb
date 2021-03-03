package com.hnb.app.service;

import com.hnb.app.models.Tecajevi;
import com.hnb.app.query.Queries;
import com.hnb.app.repository.HNBcrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
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
            if (!pocetak.isEqual(danas)) {
                Tecajevi[] sviTecajevi = webClientBuilder.build()
                        .get()
                        .uri("http://api.hnb.hr/tecajn/v2?datum-primjene=" + pocetak.plusDays(1).toString())
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

    public double prosjecnaSrednjaVrijednost(String valuta, String datumOd, String datumDo) {
        List<String> svevalute = queries.getValute();
        valuta=valuta.toUpperCase();
        LocalDate start;
        LocalDate end;
        try {
            start=LocalDate.parse(datumOd);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum pocetka koji ste unijeli nije ispravan", e);
        }
        try {
            end=LocalDate.parse(datumDo);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum kraja koji ste unijeli nije ispravan", e);
        }
        if (start.isBefore(LocalDate.parse("1994-05-30")))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prvi datum u bazi je 30.05.1994");
        }
        if (end.isAfter(LocalDate.now()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Odabrali ste datum koji jos nije dosao");
        }
        if (start.isAfter(end)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum pocetka koji se upisali je iza datuma kraja");
        }
        if (svevalute.contains(valuta)){
            double prosjek = 0;
            List<String> lista=queries.getProsjecniTecajeviRaspon(valuta, start, end);
            for (int i = 0; i < lista.size(); i++) {
                prosjek += Double.parseDouble(lista.get(i).replace(",", "."));
            }
            prosjek = prosjek / lista.size();
            return prosjek;
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valuta "+valuta+" ne postoji");
        }
    }

    public List<String> getValute(){
        return queries.getValute();
        }
}