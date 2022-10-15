package com.example.demo2.configuration;

import com.example.demo2.response.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonException {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception e){
//        GeneralResponse res = new GeneralResponse();
//        res.setMessage(e.getMessage());
//        return ResponseEntity.badRequest().body(res);
//    }
//
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleException(CustomException e){
        GeneralResponse res = new GeneralResponse();
        res.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(res);
    }

}
