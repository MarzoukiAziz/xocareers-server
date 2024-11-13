package com.xoauto.xocareers.service.interfaces;

import com.xoauto.xocareers.model.Candidature;
import com.xoauto.xocareers.model.CandidatureDetails;
import com.xoauto.xocareers.model.CandidatureJobDetails;
import com.xoauto.xocareers.model.TypeStatus;

import java.util.List;

public interface ICandidatureService {
    List<CandidatureJobDetails> getCandidatureJobDetailsByCandidate(long candidateId);
    List<CandidatureDetails> getCandidatureJobDetailsByJob(long jobId);
    CandidatureDetails getCandidatureById(long id);
    Candidature addCandidature( Candidature candidature);
    Candidature updateCandidatureStatus(long candidatureId, TypeStatus status);
    boolean deleteCandidature(long id);
}
