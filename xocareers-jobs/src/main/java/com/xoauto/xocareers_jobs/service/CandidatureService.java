package com.xoauto.xocareers_jobs.service;


import com.xoauto.xocareers_jobs.feign.CandidatureInterface;
import com.xoauto.xocareers_jobs.model.*;
import com.xoauto.xocareers_jobs.repository.CandidatureRepositoy;
import com.xoauto.xocareers_jobs.service.interfaces.ICandidatureService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidatureService implements ICandidatureService {

    private final CandidatureRepositoy candidatureRepository;
    private final CandidatureInterface candidatureInterface;
    private final JobOfferService jobOfferService;

    public CandidatureService( CandidatureRepositoy candidatureRepository, CandidatureInterface candidatureInterface, JobOfferService jobOfferService) {
        this.candidatureRepository = candidatureRepository;
        this.candidatureInterface = candidatureInterface;
        this.jobOfferService = jobOfferService;
    }

    @Override
    public List<CandidatureJobDetails>  getCandidatureJobDetailsByCandidate(long candidateId) {
        List<Candidature> candidatures = candidatureRepository.findAllByCandidateId(candidateId);
        List<CandidatureJobDetails> candidatureJobDetails = new ArrayList<>();
        for (Candidature candidature : candidatures) {
            CandidatureJobDetails   candidatureJobDetail = new CandidatureJobDetails();
            Resume resume = candidatureInterface.getResume(candidature.getResumeId()).getBody();
            JobOffer jobOffer = jobOfferService.getJobOfferById(candidature.getJobOfferId());
            candidatureJobDetail.setResume(resume);
            candidatureJobDetail.setJobOffer(jobOffer);
            candidatureJobDetail.setCandidature(candidature);
            candidatureJobDetails.add(candidatureJobDetail);
        }
        return candidatureJobDetails;
    }

    @Override
    public List<CandidatureDetails> getCandidatureJobDetailsByJob(long jobId) {
        List<Candidature> candidatures = candidatureRepository.findAllByJobOfferId(jobId);
        List<CandidatureDetails> candidatureDetails = new ArrayList<>();
        for (Candidature candidature : candidatures) {
            CandidatureDetails   candidatureDetail = new CandidatureDetails();
            Resume resume = candidatureInterface.getResume(candidature.getResumeId()).getBody();
            User candidate = candidatureInterface.getUserById(candidature.getCandidateId()).getBody();
            candidatureDetail.setResume(resume);
            candidatureDetail.setCandidate(candidate);
            candidatureDetail.setCandidature(candidature);
            candidatureDetails.add(candidatureDetail);
        }
        return candidatureDetails;
    }

    public CandidatureDetails getCandidatureById(long id) {
        Candidature candidature =  candidatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidature not found with id: " + id));

        User candidate = candidatureInterface.getUserById(candidature.getCandidateId()).getBody();
        Resume resume = candidatureInterface.getResume(candidature.getResumeId()).getBody();

        return new CandidatureDetails(candidature,candidate,resume);
    }

    @Transactional
    @Override
    public Candidature addCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    @Transactional
    @Override
    public Candidature updateCandidatureStatus(long candidatureId, TypeStatus status) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new EntityNotFoundException("Candidature not found with id: " + candidatureId));
        candidature.setStatus(status);
        return candidatureRepository.save(candidature);
    }

    @Transactional
    @Override
    public boolean deleteCandidature(long id) {
        if (!candidatureRepository.existsById(id)) {
            return false;
        }
        candidatureRepository.deleteById(id);
        return true;
    }
}
