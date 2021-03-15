package com.hnb.uploads;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService){
        this.uploadService=uploadService;
    }

    @PostMapping("upload")
    public void upload(@RequestParam("file")MultipartFile file,
                       HttpServletResponse response,
                       HttpServletRequest request) throws Exception {
        uploadService.upload(file, request.getUserPrincipal().getName(), response);
    }
}
