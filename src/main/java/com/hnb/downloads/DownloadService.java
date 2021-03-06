package com.hnb.downloads;

import com.hnb.app.models.Tecajevi;
import com.hnb.app.query.Queries;
import com.hnb.app.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoggerService loggerService;

    @Autowired
    Queries queries;

    public List<Tecajevi> listaZaExcel(String valuta,
                                       String datumOd,
                                       String datumDo,
                                       String loggedUser,
                                       HttpServletResponse response) {
        List<String> svevalute = queries.getValute();
        LocalDate start;
        LocalDate end;

        if (valuta != null) {
            valuta = valuta.toUpperCase();
            if(!svevalute.contains(valuta)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valuta ne postoji u bazi");
            }
        }
        if (datumOd == null && datumDo == null) {
            if (valuta == null) {
                List<Tecajevi> tecajeviOdDo = new ArrayList<>();
                List<String> brojTecajnice = queries.getBrojTecajniceRaspon(LocalDate.now(), LocalDate.now());
                List<String> datumPrimjene = queries.getDatumPrimjeneRaspon(LocalDate.now(), LocalDate.now());
                List<String> drzava = queries.getDrzavaRaspon(LocalDate.now(), LocalDate.now());
                List<String> drzavaISO = queries.getDrzavaISORaspon(LocalDate.now(), LocalDate.now());
                List<String> sifraValute = queries.getSifraValuteRaspon(LocalDate.now(), LocalDate.now());
                List<String> valute = queries.getValuteRaspon(LocalDate.now(), LocalDate.now());
                List<String> jedinica = queries.getJedinicaRaspon(LocalDate.now(), LocalDate.now());
                List<String> kupovniTecaj = queries.getKupovniTecajRaspon(LocalDate.now(), LocalDate.now());
                List<String> srednjiTecaj = queries.getSrednjiTecajRaspon(LocalDate.now(), LocalDate.now());
                List<String> prodajniTecaj = queries.getProdajniTecajRaspon(LocalDate.now(), LocalDate.now());
                List<String> id = queries.getIdRaspon(LocalDate.now(), LocalDate.now());

                for (int i = 0; i < brojTecajnice.size(); i++) {
                    tecajeviOdDo.add(new Tecajevi(brojTecajnice.get(i), LocalDate.parse(datumPrimjene.get(i)), drzava.get(i), drzavaISO.get(i), sifraValute.get(i), valute.get(i), Integer.parseInt(jedinica.get(i)), kupovniTecaj.get(i), srednjiTecaj.get(i), prodajniTecaj.get(i), Long.parseLong(id.get(i))));
                }
                logger.debug("Downloadanje excela - Danasnji dan, sve valute");
                loggerService.spremiLog("Downloadanje excela - Danasnji dan, sve valute", "/download/", loggedUser, response.getStatus());
                return tecajeviOdDo;
            } else {
                List<Tecajevi> tecajeviOdDo = new ArrayList<>();
                List<String> brojTecajnice = queries.getBrojTecajniceRasponValuta(valuta, LocalDate.now(), LocalDate.now());
                List<String> datumPrimjene = queries.getDatumPrimjeneRasponValuta(valuta, LocalDate.now(), LocalDate.now());
                List<String> drzava = queries.getDrzavaRasponValuta(valuta, LocalDate.now(), LocalDate.now());
                List<String> drzavaISO = queries.getDrzavaISORasponValuta(valuta, LocalDate.now(), LocalDate.now());
                List<String> sifraValute = queries.getSifraValuteRasponValuta(valuta, LocalDate.now(), LocalDate.now());
                List<String> valute = queries.getValuteRasponValuta(valuta, LocalDate.now(), LocalDate.now());
                List<String> jedinica = queries.getJedinicaRasponValuta(valuta, LocalDate.now(), LocalDate.now());
                List<String> kupovniTecaj = queries.getKupovniTecajRasponValuta(valuta, LocalDate.now(), LocalDate.now());
                List<String> srednjiTecaj = queries.getSrednjiTecajRasponValuta(valuta, LocalDate.now(), LocalDate.now());
                List<String> prodajniTecaj = queries.getProdajniTecajRasponValuta(valuta, LocalDate.now(), LocalDate.now());
                List<String> id = queries.getIdRasponValuta(valuta, LocalDate.now(), LocalDate.now());

                for (int i = 0; i < brojTecajnice.size(); i++) {
                    tecajeviOdDo.add(new Tecajevi(brojTecajnice.get(i), LocalDate.parse(datumPrimjene.get(i)), drzava.get(i), drzavaISO.get(i), sifraValute.get(i), valute.get(i), Integer.parseInt(jedinica.get(i)), kupovniTecaj.get(i), srednjiTecaj.get(i), prodajniTecaj.get(i), Long.parseLong(id.get(i))));
                }
                logger.debug("Downloadanje excela - Danasnji dan, valuta: "+ valuta);
                loggerService.spremiLog("Downloadanje excela - Danasnji dan, valuta: "+ valuta, "/download/", loggedUser, response.getStatus());
                return tecajeviOdDo;
            }

        }
        else if (datumOd != null && datumDo != null){
            try{

                start=LocalDate.parse(datumOd);
            }catch (Exception e){
                response.setStatus(400);
                logger.debug("Error - Downloadanje excela - Datum pocetka nije ispravan");
                loggerService.spremiLog("Error - Downloadanje - Datum pocetka nije ispravan", "/download/", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum pocetka nije ispravan", e);
            }
            try {
                end=LocalDate.parse(datumDo);
            }catch (Exception e){
                response.setStatus(400);
                logger.debug("Error - Downloadanje excela - Datum kraja nije ispravan");
                loggerService.spremiLog("Error - Downloadanje excela - Datum kraja nije ispravan", "/download/", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum kraja nije ispravan", e);
            }
            if (start.isBefore(LocalDate.parse("1994-05-30")))
            {
                response.setStatus(400);
                logger.debug("Error - Downloadanje excela - Prvi datum u bazi je 30.05.1994");
                loggerService.spremiLog("Error - Downloadanje excela - Prvi datum u bazi je 30.05.1994", "/download/", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prvi datum u bazi je 30.05.1994");
            }
            if (end.isAfter(LocalDate.now()))
            {
                response.setStatus(400);
                logger.debug("Error - Downloadanje excela - Datum jos nije dosao");
                loggerService.spremiLog("Error - Downloadanje excela - Datum jos nije dosao", "/download/", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum jos nije dosao");
            }
            if (start.isAfter(end)){
                response.setStatus(400);
                logger.debug("Error - Downloadanje excela - Datum pocetka je iza datuma kraja");
                loggerService.spremiLog("Error - Downloadanje excela - Datum pocetka je iza datuma kraja", "/download/", loggedUser, response.getStatus());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum pocetka je iza datuma kraja");
            }
            if (valuta == null) {
                List<Tecajevi> tecajeviOdDo = new ArrayList<>();
                List<String> brojTecajnice = queries.getBrojTecajniceRaspon(start, end);
                List<String> datumPrimjene = queries.getDatumPrimjeneRaspon(start, end);
                List<String> drzava = queries.getDrzavaRaspon(start, end);
                List<String> drzavaISO = queries.getDrzavaISORaspon(start, end);
                List<String> sifraValute = queries.getSifraValuteRaspon(start, end);
                List<String> valute = queries.getValuteRaspon(start, end);
                List<String> jedinica = queries.getJedinicaRaspon(start, end);
                List<String> kupovniTecaj = queries.getKupovniTecajRaspon(start, end);
                List<String> srednjiTecaj = queries.getSrednjiTecajRaspon(start, end);
                List<String> prodajniTecaj = queries.getProdajniTecajRaspon(start, end);
                List<String> id = queries.getIdRaspon(start, end);

                for (int i = 0; i < brojTecajnice.size(); i++) {
                    tecajeviOdDo.add(new Tecajevi(brojTecajnice.get(i), LocalDate.parse(datumPrimjene.get(i)), drzava.get(i), drzavaISO.get(i), sifraValute.get(i), valute.get(i), Integer.parseInt(jedinica.get(i)), kupovniTecaj.get(i), srednjiTecaj.get(i), prodajniTecaj.get(i), Long.parseLong(id.get(i))));
                }
                logger.debug("Downloadanje excela - Datum od: "+datumOd+" do: "+ datumDo+", sve valute");
                loggerService.spremiLog("Downloadanje excela - Datum od: "+datumOd+" do: "+ datumDo+", sve valute", "/download/", loggedUser, response.getStatus());
                return tecajeviOdDo;
            } else {
                List<Tecajevi> tecajeviOdDo = new ArrayList<>();
                List<String> brojTecajnice = queries.getBrojTecajniceRasponValuta(valuta, start, end);
                List<String> datumPrimjene = queries.getDatumPrimjeneRasponValuta(valuta, start, end);
                List<String> drzava = queries.getDrzavaRasponValuta(valuta, start, end);
                List<String> drzavaISO = queries.getDrzavaISORasponValuta(valuta, start, end);
                List<String> sifraValute = queries.getSifraValuteRasponValuta(valuta, start, end);
                List<String> valute = queries.getValuteRasponValuta(valuta, start, end);
                List<String> jedinica = queries.getJedinicaRasponValuta(valuta, start, end);
                List<String> kupovniTecaj = queries.getKupovniTecajRasponValuta(valuta, start, end);
                List<String> srednjiTecaj = queries.getSrednjiTecajRasponValuta(valuta, start, end);
                List<String> prodajniTecaj = queries.getProdajniTecajRasponValuta(valuta, start, end);
                List<String> id = queries.getIdRasponValuta(valuta, start, end);

                for (int i = 0; i < brojTecajnice.size(); i++) {
                    tecajeviOdDo.add(new Tecajevi(brojTecajnice.get(i), LocalDate.parse(datumPrimjene.get(i)), drzava.get(i), drzavaISO.get(i), sifraValute.get(i), valute.get(i), Integer.parseInt(jedinica.get(i)), kupovniTecaj.get(i), srednjiTecaj.get(i), prodajniTecaj.get(i), Long.parseLong(id.get(i))));
                }
                logger.debug("Downloadanje excela - Datum od: "+datumOd+" do: "+ datumDo+", valuta: "+valuta);
                loggerService.spremiLog("Downloadanje excela - Datum od: "+datumOd+" do: "+ datumDo+", valuta: "+valuta, "/download/", loggedUser, response.getStatus());
                return tecajeviOdDo;
            }
        }
        else {
            if(datumOd!=null){
                try{
                    start=LocalDate.parse(datumOd);
                }catch (Exception e){
                    response.setStatus(400);
                    logger.debug("Error - Downloadanje excela - Datum pocetka nije ispravan");
                    loggerService.spremiLog("Error - Downloadanje - Datum pocetka nije ispravan", "/download/", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum pocetka nije ispravan", e);
                }
                if (start.isBefore(LocalDate.parse("1994-05-30")))
                {
                    response.setStatus(400);
                    logger.debug("Error - Downloadanje excela - Prvi datum u bazi je 30.05.1994");
                    loggerService.spremiLog("Error - Downloadanje excela - Prvi datum u bazi je 30.05.1994", "/download/", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prvi datum u bazi je 30.05.1994");
                }
                if (start.isAfter(LocalDate.now()))
                {
                    response.setStatus(400);
                    logger.debug("Error - Downloadanje excela - Datum jos nije dosao");
                    loggerService.spremiLog("Error - Downloadanje excela - Datum jos nije dosao", "/download/", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum jos nije dosao");
                }
                if(valuta==null){
                    List<Tecajevi> tecajeviOdDo = new ArrayList<>();
                    List<String> brojTecajnice = queries.getBrojTecajniceRaspon(start, start);
                    List<String> datumPrimjene = queries.getDatumPrimjeneRaspon(start, start);
                    List<String> drzava = queries.getDrzavaRaspon(start, start);
                    List<String> drzavaISO = queries.getDrzavaISORaspon(start, start);
                    List<String> sifraValute = queries.getSifraValuteRaspon(start, start);
                    List<String> valute = queries.getValuteRaspon(start, start);
                    List<String> jedinica = queries.getJedinicaRaspon(start, start);
                    List<String> kupovniTecaj = queries.getKupovniTecajRaspon(start, start);
                    List<String> srednjiTecaj = queries.getSrednjiTecajRaspon(start, start);
                    List<String> prodajniTecaj = queries.getProdajniTecajRaspon(start, start);
                    List<String> id = queries.getIdRaspon(start, start);

                    for (int i = 0; i < brojTecajnice.size(); i++) {
                        tecajeviOdDo.add(new Tecajevi(brojTecajnice.get(i), LocalDate.parse(datumPrimjene.get(i)), drzava.get(i), drzavaISO.get(i), sifraValute.get(i), valute.get(i), Integer.parseInt(jedinica.get(i)), kupovniTecaj.get(i), srednjiTecaj.get(i), prodajniTecaj.get(i), Long.parseLong(id.get(i))));
                    }
                    logger.debug("Downloadanje excela - Datum: "+datumOd+", sve valute");
                    loggerService.spremiLog("Downloadanje excela - Datum: "+datumOd+", sve valute", "/download/", loggedUser, response.getStatus());
                    return tecajeviOdDo;
                }
                else{
                    List<Tecajevi> tecajeviOdDo = new ArrayList<>();
                    List<String> brojTecajnice = queries.getBrojTecajniceRasponValuta(valuta, start, start);
                    List<String> datumPrimjene = queries.getDatumPrimjeneRasponValuta(valuta, start, start);
                    List<String> drzava = queries.getDrzavaRasponValuta(valuta, start, start);
                    List<String> drzavaISO = queries.getDrzavaISORasponValuta(valuta, start, start);
                    List<String> sifraValute = queries.getSifraValuteRasponValuta(valuta, start, start);
                    List<String> valute = queries.getValuteRasponValuta(valuta, start, start);
                    List<String> jedinica = queries.getJedinicaRasponValuta(valuta, start, start);
                    List<String> kupovniTecaj = queries.getKupovniTecajRasponValuta(valuta, start, start);
                    List<String> srednjiTecaj = queries.getSrednjiTecajRasponValuta(valuta, start, start);
                    List<String> prodajniTecaj = queries.getProdajniTecajRasponValuta(valuta, start, start);
                    List<String> id = queries.getIdRasponValuta(valuta, start, start);

                    for (int i = 0; i < brojTecajnice.size(); i++) {
                        tecajeviOdDo.add(new Tecajevi(brojTecajnice.get(i), LocalDate.parse(datumPrimjene.get(i)), drzava.get(i), drzavaISO.get(i), sifraValute.get(i), valute.get(i), Integer.parseInt(jedinica.get(i)), kupovniTecaj.get(i), srednjiTecaj.get(i), prodajniTecaj.get(i), Long.parseLong(id.get(i))));
                    }
                    logger.debug("Downloadanje excela - Datum: "+datumOd+", valuta: "+valuta);
                    loggerService.spremiLog("Downloadanje excela - Datum: "+datumOd+", valuta: "+valuta, "/download/", loggedUser, response.getStatus());
                    return tecajeviOdDo;
                }
            }
            else{
                try{
                    start=LocalDate.parse(datumDo);
                }catch (Exception e){
                    response.setStatus(400);
                    logger.debug("Error - Downloadanje excela - Datum pocetka nije ispravan");
                    loggerService.spremiLog("Error - Downloadanje - Datum pocetka nije ispravan", "/download/", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum pocetka nije ispravan", e);
                }
                if (start.isBefore(LocalDate.parse("1994-05-30")))
                {
                    response.setStatus(400);
                    logger.debug("Error - Downloadanje excela - Prvi datum u bazi je 30.05.1994");
                    loggerService.spremiLog("Error - Downloadanje excela - Prvi datum u bazi je 30.05.1994", "/download/", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prvi datum u bazi je 30.05.1994");
                }
                if (start.isAfter(LocalDate.now()))
                {
                    response.setStatus(400);
                    logger.debug("Error - Downloadanje excela - Datum jos nije dosao");
                    loggerService.spremiLog("Error - Downloadanje excela - Datum jos nije dosao", "/download/", loggedUser, response.getStatus());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datum jos nije dosao");
                }
                if(valuta==null){
                    List<Tecajevi> tecajeviOdDo = new ArrayList<>();
                    List<String> brojTecajnice = queries.getBrojTecajniceRaspon(start, start);
                    List<String> datumPrimjene = queries.getDatumPrimjeneRaspon(start, start);
                    List<String> drzava = queries.getDrzavaRaspon(start, start);
                    List<String> drzavaISO = queries.getDrzavaISORaspon(start, start);
                    List<String> sifraValute = queries.getSifraValuteRaspon(start, start);
                    List<String> valute = queries.getValuteRaspon(start, start);
                    List<String> jedinica = queries.getJedinicaRaspon(start, start);
                    List<String> kupovniTecaj = queries.getKupovniTecajRaspon(start, start);
                    List<String> srednjiTecaj = queries.getSrednjiTecajRaspon(start, start);
                    List<String> prodajniTecaj = queries.getProdajniTecajRaspon(start, start);
                    List<String> id = queries.getIdRaspon(start, start);

                    for (int i = 0; i < brojTecajnice.size(); i++) {
                        tecajeviOdDo.add(new Tecajevi(brojTecajnice.get(i), LocalDate.parse(datumPrimjene.get(i)), drzava.get(i), drzavaISO.get(i), sifraValute.get(i), valute.get(i), Integer.parseInt(jedinica.get(i)), kupovniTecaj.get(i), srednjiTecaj.get(i), prodajniTecaj.get(i), Long.parseLong(id.get(i))));
                    }
                    logger.debug("Downloadanje excela - Datum: "+datumDo+", sve valute");
                    loggerService.spremiLog("Downloadanje excela - Datum: "+datumDo+", valuta: "+valuta, "/download/", loggedUser, response.getStatus());
                    return tecajeviOdDo;
                }
                else{
                    List<Tecajevi> tecajeviOdDo = new ArrayList<>();
                    List<String> brojTecajnice = queries.getBrojTecajniceRasponValuta(valuta, start, start);
                    List<String> datumPrimjene = queries.getDatumPrimjeneRasponValuta(valuta, start, start);
                    List<String> drzava = queries.getDrzavaRasponValuta(valuta, start, start);
                    List<String> drzavaISO = queries.getDrzavaISORasponValuta(valuta, start, start);
                    List<String> sifraValute = queries.getSifraValuteRasponValuta(valuta, start, start);
                    List<String> valute = queries.getValuteRasponValuta(valuta, start, start);
                    List<String> jedinica = queries.getJedinicaRasponValuta(valuta, start, start);
                    List<String> kupovniTecaj = queries.getKupovniTecajRasponValuta(valuta, start, start);
                    List<String> srednjiTecaj = queries.getSrednjiTecajRasponValuta(valuta, start, start);
                    List<String> prodajniTecaj = queries.getProdajniTecajRasponValuta(valuta, start, start);
                    List<String> id = queries.getIdRasponValuta(valuta, start, start);

                    for (int i = 0; i < brojTecajnice.size(); i++) {
                        tecajeviOdDo.add(new Tecajevi(brojTecajnice.get(i), LocalDate.parse(datumPrimjene.get(i)), drzava.get(i), drzavaISO.get(i), sifraValute.get(i), valute.get(i), Integer.parseInt(jedinica.get(i)), kupovniTecaj.get(i), srednjiTecaj.get(i), prodajniTecaj.get(i), Long.parseLong(id.get(i))));
                    }
                    logger.debug("Downloadanje excela - Datum: "+datumDo+", valuta: "+valuta);
                    loggerService.spremiLog("Downloadanje excela - Datum: "+datumDo+", valuta: "+valuta, "/download/", loggedUser, response.getStatus());
                    return tecajeviOdDo;
                }
            }
        }
    }
}
