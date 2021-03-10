package com.hnb.app.query;

import com.hnb.app.models.Tecajevi;
import com.hnb.app.models.Users;
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


    //    Excel - sa valutom
    @Query(value = "select broj_tecajnice from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getBrojTecajniceRasponValuta(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select datum_primjene from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getDatumPrimjeneRasponValuta(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select drzava from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getDrzavaRasponValuta(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select drzava_iso from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getDrzavaISORasponValuta(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select sifra_valute from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getSifraValuteRasponValuta(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select jedinica from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getJedinicaRasponValuta(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select kupovni_tecaj from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getKupovniTecajRasponValuta(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select srednji_tecaj from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getSrednjiTecajRasponValuta(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select prodajni_tecaj from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getProdajniTecajRasponValuta(String valuta, LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select id from tecajevi where valuta = ?1 and datum_primjene between ?2 and ?3 order by datum_primjene asc", nativeQuery = true)
    List<String> getIdRasponValuta(String valuta, LocalDate datumOd, LocalDate datumDo);

//    Excel - bez valute
    @Query(value = "select broj_tecajnice from tecajevi where datum_primjene between ?1 and ?2 order by datum_primjene asc", nativeQuery = true)
    List<String> getBrojTecajniceRaspon(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select datum_primjene from tecajevi where datum_primjene between ?1 and ?2 order by datum_primjene asc", nativeQuery = true)
    List<String> getDatumPrimjeneRaspon(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select drzava from tecajevi where datum_primjene between ?1 and ?2 order by datum_primjene asc", nativeQuery = true)
    List<String> getDrzavaRaspon(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select drzava_iso from tecajevi where datum_primjene between ?1 and ?2 order by datum_primjene asc", nativeQuery = true)
    List<String> getDrzavaISORaspon(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select sifra_valute from tecajevi where datum_primjene between ?1 and ?2 order by datum_primjene asc", nativeQuery = true)
    List<String> getSifraValuteRaspon(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select jedinica from tecajevi where datum_primjene between ?1 and ?2 order by datum_primjene asc", nativeQuery = true)
    List<String> getJedinicaRaspon(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select kupovni_tecaj from tecajevi where datum_primjene between ?1 and ?2 order by datum_primjene asc", nativeQuery = true)
    List<String> getKupovniTecajRaspon(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select srednji_tecaj from tecajevi where datum_primjene between ?1 and ?2 order by datum_primjene asc", nativeQuery = true)
    List<String> getSrednjiTecajRaspon(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select prodajni_tecaj from tecajevi where datum_primjene between ?1 and ?2 order by datum_primjene asc", nativeQuery = true)
    List<String> getProdajniTecajRaspon(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select id from tecajevi where datum_primjene between ?1 and ?2 order by datum_primjene asc", nativeQuery = true)
    List<String> getIdRaspon(LocalDate datumOd, LocalDate datumDo);


    //    Logger
    @Query(value = "select * from logovi where vrijeme between ?1 and ?2 and logged_user=?3", nativeQuery = true)
    List<String> getLogoviPoDatumuIUseru(LocalDate datumOd, LocalDate datumDo, String username);

    @Query(value = "select * from logovi where vrijeme between ?1 and ?2", nativeQuery = true)
    List<String> getLogoviPoDatumu(LocalDate datumOd, LocalDate datumDo);

    @Query(value = "select vrijeme from logovi order by vrijeme asc limit 1", nativeQuery = true)
    Timestamp getPrviDatumLogger();
    @Query(value = "select vrijeme from logovi order by vrijeme desc limit 1", nativeQuery = true)
    Timestamp getZadnjiDatumLogger();


//    Users

    @Query(value = "select roles from users where username = ?1", nativeQuery = true)
    String getRolesByUsername(String username);

//    @Transactional
//    @Modifying(clearAutomatically = true)
    @Query(value = "delete from users where username = ?1", nativeQuery = true)
    void deleteUserByUsername(String username);
}
