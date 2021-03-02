package com.hnb.downloads;

import com.hnb.app.models.Tecajevi;
import com.hnb.app.query.Queries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadService {

    @Autowired
    Queries queries;

    public List<Tecajevi> listaZaExcel(String valuta, LocalDate datumOd, LocalDate datumDo) {

        List<Tecajevi> tecajeviOdDo= new ArrayList<>();

        List<String> brojTecajnice = queries.getBrojTecajniceRaspon(valuta, datumOd, datumDo);
        List<String> datumPrimjene = queries.getDatumPrimjeneRaspon(valuta, datumOd, datumDo);
        List<String> drzava = queries.getDrzavaRaspon(valuta, datumOd, datumDo);
        List<String> drzavaISO = queries.getDrzavaISORaspon(valuta, datumOd, datumDo);
        List<String> sifraValute = queries.getSifraValuteRaspon(valuta, datumOd, datumDo);
        List<String> jedinica = queries.getJedinicaRaspon(valuta, datumOd, datumDo);
        List<String> kupovniTecaj = queries.getKupovniTecajRaspon(valuta, datumOd, datumDo);
        List<String> srednjiTecaj = queries.getSrednjiTecajRaspon(valuta, datumOd, datumDo);
        List<String> prodajniTecaj = queries.getProdajniTecajRaspon(valuta, datumOd, datumDo);
        List<String> id = queries.getIdRaspon(valuta, datumOd, datumDo);

        for (int i = 0; i < brojTecajnice.size(); i++) {
            tecajeviOdDo.add(new Tecajevi(brojTecajnice.get(i), LocalDate.parse(datumPrimjene.get(i)), drzava.get(i), drzavaISO.get(i), sifraValute.get(i), valuta, Integer.parseInt(jedinica.get(i)), kupovniTecaj.get(i), srednjiTecaj.get(i), prodajniTecaj.get(i), Long.parseLong(id.get(i))));
        }
        return tecajeviOdDo;
    }




}
