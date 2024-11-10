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

    public CandidatureDetails getCandidatureById(long id) {
        Candidature candidature =  candidatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidature not found with id: " + id));

        User candidate = candidatureInterface.getUserById(candidature.getCandidateId()).getBody();
        Resume resume = candidatureInterface.getResume(candidature.getResumeId()).getBody();

        return new CandidatureDetails(candidature,candidate,resume);
    }

    @Transactional
    @Override
    public Candidature addCandidature(long jobOfferId, long candidateId, long resumeId, String cover_letter) {
        Candidature candidature = new Candidature();
        candidature.setJobOfferId(jobOfferId);
        candidature.setCandidateId(candidateId);
        candidature.setResumeId(resumeId);
        candidature.setCover_letter(cover_letter);
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
