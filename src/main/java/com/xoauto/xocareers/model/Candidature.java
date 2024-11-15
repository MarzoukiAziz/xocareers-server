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
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(length = 4000)
    String cover_letter;

    long candidateId;

    long resumeId;

    long jobOfferId;

    @Enumerated(EnumType.STRING)
    TypeStatus status;

    @Column(updatable = false)
    LocalDateTime appliedAt;

    LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        appliedAt = LocalDateTime.now();
        status = TypeStatus.APPLIED;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
