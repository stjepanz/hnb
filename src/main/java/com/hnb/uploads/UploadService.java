package com.hnb.uploads;

import com.hnb.app.models.TecajeviUpload;
import com.hnb.app.service.LoggerService;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
public class UploadService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoggerService loggerService;

//    @Autowired
//    UploadFlag uploadFlag;

    @Autowired
    UploadRepository uploadRepository;

    private final UploadUtil uploadUtil;

    public UploadService(UploadUtil uploadUtil){
        this.uploadUtil=uploadUtil;
    }

    public void upload(MultipartFile file,
                       String loggedUser,
                       HttpServletResponse response) throws Exception {
        final Integer[] greska = {new Integer(0)};
        final Integer[] flag = {new Integer(0)};
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
                int jedinica = (int) row.getCell(6).getNumericCellValue();
                String kupovni_tecaj = row.getCell(7).getStringCellValue();
                String srednji_tecaj = row.getCell(8).getStringCellValue();
                String prodajni_tecaj = row.getCell(9).getStringCellValue();

                TecajeviUpload tecajeviUpload =new TecajeviUpload(broj_tecajnice, datum_primjene, drzava, drzava_iso, sifra_valute, valuta, jedinica, kupovni_tecaj, srednji_tecaj, prodajni_tecaj);
                flag[0] =1;
                uploadRepository.save(tecajeviUpload);

            }catch (Exception e){
                greska[0]+=1;
                if (greska[0]>1){
                    System.out.println("greska");
                    response.setStatus(400);
                    logger.debug("Error - Uploadanje excela");
                    loggerService.spremiLog("Error - Uploadanje excela", "/upload/", loggedUser, response.getStatus());
                }
            }
        });
        if (flag[0].equals(1)){
            logger.debug("Uploadanje excela");
            loggerService.spremiLog("Uploadanje excela", "/upload/", loggedUser, response.getStatus());
        }

    }
}