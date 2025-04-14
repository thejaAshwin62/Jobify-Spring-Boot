package com.odin.job.projection;

public interface MonthlyJobCount {
    Integer getYear();
    Integer getMonth();
    Long getCount();
}
