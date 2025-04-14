package com.odin.job.model;

import lombok.Data;

@Data
public class UpdateUser {
    private String name;
    private String email;
    private String password;
    private String location;
}
