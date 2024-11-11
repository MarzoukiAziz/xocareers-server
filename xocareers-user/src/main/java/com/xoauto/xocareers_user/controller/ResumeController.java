package com.xoauto.xocareers_user.controller;

import com.xoauto.xocareers_user.model.Resume;
import com.xoauto.xocareers_user.service.ResumeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("resume")
public class ResumeController {
    private final ResumeService resumeService;

    @Value("${file.upload-dir}")
    private String uploadDir;

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
    public ResponseEntity<Resume> createResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam String name,
            @RequestParam int candidateId) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Define the path to save the file inside the project directory
        String uploadDir = "uploads/resumes";  // Relative path to the uploads folder in your project directory
        Path projectDir = Paths.get(System.getProperty("user.dir"));
        Path filePath = projectDir.resolve(uploadDir).resolve(file.getOriginalFilename());

        // Create necessary directories if they do not exist
        Files.createDirectories(filePath.getParent());

        // Transfer the file to the specified location
        file.transferTo(filePath.toFile());

        // Create the Resume object and set the attributes
        Resume resume = new Resume();
        resume.setName(name);
        resume.setLink(filePath.toString()); // Store the relative path or absolute path as needed

        // Save the resume and associate it with the candidate
        Resume createdResume = resumeService.saveResume(resume, candidateId);

        // Return the created resume with a CREATED status
        return new ResponseEntity<>(createdResume, HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteResume(@PathVariable Long id) {
        try {
            // Fetch the resume to get the file path
            Resume resume = resumeService.getResume(id);
            if (resume == null) {
                throw new EntityNotFoundException("Resume not found");
            }

            // Delete the file associated with the resume
            Path filePath = Paths.get(resume.getLink());
            File file = filePath.toFile();
            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    return new ResponseEntity<>(Map.of("message", "Failed to delete the file"), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            // Delete the resume record from the database
            resumeService.deleteResume(id);

            // Return success response
            Map<String, String> response = new HashMap<>();
            response.put("message", "Deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
