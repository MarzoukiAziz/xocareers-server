package com.xoauto.xocareers.repository;

import com.xoauto.xocareers.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findResumesByCandidateId(long candidateId);

}
