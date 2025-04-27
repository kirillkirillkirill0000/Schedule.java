package com.example.schedule.controller;

import com.example.schedule.service.RequestCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    private RequestCounter requestCounter;

    @GetMapping("/count")
    public int getRequestCount() {
        return requestCounter.getCount();
    }

    @GetMapping("/count/reset")
    public String resetCounter() {
        requestCounter.reset();
        return "Counter reset successfully";
    }
}