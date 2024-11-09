package com.xoauto.xocareers_jobs.controller;


import com.xoauto.xocareers_jobs.model.Candidature;
import com.xoauto.xocareers_jobs.model.TypeStatus;
import com.xoauto.xocareers_jobs.service.interfaces.ICandidatureService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/candidature")
public class CandidatureController {
    private final ICandidatureService candidatureService;

    public CandidatureController(ICandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    @GetMapping
    public ResponseEntity<List<Candidature>> getAllCandidatures() {
        List<Candidature> candidatures = candidatureService.getAllCandidatures();
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/job-offer")
    public ResponseEntity<List<Candidature>> getJobOfferCandidatures(@RequestParam long jobOfferId) {
        List<Candidature> candidatures = candidatureService.getCandidaturesByJobOffer(jobOfferId);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/candidate")
    public ResponseEntity<List<Candidature>> getCandidateCandidatures(@RequestParam long candidateId) {
        List<Candidature> candidatures = candidatureService.getCandidaturesByCandidate(candidateId);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/filter-job-offer")
    public ResponseEntity<List<Candidature>> getFilterJobOfferCandidatures(@RequestParam long jobOfferId, @RequestParam TypeStatus status) {
        List<Candidature> candidatures = candidatureService.getCandidaturesByStatusAndJobOffer(status, jobOfferId);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/filter-candidate")
    public ResponseEntity<List<Candidature>> getFilterCandidateCandidatures(@RequestParam long candidateId, @RequestParam TypeStatus status) {
        List<Candidature> candidatures = candidatureService.getCandidaturesByStatusAndCandidate(status, candidateId);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidature> getCandidatureById(@PathVariable long id) {
        Candidature candidature = candidatureService.getCandidatureById(id);
        return ResponseEntity.ok(candidature);
    }

    @PostMapping
    public ResponseEntity<Candidature> createCandidature(@RequestParam long jobOfferId, @RequestParam long candidateId, @RequestParam long resumeId, @RequestParam String cover_letter) {
        Candidature createdCandidature = candidatureService.addCandidature( jobOfferId,  candidateId, resumeId, cover_letter);
        return new ResponseEntity<>(createdCandidature, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<Candidature> updateCandidatureStatus(@RequestParam long candidatureId, @RequestParam TypeStatus status) {
        try {
            Candidature candidature = candidatureService.updateCandidatureStatus(candidatureId, status);
            return new ResponseEntity<>(candidature, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCandidatureById(@RequestParam long candidatureId) {
        try {
            candidatureService.deleteCandidature(candidatureId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }

    }

}
