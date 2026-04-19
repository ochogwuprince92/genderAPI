package com.example.classifier.service;

import com.example.classifier.dto.*;
import com.example.classifier.entity.Profile;
import com.example.classifier.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public Profile createProfile(String name) {

        // CHECK IF PROFILE ALREADY EXISTS (IDEMPOTENCY)
        Optional<Profile> existing = repository.findByNameIgnoreCase(name);
        if (existing.isPresent()) {
            return existing.get();
        }

        // CALL EXTERNAL APIs
        GenderizeResponseDto genderize =
                restTemplate.getForObject("https://api.genderize.io?name=" + name, GenderizeResponseDto.class);

        AgifyResponseDto agify =
                restTemplate.getForObject("https://api.agify.io?name=" + name, AgifyResponseDto.class);

        NationalizeResponseDto nationalize =
                restTemplate.getForObject("https://api.nationalize.io?name=" + name, NationalizeResponseDto.class);

        // EDGE CASE HANDLING

        if (genderize == null || genderize.getGender() == null || genderize.getCount() == 0) {
            throw new RuntimeException("Genderize returned an invalid response");
        }

        if (agify == null || agify.getAge() == null) {
            throw new RuntimeException("Agify returned an invalid response");
        }

        if (nationalize == null || nationalize.getCountry() == null || nationalize.getCountry().isEmpty()) {
            throw new RuntimeException("Nationalize returned an invalid response");
        }

        // CLASSIFICATION LOGIC

        // AGE GROUP
        int age = agify.getAge();
        String ageGroup;

        if (age <= 12) ageGroup = "child";
        else if (age <= 19) ageGroup = "teenager";
        else if (age <= 59) ageGroup = "adult";
        else ageGroup = "senior";

        // NATIONALITY (HIGHEST PROBABILITY)
        NationalizeResponseDto.Country topCountry =
                nationalize.getCountry()
                        .stream()
                        .max(Comparator.comparing(NationalizeResponseDto.Country::getProbability))
                        .orElseThrow();

        // CREATE ENTITY

        Profile profile = Profile.builder()
                .id(UUID.randomUUID().toString()) // we'll improve to UUID v7 later
                .name(name)
                .gender(genderize.getGender())
                .genderProbability(genderize.getProbability())
                .sampleSize(genderize.getCount())
                .age(age)
                .ageGroup(ageGroup)
                .countryId(topCountry.getCountry_id())
                .countryProbability(topCountry.getProbability())
                .createdAt(Instant.now())
                .build();

        // SAVE TO DATABASE
        return repository.save(profile);
    }

    // Wrapper methods for controller
    public Optional<Profile> getProfileById(String id) {
        return repository.findById(id);
    }

    public List<Profile> getAllProfiles(String gender, String countryId, String ageGroup) {
        List<Profile> profiles = repository.findAll();

        return profiles.stream()
                .filter(p -> gender == null || p.getGender().equalsIgnoreCase(gender))
                .filter(p -> countryId == null || p.getCountryId().equalsIgnoreCase(countryId))
                .filter(p -> ageGroup == null || p.getAgeGroup().equalsIgnoreCase(ageGroup))
                .toList();
    }

    public boolean deleteProfile(String id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}