package com.example.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping(value = {"/", "/schedules/view/**", "/student-groups/view/**", "/api-schedule", "/metrics"
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}