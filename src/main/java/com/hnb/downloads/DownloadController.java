package com.hnb.downloads;

// Tecajevi i HNBcrudRepository su iz foldera app




import com.hnb.app.models.Tecajevi;
import com.hnb.app.query.Queries;
import com.hnb.app.repository.HNBcrudRepository;
import com.hnb.app.service.LoggerService;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DownloadController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoggerService loggerService;

    @Autowired
    HNBcrudRepository customerRepository;

    @Autowired
    Queries queries;

    @Autowired
    DownloadService service;

    @GetMapping(value = "/download")
    public ResponseEntity<InputStreamResource> excelCustomersReport(@RequestParam(required = false) String valuta,
                                                                    @RequestParam(required = false) String datumOd,
                                                                    @RequestParam(required = false) String datumDo,
                                                                    HttpServletResponse response,
                                                                    HttpServletRequest request) throws IOException {
        List<Tecajevi> tecajevi = service.listaZaExcel(valuta, datumOd, datumDo, request.getUserPrincipal().getName(), response);
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
