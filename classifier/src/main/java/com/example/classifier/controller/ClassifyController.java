package com.example.classifier.controller;

import com.example.classifier.dto.ClassifyResponseDto;
import com.example.classifier.service.GenderizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
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
            return ResponseEntity.status(400).body(
                    Map.of(
                            "status", "error",
                            "message", "name query parameter is required"
                    )
            );
        }

        // CALL SERVICE
        ClassifyResponseDto response = genderizeService.classifyResponseDto(name);

        // RETURN SUCCESS RESPONSE
        return ResponseEntity.ok(
                Map.of(
                        "status", "success",
                        "data", response
                )
        );
    }
}
