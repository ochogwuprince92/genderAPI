package com.example.classifier.controller;

import com.example.classifier.dto.ClassifyResponseDto;
import com.example.classifier.service.GenderizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/classify")
public class ClassifyController {

    private final GenderizeService genderizeService;

    public ClassifyController(GenderizeService genderizeService) {
        this.genderizeService = genderizeService;
    }

    @GetMapping
    public ResponseEntity<?> classify(@RequestParam(required = false) String name) {

        // VALIDATION
        if (name == null || name.isBlank()) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("status", "error");
            error.put("message", "name query parameter is required");

            return ResponseEntity.status(400).body(error);
        }
            // CALL SERVICE
            ClassifyResponseDto response = genderizeService.classifyName(name);

            // RETURN SUCCESS RESPONSE
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("status", "success");
            body.put("data", response);

            return ResponseEntity.ok(body);
        }
    }