package com.xoauto.xocareers.controller;


import com.xoauto.xocareers.model.JobOffer;
import com.xoauto.xocareers.model.TypeJobType;
import com.xoauto.xocareers.service.interfaces.IJobOfferService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("job-offer")
public class JobOfferController {
    private final IJobOfferService jobOfferService;

    public JobOfferController(IJobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    //// PUBLIC ////

    @GetMapping("/only-active")
    public ResponseEntity<List<JobOffer>> getOnlyActiveJobOffers() {
        List<JobOffer> activeJobOffers = jobOfferService.getActiveJobOffers();
        return ResponseEntity.ok(activeJobOffers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOffer> getJobOfferById(@PathVariable long id) {
        JobOffer jobOffer = jobOfferService.getJobOfferById(id);
        return ResponseEntity.ok(jobOffer);
    }

    //// ADMIN ////

    @GetMapping("admin/all")
    public ResponseEntity<List<JobOffer>> getAllJobOffers() {
        List<JobOffer> jobOffers = jobOfferService.getAllJobOffers();
        return ResponseEntity.ok(jobOffers);
    }

    @GetMapping("/admin/filter-type")
    public ResponseEntity<List<JobOffer>> getFilterTypeJobOffers(@RequestParam TypeJobType type, @RequestParam boolean active) {
        List<JobOffer> filteredJobOffers = jobOfferService.getJobOffersByType(type, active);
        return ResponseEntity.ok(filteredJobOffers);

    }

    @GetMapping("/admin/search")
    public ResponseEntity<List<JobOffer>> searchJobOffers(@RequestParam String search, @RequestParam TypeJobType type, @RequestParam boolean active) {
        List<JobOffer> filteredJobOffers = jobOfferService.searchJobOffers(search,type, active);
        return ResponseEntity.ok(filteredJobOffers);
    }

    @PostMapping("/admin")
    public ResponseEntity<JobOffer> createJobOffer(@RequestBody JobOffer jobOffer, @RequestParam long recruiterId) {
        JobOffer createdJobOffer = jobOfferService.addJobOffer(jobOffer,recruiterId);
        return new ResponseEntity<>(createdJobOffer, HttpStatus.CREATED);
    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity<JobOffer> updateJobOffer(@PathVariable int id, @RequestBody JobOffer jobOffer, @RequestParam long recruiterId) {
        jobOffer.setId(id);
        JobOffer updatedJobOffer = jobOfferService.updateJobOffer(jobOffer,recruiterId);
        return new ResponseEntity<>(updatedJobOffer, HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteJobOffer(@PathVariable long id) {
        try {
            jobOfferService.deleteJobOffer(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
