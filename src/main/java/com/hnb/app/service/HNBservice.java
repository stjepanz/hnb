package com.hnb.app.service;

import com.hnb.app.models.Tecajevi;
import com.hnb.app.query.Queries;
import com.hnb.app.repository.HNBcrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@Service
public class HNBservice {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoggerService loggerService;

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
    }

    public void popunjavanjePraznina(String loggedUser,
                                     HttpServletResponse response) {
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
        logger.debug("Popunjavanje praznina u talici koja sadrzi podatke o tecajevima");
        loggerService.spremiLog("Popunjavanje praznina u talici koja sadrzi podatke o tecajevima", "/praznine/popunjavanje", loggedUser, response.getStatus());
    }

    public String provjeravanjePraznina(String loggedUser,
                                        HttpServletResponse response) {
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
        logger.debug("Provjeravanje praznina u tablici koja sadrzi podatke o tecajevima");
        loggerService.spremiLog("Provjeravanje praznina u tablici koja sadrzi podatke o tecajevima", "/praznine/provjera", loggedUser, response.getStatus());
        return "Broj dana koji nedostaju: " + brojac;
    }
    

    public double prosjecnaSrednjaVrijednost(String valuta,
                                             String datumOd,
                                             String datumDo,
                                             String loggedUser,
                                             HttpServletResponse response) {
        List<String> svevalute = queries.getValute();
        valuta=valuta.toUpperCase();
        LocalDate start;
        LocalDate end;
        try {
            start=LocalDate.parse(datumOd);
        }catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Datum pocetka nije ispravan");
            loggerService.spremiLog("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Datum pocetka nije ispravan", "/srednjitecaj/"+valuta+"/"+datumOd+"/"+datumDo, loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum pocetka nije ispravan", e);
        }
        try {
            end=LocalDate.parse(datumDo);
        }catch (Exception e){
            response.setStatus(400);
            logger.debug("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Datum kraja nije ispravan");
            loggerService.spremiLog("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Datum kraja nije ispravan", "/srednjitecaj/"+valuta+"/"+datumOd+"/"+datumDo, loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum kraja nije ispravan", e);
        }
        if (start.isBefore(LocalDate.parse("1994-05-30")))
        {
            response.setStatus(400);
            logger.debug("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Prvi datum u bazi je 30.05.1994");
            loggerService.spremiLog("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Prvi datum u bazi je 30.05.1994", "/srednjitecaj/"+valuta+"/"+datumOd+"/"+datumDo, loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prvi datum u bazi je 30.05.1994");
        }
        if (end.isAfter(LocalDate.now()))
        {
            response.setStatus(400);
            logger.debug("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Datum jos nije dosao");
            loggerService.spremiLog("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Datum jos nije dosao", "/srednjitecaj/"+valuta+"/"+datumOd+"/"+datumDo, loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum jos nije dosao");
        }
        if (start.isAfter(end)){
            response.setStatus(400);
            logger.debug("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Datum pocetka je iza datuma kraja");
            loggerService.spremiLog("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Datum pocetka je iza datuma kraja", "/srednjitecaj/"+valuta+"/"+datumOd+"/"+datumDo, loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum pocetka je iza datuma kraja");
        }
        if (svevalute.contains(valuta)){
            double prosjek = 0;
            List<String> lista=queries.getProsjecniTecajeviRaspon(valuta, start, end);
            for (int i = 0; i < lista.size(); i++) {
                prosjek += Double.parseDouble(lista.get(i).replace(",", "."));
            }
            prosjek = prosjek / lista.size();
            logger.debug("Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+start+ " do "+end);
            loggerService.spremiLog("Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+start+ " do "+end, "/srednjitecaj/"+valuta+"/"+start+"/"+end, loggedUser, response.getStatus());
            return prosjek;
        }
        else{
            response.setStatus(400);
            logger.debug("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Valuta "+valuta+" ne postoji");
            loggerService.spremiLog("Error - Racunanje srednjeg tecaja za valutu "+valuta.toUpperCase()+" u periodu od "+datumOd+ " do "+datumDo+"- Valuta "+valuta+" ne postoji", "/srednjitecaj/"+valuta+"/"+datumOd+"/"+datumDo, loggedUser, response.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valuta "+valuta+" ne postoji");
        }
    }

    public List<String> getValute(String loggedUser,
                                  HttpServletResponse response){
        logger.debug("Dohvacanje svih valuta");
        loggerService.spremiLog("Dohvacanje svih valuta", "/valute", loggedUser, response.getStatus());
        return queries.getValute();
        }
}