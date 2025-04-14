package com.odin.job.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "jobs")
@Data
@RequiredArgsConstructor
public class Job {


    public enum JobStatus{
        PENDING,
        INTERVIEW,
        DECLINED
    };
    public enum JobType{
        FULL_TIME,
        PART_TIME,
        INTERNSHIP
    };
//    public enum JobSortBy{
//        NEWEST_FIRST,
//        OLDEST_FIRST,
//        ASCENDING,
//        DESCENDING
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String position;

    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus = JobStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private JobType jobType = JobType.FULL_TIME;

    private String jobLocation = "Earth";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }



}
