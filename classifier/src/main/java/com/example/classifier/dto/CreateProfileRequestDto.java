package com.example.classifier.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateProfileRequestDto {

    @Schema(description = "The name To Classify", example = "Imma")
    private String name;
}
