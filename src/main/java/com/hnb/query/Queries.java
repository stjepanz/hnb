package com.hnb.query;

import com.hnb.models.Tecajevi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface Queries extends CrudRepository<Tecajevi, Long> {

    @Query(value = "select datum_primjene from tecajevi order by datum_primjene desc limit 1", nativeQuery = true)
    CharSequence getLastDate();

    @Query(value = "select valuta from tecajevi group by valuta", nativeQuery = true)
    List<String> getValute ();

    @Query(value = "select srednji_tecaj from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3", nativeQuery = true)
    List<String> getProsjecniTecajeviRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);
}
