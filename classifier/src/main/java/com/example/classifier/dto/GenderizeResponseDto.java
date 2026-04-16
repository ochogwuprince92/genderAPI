package com.example.classifier.dto;

import lombok.Data;

@Data
public class GenderizeResponseDto {

    private String name;
    private String gender;
    private double probability;
    private int count;
}
