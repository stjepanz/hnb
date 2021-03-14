package com.hnb.uploads;

import com.hnb.app.models.TecajeviUpload;
import org.springframework.data.repository.CrudRepository;

public interface UploadRepository extends CrudRepository<TecajeviUpload, Integer> {
}
