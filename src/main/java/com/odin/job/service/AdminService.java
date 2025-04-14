package com.odin.job.service;

import com.odin.job.model.User;
import com.odin.job.repository.JobRepository;
import com.odin.job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {


    private final UserRepository userRepo;
    private final JobRepository jobRepo;

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public void deleteUser(Integer id){
        userRepo.deleteById(id);
    }

    public Map<String, Long> getApplicationStats(){
        long user = userRepo.count();
        long job = jobRepo.count();
        Map<String,Long> stats = new HashMap<>();
        stats.put("User",user);
        stats.put("Jobs",job);
        return stats;
    }


}
