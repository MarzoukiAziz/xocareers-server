package com.xoauto.xocareers_jobs.repository;

import com.xoauto.xocareers_jobs.model.Candidature;
import com.xoauto.xocareers_jobs.model.TypeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatureRepositoy extends JpaRepository<Candidature, Long> {
    List<Candidature> findAllByJobOfferId(long jobOfferId);
    List<Candidature> findAllByCandidateId(long candidateId);

    @Query("Select c from Candidature c where c.status=:status and c.candidateId=:candidatureId")
    List<Candidature> findAllByStatusAndCandidate(@Param("candidatureId") int candidatureId, @Param("status") TypeStatus status);

    @Query("Select c from Candidature c where c.status=:status and c.jobOffer.id=:jobOfferId")
    List<Candidature> findAllByStatusAndJobOffer(@Param("jobOfferId") int jobOfferId, @Param("status") TypeStatus status);

}
