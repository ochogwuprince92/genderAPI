package com.example.classifier.dto;

import lombok.Data;

import java.util.List;

@Data
public class NationalizeResponseDto {

    private String name;
    private List<Country> country;

    @Data
    public static class Country {
        private String country_id;
        private double probability;
    }
}