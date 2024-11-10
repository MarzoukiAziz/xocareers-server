package com.xoauto.xocareers_jobs.feign;

import com.xoauto.xocareers_jobs.model.Resume;
import com.xoauto.xocareers_jobs.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("xocareers-user")
public interface CandidatureInterface {
    @GetMapping("user/admin/id")
    public ResponseEntity<User> getUserById(@RequestParam long id);

    @GetMapping("resume/{id}")
    public ResponseEntity<Resume> getResume(@PathVariable long id);
}
