package com.hnb.query;

import com.hnb.models.Tecajevi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Queries extends CrudRepository<Tecajevi, Long> {

    @Query(value = "select datum_primjene from tecajevi order by datum_primjene desc limit 1", nativeQuery = true)
    CharSequence getLastDate();

}
