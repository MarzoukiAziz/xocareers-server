package com.xoauto.xocareers.controller;


import com.xoauto.xocareers.model.*;
import com.xoauto.xocareers.service.CandidatureService;
import com.xoauto.xocareers.service.interfaces.ICandidatureService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("candidature")
public class CandidatureController {
    private final ICandidatureService candidatureService;

    public CandidatureController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    //// USER ///
    @GetMapping("candidate/{id}")
    public ResponseEntity<List<CandidatureJobDetails> > getAllCandidatureByCandidateId(@PathVariable long id) {
        List<CandidatureJobDetails> candidatures = candidatureService.getCandidatureJobDetailsByCandidate(id);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatureDetails> getCandidatureById(@PathVariable long id) {
        CandidatureDetails candidature = candidatureService.getCandidatureById(id);
        return ResponseEntity.ok(candidature);
    }

    @PostMapping
    public ResponseEntity<Candidature> createCandidature(@RequestBody Candidature candidature) {
        Candidature createdCandidature = candidatureService.addCandidature( candidature);
        return new ResponseEntity<>(createdCandidature, HttpStatus.CREATED);
    }

    //// ADMIN ////

    @GetMapping("/admin/job-offer/{id}")
    public ResponseEntity<List<CandidatureDetails> > getAllCandidatureByJobID(@PathVariable long id) {
        List<CandidatureDetails> candidatures = candidatureService.getCandidatureJobDetailsByJob(id);
        return ResponseEntity.ok(candidatures);
    }


    @PatchMapping("/admin")
    public ResponseEntity<Candidature> updateCandidatureStatus(@RequestParam long candidatureId, @RequestParam TypeStatus status) {
        try {
            Candidature candidature = candidatureService.updateCandidatureStatus(candidatureId, status);
            return new ResponseEntity<>(candidature, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/admin")
    public ResponseEntity<String> deleteCandidatureById(@RequestParam long candidatureId) {
        try {
            candidatureService.deleteCandidature(candidatureId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }

    }

}
