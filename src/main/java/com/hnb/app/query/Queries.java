package com.hnb.app.query;

import com.hnb.app.models.Tecajevi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface Queries extends CrudRepository<Tecajevi, Long> {

    @Query(value = "select datum_primjene from tecajevi order by datum_primjene desc limit 1", nativeQuery = true)
    LocalDate getLastDate();

    @Query(value = "select valuta from tecajevi group by valuta", nativeQuery = true)
    List<String> getValute ();

    @Query(value = "select srednji_tecaj from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3", nativeQuery = true)
    List<String> getProsjecniTecajeviRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select datum_primjene from tecajevi where datum_primjene = ?1 limit 1", nativeQuery = true)
    LocalDate provjeriDatum(LocalDate datum);
}
