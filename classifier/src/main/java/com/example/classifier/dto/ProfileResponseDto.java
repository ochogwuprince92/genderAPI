package com.example.classifier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ProfileResponseDto {

    private String id;
    private String name;
    private String gender;

    @JsonProperty("gender_probability")
    private double genderProbability;

    @JsonProperty("sample_size")
    private int sampleSize;

    private Integer age;

    @JsonProperty("age_group")
    private String ageGroup;

    @JsonProperty("country_id")
    private String countryId;

    @JsonProperty("country_probability")
    private double countryProbability;

    @JsonProperty("created_at")
    private Instant createdAt;
}