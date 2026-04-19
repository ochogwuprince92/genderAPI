package com.example.classifier.controller;

import com.example.classifier.entity.Profile;
import com.example.classifier.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/profiles")
@CrossOrigin(origins = "*") // CORS requirement
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    // CREATE PROFILE
    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody Map<String, Object> body) {

        Object nameObj = body.get("name");

        // VALIDATION
        if (!(nameObj instanceof String name) || name.isBlank()) {
            return ResponseEntity.status(400).body(
                    Map.of("status", "error", "message", "Missing or invalid name")
            );
        }

        Profile profile = service.createProfile(name);

        // RESPONSE
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("data", profile);

        return ResponseEntity.status(201).body(response);
    }

    // GET SINGLE PROFILE
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable String id) {

        Optional<Profile> profile = service.getProfileById(id);

        if (profile.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Map.of("status", "error", "message", "Profile not found")
            );
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("data", profile.get());

        return ResponseEntity.ok(response);
    }

    // GET ALL PROFILES (WITH FILTERS)
    @GetMapping
    public ResponseEntity<?> getAllProfiles(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String country_id,
            @RequestParam(required = false) String age_group
    ) {

        List<Profile> profiles = service.getAllProfiles(gender, country_id, age_group);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("count", profiles.size());
        response.put("data", profiles);

        return ResponseEntity.ok(response);
    }

    // DELETE PROFILE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable String id) {

        boolean deleted = service.deleteProfile(id);

        if (!deleted) {
            return ResponseEntity.status(404).body(
                    Map.of("status", "error", "message", "Profile not found")
            );
        }

        return ResponseEntity.noContent().build();
    }
}