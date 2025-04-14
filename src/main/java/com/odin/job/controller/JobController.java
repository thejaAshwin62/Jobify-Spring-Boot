package com.odin.job.controller;


import com.odin.job.model.Job;
import com.odin.job.model.JobResponseDTO;
import com.odin.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

@RequestMapping("/api/v1/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService service;

    @PostMapping
    public ResponseEntity<JobResponseDTO> createJob(@RequestBody Job job) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Job newJob = service.createJob(job, userName);
        return new ResponseEntity<>(JobResponseDTO.fromJob(newJob), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDTO> updateJob(@PathVariable int id, @RequestBody Job job) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try {
            Job updatedJob = service.updateJob(id, job, userName);
            return new ResponseEntity<>(JobResponseDTO.fromJob(updatedJob), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try {
            service.deleteJob(id, userName);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJob(@PathVariable int id) {
        try {
            Job job = service.getJob(id);
            return new ResponseEntity<>(JobResponseDTO.fromJob(job), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-jobs")
    public ResponseEntity<Page<JobResponseDTO>> getAllJobs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "all") String jobStatus,
            @RequestParam(required = false, defaultValue = "all") String jobType,
            @RequestParam(required = false, defaultValue = "newest") String sort,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        
        Page<Job> jobs = service.getAlljobs(search, jobStatus, jobType, sort, page, limit, userName);
        Page<JobResponseDTO> jobDtos = jobs.map(JobResponseDTO::fromJob);
        return ResponseEntity.ok(jobDtos);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String,Object>> getJobStats() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Map<String,Object> stats = service.getJobStats(userName);
        return ResponseEntity.ok(stats);
    }


}