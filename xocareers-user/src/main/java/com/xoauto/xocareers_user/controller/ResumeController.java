package com.xoauto.xocareers_user.controller;

import com.xoauto.xocareers_user.model.Resume;
import com.xoauto.xocareers_user.service.ResumeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("resume")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Resume>> getAllResumesByUser(@PathVariable int id) {
        List<Resume> resumes = resumeService.getAllResumesByUser(id);
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResume(@PathVariable int id) {
        Resume resume = resumeService.getResume(id);
        return ResponseEntity.ok(resume);
    }

    @PostMapping
    public ResponseEntity<Resume> createResume(@RequestBody Resume resume, @RequestParam int candidateId) {
        Resume createdResume = resumeService.saveResume(resume, candidateId);
        return new ResponseEntity<>(createdResume, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable int id) {
        try {
            resumeService.deleteResume(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
