package com.odin.job.model;

import lombok.Data;
import java.util.Date;


@Data
public class JobResponseDTO {
    private int id;
    private String company;
    private String position;
    private Job.JobStatus jobStatus;
    private Job.JobType jobType;
    private String jobLocation;
    private int createdBy;
    private Date createdAt;

    public static JobResponseDTO fromJob(Job job) {
        JobResponseDTO dto = new JobResponseDTO();
        dto.setId(job.getId());
        dto.setCompany(job.getCompany());
        dto.setPosition(job.getPosition());
        dto.setJobStatus(job.getJobStatus());
        dto.setJobType(job.getJobType());
        dto.setJobLocation(job.getJobLocation());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setCreatedBy(job.getCreatedBy().getId());
        return dto;
    }
}