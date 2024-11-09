package com.xoauto.xocareers_user.repository;

import com.xoauto.xocareers_user.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findResumesByCandidateId(long candidateId);

}
