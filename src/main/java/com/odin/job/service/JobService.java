package com.odin.job.service;

import com.odin.job.model.Job;
import com.odin.job.model.User;
import com.odin.job.projection.JobStatusCount;
import com.odin.job.projection.MonthlyJobCount;
import com.odin.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository repo;
    private final UserService userService;

    public Job createJob(Job job, String username) {
        User currentUser = userService.getCurrentUser(username);
        job.setCreatedBy(currentUser);
        return repo.save(job);
    }
    public Job updateJob(int id, Job updateJob, String username) {
        User currentUser = userService.getCurrentUser(username);
        Job existingJob = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Ensure the job belongs to the logged-in user
        if (existingJob.getCreatedBy().getId() != currentUser.getId()) {
            throw new RuntimeException("Not authorized to update this job");
        }

        // Update only non-null fields
        if (updateJob.getCompany() != null) existingJob.setCompany(updateJob.getCompany());
        if (updateJob.getPosition() != null) existingJob.setPosition(updateJob.getPosition());
        if (updateJob.getJobStatus() != null) existingJob.setJobStatus(updateJob.getJobStatus());
        if (updateJob.getJobType() != null) existingJob.setJobType(updateJob.getJobType());
        if (updateJob.getJobLocation() != null) existingJob.setJobLocation(updateJob.getJobLocation());

        return repo.save(existingJob);
    }


    public void deleteJob(int id, String username) {
        User currentUser = userService.getCurrentUser(username);
        Job job = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));


        if (job.getCreatedBy().getId() != currentUser.getId()) {
            throw new RuntimeException("Not authorized to update this job");
        }

        repo.deleteById(id);
    }

    public Job getJob(int id) throws Exception {
        return repo.findById(id)
                .orElseThrow(() -> new Exception("Job not found"));
    }

    public Page<Job> getAlljobs(
            String search,
            String jobStatus,
            String jobType,
            String sort,
            int page,
            int limit,
            String username
    ){
        User currentUser = userService.getCurrentUser(username);
        
        Specification<Job> spec = (root, query, cb) -> {
            // Always filter by the current user's ID
            query.where(cb.equal(root.get("createdBy").get("id"), currentUser.getId()));

            // Search by position or company
            if (search != null && !search.isEmpty()) {
                query.where(cb.or(
                    cb.like(cb.lower(root.get("position")), "%" + search.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("company")), "%" + search.toLowerCase() + "%")
                ));
            }

            // Filter by jobStatus
            if (jobStatus != null && !jobStatus.equalsIgnoreCase("all")) {
                query.where(cb.equal(root.get("jobStatus"), Job.JobStatus.valueOf(jobStatus.toUpperCase())));
            }

            // Filter by jobType
            if (jobType != null && !jobType.equalsIgnoreCase("all")) {
                query.where(cb.equal(root.get("jobType"), Job.JobType.valueOf(jobType.toUpperCase())));
            }

            return query.getRestriction();
        };

        // Sorting
        Sort sortBy = Sort.by("createdAt").descending(); // default
        if ("oldest".equalsIgnoreCase(sort)) {
            sortBy = Sort.by("createdAt").ascending();
        } else if ("a-z".equalsIgnoreCase(sort)) {
            sortBy = Sort.by("position").ascending();
        } else if ("z-a".equalsIgnoreCase(sort)) {
            sortBy = Sort.by("position").descending();
        }

        Pageable pageable = PageRequest.of(page - 1, limit, sortBy);
        return repo.findAll(spec, pageable);
    }

    public Map<String, Object> getJobStats(String username) {
        User currentUser = userService.getCurrentUser(username);

        // Get job status counts
        List<JobStatusCount> statusCounts = repo.countJobsByStatus(currentUser.getId());
        Map<String, Long> defaultStats = new HashMap<>();

        // Convert to map
        for (JobStatusCount sc : statusCounts) {
            defaultStats.put(sc.getStatus().toLowerCase(), sc.getCount());
        }

        // Add default values for missing statuses
        defaultStats.putIfAbsent("pending", 0L);
        defaultStats.putIfAbsent("interview", 0L);
        defaultStats.putIfAbsent("declined", 0L);

        // Get monthly applications
        List<MonthlyJobCount> monthlyCounts = repo.countJobsPerMonth(currentUser.getId());
        List<Map<String, Object>> monthlyApplications = monthlyCounts.stream()
                .limit(6)
                .map(mc -> {
                    YearMonth ym = YearMonth.of(mc.getYear(), mc.getMonth());
                    Map<String, Object> data = new HashMap<>();
                    data.put("date", ym.format(DateTimeFormatter.ofPattern("MMM yy")));
                    data.put("count", mc.getCount());
                    return data;
                })
                .collect(Collectors.toList());

        Collections.reverse(monthlyApplications);

        // Create final response
        Map<String, Object> response = new HashMap<>();
        response.put("defaultStats", defaultStats);
        response.put("monthlyApplications", monthlyApplications);
        return response;
    }

}
