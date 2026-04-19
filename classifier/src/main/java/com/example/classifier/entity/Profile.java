package com.example.classifier.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    private String gender;

    private double genderProbability;

    private int sampleSize;

    private Integer age;

    private String ageGroup;

    private String countryId;

    private double countryProbability;

    private Instant createdAt;
}