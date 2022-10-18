package com.examplespr.service2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service2")
public class Service2Controller {

    @GetMapping("sample")
    public String getName(){return "Hello, I am from Service2";}


}
