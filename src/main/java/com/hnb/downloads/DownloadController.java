package com.hnb.downloads;

// Tecajevi i HNBcrudRepository su iz foldera app




import com.hnb.app.models.Tecajevi;
import com.hnb.app.query.Queries;
import com.hnb.app.repository.HNBcrudRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DownloadController {

    @Autowired
    HNBcrudRepository customerRepository;

    @Autowired
    Queries queries;

    @Autowired
    DownloadService service;

    @GetMapping(value = "/download/{valuta}/{start}/{end}")
    public ResponseEntity<InputStreamResource> excelCustomersReport(@PathVariable("valuta") String valuta, @PathVariable("start") String start, @PathVariable("end") String end) throws IOException {

        List<Tecajevi> tecajevi = service.listaZaExcel(valuta, LocalDate.parse(start), LocalDate.parse(end));

        ByteArrayInputStream in = ExcelGenerator.tecajeviToExcel(tecajevi);
        // return IOUtils.toByteArray(in);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=tecajevi.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }
}
