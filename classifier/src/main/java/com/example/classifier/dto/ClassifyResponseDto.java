package com.example.classifier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassifyResponseDto {

    private String name;
    private String gender;
    private double probability;
    private int sample_size;
    private boolean is_confident;
    private String processed_at;
}
