package com.xoauto.xocareers_jobs.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CandidatureJobDetails {
    Candidature candidature;
    JobOffer jobOffer;
    Resume resume;
}
