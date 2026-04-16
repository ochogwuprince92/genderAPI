package com.example.classifier.service;

import com.example.classifier.dto.ClassifyResponseDto;
import com.example.classifier.dto.GenderizeResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class GenderizeService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ClassifyResponseDto classifyName(String name ) {
        String url = "https://api.genderize.io?name=" + name;

        GenderizeResponseDto response =
                restTemplate.getForObject(url, GenderizeResponseDto.class);

        if(response == null || response.getGender() == null || response.getCount() == 0) {
            throw new RuntimeException("No prediction available for the provided name");
        }

        double probability = response.getProbability();
        int sampleSize = response.getCount();

        boolean isConfident = probability >= 0.7 && sampleSize >= 100;

        String processedAt = Instant.now().toString();

        return new ClassifyResponseDto(
                name,
                response.getGender(),
                probability,
                sampleSize,
                isConfident,
                processedAt
        );

    }
}
