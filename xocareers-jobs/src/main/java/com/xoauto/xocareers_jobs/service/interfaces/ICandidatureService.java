package com.xoauto.xocareers_jobs.service.interfaces;

import com.xoauto.xocareers_jobs.model.Candidature;
import com.xoauto.xocareers_jobs.model.TypeStatus;

import java.util.List;

public interface ICandidatureService {
    List<Candidature> getAllCandidatures();
    List<Candidature> getCandidaturesByJobOffer(long jobOfferId);
    List<Candidature> getCandidaturesByCandidate(long candidateId);
    List<Candidature> getCandidaturesByStatusAndCandidate(TypeStatus status, long candidateId);
    List<Candidature> getCandidaturesByStatusAndJobOffer(TypeStatus status, long jobOfferId);
    Candidature getCandidatureById(long id);
    Candidature addCandidature( long jobOfferId, long candidateId,long resumeId, String cover_letter);
    Candidature updateCandidatureStatus(long candidatureId, TypeStatus status);
    boolean deleteCandidature(long id);
}
