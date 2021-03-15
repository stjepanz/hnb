//package com.hnb.uploads;
//
//import com.hnb.app.service.LoggerService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletResponse;
//
//@Service
//public class UploadFlag {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    LoggerService loggerService;
//    int f=0;
//
//    public void LogirajUpload(int flag, String loggedUser, HttpServletResponse response){
//        if (flag!=0){
//            logger.debug("Uploadanje excela");
//            loggerService.spremiLog("Downloadanje excela", "/upload/", loggedUser, response.getStatus());
//        }
//    }
//
//}
