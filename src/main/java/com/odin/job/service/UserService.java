package com.odin.job.service;

import com.odin.job.model.UpdateUser;
import com.odin.job.model.User;
import com.odin.job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User saveData(User user){

        if (repo.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists");
        }
        if (repo.findByName(user.getName()) != null) {
            throw new RuntimeException("User with this name already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }
        return repo.save(user);
    }

    public User getCurrentUser(String email){
        User user = repo.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    public User updateUser(int id, UpdateUser request){
        Optional<User> optionalUser = repo.findById(id);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();
        if(request.getName() != null) {
            // Check if new name is already taken by another user
            User existingUser = repo.findByName(request.getName());
            if (existingUser != null && existingUser.getId() != id) {
                throw new RuntimeException("Name is already taken");
            }
            user.setName(request.getName());
        }
        if(request.getLocation() != null) user.setLocation(request.getLocation());
        if(request.getPassword() != null) user.setPassword(encoder.encode(request.getPassword()));

        return repo.save(user);
    }
}
