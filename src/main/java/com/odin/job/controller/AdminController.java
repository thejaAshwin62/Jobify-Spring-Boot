package com.odin.job.controller;

import com.odin.job.model.User;
import com.odin.job.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService service;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
      return ResponseEntity.ok(service.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        service.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/app-stats")
    public ResponseEntity<Map<String,Long>>getApplicationStats(){
       return ResponseEntity.ok( service.getApplicationStats());
    }

}
