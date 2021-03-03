package com.hnb.downloads;

import com.hnb.app.models.Tecajevi;
import com.hnb.app.query.Queries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadService {

    @Autowired
    Queries queries;

    public List<Tecajevi> listaZaExcel(String valuta, String datumOd, String datumDo) {
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

            List<Tecajevi> tecajeviOdDo= new ArrayList<>();

            List<String> brojTecajnice = queries.getBrojTecajniceRaspon(valuta, start, end);
            List<String> datumPrimjene = queries.getDatumPrimjeneRaspon(valuta, start, end);
            List<String> drzava = queries.getDrzavaRaspon(valuta, start, end);
            List<String> drzavaISO = queries.getDrzavaISORaspon(valuta, start, end);
            List<String> sifraValute = queries.getSifraValuteRaspon(valuta, start, end);
            List<String> jedinica = queries.getJedinicaRaspon(valuta, start, end);
            List<String> kupovniTecaj = queries.getKupovniTecajRaspon(valuta, start, end);
            List<String> srednjiTecaj = queries.getSrednjiTecajRaspon(valuta, start, end);
            List<String> prodajniTecaj = queries.getProdajniTecajRaspon(valuta, start, end);
            List<String> id = queries.getIdRaspon(valuta, start, end);

            for (int i = 0; i < brojTecajnice.size(); i++) {
                tecajeviOdDo.add(new Tecajevi(brojTecajnice.get(i), LocalDate.parse(datumPrimjene.get(i)), drzava.get(i), drzavaISO.get(i), sifraValute.get(i), valuta, Integer.parseInt(jedinica.get(i)), kupovniTecaj.get(i), srednjiTecaj.get(i), prodajniTecaj.get(i), Long.parseLong(id.get(i))));
            }
            return tecajeviOdDo;
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valuta "+valuta+" ne postoji");
        }

    }




}
