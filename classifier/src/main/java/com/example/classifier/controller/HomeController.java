package com.example.classifier.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.http.HttpClient;

@RestController
public class HomeController {

    @GetMapping("/")
    public RedirectView redirect(){
        return new RedirectView("/api/classify?name=alex");
    }

}
