package com.odin.job.repository;

import com.odin.job.model.Job;
import com.odin.job.projection.JobStatusCount;
import com.odin.job.projection.MonthlyJobCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer>, JpaSpecificationExecutor<Job> {

    // Count by job status
    @Query("SELECT j.jobStatus AS status, COUNT(j) AS count " +
            "FROM Job j WHERE j.createdBy.id = :userId " +
            "GROUP BY j.jobStatus")
    List<JobStatusCount> countJobsByStatus(@Param("userId") Integer userId);

    @Query("SELECT YEAR(j.createdAt) AS year, " +
            "MONTH(j.createdAt) AS month, " +
            "COUNT(j) AS count " +
            "FROM Job j WHERE j.createdBy.id = :userId " +
            "GROUP BY YEAR(j.createdAt), MONTH(j.createdAt) " +
            "ORDER BY YEAR(j.createdAt) DESC, MONTH(j.createdAt) DESC")
    List<MonthlyJobCount> countJobsPerMonth(@Param("userId") Integer userId);

}
