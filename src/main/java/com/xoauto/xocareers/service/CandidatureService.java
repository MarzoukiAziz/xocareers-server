package com.xoauto.xocareers.service;


import com.xoauto.xocareers.model.*;
import com.xoauto.xocareers.repository.CandidatureRepositoy;
import com.xoauto.xocareers.service.interfaces.ICandidatureService;
import com.xoauto.xocareers.service.interfaces.IResumeService;
import com.xoauto.xocareers.service.interfaces.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidatureService implements ICandidatureService {

    private final CandidatureRepositoy candidatureRepository;
    private final JobOfferService jobOfferService;
    private final IResumeService resumeService;
    private final IUserService  userService;

    public CandidatureService( CandidatureRepositoy candidatureRepository,  JobOfferService jobOfferService, IResumeService resumeService, IUserService userService) {
        this.candidatureRepository = candidatureRepository;
        this.jobOfferService = jobOfferService;
        this.resumeService = resumeService;
        this.userService = userService;
    }

    @Override
    public List<CandidatureJobDetails>  getCandidatureJobDetailsByCandidate(long candidateId) {
        List<Candidature> candidatures = candidatureRepository.findAllByCandidateId(candidateId);
        List<CandidatureJobDetails> candidatureJobDetails = new ArrayList<>();
        for (Candidature candidature : candidatures) {
            CandidatureJobDetails   candidatureJobDetail = new CandidatureJobDetails();
            Resume resume = resumeService.getResume(candidature.getResumeId());
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
            Resume resume = resumeService.getResume(candidature.getResumeId());
            User candidate = userService.findUserById(candidature.getCandidateId());
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


        Resume resume = resumeService.getResume(candidature.getResumeId());
        User candidate = userService.findUserById(candidature.getCandidateId());

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
