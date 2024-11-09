package com.xoauto.xocareers_jobs.service;


import com.xoauto.xocareers_jobs.model.Candidature;
import com.xoauto.xocareers_jobs.model.TypeStatus;
import com.xoauto.xocareers_jobs.repository.CandidatureRepositoy;
import com.xoauto.xocareers_jobs.service.interfaces.ICandidatureService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CandidatureService implements ICandidatureService {

    private final CandidatureRepositoy candidatureRepository;

    public CandidatureService( CandidatureRepositoy candidatureRepository) {
        this.candidatureRepository = candidatureRepository;
    }

    @Override
    public List<Candidature> getAllCandidatures() {
        return candidatureRepository.findAll();
    }

    @Override
    public List<Candidature> getCandidaturesByJobOffer(long jobOfferId) {
        return candidatureRepository.findAllByJobOfferId(jobOfferId);
    }

    @Override
    public List<Candidature> getCandidaturesByCandidate(long candidateId) {
        return candidatureRepository.findAllByCandidateId(candidateId);
    }

    @Override
    public List<Candidature> getCandidaturesByStatusAndCandidate(TypeStatus status, long candidateId) {
        return candidatureRepository.findAllByStatusAndCandidate(candidateId, status);
    }


    @Override
    public List<Candidature> getCandidaturesByStatusAndJobOffer(TypeStatus status, long jobOfferId) {
        return candidatureRepository.findAllByStatusAndJobOffer(jobOfferId, status);
    }

    @Override
    public Candidature getCandidatureById(long id) {
        return candidatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidature not found with id: " + id));
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
