package com.xoauto.xocareers.service;


import com.xoauto.xocareers.model.JobOffer;
import com.xoauto.xocareers.model.TypeJobType;
import com.xoauto.xocareers.repository.JobOfferRepository;
import com.xoauto.xocareers.service.interfaces.IJobOfferService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobOfferService implements IJobOfferService {

    private final JobOfferRepository jobOfferRepository;

    public JobOfferService(JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
    }

    @Override
    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    @Override
    public List<JobOffer> getActiveJobOffers() {
        return jobOfferRepository.findAllActive();
    }

    @Override
    public List<JobOffer> getJobOffersByType(TypeJobType type, boolean active) {
        return jobOfferRepository.findAllByType(type, active);
    }

    @Override
    public List<JobOffer> searchJobOffers(String search, TypeJobType type, boolean active) {
        return jobOfferRepository.searchJobOfferBy(search,type, active);
    }

    @Override
    public JobOffer getJobOfferById(long id) {
        return jobOfferRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job Offer not found with id: " + id));
    }

    @Transactional
    @Override
    public JobOffer addJobOffer(JobOffer jobOffer,  long recruiterId) {
        jobOffer.setRecruiterId(recruiterId);
        return jobOfferRepository.save(jobOffer);
    }

    @Transactional
    @Override
    public JobOffer updateJobOffer(JobOffer jobOffer,  long recruiterId) {
        jobOffer.setRecruiterId(recruiterId);
        return jobOfferRepository.save(jobOffer);
    }

    @Transactional
    @Override
    public void deleteJobOffer(long id) {
        if (!jobOfferRepository.existsById(id)) {
            throw new EntityNotFoundException("Job Offer not found with id: " + id);
        }
        jobOfferRepository.deleteById(id);
    }
}
