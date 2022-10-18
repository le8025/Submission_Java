package com.examplespr.service1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("service1")
public class Service1Controller {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @GetMapping("get")
    public String callAPI(){
        String response = restTemplate.getForObject("http://localhost:8082/service2/sample", String.class);
        System.out.println("Calling Service 2 API: ' " + response + "'");
        return "This is service1";
    }

    private String cbError(){
        // add some success data...
        return "Error, but using CircuitBreaker to send success response.";
    }

    @GetMapping("getcb")
    public String callAPICB(){
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        String url = "http://localhost:8082/service2/samplecb";
        //restTemplate.postForObject()
        return circuitBreaker.run(()-> restTemplate.getForObject(url, String.class),
                throwable -> cbError());
    }
}
