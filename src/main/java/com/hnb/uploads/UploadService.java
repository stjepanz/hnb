package com.hnb.uploads;

import com.hnb.app.models.TecajeviUpload;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class UploadService {

    @Autowired
    UploadRepository uploadRepository;

    private final UploadUtil uploadUtil;

    public UploadService(UploadUtil uploadUtil){
        this.uploadUtil=uploadUtil;
    }

    public void upload(MultipartFile file) throws Exception {


        Path tempDir = Files.createTempDirectory("");
        File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
        file.transferTo(tempFile);

        Workbook workbook = WorkbookFactory.create(tempFile);
        Sheet sheet = workbook.getSheetAt(0);

        Supplier<Stream<Row>> rowStreamSupplier = uploadUtil.getRowStreamSupplier(sheet);

        rowStreamSupplier.get().forEach(row -> {


            try {

                String broj_tecajnice = row.getCell(0).getStringCellValue();
                LocalDate datum_primjene = LocalDate.parse(row.getCell(1).getStringCellValue());
                String drzava = row.getCell(2).getStringCellValue();
                String drzava_iso = row.getCell(3).getStringCellValue();
                String sifra_valute = row.getCell(4).getStringCellValue();
                String valuta = row.getCell(5).getStringCellValue();
                System.out.println(valuta);
                int jedinica = (int) row.getCell(6).getNumericCellValue();
                String kupovni_tecaj = row.getCell(7).getStringCellValue();
                String srednji_tecaj = row.getCell(8).getStringCellValue();
                String prodajni_tecaj = row.getCell(9).getStringCellValue();

                TecajeviUpload tecajeviUpload =new TecajeviUpload(broj_tecajnice, datum_primjene, drzava, drzava_iso, sifra_valute, valuta, jedinica, kupovni_tecaj, srednji_tecaj, prodajni_tecaj);
                uploadRepository.save(tecajeviUpload);

            }catch (Exception e){
            }
        });

    }
}