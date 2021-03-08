package com.hnb.app.query;

import com.hnb.app.models.Tecajevi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface Queries extends CrudRepository<Tecajevi, Long> {

//    HNB tecajevi
    @Query(value = "select datum_primjene from tecajevi order by datum_primjene desc limit 1", nativeQuery = true)
    LocalDate getLastDate();

    @Query(value = "select valuta from tecajevi group by valuta", nativeQuery = true)
    List<String> getValute ();

    @Query(value = "select srednji_tecaj from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3", nativeQuery = true)
    List<String> getProsjecniTecajeviRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select datum_primjene from tecajevi where datum_primjene = ?1 limit 1", nativeQuery = true)
    LocalDate provjeriDatum(LocalDate datum);


//    Excel
    @Query(value = "select broj_tecajnice from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getBrojTecajniceRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select datum_primjene from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getDatumPrimjeneRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select drzava from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getDrzavaRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select drzava_iso from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getDrzavaISORaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select sifra_valute from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getSifraValuteRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select jedinica from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getJedinicaRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select kupovni_tecaj from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getKupovniTecajRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select srednji_tecaj from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getSrednjiTecajRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select prodajni_tecaj from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getProdajniTecajRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select id from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getIdRaspon(String valuta, LocalDate datumOd, LocalDate datumDo);


//    Logger
    @Query(value = "select * from logovi where vrijeme between ?1 and ?2 and logged_user=?3", nativeQuery = true)
    List<String> getLogoviPoDatumuIUseru(LocalDate datumOd, LocalDate datumDo, String username);

    @Query(value = "select * from logovi where vrijeme between ?1 and ?2", nativeQuery = true)
    List<String> getLogoviPoDatumu(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select vrijeme from logovi order by vrijeme asc limit 1", nativeQuery = true)
    Timestamp getPrviDatumLogger();
    @Query(value = "select vrijeme from logovi order by vrijeme desc limit 1", nativeQuery = true)
    Timestamp getZadnjiDatumLogger();
}
