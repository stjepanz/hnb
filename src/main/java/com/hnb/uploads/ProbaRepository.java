package com.hnb.uploads;

import com.hnb.app.models.TecajeviUpload;
import com.hnb.app.models.Users;
import org.springframework.data.repository.CrudRepository;

public interface ProbaRepository extends CrudRepository<TecajeviUpload, Integer> {
}
