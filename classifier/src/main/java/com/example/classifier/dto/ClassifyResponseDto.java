package com.example.classifier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("sample_size")
    private int sampleSize;

    @JsonProperty("is_confident")
    private boolean confident;

    @JsonProperty("processed_at")
    private String processedAt;
}