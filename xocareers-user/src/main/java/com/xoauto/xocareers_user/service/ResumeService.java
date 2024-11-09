package com.xoauto.xocareers_user.service;

import com.xoauto.xocareers_user.model.Resume;
import com.xoauto.xocareers_user.model.User;
import com.xoauto.xocareers_user.repository.ResumeRepository;
import com.xoauto.xocareers_user.service.interfaces.IResumeService;
import com.xoauto.xocareers_user.service.interfaces.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResumeService implements IResumeService {
    private final ResumeRepository resumeRepository;
    private final IUserService userService;

    public ResumeService(ResumeRepository resumeRepository, IUserService userService) {
        this.resumeRepository = resumeRepository;
        this.userService = userService;
    }

    @Override
    public List<Resume> getAllResumesByUser(long candidateId) {
        User candidate = userService.findUserById(candidateId);
        if(candidate == null){
            throw new EntityNotFoundException("User not found with id: "+candidateId);
        }
        return resumeRepository.findResumesByCandidateId(candidateId);
    }

    @Override
    public Resume getResume(long resumeId) {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() -> new EntityNotFoundException("Resume not found with id: " + resumeId));
    }

    @Transactional
    @Override
    public Resume saveResume(Resume resume, long candidateId) {
        User candidate = userService.findUserById(candidateId);
        if(candidate==null){
            throw new EntityNotFoundException("User not found with id: "+candidateId);
        }
        resume.setCandidate(candidate);
        return resumeRepository.save(resume);
    }

    @Transactional
    @Override
    public void deleteResume(long resumeId) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new EntityNotFoundException("Resume not found with id: " + resumeId);
        }
        resumeRepository.deleteById(resumeId);
    }
}
