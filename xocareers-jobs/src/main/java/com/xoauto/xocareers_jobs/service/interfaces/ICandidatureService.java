package com.xoauto.xocareers_jobs.service.interfaces;

import com.xoauto.xocareers_jobs.model.Candidature;
import com.xoauto.xocareers_jobs.model.CandidatureDetails;
import com.xoauto.xocareers_jobs.model.CandidatureJobDetails;
import com.xoauto.xocareers_jobs.model.TypeStatus;

import java.util.List;

public interface ICandidatureService {
    List<CandidatureJobDetails> getCandidatureJobDetailsByCandidate(long candidateId);
    List<CandidatureDetails> getCandidatureJobDetailsByJob(long jobId);
    CandidatureDetails getCandidatureById(long id);
    Candidature addCandidature( Candidature candidature);
    Candidature updateCandidatureStatus(long candidatureId, TypeStatus status);
    boolean deleteCandidature(long id);
}
