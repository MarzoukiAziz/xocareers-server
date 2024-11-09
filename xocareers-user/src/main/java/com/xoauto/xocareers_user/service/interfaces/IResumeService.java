package com.xoauto.xocareers_user.service.interfaces;


import com.xoauto.xocareers_user.model.Resume;

import java.util.List;

public interface IResumeService {
    List<Resume> getAllResumesByUser(long userId);
    Resume getResume(long resumeId);
    Resume saveResume(Resume resume,long candidateId);
    void deleteResume(long resumeId);
}
