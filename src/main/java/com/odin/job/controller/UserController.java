package com.odin.job.controller;

import com.odin.job.model.UserDTO;
import com.odin.job.model.User;
import com.odin.job.model.UpdateUser;
import com.odin.job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("current-user")
    public ResponseEntity<UserDTO> getCurrentUser(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = service.getCurrentUser(userName);
            return ResponseEntity.ok(UserDTO.fromUser(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UpdateUser updateUser) {
        try {
            User updatedUser = service.updateUser(id, updateUser);
            return ResponseEntity.ok(UserDTO.fromUser(updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
