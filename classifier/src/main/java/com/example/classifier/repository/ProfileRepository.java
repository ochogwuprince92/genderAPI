package com.example.classifier.repository;

import com.example.classifier.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    Optional<Profile> findByNameIgnoreCase(String name);
}