package com.xoauto.xocareers.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String title;

    @Column(length = 4000)
    String description;

    String location;

    @Enumerated(EnumType.STRING)
    TypeJobType jobType;

    long recruiterId;

    LocalDateTime updatedAt = LocalDateTime.now();

    @Column(updatable = false) // Prevents updates after creation
    LocalDateTime createdAt = LocalDateTime.now(); // Automatically set on creation

    Boolean active;

    @PrePersist // Automatically set before persisting
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
